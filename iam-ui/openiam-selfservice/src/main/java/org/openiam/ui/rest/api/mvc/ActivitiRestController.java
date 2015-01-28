package org.openiam.ui.rest.api.mvc;

import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.bpm.request.ActivitiClaimRequest;
import org.openiam.bpm.request.ActivitiRequestDecision;
import org.openiam.bpm.request.HistorySearchBean;
import org.openiam.bpm.response.TaskHistoryWrapper;
import org.openiam.bpm.response.TaskListWrapper;
import org.openiam.bpm.response.TaskWrapper;
import org.openiam.ui.selfservice.web.model.TaskHistoryTreeBeanResponse;
import org.openiam.ui.selfservice.web.model.TaskSummary;
import org.openiam.ui.selfservice.web.model.TaskWrapperBean;
import org.openiam.ui.web.mvc.AbstractActivitiController;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.model.BeanResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;

@Controller
public class ActivitiRestController extends AbstractActivitiController {
	
	@RequestMapping(value="/activiti/summary",method=RequestMethod.GET)
	public @ResponseBody TaskSummary getTaskSummary(final HttpServletRequest httpRequest) {
		final int numOfAssignedTasks = activitiService.getNumOfAssignedTasks(getRequesterId(httpRequest));
		final int numOfCandidateTasks = activitiService.getNumOfCandidateTasks(getRequesterId(httpRequest));
		return new TaskSummary(numOfAssignedTasks, numOfCandidateTasks);
	}
	
	@RequestMapping(value="/activiti/task/executiongroup/find",method=RequestMethod.GET)
	public @ResponseBody TaskHistoryTreeBeanResponse findTaskExecutionGroup(@RequestParam(required=false, value="id") String executionId) {
		final List<TaskHistoryWrapper> wrappers = activitiService.getHistoryForInstance(executionId);
		return new TaskHistoryTreeBeanResponse(wrappers);
	}
	
	@RequestMapping(value="/activiti/task/history/search",method=RequestMethod.GET)
	@ResponseBody BeanResponse historySearch(final HttpServletRequest request, 
											 final @RequestParam(required=true, value="from") int from,
											 final @RequestParam(required=true, value="size") int size) {
		final HistorySearchBean searchBean = new HistorySearchBean();
		searchBean.setAssigneeId(cookieProvider.getUserId(request));
		final int count = activitiService.count(searchBean);
		final List<TaskWrapper> beans = activitiService.getHistory(searchBean, from, size);
		return new BeanResponse(mapper.mapToList(beans, TaskWrapperBean.class), count);
	}

	@RequestMapping(value="/activiti/tasks/candidate", method=RequestMethod.GET)
	public @ResponseBody BeanResponse candidateTasks(final HttpServletRequest request,
													 final @RequestParam(required=true, value="from") int from,
													 final @RequestParam(required=true, value="size") int size) {
		List<TaskWrapper> results = new LinkedList<>();
		final TaskListWrapper wrapper = activitiService.getTasksForUser(getRequesterId(request), from, size);
		if(wrapper != null) {
			results = wrapper.getCandidateTasks();
		}
		
		final List<TaskWrapperBean> beans = mapper.mapToList(results, TaskWrapperBean.class);
		return new BeanResponse(beans, activitiService.getNumOfCandidateTasks(getRequesterId(request)));
	}
	
	@RequestMapping(value="/activiti/tasks/assigned", method=RequestMethod.GET)
	public @ResponseBody BeanResponse assignedTasks(final HttpServletRequest request,
													final @RequestParam(required=true, value="from") int from,
													final @RequestParam(required=true, value="size") int size) {
		List<TaskWrapper> results = new LinkedList<>();
		final TaskListWrapper wrapper = activitiService.getTasksForUser(getRequesterId(request), from, size);
		if(wrapper != null) {
			results = wrapper.getAssignedTasks();
		}
		
		final List<TaskWrapperBean> beans = mapper.mapToList(results, TaskWrapperBean.class);
		return new BeanResponse(beans, activitiService.getNumOfAssignedTasks(getRequesterId(request)));
	}
	
	
	@RequestMapping(value="/activiti/task", method=RequestMethod.GET)
	public @ResponseBody TaskWrapper getTask(final HttpServletRequest request, 
											 final @RequestParam(value="id", required=true) String id) {
		return activitiService.getTask(id);
	}
	
	@RequestMapping(value="/activiti/task/delete", method=RequestMethod.POST)
	public @ResponseBody BasicAjaxResponse deleteTask(final HttpServletRequest request, 
												 final HttpServletResponse response,
												 final @RequestParam(required=true, value="id") String taskId) {
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		
		final String userId = cookieProvider.getUserId(request);
		final Response wsResponse = activitiService.deleteTaskForUser(taskId, userId);
		Errors error = null;
		try {
			if(wsResponse == null) {
				error = Errors.COULD_NOT_DELETE_TASK;
			}
			if(wsResponse.getStatus() == ResponseStatus.FAILURE) {
				error = Errors.COULD_NOT_DELETE_TASK;
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
				ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.TASK_DELETED));
				ajaxResponse.setStatus(200);
			}
			ajaxResponse.process(localeResolver, messageSource, request);
		}
		return ajaxResponse;
	}
	
	@RequestMapping(value="/activiti/task/claim", method=RequestMethod.POST)
	public @ResponseBody BasicAjaxResponse claim(final HttpServletRequest request, 
												 final HttpServletResponse response,
												 final @RequestParam(required=true, value="id") String taskId) {
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		
		final ActivitiClaimRequest claimRequest = new ActivitiClaimRequest();
		claimRequest.setTaskId(taskId);
		claimRequest.setRequestorUserId(cookieProvider.getUserId(request));
		claimRequest.setRequestClientIP(request.getRemoteAddr());
		final Response wsResponse = activitiService.claimRequest(claimRequest);
		Errors error = null;
		try {
			if(wsResponse == null) {
				error = Errors.COULD_NOT_CLAIM_TASK;
			}
			if(wsResponse.getStatus() == ResponseStatus.FAILURE) {
				error = Errors.COULD_NOT_CLAIM_TASK;
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
				ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.TASK_CLAIMED));
				ajaxResponse.setStatus(200);
			}
			ajaxResponse.process(localeResolver, messageSource, request);
		}
		return ajaxResponse;
	}
	
	@RequestMapping(value="/activiti/task/decision", method=RequestMethod.POST)
	public @ResponseBody BasicAjaxResponse makeDecision(final HttpServletRequest request, 
														final HttpServletResponse response, 
														final @RequestBody ActivitiRequestDecision decision) {
//		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
//
//		decision.setRequestorUserId(cookieProvider.getUserId(request));
//		final Response wsResponse = activitiService.makeDecision(decision);
//		Errors error = null;
//		try {
//			if(wsResponse == null) {
//				error = Errors.INTERNAL_ERROR;
//			}
//			if(wsResponse.getStatus() == ResponseStatus.FAILURE) {
//				error = Errors.INTERNAL_ERROR;
//				if(wsResponse.getErrorCode() != null) {
//					switch(wsResponse.getErrorCode()) {
//						default:
//							break;
//					}
//				}
//			}
//		} finally {
//			if(error != null) {
//				ajaxResponse.addError(new ErrorToken(error));
//				ajaxResponse.setStatus(500);
//			} else {
//				ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.TASK_COMPLETED));
//				ajaxResponse.setRedirectURL("myTasks.html");
//				ajaxResponse.setStatus(200);
//			}
//			ajaxResponse.process(localeResolver, messageSource, request);
//		}
		return doDecision(request, response, decision);
	}
}
