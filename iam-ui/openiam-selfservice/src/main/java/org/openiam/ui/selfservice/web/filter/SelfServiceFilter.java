package org.openiam.ui.selfservice.web.filter;

import org.openiam.ui.web.filter.OpeniamFilter;

/**
 * Created by: Alexander Duckardt
 * Date: 4/25/14.
 */
public class SelfServiceFilter extends OpeniamFilter {
    protected String getApplicationTitle(){
        return "openiam.ui.selfservice.text";
    }
}
