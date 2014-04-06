<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cadastro de Usu&aacute;rios</title>
</head>
<body>

	<form method="post" action="<c:out value="${pageContext.request.contextPath}/usuario/cadastra_usuario"/>" id="target">
			<table class="bordered">
				    <thead>
				    <tr>    
				        <th>Atributo</th>
				        <th>Valor</th>
				    </tr>
				    </thead>
				    <tfoot>
				    <tr>
				        <td></td>        
				        <td></td>
				    </tr>
				    </tfoot>
				    
				    <tr>
						<td> Login:</td> <td> <input type="text" name="login" value="" size=20 maxlength=40> </td>
					</tr>
					
				    <tr>
						<td> Digite uma Senha:</td> <td> <input type="password" value="" name="senha1" size=20 maxlength=40> </td>
					</tr>
					
					<tr>
						<td> Repita a Senha: </td> <td> <input type="password" value="" name="senha2" size=20 maxlength=40> </td>
					</tr>
					
					<tr>
						<td> Primeiro Nome: </td> <td> <input type="text" name="pnome" value="" size=20 maxlength=40> </td>
					</tr>
					
					<tr>
						<td> Ultimo Nome: </td> <td> <input type="text" name="unome" value="" size=20 maxlength=40> </td>
					</tr>
					
					<tr>
						<td> Tipo: </td> <td> <select name="tipo">
							<c:forEach var="tipos" items="${tipos}">
								<option value=<c:out value="${tipos.id}"/> > <c:out value="${tipos.nome}"/> </option>
						    </c:forEach>
						</select> </td>
					</tr>
					
					<c:forEach var="campo" items="${campos}">
					<tr>
						<td>${campo}:</td> <td> <input type="text" name="${campo}" value="" size=20 maxlength=40> </td>
					</tr>
					</c:forEach>
					
					<tr>
						<td> <button type="submit" class="btn btn-lg btn-primary">Cadastrar</button> </td>
						<td> <div id="result"></div> </td>
					</tr>
				</table>
			</form>
			
    <script>
    $( "#target" ).submit(function( event ) {
    	  // Stop form from submitting normally
    	  event.preventDefault();
    	 
    	  // Get some values from elements on the page:
    	  var $form = $( this ),
    	  	url = $form.attr( "action" );
    	 
    	  // Send the data using post
    	  var posting = $.post( url, $(this).serialize() );
    	 
    	  // Put the results in a div
    	  posting.done(function( data ) {
    		  alert("done submit cadastro_usuario: "+data);
    		  
    		  if(data == "yes")
    			  $( "#result" ).empty().append( "Usuario cadastrado com sucesso" );
    		  else
    			  $( "#result" ).empty().append( "Usuario n&atilde;o cadastrado" );
    		  
    		  $('#target').each (function(){
    			  this.reset();
    		  });
    	  });
    	  posting.fail(function( data ) {
    		  alert("fail submit cadastra_evento: "+data);
    	  });
    	});
    </script>
    
</body>
</html>