package org.openiam.ui.selfservice.ext.web.filter;

import org.openiam.ui.web.filter.OpeniamFilter;

/**
 * Created by: Alexander Duckardt
 * Date: 4/25/14.
 */
public class SelfServiceExtFilter extends OpeniamFilter {
    protected String getApplicationTitle(){
        return "openiam.ui.selfservice.ext.title";
    }
}
