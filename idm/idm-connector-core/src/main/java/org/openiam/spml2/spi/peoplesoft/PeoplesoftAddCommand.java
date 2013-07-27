package org.openiam.spml2.spi.peoplesoft;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.idm.srvc.mngsys.dto.AttributeMap;
import org.openiam.idm.srvc.mngsys.dto.ManagedSys;
import org.openiam.idm.srvc.res.dto.Resource;
import org.openiam.provision.type.ExtensibleAttribute;
import org.openiam.provision.type.ExtensibleObject;
import org.openiam.spml2.msg.AddRequestType;
import org.openiam.spml2.msg.AddResponseType;
import org.openiam.spml2.msg.ErrorCode;
import org.openiam.spml2.msg.StatusCodeType;
import org.openiam.spml2.spi.common.AddCommand;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


/**
 * Add command for the peoplesoft connector
 * User: Suneet
 */
public class PeoplesoftAddCommand extends  AbstractPeoplesoftCommand implements AddCommand {


    @Override
    public AddResponseType add(AddRequestType reqType) {
        final AddResponseType response = new AddResponseType();
        response.setStatus(StatusCodeType.SUCCESS);

        String displayName = null;
        String role = null;
        String email = null ;
        String employeeId = null ;
        String symbolicID;
        String password = null;

        schemaName =  res.getString("SCHEMA");


        final String targetID = reqType.getTargetID();
        final ManagedSys managedSys = managedSysService.getManagedSys(targetID);
        if(managedSys == null) {
            populateResponse(response, StatusCodeType.FAILURE, ErrorCode.INVALID_CONFIGURATION, String.format("No Managed System with target id: %s", targetID));
            return response;
        }

        if (StringUtils.isBlank(managedSys.getResourceId())) {
            populateResponse(response, StatusCodeType.FAILURE, ErrorCode.INVALID_CONFIGURATION, "ResourceID is not defined in the ManagedSys Object");
            return response;
        }

        final Resource res = resourceDataService.getResource(managedSys.getResourceId());
        if(res == null) {
            populateResponse(response, StatusCodeType.FAILURE, ErrorCode.INVALID_CONFIGURATION, "No resource for managed resource found");
            return response;
        }

        final String principalName = reqType.getPsoID().getID();
        if(principalName == null) {
            populateResponse(response, StatusCodeType.FAILURE, ErrorCode.INVALID_CONFIGURATION, "No principal sent");
            return response;
        }

        final List<ExtensibleObject> objectList = reqType.getData().getAny();

        if(log.isDebugEnabled()) {
            log.debug(String.format("ExtensibleObject in Add Request=%s", objectList));
        }

        final List<AttributeMap> attributeMap = attributeMaps(res);

        // get the attributes that are needed for this operation
        for (final ExtensibleObject obj : objectList) {
            final List<ExtensibleAttribute> attrList = obj.getAttributes();

            if(log.isDebugEnabled()) {
                log.debug(String.format("Number of attributes to persist in ADD = %s", attrList.size()));
            }

            if(CollectionUtils.isNotEmpty(attributeMap)) {
                for (final ExtensibleAttribute att : attrList) {
                    //for(final AttributeMap attribute : attributeMap) {

                        if(StringUtils.equalsIgnoreCase("displayName",  att.getName())) {
                            displayName = att.getValue();
                        }
                        if(StringUtils.equalsIgnoreCase("role",  att.getName())) {
                            role = att.getValue();
                        }
                        if(StringUtils.equalsIgnoreCase("email",  att.getName())) {
                            email = att.getValue();
                        }
                        if(StringUtils.equalsIgnoreCase("employeeId",  att.getName())) {
                            employeeId = att.getValue();
                        }
                        if(StringUtils.equalsIgnoreCase("password",  att.getName())) {
                            password = att.getValue();
                        }

                    //}
                }
            }
        }




        Connection con = null;
        try {
            con = connectionMgr.connect(managedSys);

            symbolicID = getSymbolicID(con);
            if (symbolicID == null) {
                if(log.isDebugEnabled()) {
                    log.debug(String.format("SymbolicID not found"));
                }
                return response;
            }

            // get the required attributes

            // check if the identity exists
            // if it does then ignore the record - check the role membership
            // if it does not then create the record - add the users role membership

            if (!identityExists(con, principalName)) {
                insertUser(con, principalName, displayName, employeeId, email, symbolicID, password);
            }
            // check if this user already has a role membership
            if (!StringUtils.isBlank(role)) {
                if (!roleExists(con, principalName,role)) {
                   addToRole(con, principalName, role);
                }
            }

            if (!emailExists(con, principalName)) {
                insertEmail(con,principalName,email);

            }

            if (!roleExlatoprExists(con,principalName)) {
                insertRoleExlatopr(con,principalName,displayName,email,employeeId);
            }


            if (!userAttrExists(con, principalName)) {
                insertUserAttribute(con, principalName);

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
                    log.error(s.toString());
                    populateResponse(response, StatusCodeType.FAILURE, ErrorCode.SQL_ERROR, s.toString());
                }
            }
        }


        return response;
    }
}
