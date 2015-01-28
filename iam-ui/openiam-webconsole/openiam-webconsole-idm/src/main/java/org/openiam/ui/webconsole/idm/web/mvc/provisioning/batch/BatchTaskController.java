package org.openiam.ui.webconsole.idm.web.mvc.provisioning.batch;

import org.apache.commons.lang.StringUtils;
import org.openiam.base.OrderConstants;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.base.ws.SortParam;
import org.openiam.idm.searchbeans.BatchTaskSearchBean;
import org.openiam.idm.srvc.batch.dto.BatchTask;
import org.openiam.idm.srvc.batch.service.BatchDataService;
import org.openiam.ui.dozer.DozerBeanMapper;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.model.BeanResponse;
import org.openiam.ui.web.mvc.AbstractController;
import org.openiam.ui.web.util.DateFormatStr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class BatchTaskController extends AbstractController {

	@Value("${org.openiam.ui.idm.batch.task.root}")
	private String batchRoot;
	
	@Value("${org.openiam.ui.idm.batch.task.edit}")
	private String batchEdit;
	
	@Autowired
	@Qualifier("dozerBeanMapper")
	private DozerBeanMapper mapper;
	
	@Autowired
	@Resource(name="batchTaskClient")
	private BatchDataService batchService;

	@RequestMapping(value = "/provisioning/batchTaskSearch", method = RequestMethod.GET)
	public String searchBatchTasks(final HttpServletRequest request) {
		setMenuTree(request, batchRoot);
		return "/provisioning/batch/search";
	}
	
	@RequestMapping(value = "/provisioning/batch/task/search", method = RequestMethod.GET)
	public @ResponseBody BeanResponse search(final @RequestParam(required=true, value="from") int from,
											 final @RequestParam(required=true, value="size") int size) {
		final BatchTaskSearchBean searchBean = new BatchTaskSearchBean();
		searchBean.setDeepCopy(false);
        List<SortParam> sortParamList = new ArrayList<>();
        sortParamList.add(new SortParam(OrderConstants.ASC, "name"));
        searchBean.setSortBy(sortParamList);
		
		final List<BatchTask> resultList = batchService.findBeans(searchBean, from, size);
		int count = batchService.count(searchBean);
		
		return new BeanResponse(mapper.mapToList(resultList, BatchDataBean.class), count);
	}
	
	@RequestMapping(value = "/provisioning/batchTask", method = RequestMethod.GET)
	public String getBatchTask(final HttpServletRequest request,
							   final HttpServletResponse response,
							   final @RequestParam(required=false,value="id") String taskId) throws IOException {
		BatchTaskRequestBean task = null;
		if(StringUtils.isNotBlank(taskId)) {
			BatchTask taskBean = batchService.getBatchTask(taskId);
			if(taskBean == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return null;
			}
			task = mapper.mapToObject(taskBean, BatchTaskRequestBean.class);
			setMenuTree(request, batchEdit);
		} else {
			task = new BatchTaskRequestBean();
			setMenuTree(request, batchRoot);
		}
		
		task.doDateRithmetic();
		request.setAttribute("batchTask", task);
        request.setAttribute("dateFormatDP", DateFormatStr.getDpDate());
		return "/provisioning/batch/edit";
	}
	
	@RequestMapping(value = "/provisioning/deletBatchTask", method = RequestMethod.POST)
	public @ResponseBody BasicAjaxResponse deleteBatchTask(final HttpServletRequest request,
			   final HttpServletResponse response,
			   final @RequestParam(required=true,value="id") String taskId) {
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		final Response wsResponse = batchService.removeBatchTask(taskId);
		Errors error = null;
		try {
			if(wsResponse == null) {
				error = Errors.BATCH_TASK_DELETE_ERROR;
			} else if(wsResponse.getStatus() == ResponseStatus.FAILURE) {
				error = Errors.BATCH_TASK_DELETE_ERROR;
				if(wsResponse.getErrorCode() != null) {
					switch(wsResponse.getErrorCode()) {
						default:
							break;
					}
				}
			}
		} finally {
			if(error != null) {
				ajaxResponse.addError(new ErrorToken(error));
				ajaxResponse.setStatus(500);
			} else {
				ajaxResponse.setStatus(200);
				ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.BATCH_TASK_DELETE_SUCCESS));
			}
			ajaxResponse.process(localeResolver, messageSource, request);
		}
		return ajaxResponse;
	}
	
	@RequestMapping(value = "/provisioning/batchtask/execute", method = RequestMethod.POST)
	public @ResponseBody BasicAjaxResponse saveBatchTask(final HttpServletRequest request,
														 final @RequestParam(required=true,value="id") String id) {
		Errors error = null;
		Response wsResponse = null;
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		try {
			wsResponse = batchService.run(id, false);
			if(wsResponse.isFailure()) {
				error = Errors.INTERNAL_ERROR;
				if(wsResponse.getErrorCode() != null) {
					switch(wsResponse.getErrorCode()) {
						default:
							break;
					}
				}
			}
		} finally {
			if(error != null) {
				ajaxResponse.addError(new ErrorToken(error));
				ajaxResponse.setStatus(500);
			} else {
				ajaxResponse.setStatus(200);
				ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.BATCH_TASK_EXECUTED_ASYNCHONOUSLY));
			}
			ajaxResponse.process(localeResolver, messageSource, request);
		}
		return ajaxResponse;
	}
	
	@RequestMapping(value = "/provisioning/saveBatchTask", method = RequestMethod.POST)
	public @ResponseBody BasicAjaxResponse saveBatchTask(final HttpServletRequest request, 
								final HttpServletResponse response, 
								final @RequestBody BatchTaskRequestBean task) {
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		task.doDateRithmetic();
        if (StringUtils.isNotBlank(task.getId())) {
            BatchTask bt = batchService.getBatchTask(task.getId());
            task.setLastExecTime(bt.getLastExecTime());
        }
        task.setLastModifiedDate(new Date());
        final Response wsResponse = batchService.save(task);
		Errors error = null;
		try {
			if(wsResponse == null) {
				error = Errors.BATCH_TASK_SAVE_ERROR;
			} else if(wsResponse.getStatus() == ResponseStatus.FAILURE) {
				error = Errors.BATCH_TASK_SAVE_ERROR;
				if(wsResponse.getErrorCode() != null) {
					switch(wsResponse.getErrorCode()) {
						case NO_NAME:
							error = Errors.NAME_REQUIRED;
							break;
						case NO_EXEUCUTION_TIME:
							error = Errors.BATCH_EXECUTION_TIME_MISSING;
							break;
						case INVALID_CRON_EXRPESSION:
							error = Errors.BATCH_INVALID_CRON_EXRPESSION;
							break;
						case DATE_INVALID:
							error = Errors.BATCH_INVALID_DATE;
							break;
						case SPRING_BEAN_OR_SCRIPT_REQUIRED:
							error = Errors.BATCH_SPRING_BEAN_OR_SCRIPT_REQUIRED;
							break;
						case FILE_DOES_NOT_EXIST:
							error = Errors.BATCH_GROOVY_SCRIPT_NOT_EXISTS;
							break;
						case INVALID_SPRING_BEAN:
							error = Errors.BATCH_INVALID_SPRING_BEAN;
							break;
						default:
							break;
					}
				}
			}
		} finally {
			if(error != null) {
				ajaxResponse.addError(new ErrorToken(error));
				ajaxResponse.setStatus(500);
			} else {
				ajaxResponse.setRedirectURL(String.format("batchTask.html?id=%s", wsResponse.getResponseValue()));
				ajaxResponse.setStatus(200);
				ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.BATCH_TASK_SAVE_SUCCESS));
			}
			ajaxResponse.process(localeResolver, messageSource, request);
		}
		return ajaxResponse;
	}
}
