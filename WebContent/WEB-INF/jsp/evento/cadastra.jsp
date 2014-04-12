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
	<table id="hor-minimalist-a">
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
	    <tbody>
	    <tr>
	    	<td> Nome: </td>
			<td><input type="text" name="nome" size=20 maxlength=40> </td>
		</tr>
		
		<tr>
			<td> Descri&ccedil;&atilde;o: </td>
			<td><input type="text" name="descricao" size=30 maxlength=100> </tdv>
		</td>
		
		<tr>
			<td> <h3>Periodo da Data</h3> </td>
			<td>
				<table>
					<tr>
						<td>inicio: <input type="text" name="data_inicial" id="data_inicial"/> </td>
						<td>final: <input type="text" name="data_final" id="data_final"/> </td>
					</tr>
				</table>
			</td>
		</tr>
		
		<tr>
			<td> <h3>Periodo do Hor&aacute;rio</h3> </td>
			<td>
				<table>
					<tr>
						<td>inicio: <input type="text" name="hora_inicial" id="hora_inicial"/> </td>
						<td> final: <input type="text" name="hora_final" id="hora_final"/> </td>
					</tr>
				</table>
			</td>
		</tr>
		
		<tr>
			<td> Dura&ccedil;&atilde;o: </td>
			<td><input type="text" name="duracao" size=20 maxlength=2> Minutos </td>
		</tr>
		
		<tr>
			<td> <button type="submit" class="btn btn-lg btn-primary">Cadastrar</button> </td>
			<td> <div id="result_cad_evento"> </div> </td>
		</tr>
		</tbody>
	</table>
	</form>
</div>

	<script type="text/javascript">
		$(function(){
			$('#data_inicial').datepicker({
				inline: true,
				showOtherMonths: true,
				dateFormat: 'dd/mm/yy',
				dayNamesMin: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab'],

			});
			$('#data_final').datepicker({
				inline: true,
				showOtherMonths: true,
				dateFormat: 'dd/mm/yy',
				dayNamesMin: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab'],
			});
			$('#hora_inicial').timepicker();
			$('#hora_final').timepicker();
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
    			  $( "#result_cad_evento" ).empty().append( "Evento cadastrado com sucesso" );
    		  else
    			  $( "#result_cad_evento" ).empty().append( "Evento n&atilde;o cadastrado" );
    		  
    		  $("#target").each (function(){
    			  this.reset();
    		  });
    	  });
    	});
    </script>

</body>
</html>