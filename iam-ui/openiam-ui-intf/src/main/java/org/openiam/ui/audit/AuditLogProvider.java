package org.openiam.ui.audit;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.openiam.idm.srvc.audit.constant.AuditSource;
import org.openiam.idm.srvc.audit.dto.IdmAuditLog;
import org.openiam.idm.srvc.audit.ws.IdmAuditLogWebDataService;
import org.openiam.thread.Sweepable;
import org.springframework.stereotype.Component;

@Component("auditLogProvider")
public class AuditLogProvider implements Sweepable {
	
	@Resource(name="auditServiceClient")
	private IdmAuditLogWebDataService auditWS;
	
	private ConcurrentLinkedQueue<IdmAuditLog> queue = new ConcurrentLinkedQueue<>();

	public void sweep() {
		final List<IdmAuditLog> builderList = new LinkedList<>();
		while(queue.peek() != null) {
			builderList.add(queue.poll());
		}
        if(builderList.size() > 0) {
		    auditWS.addLogs(builderList);
        }
	}
	
	public void add(final AuditSource source, final HttpServletRequest request, final IdmAuditLog idmAuditLog) {
		if(idmAuditLog != null) {
            idmAuditLog.setSource(source.value());
            idmAuditLog.setClientIP(request.getRemoteAddr());
			if(request.getSession() != null) {
                idmAuditLog.setSessionID(request.getSession().getId());
			}
			queue.add(idmAuditLog);
		}
	}

}
