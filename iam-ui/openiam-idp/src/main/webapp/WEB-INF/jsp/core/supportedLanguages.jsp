<%
    response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.openiam.com/tags/openiam" prefix="openiam"%>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<c:if test="${not empty supportedLanguageList}">
    <div id="lang-header">
          <ul id="lang">
                <li>
                	<c:forEach var="ln" items="${supportedLanguageList}">
                            <c:if test="${selectedLanguage.id==ln.id}">
                                  <a class="first" onclick="javascript:return false;"
                                        href="javascript:void(0);"
                                  ><span style="${openiam:getFlagStyle(selectedCountry)}">&nbsp;</span>${ln.name}</a>
                            </c:if>
                      </c:forEach>

                      <ul>
                            <c:forEach var="ln" items="${supportedLanguageList}">
                                  <c:forEach var="loc" items="${ln.locales}">
                                        <li><a href="javascript:void(0);"
                                              onclick="OPENIAM.Language.switchLanguage('${localeParam}', '${loc.key}');"
                                        ><span style="${openiam:getFlagStyle(loc.key)}">&nbsp;</span>${ln.name}</a></li>
                                  </c:forEach>
                            </c:forEach>
                      </ul>
				</li>
          </ul>
    </div>
</c:if>