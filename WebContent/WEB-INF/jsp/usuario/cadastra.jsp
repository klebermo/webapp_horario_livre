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
			<table class="bordered cadastro">
				    <thead>
					    <tr>
					        <th>Atributo</th>
					        <th>Valor</th>
					    </tr>
				    </thead>
				    
				    <tfoot>
						<tr>
							<td> <button type="submit" class="btn btn-lg btn-primary">Cadastrar</button> </td>
							<td> <div id="result_cad_usuario"></div> </td>
						</tr>
				    </tfoot>
				    
				    <tbody>
						<tr>
							<td> Login:</td>
							<td> <input type="text" name="login" value="" size=20 maxlength=40> </td>
						</tr>
						
						<tr>
							<td> Digite uma Senha:</td>
							<td> <input type="password" value="" name="senha1" size=20 maxlength=40> </td>
						</tr>
						
						<tr>
							<td> Repita a Senha: </td>
							<td> <input type="password" value="" name="senha2" size=20 maxlength=40> </td>
						</tr>
						
						<tr>
							<td> Primeiro Nome: </td>
							<td> <input type="text" name="pnome" value="" size=20 maxlength=40> </td>
						</tr>
						
						<tr>
							<td> Ultimo Nome: </td>
							<td> <input type="text" name="unome" value="" size=20 maxlength=40> </td>
						</tr>
				    </tbody>
				</table>
			</form>

<script>
$(document).ready(function(){
	var obj_tipo = jQuery.parseJSON( '${lista_tipos}' );
	var obj_campo = jQuery.parseJSON( '${lista_campos}' );
	
	var newRow = $('<tr>');
	col_1 = '<td> Tipo: </td>';
	col_2 = $('<td></td>');
	var select = $('<select name="tipo">');
	for(var item in obj_tipo.Tipo)
	    select.append('<option value="'+obj_tipo.Tipo[item].nome+'">'+obj_tipo.Tipo[item].nome+'</option>');

	select.appendTo(col_2);
	newRow.append(col_1);
	newRow.append(col_2);

	$("table.cadastro").append(newRow);
	
	for(var item in obj_campo.Key)
		$("table.cadastro").append('<tr> <td> '+obj_campo.Key[item].key+' : </td> <td> <input type="text" name="'+obj_campo.Key[item].key+'" size=20 maxlenght=40> </td> <tr>');
});
</script>

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
    			  $( "#result_cad_usuario" ).empty().append( "Usuario cadastrado com sucesso" );
    		  else
    			  $( "#result_cad_usuario" ).empty().append( "Usuario n&atilde;o cadastrado" );
    		  
    		  $('#target').each (function(){
    			  this.reset();
    		  });
    	  });
    	});
    </script>
    
</body>
</html>