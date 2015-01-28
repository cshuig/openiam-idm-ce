package org.openiam.ui.selfservice.web.mvc;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.bpm.activiti.ActivitiService;
import org.openiam.bpm.request.ActivitiClaimRequest;
import org.openiam.bpm.request.ActivitiRequestDecision;
import org.openiam.bpm.request.HistorySearchBean;
import org.openiam.bpm.response.ProcessWrapper;
import org.openiam.bpm.response.TaskHistoryWrapper;
import org.openiam.bpm.response.TaskListWrapper;
import org.openiam.bpm.response.TaskWrapper;
import org.openiam.ui.dozer.DozerBeanMapper;
import org.openiam.ui.security.OpenIAMCookieProvider;
import org.openiam.ui.selfservice.web.model.HistorySearchModel;
import org.openiam.ui.selfservice.web.model.TaskHistoryBean;
import org.openiam.ui.selfservice.web.model.TaskHistoryTreeBeanResponse;
import org.openiam.ui.selfservice.web.model.TaskWrapperBean;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.model.BeanResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ActivitiController {
	
    @Autowired
    @Qualifier("dozerBeanMapper")
    protected DozerBeanMapper mapper;
	
	@Resource(name="activitiClient")
	private ActivitiService activitiService;
	
	@Autowired
	private OpenIAMCookieProvider cookieProvider;

	@RequestMapping(value="/myTasks",method=RequestMethod.GET)
	public String myTasks(final HttpServletRequest request) {
		return "activiti/myTasks";
	}
	
	@RequestMapping(value="/task",method=RequestMethod.GET)
	public String myTasks(final HttpServletRequest request,
						  final HttpServletResponse response,
						  final @RequestParam(required=true,value="id") String taskId) throws IOException {
		final TaskWrapper taskWrapper = activitiService.getTask(taskId);
		if(StringUtils.isNotBlank(taskWrapper.getCustomObjectURI())) {
			response.sendRedirect(taskWrapper.getCustomObjectURI());
			return null;
		} else {
			request.setAttribute("taskWrapper", taskWrapper);
			return "activiti/task";
		}
	}
	
	@RequestMapping(value="/taskHistoryInstance",method=RequestMethod.GET)
	public String taskHistoryProcess(final HttpServletRequest request,
									 final @RequestParam(required=false, value="taskId") String taskId,
									 final @RequestParam(required=false, value="id") String executionId) {
		request.setAttribute("taskId", taskId);
		request.setAttribute("executionId", executionId);
		request.setAttribute("task", activitiService.getTaskFromHistory(executionId, taskId));
		return "activiti/taskHistoryInstance";
	}
	
	@RequestMapping(value="/taskHistory",method=RequestMethod.GET)
	public String taskHistory(final HttpServletRequest request) {
		return "activiti/history";
	}
}
