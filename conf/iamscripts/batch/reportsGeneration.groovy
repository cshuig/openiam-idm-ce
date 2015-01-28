import org.openiam.idm.groovy.helper.ServiceHelper;
import org.openiam.idm.srvc.report.ws.ReportWebService;

System.out.println("Running script: reportsGeneration.groovy");
def ReportWebService reportService = ServiceHelper.reportService();
reportService.runAllActiveSubscriptions();

output=1
