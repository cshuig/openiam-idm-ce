package org.openiam.ui.rest.processor;

import org.openiam.ui.rest.constant.ContentType;
import org.openiam.ui.rest.response.RestResponse;
import org.springframework.util.StringUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class XmlProcessor extends AbstractProcessor{


    @Override
    public <ServiceResponse extends RestResponse> ServiceResponse readResponse(String responseBody, Class<ServiceResponse> clazz) throws Exception {
        responseBody = this.preProcessBody(responseBody, clazz);

        JAXBContext context = JAXBContext.newInstance("org.openiam.ui.rest.response");
        Unmarshaller unmarshaller = context.createUnmarshaller();
        InputStream responseBodyStream = new ByteArrayInputStream(responseBody.getBytes());

        JAXBElement<ServiceResponse> root = unmarshaller.unmarshal(new StreamSource(responseBodyStream), clazz);
        return root.getValue();
    }

    @Override
    public String processRequestBody(Object postBody) throws Exception {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        JAXBContext context = JAXBContext.newInstance(postBody.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(postBody, stream);

        return new String(stream.toByteArray(), "UTF-8");
    }

    protected void setContentType(){
        this.contentType= ContentType.Xml;
    }

    protected String preProcessBody(String requestBody, Class<?> objectClass) {
        XmlRootElement alias = objectClass.getAnnotation(XmlRootElement.class);
        String rootTagName = toLowCaseFirstLetter(objectClass.getSimpleName());
        if(alias != null && !"##default".equals(alias.name()) ) {
            rootTagName =alias.name();
        }
        String result = "<" + rootTagName + ">\n</" + rootTagName + ">";
        if (StringUtils.hasText(requestBody)){
            result = this.replaceRoot(requestBody, rootTagName);
        }
        return result;
    }

    private String toLowCaseFirstLetter(String str){
        return str.substring(0,1).toUpperCase()+str.substring(1);
    }

    private String replaceRoot(String requestBody, String root) {
        Pattern p = Pattern.compile("<([^?]\\w+?[^?/])>");

        Matcher m = p.matcher(requestBody);
        if (m.find()) {
            requestBody = requestBody.trim();
            String startTag = m.group(1);
            if(!startTag.equals(root)){
                requestBody = requestBody.replaceFirst("<" + startTag + ">", "<" + root + ">");
                requestBody = requestBody.replaceFirst("</" + startTag + ">$", "</" + root + ">");
            }
        }
        return requestBody;
    }
}
