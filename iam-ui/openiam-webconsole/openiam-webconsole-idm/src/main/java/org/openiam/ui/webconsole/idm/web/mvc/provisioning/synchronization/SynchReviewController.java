package org.openiam.ui.webconsole.idm.web.mvc.provisioning.synchronization;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openiam.base.OrderConstants;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.base.ws.SortParam;
import org.openiam.idm.searchbeans.SynchReviewSearchBean;
import org.openiam.idm.srvc.synch.dto.SynchReview;
import org.openiam.idm.srvc.synch.dto.SynchReviewRecord;
import org.openiam.idm.srvc.synch.dto.SynchReviewResponse;
import org.openiam.idm.srvc.synch.ws.AsynchIdentitySynchService;
import org.openiam.idm.srvc.synch.ws.IdentitySynchWebService;
import org.openiam.idm.srvc.synch.ws.SynchConfigResponse;
import org.openiam.idm.srvc.synch.ws.SynchReviewWebService;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class SynchReviewController extends AbstractController {

    private static final Logger LOG = Logger.getLogger(SynchReviewController.class);

    @Value("${org.openiam.ui.idm.synchronization.edit.root.name}")
    private String syncConfigEditRootMenuName;

    @Autowired
    private SynchReviewWebService synchReviewServiceClient;
    @Autowired
    private IdentitySynchWebService synchConfigServiceClient;
    @Autowired
    private AsynchIdentitySynchService asynchSynchServiceWS;


    @RequestMapping(value = "/provisioning/synchReviewList", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(@RequestParam(value = "id", required = false) String synchConfigId,
                       @ModelAttribute("synchReviewListCommand") SynchReviewListCommand synchReviewListCommand,
                       final HttpServletRequest request, final HttpServletResponse response,
                       Model model) throws IOException {

        SynchConfigResponse res = synchConfigServiceClient.findById(synchConfigId);
        if (res.isFailure()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("Synchronization config with ID: '%s' not found", synchConfigId));
            return null;
        }
        synchReviewListCommand.setSynchConfigId(synchConfigId);

        SynchReviewSearchBean searchBean = new SynchReviewSearchBean();
        List<SortParam> sortParamList = new ArrayList<>();
        sortParamList.add(new SortParam(OrderConstants.DESC, "createTime"));
        searchBean.setSortBy(sortParamList);
        searchBean.setSynchConfigId(synchConfigId);

        final int count = synchReviewServiceClient.countBeans(searchBean);
        synchReviewListCommand.setCount(count);

        List<SynchReview> synchReviewList = synchReviewServiceClient.findBeans(searchBean,
                synchReviewListCommand.getPage() * synchReviewListCommand.getSize(), synchReviewListCommand.getSize());
        model.addAttribute("synchReviewList", (synchReviewList != null) ? synchReviewList : Collections.EMPTY_LIST);
        setMenuTree(request, syncConfigEditRootMenuName);

        return "/provisioning/synchronization/synchReviewList";
    }

    @RequestMapping(value = "/provisioning/synchReviewList", method = RequestMethod.POST, params="deleteSelected")
    public String deleteSelected(@RequestParam(value = "id", required = false) String synchConfigId,
                       @RequestParam(value = "entityIds", required = false) List<String> entityIds,
                       final HttpServletResponse response) throws IOException {

        SynchConfigResponse res = synchConfigServiceClient.findById(synchConfigId);
        if (res.isFailure()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("Synchronization with ID: '%s' not found", synchConfigId));
            return null;
        }
        if (CollectionUtils.isNotEmpty(entityIds)) {
            synchReviewServiceClient.deleteByIds(entityIds);
        }

        return "redirect:/provisioning/synchReviewList.html?id=" + synchConfigId;
    }

    @RequestMapping(value = "/provisioning/synchReview", method = {RequestMethod.GET, RequestMethod.POST})
    public String synchReview(@RequestParam(value = "id", required = true) String synchReviewId,
                              @ModelAttribute("synchReviewCommand") SynchReviewCommand synchReviewCommand,
                                 final HttpServletRequest request,
                                 final HttpServletResponse response,
                                 Model model) throws IOException {
        SynchReviewSearchBean searchBean = new SynchReviewSearchBean();
        searchBean.setDeepCopy(false);
        searchBean.setKey(synchReviewId);
        List<SynchReview> synchReviewList = synchReviewServiceClient.findBeans(searchBean, 0, 1);
        if (CollectionUtils.isEmpty(synchReviewList)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("Synchronization Review with ID: '%s' not found", synchReviewId));
            return null;
        }
        int page = synchReviewCommand.getPage();
        int size = synchReviewCommand.getSize();
        SynchReview synchReview = synchReviewList.get(0);
        synchReviewCommand = new SynchReviewCommand(synchReview);
        synchReviewCommand.setPage(page);
        synchReviewCommand.setSize(size);

        synchReviewCommand.setCount(synchReviewServiceClient.getRecordsCountBySynchReviewId(synchReviewId));
        List<SynchReviewRecord> records = synchReviewServiceClient.getRecordsBySynchReviewId(
                synchReviewId, page * size, size);
        synchReviewCommand.setReviewRecords(records);

        model.addAttribute("synchReviewCommand", synchReviewCommand);

        setMenuTree(request, syncConfigEditRootMenuName);

        return "/provisioning/synchronization/synchReview";
    }

    @RequestMapping(value = "/provisioning/updateSynchReview", method = RequestMethod.POST)
    public @ResponseBody
    BasicAjaxResponse updateSynchReview(final @RequestBody SynchReviewCommand synchReviewCommand,
                                        final HttpServletRequest request) {
        BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        try {
            if (StringUtils.isNotBlank(synchReviewCommand.getSynchReviewId())) {
                SynchReviewResponse res = synchReviewServiceClient.updateSynchReview(synchReviewCommand.generateRequest());
                if (ResponseStatus.SUCCESS.equals(res.getStatus())) {
                    ajaxResponse.setStatus(200);
                    SuccessToken successToken = new SuccessToken(SuccessMessage.SYNCH_REVIEW_UPDATE_SUCCESS);
                    ajaxResponse.setSuccessToken(successToken);
                    ajaxResponse.setSuccessMessage(messageSource.getMessage(
                            successToken.getMessage().getMessageName(),
                            null,
                            localeResolver.resolveLocale(request)));
                } else {
                    ErrorToken errorToken = new ErrorToken(res.getErrorText());
                    ajaxResponse.setStatus(500);
                    errorToken.setMessage(messageSource.getMessage(
                            errorToken.getError().getMessageName(),
                            errorToken.getParams(),
                            localeResolver.resolveLocale(request)));
                    ajaxResponse.addError(errorToken);
                }
            }
        } catch (Exception e) {
            Errors error = Errors.SYNCH_REVIEW_UPDATE_FAIL;
            ErrorToken errorToken = new ErrorToken(error);
            ajaxResponse.setStatus(500);
            errorToken.setMessage(messageSource.getMessage(
                    errorToken.getError().getMessageName(),
                    errorToken.getParams(),
                    localeResolver.resolveLocale(request)));
            ajaxResponse.addError(errorToken);
        }

        return ajaxResponse;
    }

    @RequestMapping(value = "/provisioning/executeSynchReview", method = RequestMethod.POST)
    public @ResponseBody
    BasicAjaxResponse executeSynchReview(final @RequestBody SynchReviewCommand synchReviewCommand,
                                        final HttpServletRequest request) {
        BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        try {
            if (StringUtils.isNotBlank(synchReviewCommand.getSynchReviewId())) {
                asynchSynchServiceWS.executeSynchReview(synchReviewCommand.generateRequest());
                ajaxResponse.setStatus(200);
                SuccessToken successToken = new SuccessToken(SuccessMessage.SYNCH_REVIEW_EXECUTE_SUCCESS);
                ajaxResponse.setSuccessToken(successToken);
                ajaxResponse.setSuccessMessage(messageSource.getMessage(
                        successToken.getMessage().getMessageName(),
                        null,
                        localeResolver.resolveLocale(request)));
            }
        } catch (Exception e) {
            Errors error = Errors.SYNCH_REVIEW_EXECUTE_FAIL;
            ErrorToken errorToken = new ErrorToken(error);
            ajaxResponse.setStatus(500);
            errorToken.setMessage(messageSource.getMessage(
                    errorToken.getError().getMessageName(),
                    errorToken.getParams(),
                    localeResolver.resolveLocale(request)));
            ajaxResponse.addError(errorToken);
        }

        return ajaxResponse;
    }

    @ModelAttribute("reviewHeader")
    public SynchReviewRecord getReviewHeader(final @RequestParam(required=false, value="id") String synchReviewId) {
        if (StringUtils.isNotBlank(synchReviewId)) {
            return synchReviewServiceClient.getHeaderReviewRecord(synchReviewId);
        }
        return null;
    }

}
