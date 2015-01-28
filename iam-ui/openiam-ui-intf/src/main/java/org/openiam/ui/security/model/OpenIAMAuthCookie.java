package org.openiam.ui.security.model;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class OpenIAMAuthCookie {

	private String principal;
	private String userId;
	private String token;
	private String tokenType;
	private Date expriationTime;

	public OpenIAMAuthCookie(final String userId, final String principal, final String token, final String tokenType, final Date expriationTime) {
		this.userId = userId;
		this.token = token;
		this.tokenType = tokenType;
		this.principal = principal;
		this.expriationTime = expriationTime;
	}

	public String getUserId() {
		return userId;
	}

	public String getToken() {
		return token;
	}

	public String getTokenType() {
		return tokenType;
	}
	
	public String getPrincipal() {
		return principal;
	}
	
	public Date getExpriationTime() {
		return expriationTime;
	}

	public boolean isExpired() {
		boolean retVal = false;
		if(expriationTime != null) {
			retVal = new Date().after(expriationTime);
		}
		return retVal;
	}

	public String tokenizeValue() {
		if(expriationTime != null) {
			return String.format("%s|%s|%s|%s|%s", userId, principal, token, tokenType, expriationTime.getTime());
		} else {
			return String.format("%s|%s|%s|%s", userId, principal, token, tokenType);
		}
	}
	
	/**
	 * @param value - the decrypted Cookie Value
	 * @return - the token represented by the value
	 */
	public static OpenIAMAuthCookie getToken(final String value) {
		OpenIAMAuthCookie retVal = null;
		if(StringUtils.isNotBlank(value)) {
			final String[] split = StringUtils.split(value, "|");
			if(split != null && split.length >= 3) {
				final String userId = StringUtils.trimToNull(split[0]); /* is not null only when cookie renew is disabled */
				final String principal = StringUtils.trimToNull(split[1]);
				final String token = StringUtils.trimToNull(split[2]);
				final String tokenType = StringUtils.trimToNull(split[3]);
				Date expriationTime = null;
				try {
					expriationTime = new Date(Long.valueOf(StringUtils.trimToNull(split[4])).longValue());
				} catch(Throwable e) {
					
				}
				retVal = new OpenIAMAuthCookie(userId, principal, token, tokenType, expriationTime);
			}
		}
		return retVal;
	}

	@Override
	public String toString() {
		return String
				.format("OpenIAMAuthCookie [principal=%s, userId=%s, token=%s, tokenType=%s, expriationTime=%s]",
						principal, userId, token, tokenType, expriationTime);
	}
	
	
}
