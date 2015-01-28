package org.openiam.ui.resource.mvc;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.openiam.ui.util.ExtendedResourceBundleMessageSource;
import org.openiam.ui.web.util.OpeniamCookieLocaleResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Map;

@Controller
public class ResourceBundleController {
    private static Logger log = Logger.getLogger(ResourceBundleController.class);
    private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();
    private static final long lastModifiedTime = System.currentTimeMillis();

    @Autowired
    @Qualifier("messageSource")
    private ExtendedResourceBundleMessageSource messageSource;

    @Autowired
    @Qualifier("localeResolver")
    private OpeniamCookieLocaleResolver localeResolver;

    @Autowired
    @Qualifier("jacksonMapper")
    private ObjectMapper jacksonMapper;


    @RequestMapping(value="/_dynamic/openiamResourceBundle", method= RequestMethod.GET)
    public String openiamResourceBundle(WebRequest webRequest, final HttpServletRequest request,
                                        HttpServletResponse response, Model model) throws Exception {
        final String requestedEtag = getEtag(request);

        if(webRequest.checkNotModified(requestedEtag)){
            response.sendError(HttpServletResponse.SC_NOT_MODIFIED);
            return null;
        } else {
            response.setHeader("Etag", requestedEtag);
            Map<String, String> messageMaps = messageSource.getMessagesMap(localeResolver.resolveLocale(request));
            model.addAttribute("messagesMap", this.jacksonMapper.writeValueAsString(messageMaps));
            return "locale";
        }
    }


    private String getEtag(final HttpServletRequest request){
        Locale requestedLocale = localeResolver.resolveLocale(request);

        StringBuilder sb = new StringBuilder();
        sb.append(byteArrayToString( requestedLocale.getLanguage().getBytes(),'0'))
          .append("-").append(lastModifiedTime);

        return sb.toString();
    }

    public static String byteArrayToString(byte[] data, char separator) {
        int size = data.length;
        char[] chars = null;
        if (separator == '0') {
            chars = new char[2 * size];
            for (int i = 0, j = 0; i < size; ++i, j += 2) {
                chars[j] = HEX_CHARS[(data[i] & 0xF0) >>> 4];
                chars[j + 1] = HEX_CHARS[data[i] & 0x0F];
            }
        } else {
            chars = new char[3 * size + 1];
            chars[0] = separator;
            for (int i = 0, j = 1; i < size; ++i, j += 3) {
                chars[j] = HEX_CHARS[(data[i] & 0xF0) >>> 4];
                chars[j + 1] = HEX_CHARS[data[i] & 0x0F];
                chars[j + 2] = separator;
            }
        }
        String result = new String(chars);
        if (separator != '0')
            result = result.substring(0, result.lastIndexOf(separator));
        return result.trim();
    }
}
