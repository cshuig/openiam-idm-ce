package org.openiam.ui.webconsole.am.web.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.openiam.am.srvc.dto.ContentProviderServer;
import org.openiam.am.srvc.ws.ContentProviderWebService;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.model.BeanResponse;
import org.openiam.ui.web.mvc.AbstractController;
import org.openiam.ui.webconsole.am.web.model.ContentProviderServerBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

@Controller
public class ContentProviderServerController  extends AbstractController {
    private static Logger log = Logger.getLogger(AuthenticationProviderController.class);
    @Resource(name="contentProviderServiceClient")
    private ContentProviderWebService contentProviderServiceClient;

    @Value("${org.openiam.ui.content.provider.edit.name}")
    private String editMenuName;

    @RequestMapping(value="/getServersForProvider", method = RequestMethod.GET)
    public @ResponseBody
    BeanResponse getServersForProvider(final @RequestParam(required=true, value="providerId") String providerId,
                                  final @RequestParam(required=true, value="size") Integer size,
                                  final @RequestParam(required=true, value="from") Integer from) {
        final List<ContentProviderServer> serverlList = contentProviderServiceClient.getServersForProvider(providerId,from, size);
        //log.info(String.format("serverlList=%s", serverlList));
        final Integer count = contentProviderServiceClient.getNumOfServersForProvider(providerId);
        final List<ContentProviderServerBean> beanList = new LinkedList<ContentProviderServerBean>();
        if(CollectionUtils.isNotEmpty(serverlList)) {
            for(final ContentProviderServer server : serverlList) {
                beanList.add(new ContentProviderServerBean(server.getId(), server.getContentProviderId(), server.getServerURL()));
            }
        }
        return new BeanResponse(beanList, count);
    }

    //saveProviderServer
    @RequestMapping(value="/saveProviderServer", method=RequestMethod.POST)
    public String saveProviderServer(final HttpServletRequest request, @RequestBody ContentProviderServerBean serverRequest) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        try{
            ContentProviderServer server = convertToDTO(serverRequest);

            Response wsResponse = contentProviderServiceClient.saveProviderServer(server);

            if(wsResponse.getStatus() == ResponseStatus.SUCCESS) {
                SuccessToken successToken = new SuccessToken(SuccessMessage.CONTENT_PROVIDER_SERVER_SAVED);
                ajaxResponse.setStatus(200);
                ajaxResponse.setSuccessToken(successToken);
            } else {
                Errors error = Errors.CANNOT_SAVE_CONTENT_PROVIDER_SERVER;
                if(wsResponse.getErrorCode() != null) {
                    switch(wsResponse.getErrorCode()) {
                        case CONTENT_PROVIDER_SERVER_URL_NOT_SET:
                            error = Errors.CONTENT_PROVIDER_SERVER_URL_NOT_SET;
                            break;
                        case CONTENT_PROVIDER_NOT_SET:
                            error = Errors.CONTENT_PROVIDER_NOT_SET;
                            break;
                        case CONTENT_PROVIDER_SERVER_EXISTS:
                            error = Errors.CONTENT_PROVIDER_SERVER_EXISTS;
                            break;
                        default:
                            error = Errors.INTERNAL_ERROR;
                            break;
                    }
                }
                ErrorToken errorToken = new ErrorToken(error);
                ajaxResponse.addError(errorToken);
            }
        }  catch (Exception e){
            log.error(e.getMessage(), e);
            ajaxResponse.addError(new ErrorToken(Errors.INTERNAL_ERROR));
        }
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value="/deleteProviderServer", method=RequestMethod.POST)
    public String deleteProviderServer(final HttpServletRequest request, @RequestBody ContentProviderServerBean serverRequest) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        try{

            Response wsResponse = contentProviderServiceClient.deleteProviderServer(serverRequest.getId());

            if(wsResponse.getStatus() == ResponseStatus.SUCCESS) {
                SuccessToken successToken = new SuccessToken(SuccessMessage.CONTENT_PROVIDER_SERVER_DELETED);
                ajaxResponse.setStatus(200);
                ajaxResponse.setSuccessToken(successToken);
            } else {
                Errors error = Errors.CANNOT_DELETE_CONTENT_PROVIDER_SERVER;
                if(wsResponse.getErrorCode() != null) {
                    switch(wsResponse.getErrorCode()) {
                        case INVALID_ARGUMENTS:
                            error = Errors.CONTENT_PROVIDER_SERVER_NOT_SELECTED;
                            break;
                        default:
                            error = Errors.INTERNAL_ERROR;
                            break;
                    }
                }
                ErrorToken errorToken = new ErrorToken(error);
                ajaxResponse.addError(errorToken);
            }
        }  catch (Exception e){
            log.error(e.getMessage(), e);
            ajaxResponse.addError(new ErrorToken(Errors.INTERNAL_ERROR));
        }
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    private ContentProviderServer convertToDTO(ContentProviderServerBean serverRequest) {
        ContentProviderServer dto = new ContentProviderServer();
        dto.setId(serverRequest.getId());
        dto.setContentProviderId(serverRequest.getProviderId());
        dto.setServerURL(serverRequest.getServerURL());
        return dto;
    }


    //
}
