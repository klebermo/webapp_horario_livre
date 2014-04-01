package com.horariolivre.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.horariolivre.entity.Usuario;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
		System.out.println("CustomAuthenticationSuccessHandler");
		HttpSession session = request.getSession();
		SavedRequest savedReq = (SavedRequest) session.getAttribute(WebAttributes.ACCESS_DENIED_403);
		if (savedReq == null) {
			Usuario user = (Usuario) auth.getPrincipal();
			System.out.println("user="+user.getPrimeiroNome()+" "+user.getUltimoNome());
			
			request.getSession().setAttribute("usuario", user);
		    response.sendRedirect(request.getContextPath() + "/acesso/start");
		}
		else {
		    response.sendRedirect(savedReq.getRedirectUrl());
		}
	}

}
