package org.openiam.ui.util;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;

public class CustomJacksonMapper extends ObjectMapper {

	public CustomJacksonMapper() {
		super();
		this.configure(Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
		 .configure(Feature.FAIL_ON_NULL_FOR_PRIMITIVES, false)
		 .configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		final SimpleModule stringModule = new SimpleModule("MyModule", new Version(1, 0, 0, null)).addDeserializer(String.class, new StringDeserializer());
		this.registerModule(stringModule);
	}
	
	private class StringDeserializer extends JsonDeserializer<String> {

		@Override
		public String deserialize(JsonParser parser, DeserializationContext ctx) throws IOException, JsonProcessingException {
			String str = StringUtils.trimToNull(parser.getText());
			//IDMAPPS-2393
			if(str != null) {
				str = Jsoup.clean(str, Whitelist.relaxed());
			}
			
			return HtmlUtils.htmlUnescape(XSSUtils.stripXSS(str, false));
		}
	}
}
