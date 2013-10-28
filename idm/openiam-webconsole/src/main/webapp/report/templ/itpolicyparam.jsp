<%@ page language="java" contentType="text/html; charset=ISO-8859-1"     pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 

<script type="text/javascript">
<!--

function clearFields() {
	document.getElementById ('usrId').value = "";
	document.getElementById ('principal').value = "";
}

function showSupervisorDialog() {
	var ua = window.navigator.userAgent;
    var msie = ua.indexOf ( "MSIE " );

    if ( msie > 0 ) {
		dialogReturnValue = window.showModalDialog("priv/dialoguseragency.jsp",null,"dialogWidth:670px;dialogHeight:600px;");
		document.getElementById ('usrId').value = dialogReturnValue.id;
		document.getElementById ('principal').value = dialogReturnValue.name;

    }else {
    	dialogReturnValue = window.showModalDialog("priv/seluseragency.jsp",null,"dialogWidth:670px;dialogHeight:600px;");
    	document.getElementById ('usrId').value = dialogReturnValue.id;
    	document.getElementById ('pricipal').value = dialogReturnValue.name;
    }
    document.getElementById('department').value = "" ;
}

//-->
</script>

<table  width="1000pt">
    <tr>
        <td>
            <table width="100%">
                <tr>
                    <td class="pageTitle" width="70%">
                        <h2 class="contentheading">IT Policy Report </h2>
                    </td>
                    <td class="tabnav" >
                    </td>
                    <td></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>

<form:form commandName="itPolicyReportCmd" target="_blank">

<table width="100%">

          <tr>
			  <td class="tdlightnormal" colspan="2">Please select a single user (to see all policy clicks) or a Dept (to see those who have completed the IT Policy)</td>

          </tr>		  
          <tr>
			  <td class="tddark">Name (Last, First):</td>
              <td class="tdlightnormal"><form:hidden path="usrId" /> <form:input path="principal" size="30" maxlength="50" readonly="true" /><br>
              <a href="javascript:showSupervisorDialog();"><i>Select User</i></a>
              </td>
          </tr>
       <tr>
    	  <td colspan="2" bgcolor="8397B4" align="center"><b>OR</b></td>
    	</tr>
          <tr>
			  <td class="tddark">Department:</td>
              <td class="tdlightnormal">
              	<form:select path="department" multiple="false" onchange="clearFields();">
              		<form:option value="" label="-Please Select-"/>
              		<form:options items="${deptList}" itemValue="orgId" itemLabel="organizationName"/>
         		 </form:select>
              
              
              </td>
          </tr>
    <tr>
        <td class="tddark">Date Range (MM/dd/yyyy):</td>
        <td class="tdlightnormal">
            <form:input path="startDate" size="20" maxlength="20" /> - <form:input path="startDate" size="20" maxlength="20" />


        </td>
    </tr>


         <tr>
    	  <td colspan="2">&nbsp;</td>
    	</tr>
    	<tr>
 		   <td colspan="2" align="center" bgcolor="8397B4" >
 		    <font></font>
 		   </td>
    	</tr>
          <tr>
              <td colspan="2" align="right">
 <input type="submit" name="btn" value="Submit"> <input type="reset"> </td>
          </tr>
</table>
</form:form>

</td>
    </tr>
    </table>