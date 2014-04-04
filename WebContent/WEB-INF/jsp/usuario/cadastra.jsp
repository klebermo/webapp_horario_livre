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
					<p>Login: <input type="text" name="login" size=20 maxlength=40> </p>
					
					<p>Digite uma Senha: <input type="password" name="senha1" value=" " size=20 maxlength=40> </p>
					
					<p>Repita a Senha: <input type="password" name="senha2" value=" " size=20 maxlength=40> </p>
					
					<p>Primeiro Nome: <input type="text" name="pnome" value=" " size=20 maxlength=40> </p>
					
					<p>Ultimo Nome: <input type="text" name="unome" value=" " size=20 maxlength=40> </p>
					
					<p>Tipo:
						<select name="tipo">
							<c:forEach var="tipos" items="${tipos}">
								<option value=<c:out value="${tipos.id}"/> > <c:out value="${tipos.nome}"/> </option>
						    </c:forEach>
						</select>
					</p>
					
					<c:forEach var="campo" items="${campos}" varStatus="status">
						<p>${campo}: <input type="text" name="<c:out value="${campo}"/>" value=" " size=20 maxlength=40> </p>
					</c:forEach>
	<p><button type="submit" class="btn btn-lg btn-primary">Cadastrar</button></p>
	</form>
	
	<div class="alert alert-info">
		<strong>Resultado</strong> <div id="result"></div>
	</div>
	
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
    		  if(data == "yes")
    			  $( "#result" ).empty().append( "Usuario cadastrado com sucesso" );
    		  else
    			  $( "#result" ).empty().append( "Usuario n&atilde;o cadastrado" );
    	  });
    	});
    </script>

</body>
</html>