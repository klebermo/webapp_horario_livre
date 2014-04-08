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
			<table class="cadastro" id="hor-minimalist-a">
				    <thead>
					    <tr>
					        <th>Atributo</th>
					        <th>Valor</th>
					    </tr>
				    </thead>
				    
				    <tfoot>
						<tr>
							<td> <button type="submit" class="btn btn-lg btn-primary">Cadastrar</button> </td>
							<td> <div id="result"></div> </td>
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
$("document").ready(function(){
	var obj_tipo = jQuery.parseJSON( "${lista_tipos}" );
	var obj_campo = jQuery.parseJSON( "${lista_campos}" );
	
	var newRow = $('<tr>');
	col_1 = '<td> Tipo: </td>';
	col_2 = '<td> <select name="tipo"> </select> </td> </tr>';
	for(var nome in obj_tipo)
		col_2.append('<option value="'+nome+'">'+nome+'</option>');
	
	newRow.append(col_1);
	newRow.append(col_2);
	
	$("table.cadastro").append(newRow);
	
	col_3 = '';
	for(var nome in obj_campo)
		col_3 += '<tr> <td> '+nome+' : </td> <td> <input type="text" name="'+nome+'" value="'+nome+'" size=20 maxlenght=40> </td> <tr>';
	
	$("table.cadastro").append(col_3);
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
    			  $( "#result" ).empty().append( "Usuario cadastrado com sucesso" );
    		  else
    			  $( "#result" ).empty().append( "Usuario n&atilde;o cadastrado" );
    		  
    		  $('#target').each (function(){
    			  this.reset();
    		  });
    	  });
    	});
    </script>
    
</body>
</html>