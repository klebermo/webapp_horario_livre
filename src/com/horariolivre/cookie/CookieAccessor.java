package com.horariolivre.cookie;

import java.net.*;
import java.util.*;

public class CookieAccessor {
	
	private static final String endereco = "http://localhost:8080/HorarioLivre2";
	
	private static final String cookiename = "horario_livre_usuario";
    
    /**
     * Get cookies for a url from cookie store
     */
    public int getCookieUsingCookieHandler() { 
        try {       
            // instantiate CookieManager; make sure to set CookiePolicy
            CookieManager manager = new CookieManager();
            manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            CookieHandler.setDefault(manager);

            // get content from URLConnection; cookies are set by web site
            URL url = new URL(endereco);
            URLConnection connection = url.openConnection();
            connection.getContent();

            // get cookies from underlying CookieStore
            CookieStore cookieJar = manager.getCookieStore();
            List <HttpCookie> cookies = cookieJar.getCookies();
            for (HttpCookie cookie: cookies) {
            	System.out.println(cookie.getName()+"="+cookie.getValue());
            	if(cookie.getName().equals(cookiename))
            		return Integer.valueOf(cookie.getValue()).intValue();
            }
        } catch(Exception e) {
            System.out.println("Unable to get cookie using CookieHandler");
            e.printStackTrace();
        }
		return -1;
    } 
    
    /**
     * Set cookie in cookie store
     */
    public void setCookieUsingCookieHandler(int id) {
        try {
            // instantiate CookieManager
            CookieManager manager = new CookieManager();
            CookieHandler.setDefault(manager);
            CookieStore cookieJar = manager.getCookieStore();

            // create cookie
            HttpCookie cookie = new HttpCookie(cookiename, String.valueOf(id));

            // add cookie to CookieStore for a particular URL
            URL url = new URL(endereco);
            cookieJar.add(url.toURI(), cookie);
            System.out.println("Added cookie using cookie handler");
        } catch(Exception e) {
            System.out.println("Unable to set cookie using CookieHandler");
            e.printStackTrace();
        }
    }

 /**
   * Retrieves cookie from header fields in URL connection.
   * Another way to get cookies before recent changes to CookieManager
   */
    public int getCookieFromURLConn() {
        try {
            URL url = new URL(endereco);            
            URLConnection conn = url.openConnection();
            Map<String, List<String>> headers = conn.getHeaderFields(); 
            // the literal "Set-Cookie" may be capitalized differently in different web sites
            List<String> values = headers.get("Set-cookie");  

            if (values != null) {
                String cookieValue = null; 
                for (Iterator<String> iter = values.iterator(); iter.hasNext(); ) {
                	if(values.equals(cookiename)) {
                		return Integer.valueOf(cookieValue).intValue();
                	}
                	else {
                		cookieValue = (String)iter.next();
                	}  
                }
            } else {
                System.out.println("No cookies found");
            }
        } catch (Exception e) {
            System.out.println("Unable to get cookie from URL connection");
            e.printStackTrace();
        }
		return -1;
    }
    
    /**
     * Sets a custom cookie in request header
     * Another way to set cookies before recent changes to CookieManager
     */
    public void setCookieInURLConn(int id) {
        try {
            URL url = new URL( endereco );
            URLConnection conn = url.openConnection(); 
            conn.setRequestProperty("Cookie", cookiename+"="+String.valueOf(id)); 
            System.out.println("Set cookie in URL connection");            
        } catch(Exception e) {
            System.out.println("Unable to set cookie in URL connection");
            e.printStackTrace();
        }        
    }  
}
