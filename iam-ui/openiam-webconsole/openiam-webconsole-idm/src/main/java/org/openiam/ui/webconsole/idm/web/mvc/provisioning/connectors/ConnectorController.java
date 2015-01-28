package org.openiam.ui.webconsole.idm.web.mvc.provisioning.connectors;

import org.mule.util.StringUtils;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.srvc.meta.dto.MetadataType;
import org.openiam.idm.srvc.mngsys.dto.ProvisionConnectorDto;
import org.openiam.idm.srvc.mngsys.dto.ProvisionConnectorSearchBean;
import org.openiam.idm.srvc.mngsys.ws.ProvisionConnectorWebService;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.mvc.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Controller
public class ConnectorController extends AbstractController {

    @Value("${org.openiam.ui.idm.connector.root.name}")
    private String rootMenuName;

    @Autowired
    private ProvisionConnectorWebService connectorServiceClient;

    @RequestMapping(value = "/provisioning/connectorlist", method = RequestMethod.GET)
    public String list(ConnectorListCommand connectorListCommand,
                       Model model,
                       HttpServletRequest request,
                       BindingResult result) {
        setMenuTree(request, rootMenuName);

        ProvisionConnectorSearchBean provisionConnectorSearchBean = new ProvisionConnectorSearchBean();
        if(StringUtils.isNotEmpty(connectorListCommand.getConnectorTypeId())) {
            provisionConnectorSearchBean.setConnectorTypeId(connectorListCommand.getConnectorTypeId());
        }
        if(StringUtils.isNotEmpty(connectorListCommand.getConnectorName())) {
            provisionConnectorSearchBean.setConnectorName(connectorListCommand.getConnectorName());
        }

        List<ProvisionConnectorDto> connectors = connectorServiceClient.getProvisionConnectors(provisionConnectorSearchBean,
                connectorListCommand.getPage() * connectorListCommand.getSize(), connectorListCommand.getSize());
        if(connectors == null) {
            connectors = Collections.EMPTY_LIST;
        }
        List<MetadataType> provConnectorTypeList = connectorServiceClient.getProvisionConnectorsTypes();
        final int count = connectorServiceClient.getProvisionConnectorsCount(provisionConnectorSearchBean);
        connectorListCommand.setCount(count);

        model.addAttribute("provConnectorTypeList", provConnectorTypeList);
        model.addAttribute("connectors", connectors);

        model.addAttribute("connectorListCommand",connectorListCommand);
        return "/provisioning/connectorlist";
    }

    @RequestMapping(value = "/provisioning/connector", method = RequestMethod.GET)
    public String formEdit(Model model, @RequestParam(value="id", required=false) String connectorId) {
      ConnectorCommand connector;
      if(StringUtils.isNotEmpty(connectorId)) {
          ProvisionConnectorDto provisionConnectorDto = connectorServiceClient.getProvisionConnector(connectorId);
          connector = new ConnectorCommand(provisionConnectorDto);
      } else {
         connector = new ConnectorCommand();
      }
      model.addAttribute("connectorCommand", connector);
      return "/provisioning/connector";
    }

    @RequestMapping(value = "/provisioning/connector", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("connectorCommand") ConnectorCommand connectorCommand, BindingResult result, Model model) {
        if (!result.hasErrors()) {
          
                if(StringUtils.isNotEmpty(connectorCommand.getConnectorId())) {
                    connectorServiceClient.updateProvisionConnector(connectorCommand.getProvisionConnectorDto());
                } else {
                    connectorServiceClient.addProvisionConnector(connectorCommand.getProvisionConnectorDto());
                }
          
        } else {
            model.addAttribute("connectorCommand", connectorCommand);
            return "/provisioning/connector";
        }

        return "redirect:/provisioning/connectorlist.html";
    }
    
    
    @RequestMapping(value = "/provisioning/deleteProvider", method = RequestMethod.POST)
	public String deleteProvider(final @RequestParam(value = "id") String connectorId,
			final HttpServletResponse response, final HttpServletRequest request)
			throws IOException {
    	
    	final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
   	        Response wsResponse=new Response();
   	        if(connectorId!=null){
		     
		       wsResponse= connectorServiceClient.removeProvisionConnector(connectorId);
		       if(ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
					ajaxResponse.setStatus(200);
					ajaxResponse.setRedirectURL("/webconsole-idm/provisioning/connectorlist.html");
					ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.CONNECTOR_DELETED));
				} else {
					
					Object[] params = null;
					Errors error = Errors.CONNECTOR_DELETE_ERROR;
					if(wsResponse.getErrorCode() != null) {
						final Object responseValue = wsResponse.getResponseValue();
						switch(wsResponse.getErrorCode()) {
						 	default:
						 		break;
						}
					}
					ajaxResponse.addError(new ErrorToken(error, params));
					ajaxResponse.setStatus(500);
				}
   	        }
		request.setAttribute("response", ajaxResponse);
		return "common/basic.ajax.response";
	}
}



