package com.horariolivre.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.horariolivre.dao.UsuarioHome;
import com.horariolivre.entity.Autorizacoes;
import com.horariolivre.entity.Usuario;

@Component
public class AuthenticationService implements UserDetailsService {

	@Autowired
	private UsuarioHome accountDao;

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    Usuario account = accountDao.findByUsername(username);
	    
    	if(account == null) {
    		throw new UsernameNotFoundException("No such user: " + username);
    	}
	    
    	List<Autorizacoes> auth = account.getAutorizacoes();
    	
    	if(auth.isEmpty()) {
    		throw new UsernameNotFoundException("User " + username + " has no authorities");
    	}
		
	    boolean accountNonExpired = true;
	    boolean credentialsNonExpired = true;
	    boolean accountNonLocked = true;
	    boolean accountEnabled = true;
	
	    return new User(account.getLogin(), account.getSenha(), accountEnabled, accountNonExpired, credentialsNonExpired, accountNonLocked, getAuthorities(account.getAutorizacoes()));
	}
	
	public List<String> getRolesAsList(List<Autorizacoes> list) {
	    List <String> rolesAsList = new ArrayList<String>();
	    for(Autorizacoes role : list){
	        rolesAsList.add(role.getNome());
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
	
	public Collection<? extends GrantedAuthority> getAuthorities(List<Autorizacoes> list) {
	    List<GrantedAuthority> authList = getGrantedAuthorities(getRolesAsList(list));
	    return authList;
	}
	
}