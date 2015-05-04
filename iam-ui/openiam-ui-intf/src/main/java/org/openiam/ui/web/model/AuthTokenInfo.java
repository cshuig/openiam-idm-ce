package org.openiam.ui.web.model;

public class AuthTokenInfo {
	
	public AuthTokenInfo() {
		
	}

	private String authToken;
	private int timeToLiveSeconds;
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public int getTimeToLiveSeconds() {
		return timeToLiveSeconds;
	}
	public void setTimeToLiveSeconds(int timeToLiveSeconds) {
		this.timeToLiveSeconds = timeToLiveSeconds;
	}
	
	
	
}
