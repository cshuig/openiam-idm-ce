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

public enum ReconciliationHTMLReportResults {
	BROKEN_CSV("Broken record in CSV","#ff6666"),
	NOT_EXIST_IN_IDM_DB("Record don't exist in DB, but exist in CSV","#aa7777"),
	NOT_UNIQUE_KEY("Defined key is not unique","#bbbbff"),
	IDM_DELETED("IDM Record status is 'Deleted'","#339933"),
	LOGIN_NOT_FOUND("Login for current user is not founded","#4444aa"),
	MATCH_FOUND("Records is matched","#66af66"),
	RESOURSE_DELETED("Resourse Deleted","#226622");
	
	String value;
	String color;
	
	ReconciliationHTMLReportResults(String value,String color) {
		this.value = value;
		this.color = color;
	}
	
	public String getValue() {
		return value;
	}
	
	public String getColor() {
		return color;
	}
}
