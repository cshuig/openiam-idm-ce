package org.openiam.ui.web.util;

import org.apache.commons.lang.StringUtils;
import org.openiam.ui.rest.api.model.UserBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by: Alexander Duckardt
 * Date: 7/21/14.
 */
@Component
public class UserBeanPropertiesParser implements InitializingBean {

    @Value("${org.openiam.organization.type.id}")
    private String organizationTypeId;
    @Value("${org.openiam.department.type.id}")
    private String departmentTypeId;

    @Value("${org.openiam.ui.user.search.result.columns}")
    private String userSearchResultColumns;
    @Value("${org.openiam.ui.user.fullname.compose.rule}")
    private String userFullNameComposeRule;
    @Value("${org.openiam.ui.user.search.form.additional.criteria}")
    private String additionalSearchCriteria;
    @Value("${org.openiam.ui.user.dir.lookup.search.result.columns}")
    private String dirLookupSearchResultColumns;
    @Value("${org.openiam.ui.user.view.user.search.result.columns}")
    private String viewUserSearchResultColumns;

    @Value("${org.openiam.date.format}")
    private String dateFormatProp;

    private List<String> userSearchResultColumnList = new LinkedList<String>();
    private List<String> userFullNameComposeOrderList = new LinkedList<String>();
    private List<String> additionalSearchCriteriaList = new LinkedList<String>();
    private List<String> dirLookupSearchResultColumnsList = new LinkedList<String>();

    private List<String> viewUserSearchResultColumnsList = new LinkedList<String>();

    public List<String> getUserSearchResultColumnList() {
        return userSearchResultColumnList;
    }

    public List<String> getUserFullNameComposeOrderList() {
        return userFullNameComposeOrderList;
    }

    public List<String> getAdditionalSearchCriteriaList() {
        return this.additionalSearchCriteriaList;
    }

    public List<String> getDirLookupSearchResultColumnsList() {
        return dirLookupSearchResultColumnsList;
    }

    public List<String> getViewUserSearchResultColumnsList() {
        return viewUserSearchResultColumnsList;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
//        final String[] columns = StringUtils.split(userSearchResultColumns, ",");
//        for(int i = 0; i < columns.length; i++) {
//            userSearchResultColumnList.add(columns[i]);
//        }
        userSearchResultColumnList = parseProperty(userSearchResultColumns);
        dirLookupSearchResultColumnsList = parseProperty(dirLookupSearchResultColumns);
        viewUserSearchResultColumnsList = parseProperty(viewUserSearchResultColumns);

        final String[] ruleOrder = StringUtils.split(userFullNameComposeRule, ",");
        for (int i = 0; i < ruleOrder.length; i++) {
            userFullNameComposeOrderList.add(ruleOrder[i]);
        }
        final String[] additionalCriteria = StringUtils.split(additionalSearchCriteria, ",");
        for (int i = 0; i < additionalCriteria.length; i++) {
            additionalSearchCriteriaList.add(additionalCriteria[i]);
        }

        UserBean.setOrganizationTypeId(organizationTypeId);
        UserBean.setDepartmentTypeId(departmentTypeId);
        UserBean.setFullNameComposeOrderList(userFullNameComposeOrderList);
        UserBean.setSDF(new SimpleDateFormat(dateFormatProp));
    }

    private List<String> parseProperty(final String property) {

        List<String> result = new LinkedList<String>();
        if (StringUtils.isNotBlank(property)) {
            final String[] propertyArray = StringUtils.split(property, ",");
            if (propertyArray != null && propertyArray.length > 0) {
                for (int i = 0; i < propertyArray.length; i++) {
                    result.add(propertyArray[i]);
                }
            }
        }
        return result;
    }
}