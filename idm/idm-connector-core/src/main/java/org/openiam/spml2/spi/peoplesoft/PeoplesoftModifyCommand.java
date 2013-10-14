package org.openiam.spml2.spi.peoplesoft;

import org.apache.commons.lang.StringUtils;
import org.openiam.base.BaseAttribute;
import org.openiam.idm.srvc.mngsys.dto.ManagedSys;
import org.openiam.provision.type.ExtensibleAttribute;
import org.openiam.provision.type.ExtensibleObject;
import org.openiam.spml2.msg.*;
import org.openiam.spml2.spi.common.ModifyCommand;

import java.sql.Connection;
import java.sql.SQLException;
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

        String displayName = null;
        String role = null;
        String email = null ;
        String employeeId = null ;
        String symbolicID;
        String password = null;
        String status = null;

        final PSOIdentifierType psoID = reqType.getPsoID();
        String principalName = psoID.getID();
        final String targetID = psoID.getTargetID();
        final ManagedSys managedSys = managedSysService.getManagedSys(targetID);


        if(managedSys == null) {
            populateResponse(response, StatusCodeType.FAILURE, ErrorCode.INVALID_CONFIGURATION, String.format("No Managed System with target id: %s", targetID));
            return response;
        }

        if (principalName != null) {
            principalName = principalName.toUpperCase();
        }

        final List<ModificationType> modTypeList = reqType.getModification();


            try {
                con = connectionMgr.connect(managedSys);

                for (ModificationType mod : modTypeList) {
                    final ExtensibleType extType = mod.getData();
                    final List<ExtensibleObject> extobjectList = extType.getAny();

                    int ctr = 0;
                    for (final ExtensibleObject obj : extobjectList) {

                        final List<ExtensibleAttribute> attrList = obj.getAttributes();

                        for (ExtensibleAttribute att : attrList) {
                            if (att.getOperation() != 0 && att.getName() != null) {
                                if (att.getObjectType().equalsIgnoreCase("USER")) {

                                    System.out.println("Attribute: " + att.getName() + " -> " + att.getValue());
                                    if(StringUtils.equalsIgnoreCase("displayName", att.getName())) {
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
                                    if(StringUtils.equalsIgnoreCase("status",  att.getName())) {
                                        status = att.getValue();
                                        if (status == null) {
                                            status = "0";

                                        }
                                    }




                                }
                            }
                        }

                    }
                }

                int version = (getVersion(con) + 1);

                if (identityExists(con, principalName)) {
                    updateUser(con, principalName, displayName, email, Integer.valueOf(status));
                }

                if (!StringUtils.isBlank(role)) {
                    if (!roleExists(con, principalName,role)) {
                        addToRole(con, principalName, role);
                    }
                }

                if (!StringUtils.isBlank(email)) {
                    if (!emailExists(con, principalName)) {
                        insertEmail(con,principalName,email);

                    }else {

                        updateEmail(con, principalName, email);

                    }
                }

                if (!roleExlatoprExists(con,principalName)) {
                    insertRoleExlatopr(con,principalName,displayName,email,employeeId);
                }


                if (!userAttrExists(con, principalName)) {
                    insertUserAttribute(con, principalName);

                }

                if (!pspruhdefnExists(con, principalName)) {


                    insertPSPRUHDEFN(con, principalName, version);

                }

                if (!pspruhtabExists(con, principalName)) {
                    insertPSPRUHTAB(con, principalName);

                }

                if (!psruhtabpgltExists(con, principalName)) {
                    insertPSPRUHTABPGLT(con, principalName);

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

