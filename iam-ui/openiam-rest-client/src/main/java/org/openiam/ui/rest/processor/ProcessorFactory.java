package org.openiam.ui.rest.processor;

import org.openiam.ui.rest.constant.ContentType;

public class ProcessorFactory {

    public static AbstractProcessor getInstance(ContentType contentType) throws Exception{
           switch (contentType){
                case Json:
                    return new JsonProcessor();
                case Xml:
                    return new XmlProcessor();
                default:
                    throw new UnsupportedOperationException("Unknown content type. Cannot find processor");
           }
    }
}
