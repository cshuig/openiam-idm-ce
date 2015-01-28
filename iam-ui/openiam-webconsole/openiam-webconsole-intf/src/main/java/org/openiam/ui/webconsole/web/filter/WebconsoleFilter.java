package org.openiam.ui.webconsole.web.filter;

import org.openiam.ui.web.filter.OpeniamFilter;

/**
 * Created by: Alexander Duckardt
 * Date: 4/25/14.
 */
public class WebconsoleFilter extends OpeniamFilter {

    protected String getApplicationTitle(){
        return "org.openiam.ui.webconsole.title";
    }
}
