<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Configura&ccedil;&otilde;es</title>
</head>
<body>

      <div class="jumbotron">
        <h1>Intervalos</h1>
        <form method="POST" action="save_config_usuario.html">
	        <p>Para qual intervalo do dia você deseja cadastrar horários?</p>
	        <p> De <input type="time" name="hora_inicial" value="${usuario.config.horaInicial}" pattern="\d{2}:\d{2}:\d{2}">
	        At&eacute; <input type="time" name="hora_final" value="${usuario.config.horaFinal}" pattern="\d{2}:\d{2}:\d{2}"> </p>
	        <button type="submit" class="btn btn-lg btn-default">Salvar</button>
        </form>
      </div>
      
      <div id="result"> </div>
    
    <script>
    $(document).ready(function(){
        $( "#target" ).submit(function( event ) {
       	 debugger;
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
    })
    </script>

</body>
</html>