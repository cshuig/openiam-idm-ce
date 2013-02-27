package org.openiam.spml2.msg;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openiam.idm.srvc.mngsys.dto.ManagedSys;
import org.openiam.idm.srvc.recon.dto.ReconciliationSituation;

public class ReconciliationHTMLReport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<ReconciliationHTMLRow> report = new ArrayList<ReconciliationHTMLRow>();

	public List<ReconciliationHTMLRow> getReport() {
		return report;
	}

	public void setReport(List<ReconciliationHTMLRow> report) {
		this.report = report;
	}

	@Override
	public String toString() {
		StringBuilder html = new StringBuilder();
		html.append("<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>");
		html.append("<html>");
		html.append("<head>");
		html.append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8'>");
		html.append("<title>Reconciliation result</title>");
		html.append("<style>");
		html.append("td {");
		html.append("font-size:14px;");
		html.append("}");

		html.append(".legend {");
		html.append("width:100%;");
		html.append("height:80px;");
		html.append("}");

		html.append(".legend-item {");
		html.append("width:10%;");
		html.append("height:100%;");
		html.append("padding:4px;");
		html.append("margin:3px;");
		html.append("float:left;");
		html.append("}");

		html.append(".clear {");
		html.append("clear:both;");
		html.append("}");

		html.append("</style>");
		html.append("</head>");
		html.append("<body>"); 
		html.append("<div>");
		html.append("<div style='position:fixed;top:0px;background-color:#ffffff'>");
		html.append("<h2>");
		html.append("Reconciliation result: ");
		html.append(Calendar.getInstance().getTime().toString());
		html.append("</h2>");
		html.append("<div class='legend'>");
		for (ReconciliationHTMLReportResults a : ReconciliationHTMLReportResults
				.values()) {
			html.append(this.legendItem(a));
		}
		html.append("<div class='clear' />");
		html.append("</div>");
		html.append("</div>");
		html.append("</div>");
		html.append("<table style='margin-top:170px;' width='100%' border='1' cellspacing='0' cellpadding='0'>");
		for (ReconciliationHTMLRow row : report) {
			html.append(row.toString());
		}
		html.append("</table>");
		html.append("</body>");
		html.append("</html>");

		return html.toString();
	}

	private String legendItem(ReconciliationHTMLReportResults a) {
		StringBuilder html = new StringBuilder();
		html.append("<div class='legend-item' style='background-color:"
				+ a.getColor() + ";'>");
		html.append("<p>");
		html.append(a.getValue());
		html.append("</p>");
		html.append("</div>");
		return html.toString();
	}

	public void save(String pathToCSV, ManagedSys mSys) throws IOException {

		StringBuilder sb = new StringBuilder(pathToCSV);
		sb.append("report_");
		sb.append(mSys.getManagedSysId());
		sb.append(mSys.getResourceId());
		sb.append(".html");

		FileWriter fw = new FileWriter(sb.toString());
		fw.append(this.toString());
		fw.flush();
		fw.close();
	}
}
