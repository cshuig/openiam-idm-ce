package org.openiam.connector.ldap.command.user;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.base.AttributeOperationEnum;
import org.openiam.base.BaseAttribute;
import org.openiam.base.BaseAttributeContainer;
import org.openiam.connector.type.ConnectorDataException;
import org.openiam.connector.type.ObjectValue;
import org.openiam.connector.type.constant.ErrorCode;
import org.openiam.connector.type.request.LookupRequest;
import org.openiam.connector.type.response.SearchResponse;
import org.openiam.idm.srvc.mngsys.domain.AttributeMapEntity;
import org.openiam.idm.srvc.mngsys.domain.ManagedSysEntity;
import org.openiam.idm.srvc.mngsys.dto.ManagedSystemObjectMatch;
import org.openiam.idm.srvc.res.dto.ResourceProp;
import org.openiam.provision.type.ExtensibleAttribute;
import org.openiam.provision.type.ExtensibleObject;
import org.openiam.connector.ldap.command.base.AbstractLookupLdapCommand;
import org.openiam.provision.type.ExtensibleUser;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("lookupUserLdapCommand")
public class LookupUserLdapCommand extends AbstractLookupLdapCommand<ExtensibleUser> {
    @Override
    protected boolean lookup(ManagedSysEntity managedSys, LookupRequest<ExtensibleUser> lookupRequest, SearchResponse respType, LdapContext ldapctx) throws ConnectorDataException {
        boolean found = false;
        ManagedSystemObjectMatch matchObj = getMatchObject(lookupRequest.getTargetID(), ManagedSystemObjectMatch.USER);
        String resourceId = managedSys.getResourceId();

        String identity = lookupRequest.getSearchValue();
        try {
            //Check identity on DN format or not
            String identityPatternStr = MessageFormat.format(DN_IDENTITY_MATCH_REGEXP, matchObj.getKeyField());
            Pattern pattern = Pattern.compile(identityPatternStr);
            Matcher matcher = pattern.matcher(identity);
            String objectBaseDN;
            if (matcher.matches()) {
                identity = matcher.group(1);
                String CN = matchObj.getKeyField() + "=" + identity;
                objectBaseDN = lookupRequest.getSearchValue().substring(CN.length() + 1);
            } else {
                objectBaseDN = matchObj.getBaseDn();
                Set<ResourceProp> rpSet = getResourceAttributes(managedSys.getResourceId());
                boolean isLookupUserInOu = getResourceBoolean(rpSet, "LOOKUP_USER_IN_OU", true);
                if (isLookupUserInOu) {
                    // if identity is not in DN format try to find OU info in attributes
                    String OU = getAttrValue(lookupRequest.getExtensibleObject(), OU_ATTRIBUTE);
                    if (StringUtils.isNotEmpty(OU)) {
                        objectBaseDN = OU + "," + matchObj.getBaseDn();
                    }
                }
            }

            log.debug("looking up identity: " + identity);

            List<String> attrList = new ArrayList<String>();
            ExtensibleObject object = lookupRequest.getExtensibleObject();
            List<ExtensibleAttribute> listAttrs = (object != null) ? object.getAttributes() : new ArrayList<ExtensibleAttribute>();
            if (CollectionUtils.isNotEmpty(listAttrs)) {
                for (ExtensibleAttribute ea : listAttrs) {
                    attrList.add(ea.getName());
                }
            } else {
                log.debug("Resource id = " + resourceId);
                List<AttributeMapEntity> attrMap = managedSysService.getResourceAttributeMaps(resourceId);
                if (attrMap != null) {
                    attrList = getAttributeNameList(attrMap);
                }
            }

            if (CollectionUtils.isNotEmpty(attrList)) {

                String[] attrAry = new String[attrList.size()];
                attrList.toArray(attrAry);
                log.debug("Attribute array=" + attrAry);

                NamingEnumeration results = null;
                try {
                    results = lookupSearch(managedSys, matchObj, ldapctx, identity, attrAry, objectBaseDN);
                } catch (NameNotFoundException nnfe) {
                    log.debug("results=NULL");
                    log.debug(" results has more elements=0");
                    return false;
                }

                while (results != null && results.hasMoreElements()) {
                    SearchResult sr = (SearchResult) results.next();
                    Attributes attrs = sr.getAttributes();
                    if (attrs != null) {

                        ObjectValue userValue = new ObjectValue();
                        userValue.setObjectIdentity(identity);

                        found = true;

                        try {
                            ExtensibleAttribute extAttr = new ExtensibleAttribute();
                            extAttr.setName("dn");
                            String dnValue = sr.getNameInNamespace();
                            extAttr.setValue(dnValue);
                            userValue.getAttributeList().add(extAttr);
                        } catch (UnsupportedOperationException e) {
                            log.error(e.getMessage(), e);
                        }

                        for (NamingEnumeration ae = attrs.getAll(); ae.hasMore(); ) {
                            ExtensibleAttribute extAttr = new ExtensibleAttribute();
                            Attribute attr = (Attribute) ae.next();

                            boolean addToList = false;

                            extAttr.setName(attr.getID());

                            NamingEnumeration e = attr.getAll();
                            boolean isMultivalued = (attr.size() > 1);
                            while (e.hasMore()) {
                                Object o = e.next();
                                if (o instanceof String || o instanceof byte[]) {
                                    if (isMultivalued) {
                                        BaseAttributeContainer container = extAttr.getAttributeContainer();
                                        if (container == null) {
                                            container = new BaseAttributeContainer();
                                            extAttr.setAttributeContainer(container);
                                        }
                                        if (o instanceof String) {
                                            container.getAttributeList().add(
                                                    new BaseAttribute(attr.getID(), o.toString().replaceAll(patternForCTRLCHAR, ""), AttributeOperationEnum.NO_CHANGE));
                                        } else if (o instanceof byte[]) {
                                            //TODO: Add multivalued attribute support
                                            extAttr.setValueAsByteArray((byte[]) o);
                                            extAttr.setMultivalued(false);
                                        }
                                    } else {
                                        if (o instanceof String) {
                                            extAttr.setValue(o.toString().replaceAll(patternForCTRLCHAR, ""));
                                        } else if (o instanceof byte[]) {
                                            extAttr.setValueAsByteArray((byte[]) o);
                                        }
                                    }
                                    addToList = true;
                                }
                            }
                            if (addToList) {
                                userValue.getAttributeList().add(extAttr);
                            }
                        }
                        respType.getObjectList().add(userValue);
                    }
                }
            }
        } catch (NamingException e) {
            log.error(e.getMessage(), e);
            throw new ConnectorDataException(ErrorCode.DIRECTORY_ERROR, e.getMessage());
        }
        return found;
    }
}
