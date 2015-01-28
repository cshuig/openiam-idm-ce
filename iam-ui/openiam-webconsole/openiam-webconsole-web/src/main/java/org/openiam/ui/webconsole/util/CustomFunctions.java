package org.openiam.ui.webconsole.util;

import java.util.Collection;

public class CustomFunctions {
    public static boolean contains(Collection<?> collection, Object object) {
        return (collection!=null) ? collection.contains(object):false;
    }
}
