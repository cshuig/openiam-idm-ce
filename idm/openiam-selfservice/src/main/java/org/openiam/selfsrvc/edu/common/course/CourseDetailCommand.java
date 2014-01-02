package org.openiam.selfsrvc.edu.common.course;

import java.io.Serializable;

/**
 * Command object for the SelectCourses
 * @author suneet
 *
 */
public class CourseDetailCommand implements Serializable {
	 

	private static final long serialVersionUID = -667408382835178231L;

	private String from;
	private String subject;
	private String message;
	
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	


	

}
