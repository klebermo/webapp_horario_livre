<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Configura&ccedil;&otilde;es</title>
</head>
<body>

<h1>Intervalos</h1>

<form method="POST" action="<c:out value="${pageContext.request.contextPath}/usuario/salvar_config"/>" id="target">
<p>Para qual intervalo do dia você deseja cadastrar horários?</p>

<table>
<tr>
	<td>inicio: <input type="text" name="hora_inicial" value="${config.horaInicial}" id="hora_inicial"/> </td>
	<td> final: <input type="text" name="hora_final" value="${config.horaFinal}" id="hora_final"/> </td>
</tr>
</table>

<p><button type="submit" class="btn btn-lg btn-default">Salvar</button></p>

</form>


<div id="result"></div>

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
    			  $( "#result" ).empty().append( "Configura&ccedil;&otilde;es salvas com sucesso" );
    		  else
    			  $( "#result" ).empty().append( "Configura&ccedil;&otilde;es n&atilde;o cadastradas" );
    		  
    		  $('#target').each (function(){
    			  this.reset();
    		  });
    	  });
    	});
    </script>

</body>
</html>