package org.openiam.idm.srvc.batch.birt;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
//import org.eclipse.birt.report.engine.api.*;
import org.eclipse.core.internal.registry.RegistryProviderFactory;
import org.openiam.idm.srvc.msg.service.MailService;


public class ReportGenerator {
	private static ResourceBundle res = ResourceBundle.getBundle("securityconf");
	private static final Log log = LogFactory.getLog(ReportGenerator.class);

	public static void generateReport(String reportName, String reportDesign, String format, String userId, Map params, String deliveryMethod, List<String> emailAddresses, List<String> userIds, MailService mailService) throws BirtException, EngineException {
		EngineConfig config = new EngineConfig( );
		Platform.startup( config );  //If using RE API in Eclipse/RCP application this is not needed.
		IReportEngineFactory factory = (IReportEngineFactory) Platform
				.createFactoryObject( IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY );
		IReportEngine engine = factory.createReportEngine( config );
		IReportRunnable design = engine.openReportDesign(res.getString("reportRoot") + "//" + reportDesign);//e.g "C:/ficus/openIAM/repository/openiam-idm-ce/distributions/configurations/idmreports/AuditReport.rptdesign" 
		IRunAndRenderTask task = engine.createRunAndRenderTask(design); 
		
		//Set parameter values and validate
		Set<String> paramKeySet = params.keySet();
		for (String key: paramKeySet){
			task.setParameterValue(key, params.get(key));
		}
		
		//task.setParameterValue("ACTION_ID", ("AUTHENTICATION"));
		//task.setParameterValue("ACTION_DATETIME_START", ("2011-01-01"));
		//task.setParameterValue("ACTION_DATETIME_END", ("2013-01-01"));
		
		task.validateParameters();

		String outputDir = res.getString("reportRoot") + "//GENERATED_REPORTS//" + deliveryMethod + "//" ;
		String outputFileName = outputDir + userId + "//" + reportName;
		if ("HTML".equalsIgnoreCase(format)){
			//Setup rendering to HTML
			HTMLRenderOption options = new HTMLRenderOption();
			outputFileName += ".html";
			options.setOutputFileName(outputFileName);
			options.setOutputFormat(format); // HTML or PDF
			//Setting this to true removes html and body tags
			options.setEmbeddable(false);
			task.setRenderOption(options);
		}else{
			//Setup rendering to HTML
			PDFRenderOption options = new PDFRenderOption();
			outputFileName += ".pdf";
			options.setOutputFileName(outputFileName);
			options.setOutputFormat(format); // HTML or PDF
			//Setting this to true removes html and body tags
			task.setRenderOption(options);
		}
		//run and render report
		task.run();
		task.close();
		engine.destroy();
		Platform.shutdown();
		//Bugzilla 351052
		RegistryProviderFactory.releaseDefault();
		
		//Email the reports or replicate across userids as applicable
		if ("EMAIL".equalsIgnoreCase(deliveryMethod)){
			//email reports
			//TODO check fromAddress
			mailService.send("BIRTReportSender", (String[])emailAddresses.toArray(), "Subscribed Report", "Please find your subscribed report attached.", false, outputFileName);
		}else{
			//replicate across userIds
			File file = new File(outputFileName);
			for (String userID : userIds){
				File destDir = new File(outputDir + "//" + userID);
				try {
					FileUtils.copyFileToDirectory(file, destDir);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static void deleteGeneratedDirs() {
		File dir = new File(res.getString("reportRoot") + "//GENERATED_REPORTS");
		File[] files = dir.listFiles();
		for (File file: files){
			boolean deleted = file.delete();
			if (!deleted){
				log.warn("File " + file.getName() + " could not be deleted.");
			}
		}
	}

}
