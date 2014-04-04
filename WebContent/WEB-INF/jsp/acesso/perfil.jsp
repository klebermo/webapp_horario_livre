<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Perfil</title>
</head>
<body>

<div class="alert alert-info">
	<strong>Perfil de ${usuario.login}</strong> Segue abaixo os dados cadastrais do usu&aacute;rio <i>${usuario.primeiroNome} ${usuario.ultimoNome}</i>.
</div>
      
<div class="container">
	<form method="post" action="#" id="target">
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
						<td> Digite uma Senha:</td> <td> <input type="password" name="senha1" size=20 maxlength=40> </td>
					</tr>
					
					<tr>
						<td> Repita a Senha: </td> <td> <input type="password" name="senha2" size=20 maxlength=40> </td>
					</tr>
					
					<tr>
						<td> Primeiro Nome: </td> <td> <input type="text" name="pnome" value="${usuario.primeiroNome}" size=20 maxlength=40> </td>
					</tr>
					
					<tr>
						<td> Ultimo Nome: </td> <td> <input type="text" name="unome" value="${usuario.ultimoNome}" size=20 maxlength=40> </td>
					</tr>
					
					<tr>
						<td> Tipo: </td> <td> <select name="tipo">
							<c:forEach var="tipos" items="${tipos}">
								<option value=<c:out value="${tipos.id}"/> > <c:out value="${tipos.nome}"/> </option>
						    </c:forEach>
						</select> </td>
					</tr>
					
					<c:forEach var="campo" items="${chave}">
						<tr>
							<td>${campo}:</td> <td> <input type="text" name="${campo}" size=20 maxlength=40> </td>
						</tr>
					</c:forEach>
			</table>
	<p><button type="submit" class="btn btn-lg btn-primary">Cadastrar</button></p>
	</form>
	<div id="result"> </div>
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
    	    // var content = $( data ).find( "#content" );
    	    $( "#result" ).empty().append( data );
    	  });
    	});
    </script>

</body>
</html>