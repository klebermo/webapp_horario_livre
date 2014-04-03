<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cadastro de Evento</title>

    <!-- Bootstrap core CSS -->
    <link href="<c:out value="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="<c:out value="${pageContext.request.contextPath}/extras/css/starter-template.css"/>" rel="stylesheet">
    <link href="<c:out value="${pageContext.request.contextPath}/extras/css/table.css"/>" rel="stylesheet">
    <link href="<c:out value="${pageContext.request.contextPath}/extras/css/dialog.css"/>" rel="stylesheet">
    <link href="<c:out value="${pageContext.request.contextPath}/jquery/css/ui-lightness/jquery-ui-1.10.4.custom.min.css"/>" rel="stylesheet">

</head>
<body>

<div class="alert alert-info">
	<strong>Novo</strong> Cadastre um novo evento.
</div>

<div class="container">
	<form method="post" action="<c:out value="${pageContext.request.contextPath}/evento/cadastra_evento.html"/>" id="target">
		<p><input type="text" name="nome" placeholder="Nome" size=20 maxlength=40> </p>
		
		<p><input type="text" name="descricao" placeholder="Descri&ccedil;&atilde;o" size=30 maxlength=100> </p>
		
		<h3>Periodo da Data</h3>
		<p>inicio: <input type="date" name="data_inicial" pattern="\d{2}/\d{2}/\d{4}" /> </p>
		<p>final: <input type="date" name="data_final" pattern="\d{2}/\d{2}/\d{4}" /> </p>
	
		<h3>Periodo do Hor&aacute;rio</h3>
		<p>inicio: <input type="time" name="hora_inicial" pattern="\d{2}:\d{2}:\d{2}" /> </p>
		<p>final: <input type="time" name="hora_final" pattern="\d{2}:\d{2}:\d{2}" /> </p>
		
		<p><input type="text" name="duracao" placeholder="dura&ccedil;&atilde;o" size=20 maxlength=2> Minutos </p>
	
		<p><button type="submit" class="btn btn-lg btn-primary">Cadastrar</button></p>
	</form>
</div>

<div id="result"> </div>

	<!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
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