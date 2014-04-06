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

<form method="POST" action="<c:out value="${pageContext.request.contextPath}/acesso/salvar_config"/>">
<p>Para qual intervalo do dia você deseja cadastrar horários?</p>

<table>
<tr>
<td>In&iacute;cio:
<div class="bfh-timepicker open">
	<div class="input-group bfh-timepicker-toggle" data-toggle="bfh-timepicker">
		<span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
		<input type="text" name="hora_inicial" class="form-control" placeholder="" readonly="">
	</div>
	<div class="bfh-timepicker-popover">
		<table class="table">
			<tbody>
				<tr>
					<td class="hour">
						<div class="input-group">
							<input type="text" class="form-control bfh-number" data-min="0" data-max="23" data-zeros="true" data-wrap="true">
							<span class="input-group-addon bfh-number-btn inc">
								<span class="glyphicon glyphicon-chevron-up"></span>
							</span>
							<span class="input-group-addon bfh-number-btn dec">
								<span class="glyphicon glyphicon-chevron-down"></span>
							</span>
						</div>
					</td>
					<td class="separator">:</td>
					<td class="minute">
						<div class="input-group">
							<input type="text" class="form-control bfh-number" data-min="0" data-max="59" data-zeros="true" data-wrap="true">
							<span class="input-group-addon bfh-number-btn inc">
								<span class="glyphicon glyphicon-chevron-up"></span>
							</span>
							<span class="input-group-addon bfh-number-btn dec">
								<span class="glyphicon glyphicon-chevron-down"></span>
							</span>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
</td>

<td> Final:
<div class="bfh-timepicker open">
	<div class="input-group bfh-timepicker-toggle" data-toggle="bfh-timepicker">
		<span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
		<input type="text" name="hora_final" class="form-control" placeholder="" readonly="">
	</div>
	<div class="bfh-timepicker-popover">
		<table class="table">
			<tbody>
				<tr>
					<td class="hour">
						<div class="input-group">
							<input type="text" class="form-control bfh-number" data-min="0" data-max="23" data-zeros="true" data-wrap="true">
							<span class="input-group-addon bfh-number-btn inc">
								<span class="glyphicon glyphicon-chevron-up"></span>
							</span>
							<span class="input-group-addon bfh-number-btn dec">
								<span class="glyphicon glyphicon-chevron-down"></span>
							</span>
						</div>
					</td>
					<td class="separator">:</td>
					<td class="minute">
						<div class="input-group">
							<input type="text" class="form-control bfh-number" data-min="0" data-max="59" data-zeros="true" data-wrap="true">
							<span class="input-group-addon bfh-number-btn inc">
								<span class="glyphicon glyphicon-chevron-up"></span>
							</span>
							<span class="input-group-addon bfh-number-btn dec">
								<span class="glyphicon glyphicon-chevron-down"></span>
							</span>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
</td>
</tr>
</table>

<p><button type="submit" class="btn btn-lg btn-default">Salvar</button></p>

</form>


<div id="result"></div>
    
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
    		  alert("done submit salva_config: "+data);
    		  
    		  if(data == 1)
    			  $( "#result" ).empty().append( "Configura&ccedil;&otilde;es salvas com sucesso" );
    		  else
    			  $( "#result" ).empty().append( "Configura&ccedil;&otilde;es n&atilde;o cadastradas" );
    		  
    		  $("#target").each (function(){
    			  this.reset();
    		  });
    	  });
    	  posting.fail(function( data ) {
    		  alert("fail submit salva_config: "+data);
    	  });
    	});
    </script>

</body>
</html>