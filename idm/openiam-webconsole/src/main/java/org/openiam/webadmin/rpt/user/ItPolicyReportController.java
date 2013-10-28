package org.openiam.webadmin.rpt.user;

/*
 * Copyright 2009, OpenIAM LLC 
 * This file is part of the OpenIAM Identity and Access Management Suite
 *
 *   OpenIAM Identity and Access Management Suite is free software: 
 *   you can redistribute it and/or modify
 *   it under the terms of the Lesser GNU General Public License 
 *   version 3 as published by the Free Software Foundation.
 *
 *   OpenIAM is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   Lesser GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with OpenIAM.  If not, see <http://www.gnu.org/licenses/>. *
 */


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.idm.srvc.auth.ws.LoginDataWebService;
import org.openiam.idm.srvc.org.dto.Organization;
import org.openiam.idm.srvc.org.service.OrganizationDataService;
import org.openiam.idm.srvc.rpt.qryobject.dto.UserLoginStatusReport;
import org.openiam.idm.srvc.user.ws.UserDataWebService;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;


public class ItPolicyReportController extends SimpleFormController {

    ResourceBundle res = ResourceBundle.getBundle("datasource");

    private static final Log log = LogFactory.getLog(ItPolicyReportController.class);
    OrganizationDataService orgManager;

    protected LoginDataWebService loginManager;
    UserDataWebService userManager = null;


    @Override
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) throws Exception {

        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("MM/dd/yyyy"), true));
    }

    public ItPolicyReportController() {
        super();
    }


    @Override
    protected Map referenceData(HttpServletRequest request) throws Exception {

        System.out.println("referenceData called.");
        request.getSession().removeAttribute("userList");

        List<Organization> deptList = orgManager.allDepartments(null);

        Map model = new HashMap();
        model.put("deptList", deptList);

        return model;
    }


    @Override
    protected ModelAndView onSubmit(HttpServletRequest request,
                                    HttpServletResponse response, Object command, BindException errors)
            throws Exception {

        request.getSession().removeAttribute("result");

        boolean bStartDate = false;
        boolean bEndDate = false;
        boolean bDept = false;

        ItPolicyReportCommand cmd = (ItPolicyReportCommand) command;
        List<Object> resultList = null;
        List<UserLoginStatusReport> statusList = null;

        String dept = cmd.getDepartment();
        Date startDate = cmd.getStartDate();
        Date endDate = cmd.getEndDate();


        //List<User> userList = userManager.search(search).getUserList();

        //	String sql = " SELECT DISTINCT u.USER_ID, u.FIRST_NAME, u.LAST_NAME,u.NICKNAME, u.MIDDLE_INIT, u.AREA_CD, u.EMAIL_ADDRESS, u.DEPT_CD, u.DIVISION, l.TIME_STAMP, lg.LOGIN, lg.LAST_LOGIN, u.EMPLOYEE_TYPE, u.PHONE_NBR " +
        //			"  FROM USERS u LEFT JOIN LAST_IT_POLICY_ACCEPT l ON (u.USER_ID = l.USER_ID)          JOIN LOGIN lg ON (u.USER_ID  = lg.USER_ID) " +
        //			"  WHERE l.TIME_STAMP is null and  u.STATUS = 'ACTIVE' and lg.MANAGED_SYS_ID = '0' " ;


        /*if (cmd.getDepartment() != null  && cmd.getDepartment().length() > 0) {
              String sql = " SELECT DISTINCT u.USER_ID, u.COST_CENTER as UNIT, u.FIRST_NAME, u.LAST_NAME,u.NICKNAME, u.MIDDLE_INIT,sd.FIRST_NAME AS SUPER_FIRST_NAME, " +
                      " sd.LAST_NAME AS SUPER_LAST_NAME,  u.AREA_CD, u.TITLE, u.EMAIL_ADDRESS, u.DEPT_CD, dv.COMPANY_NAME AS DIVISION, c.COMPANY_NAME, l.TIME_STAMP, ' ' as RESPONSE,  " +
                      " lg.LOGIN, lg.LAST_LOGIN, u.EMPLOYEE_TYPE, u.PHONE_NBR "  +
               "  FROM USERS u LEFT JOIN LAST_IT_POLICY_ACCEPT l ON (u.USER_ID = l.USER_ID) " +
               "	    LEFT JOIN COMPANY c ON (u.COMPANY_ID = c.COMPANY_ID)    " +
               "      LEFT JOIN COMPANY dv ON (u.DIVISION = dv.COMPANY_ID)    " +
               "      LEFT JOIN ORG_STRUCTURE os on (os.STAFF_ID = u.USER_ID) " +
               "      LEFT JOIN SUPERVISOR_DETAIL sd on (os.SUPERVISOR_ID =  sd.SUPERVISOR_ID ) " +
               "      JOIN LOGIN lg ON (u.USER_ID  = lg.USER_ID) " +
               "  WHERE  u.STATUS = 'ACTIVE' and lg.MANAGED_SYS_ID = '0' " ;

              sql = sql + " and l.TIME_STAMP is null and u.DEPT_CD = '" + cmd.getDepartment() + "' ORDER BY u.LAST_NAME, u.FIRST_NAME";
              Query qry = new Query();
              qry.setSql(sql );

              qry.setObjectClass("org.openiam.idm.srvc.rpt.qryobject.dto.UserLoginStatusReport");
              resultList =  qryService.executeQuery(qry).getResultList();
              //statusList = new ArrayList(resultList);

              if (resultList != null) {
                  statusList = new ArrayList(resultList);
                  System.out.println("agency  - returning statuslist " + statusList.size());
                  request.getSession().removeAttribute("result");
                  request.getSession().setAttribute("result",statusList);
              }
              return new ModelAndView(new RedirectView("report/userloginreport.jsp", true));

          }else {
              */
        statusList = new ArrayList<UserLoginStatusReport>();

        int paramCtr = 0;
        int deptCtr = 0;
        int startDateCtr= 0;
        int endDateCtr = 0;

        StringBuilder where = new StringBuilder();

        StringBuilder sql = new StringBuilder("SELECT DISTINCT userlog.USER_ID, userlog.TIME_STAMP, userlog.RESPONSE,u.FIRST_NAME, u.LAST_NAME,u.NICKNAME, u.MIDDLE_INIT, u.TITLE, u.EMAIL_ADDRESS, c.COMPANY_NAME AS DEPT_NAME " +
                " FROM ORG_POLICY_USER_LOG userlog  JOIN USERS u ON (u.USER_ID = userlog.USER_ID)  " +
                "	    LEFT JOIN COMPANY c ON (u.DEPT_CD = c.COMPANY_ID) ");



        if (dept != null && dept.length() > 0) {
            where.append(" u.DEPT_CD = ?");
            deptCtr = (++paramCtr);
        }

        if (startDate != null) {
            if (where.length() > 0) {
                where.append(" and ");
            }
            where.append(" TIME_STAMP >= ? ");
            startDateCtr = (++paramCtr);
        }
        if (endDate != null) {
            if (where.length() > 0) {
                where.append(" and ");
            }
            where.append(" TIME_STAMP <= ? " );
            endDateCtr = (++paramCtr);
        }

        if (where.length() > 0) {
            sql.append(" WHERE ");
            sql.append(where);


        }
        sql.append(" ORDER BY TIME_STAMP");


        Connection conn = null;
        try {

            System.out.println("sql = " + sql);

            Class.forName(res.getString("openiam.driver_classname"));
            String url = res.getString("openiam.driver_url");

            System.out.println("url=" + url);


            conn = DriverManager.getConnection(url, res.getString("openiam.username"), res.getString("openiam.password"));

            PreparedStatement stmt = conn.prepareStatement(sql.toString());

            if (dept != null && dept.length() > 0) {
               stmt.setString(deptCtr, dept);

            }
            if (startDate != null) {
                stmt.setDate(startDateCtr, new java.sql.Date( startDate.getTime() ));
            }
            if (endDate != null) {
                stmt.setDate(endDateCtr, new java.sql.Date( endDate.getTime() ));
            }

            ResultSet rs = stmt.executeQuery();
            int ctr = 0;
            while (rs.next()) {
                org.openiam.idm.srvc.rpt.qryobject.dto.UserLoginStatusReport u = new org.openiam.idm.srvc.rpt.qryobject.dto.UserLoginStatusReport();

                u.setUserId(rs.getString("USER_ID"));
                u.setTimeStamp(rs.getDate("TIME_STAMP"));
                u.setResponse(rs.getString("RESPONSE"));
                u.setFirstName(rs.getString("FIRST_NAME"));
                u.setLastName(rs.getString("LAST_NAME"));
                u.setNickName(rs.getString("NICKNAME"));
                u.setMiddleInit(rs.getString("MIDDLE_INIT"));
                u.setTitle(rs.getString("TITLE"));
                u.setEmailAddress(rs.getString("EMAIL_ADDRESS"));
                u.setDeptCd(rs.getString("DEPT_NAME"));

                statusList.add(u);


            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (statusList != null) {

            request.getSession().setAttribute("result", statusList);
        }
        return new ModelAndView(getSuccessView());

    }


    public OrganizationDataService getOrgManager() {
        return orgManager;
    }

    public void setOrgManager(OrganizationDataService orgManager) {
        this.orgManager = orgManager;
    }


    public LoginDataWebService getLoginManager() {
        return loginManager;
    }

    public void setLoginManager(LoginDataWebService loginManager) {
        this.loginManager = loginManager;
    }

    public UserDataWebService getUserManager() {
        return userManager;
    }

    public void setUserManager(UserDataWebService userManager) {
        this.userManager = userManager;
    }


}
