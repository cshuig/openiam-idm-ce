package org.openiam.ui.webconsole.idm.web.mvc.provisioning.batch;

import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openiam.idm.srvc.batch.dto.BatchTask;
import org.openiam.ui.web.util.DateFormatStr;

public class BatchTaskRequestBean extends BatchTask {

	private static final Logger LOG = Logger.getLogger(BatchTaskRequestBean.class);
	
	private String runOnAsString;

	public String getRunOnAsString() {
		return runOnAsString;
	}

	public void setRunOnAsString(String runOnAsString) {
		this.runOnAsString = runOnAsString;
	}
	
	public void doDateRithmetic() {
		final SimpleDateFormat sdf = new SimpleDateFormat(DateFormatStr.getSdfDate());
		if(StringUtils.isNotBlank(runOnAsString)) {
			try {
				setRunOn(sdf.parse(runOnAsString));
			} catch(Throwable e) {
				LOG.error(String.format("Can't parse date: %s", runOnAsString), e);
			}
		} else if(getRunOn() != null) {
			try {
				runOnAsString = sdf.format(getRunOn());
			} catch(Throwable e) {
				LOG.error(String.format("Can't parse date: %s", getRunOn()), e);
			}
		}
	}
}
