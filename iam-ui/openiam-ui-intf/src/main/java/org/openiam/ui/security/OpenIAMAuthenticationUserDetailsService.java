package org.openiam.ui.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class OpenIAMAuthenticationUserDetailsService implements AuthenticationUserDetailsService<Authentication> {

	@Override
	public UserDetails loadUserDetails(final Authentication token) throws UsernameNotFoundException {
		final String pricinpal = (String)token.getPrincipal();
		final String credentials = (String)token.getCredentials();
		return new User(pricinpal, credentials, true, true, true, true, buildAuthorities());
	}

	private Set<GrantedAuthority> buildAuthorities() {
		final Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		grantedAuthorities.add(new GrantedAuthorityImpl("ROLE_USER"));
		return grantedAuthorities;
	}
}
