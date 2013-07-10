package org.openiam.spml2.spi.peoplesoft;

import org.openiam.base.BaseAttribute;
import org.openiam.idm.srvc.mngsys.dto.ManagedSys;
import org.openiam.provision.type.ExtensibleAttribute;
import org.openiam.spml2.msg.*;
import org.openiam.spml2.spi.common.ModifyCommand;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 */
public class PeoplesoftModifyCommand extends  AbstractPeoplesoftCommand implements ModifyCommand {



    @Override
    public ModifyResponseType modify(ModifyRequestType reqType) {
        ModifyResponseType response = new ModifyResponseType();
        Connection con = null;
        List<BaseAttribute> targetMembershipList = new ArrayList<BaseAttribute>();

        final PSOIdentifierType psoID = reqType.getPsoID();
        final String principalName = psoID.getID();
        final String targetID = psoID.getTargetID();
        final ManagedSys managedSys = managedSysService.getManagedSys(targetID);


        if(managedSys == null) {
            populateResponse(response, StatusCodeType.FAILURE, ErrorCode.INVALID_CONFIGURATION, String.format("No Managed System with target id: %s", targetID));
            return response;
        }
        final List<ModificationType> modTypeList = reqType.getModification();

/*        try {
            con = connectionMgr.connect(managedSys);

            for (ModificationType mod : modTypeList) {
                final ExtensibleType extType = mod.getData();
                final List<ExtensibleObject> extobjectList = extType.getAny();

                for (ExtensibleObject obj : extobjectList) {


                    List<ExtensibleAttribute> attrList = obj.getAttributes();
                    for (ExtensibleAttribute att : attrList) {
                        if (att.getDataType().equalsIgnoreCase("memberOf")) {
                                buildMembershipList(att, targetMembershipList);
                        }
                    }
                }

           }



        } catch (SQLException se) {
            log.error(se);
            populateResponse(response, StatusCodeType.FAILURE, ErrorCode.SQL_ERROR, se.toString());

        } catch (ClassNotFoundException cnfe) {
            log.error(cnfe);
            populateResponse(response, StatusCodeType.FAILURE, ErrorCode.INVALID_CONFIGURATION, cnfe.toString());

        } catch(Throwable e) {
            log.error(e);
            populateResponse(response, StatusCodeType.FAILURE, ErrorCode.OTHER_ERROR, e.toString());
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException s) {
                    log.error(s);
                    populateResponse(response, StatusCodeType.FAILURE, ErrorCode.SQL_ERROR, s.toString());
                }
            }
        }
        */

        response.setStatus(StatusCodeType.SUCCESS);
        return response;
    }



    /**
     * List of Oracle roles that we want to grant access to
     * @param att
     * @param targetMembershipList
     */
    protected void buildMembershipList( ExtensibleAttribute att ,List<BaseAttribute>targetMembershipList) {


        if (att == null)
            return;

        List<String> membershipList =  att.getValueList();
        if (membershipList != null) {
            for ( String s : membershipList) {
                targetMembershipList.add(new BaseAttribute(s,s));
            }

        }

    }




    }

