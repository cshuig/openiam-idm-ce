package org.openiam.ui.web.mvc.util;

import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

@Component
public class MappingJacksonHttpMessageConverterPostProcessor implements BeanPostProcessor {
	
	@Autowired
	@Qualifier("jacksonMapper")
	private ObjectMapper jacksonMapper;

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		if(bean instanceof RequestMappingHandlerAdapter) {
			final RequestMappingHandlerAdapter adapter = (RequestMappingHandlerAdapter)bean;
			if(adapter.getMessageConverters() != null) {
				for(final HttpMessageConverter<?> converter : adapter.getMessageConverters()) {
					if(converter instanceof MappingJacksonHttpMessageConverter) {
						final MappingJacksonHttpMessageConverter messageConverter = (MappingJacksonHttpMessageConverter)converter;
						messageConverter.setObjectMapper(jacksonMapper);
					}
				}
			}
		}
		return bean;
	}
}
