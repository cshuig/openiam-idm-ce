package org.openiam.script;

import java.util.HashMap;
import java.util.Map;
import org.springframework.context.ApplicationContext;

public class BindingModelImpl implements BindingModel {
    private final Map<String, Object> bindingMap;
    private final ApplicationContext ac;
    public static final String APP_CONTEXT = "context";

    public BindingModelImpl(final ApplicationContext ac) {
        this(new HashMap<String, Object>(), ac);
    }

    public BindingModelImpl(final Map<String, Object> bindingMap, ApplicationContext ac) {
        this.bindingMap = bindingMap != null ? bindingMap : new HashMap<String, Object>();
        this.ac = ac;
        this.bindingMap.put(APP_CONTEXT, ac);
    }

    public Map<String, Object> getBindingMap() {
        return bindingMap;
    }

}
