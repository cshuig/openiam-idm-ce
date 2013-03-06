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
	NOT_EXIST_IN_IDM_DB("Record don't exist in DB, but exist in CSV","#Af13a1"),
	NOT_UNIQUE_KEY("Defined key is not unique","#bbbbff"),
	IDM_DELETED(" Found in IDM but Marked as 'Deleted'","#afafaf"),
	LOGIN_NOT_FOUND("Login for current user is not founded","#4444aa"),
	MATCH_FOUND("Records is matched","#Af1"),
	MATCH_FOUND_DIFFERENT("Found in Both But Different","#449c44"),
	NOT_EXIST_IN_CSV("Found in IDM but not in CSV","#af9161");
	
	
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
