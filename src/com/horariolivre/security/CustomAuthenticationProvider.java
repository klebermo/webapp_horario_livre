package com.horariolivre.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.horariolivre.dao.UsuarioHome;
import com.horariolivre.entity.Usuario;
import com.horariolivre.service.AuthenticationService;

@Component
@ComponentScan(value="com.horariolivre")
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private UsuarioHome usuario;
	
	public CustomAuthenticationProvider() {
		super();
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		System.out.println("CustomAuthenticationProvider.authenticate");
        
        UserDetails user = authenticationService.loadUserByUsername(authentication.getName());
        Usuario user2 = usuario.findByUsername(user.getUsername());
                
        if (user2 != null) {
        	if(user.getPassword().equals(user2.getSenha())) {
        	    Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
	            return auth;
        	}
        	else
        		return null;
        }
        else {
        	return null;
        }
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
}
