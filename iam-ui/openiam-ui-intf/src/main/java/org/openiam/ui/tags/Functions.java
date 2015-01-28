package org.openiam.ui.tags;

import org.apache.commons.lang.StringUtils;

public class Functions {
    public static String getFlagStyle(String input) {
        String result = "background: url('%s') no-repeat scroll 0 0 rgba(0, 0, 0, 0)";
        if (StringUtils.isEmpty(input))
            return String.format(result, "");
        else if (input.length() == 2) {
            return String.format(result, "/openiam-ui-static/images/common/flags/" + input.toLowerCase() + ".gif");
        } else if (input.length() == 5) {
            return String.format(result, "/openiam-ui-static/images/common/flags/"
                    + input.substring(3, 5).toLowerCase() + ".gif");
        }
        return String.format(result, "");
    }
}
