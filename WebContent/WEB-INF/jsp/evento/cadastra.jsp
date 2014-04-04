<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cadastro de Evento</title>
</head>
<body>

<div class="alert alert-info">
	<strong>Novo</strong> Cadastre um novo evento.
</div>

<div class="container">
	<form method="post" action="<c:out value="${pageContext.request.contextPath}/evento/cadastra_evento"/>" id="target">
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
	    	<td> Nome: </td>
			<td><input type="text" name="nome" size=20 maxlength=40> </td>
		</tr>
		
		<tr>
			<td> Descri&ccedil;&atilde;o: </td>
			<td><input type="text" name="descricao" size=30 maxlength=100> </td>
		</tr>
		
		<tr>
			<td> <h3>Periodo da Data</h3> </td>
			<td>inicio: <input type="date" name="data_inicial" pattern="\d{2}/\d{2}/\d{4}" /> | final: <input type="date" name="data_final" pattern="\d{2}/\d{2}/\d{4}" /> </td>
		</tr>
		
		<tr>
			<td> <h3>Periodo do Hor&aacute;rio</h3> </td>
			<td>inicio: <input type="time" name="hora_inicial" pattern="\d{2}:\d{2}:\d{2}" /> | final: <input type="time" name="hora_final" pattern="\d{2}:\d{2}:\d{2}" /> </td>
		</tr>
		
		<tr>
			<td> Dura&ccedil;&atilde;o: </td>
			<td><input type="text" name="duracao" size=20 maxlength=2> Minutos </td>
		</tr>
		
		<tr>
			<td> <button type="submit" class="btn btn-lg btn-primary">Cadastrar</button> </td>
			<td> <div class="alert alert-info"> <strong>Resultado</strong> <div id="result"></div> </div> </td>
		</tr>
	</table>
	</form>
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
    			  $( "#result" ).empty().append( "Evento cadastrado com sucesso" );
    		  else
    			  $( "#result" ).empty().append( "Evento n&atilde;o cadastrado" );
    	  });
    	});
    </script>

</body>
</html>