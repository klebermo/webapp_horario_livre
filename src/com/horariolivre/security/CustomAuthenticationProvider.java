package com.horariolivre.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.horariolivre.dao.UsuarioHome;
import com.horariolivre.entity.AutorizacoesUsuario;
import com.horariolivre.entity.Usuario;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private UsuarioHome usuario;
	
	public CustomAuthenticationProvider() {
		super();
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		System.out.println("CustomAuthenticationProvider.authenticate");
		
		String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        Usuario user = usuario.findByUsername(username);
                
        if (user != null) {
        	if(user.getSenha().equals(password)) {        	    
	            Authentication auth = new UsernamePasswordAuthenticationToken(username, password, null);
	            return auth;
        	}
        	else {
        		return null;
        	}
        } else {
        	return null;
        }
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	public List<String> getRolesAsList(List<AutorizacoesUsuario> list) {
	    List <String> rolesAsList = new ArrayList<String>();
	    for(AutorizacoesUsuario role : list){
	        rolesAsList.add(role.getAutorizacoes().getNome());
	    }
	    return rolesAsList;
	}
	
	public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
	    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	    for (String role : roles) {
	        authorities.add(new SimpleGrantedAuthority(role));
	    }
	    return authorities;
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities(List<AutorizacoesUsuario> list) {
	    List<GrantedAuthority> authList = getGrantedAuthorities(getRolesAsList(list));
	    return authList;
	}
}
