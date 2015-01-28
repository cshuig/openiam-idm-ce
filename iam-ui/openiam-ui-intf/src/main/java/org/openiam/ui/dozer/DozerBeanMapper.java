package org.openiam.ui.dozer;

import org.apache.commons.collections.CollectionUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class DozerBeanMapper {
    @Autowired
    @Qualifier("beanMapper")
    private Mapper mapper;

    public <T> List<T> mapToList(final Collection collection, final Class<T> target) {
        final List<T> retVal = new LinkedList<T>();
        if(CollectionUtils.isNotEmpty(collection)) {
            for(final Object object : collection) {
                retVal.add(mapper.map(object, target));
            }
        }
        return retVal;
    }
    
    public <T> List<T> mapToList(final Object[] collection, final Class<T> target) {
    	final List<T> retVal = new LinkedList<T>();
        if(collection != null) {
            for(final Object object : collection) {
                retVal.add(mapper.map(object, target));
            }
        }
        return retVal;
    }

    public <T> T mapToObject(final Object object, final Class<T> target) {
        return mapper.map(object, target);
    }
}
