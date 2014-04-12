<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Eventos</title>
</head>
<body>

<c:forEach var="item" items="${lista}">
<script>
$(document).ready(function(){
	$("#edit_evento_${item.id}_2").hide();
});
</script>
</c:forEach>

<div class="alert alert-info">
	<strong>Eventos</strong> Segue a lista de eventos cadastrados.
</div>

<div class="container">
		<div class="row">
        	<div class="col-md-3">
        		Nome
       		</div>
       		
        	<div class="col-md-6">
        		Descri&ccedil;&atilde;o
       		</div>
       		
        	<div class="col-md-3">
        		#
       		</div>
      	</div>

		<c:forEach var="item" items="${lista}">
		<div id="edit_evento_${item.id}_1" class="row">
        	<div class="col-md-3">
        		<c:out value="${item.nome}"/>
       		</div>
       		
        	<div class="col-md-6">
        		<c:out value="${item.descricao}"/> <br/>
        		(<c:out value="${item.dataInicial}"/> - <c:out value="${item.dataFinal}"/>) </br>
        		Dura&ccedil;&atilde;o: <c:out value="${item.duracao}"/> Minutos
       		</div>
       		
        	<div class="col-md-3">
        		<table>
        		<tr>
	        		<td><a href="#" onclick="editar('<c:out value="${item.id}"/>')"> <span class="ui-icon ui-icon-pencil"> </span> </a></td>
	        		<td><a href="#" onclick="remover('<c:out value="${item.id}"/>')"> <span class="ui-icon ui-icon-trash"> </span> </a></td>
       			</tr>
       			</table>
       		</div>
      	</div>
       		
       		<div id="edit_evento_${item.id}_2" class="row">
       			<form class="target" method="post" action="<c:out value="${pageContext.request.contextPath}/evento/altera_evento"/>">
       			<input type="hidden" name="id" value="${item.id}">
       			<div class="col-md-3">
       				Edi&ccedil;&atilde;o de evento
     			</div>
       			<div class="col-md-6">
					<table id="hor-minimalist-a">
					    <thead>
						    <tr>
						        <th>Atributo</th>
						        <th>Valor</th>
						    </tr>
					    </thead>
					    
					    <tbody>
						    <tr>
						    	<td> Nome: </td>
								<td><input type="text" name="nome" value="${item.nome}" size=20 maxlength=40> </td>
							</tr>
							
							<tr>
								<td> Descri&ccedil;&atilde;o: </td>
								<td><input type="text" name="descricao" value="${item.descricao}" size=30 maxlength=100> </tdv>
							</td>
							
							<tr>
								<td> <h3>Periodo da Data</h3> </td>
								<td>
									<table>
										<tr>
											<td>inicio: <input type="text" class="data_inicial" name="data_inicial" value="<c:out value="${item.dataInicial}"/>"/> </td>
											<td>final: <input type="text" class="data_final" name="data_final" value="<c:out value="${item.dataFinal}"/>"/> </td>
										</tr>
									</table>
								</td>
							</tr>
							
							<tr>
								<td> <h3>Periodo do Hor&aacute;rio</h3> </td>
								<td>
									<table>
										<tr>
											<td>inicio: <input type="text" class="hora_inicial" name="hora_inicial" value="${item.horaInicial}"/> </td>
											<td> final: <input type="text" class="hora_final" name="hora_final" value="${item.horaFinal}"/> </td>
										</tr>
									</table>
								</td>
							</tr>
							
							<tr>
								<td> Dura&ccedil;&atilde;o: </td>
								<td><input type="text" name="duracao" value="${item.duracao}" size=20 maxlength=2> Minutos </td>
							</tr>
							
							<tr>
								<td> </td>
								<td> <div class="result_edit_evento"></div> </td>
							</tr>
						</tbody>
						
					    <tfoot>
						    <tr>
						    	<td></td>
						    	<td></td>
						    </tr>
					    </tfoot>
					</table>
       			</div>
       			<div class="col-md-3">
       				<button type="submit" class="btn btn-lg btn-primary">Salvar</button> <br/>
      			</div>
       			</form>
       		</div>
		</c:forEach>
</div>

		<script>
		function editar(data) {
			var div = "#edit_evento_"+data+"_2";
			$(div).toggle();
		}
		
		function remover(data) {
			$.ajax({
				type: "GET",
				url: "<c:out value="${pageContext.request.contextPath}/evento/remove_evento"/>",
				data: {id: data}
			}).done(function(resposta){
				if(resposta=="yes") {
					$("#edit_evento_"+data+"_1").remove();
					$("#edit_evento_"+data+"_2").remove();
				}
				else {
					alert("erro ao remover o evento");
				}
			});
		}
		</script>
		
	<script type="text/javascript">
		$(function(){
			$('.data_inicial').datepicker({
				inline: true,
				showOtherMonths: true,
				dateFormat: 'dd/mm/yy',
				dayNamesMin: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab'],

			});
			$('.data_final').datepicker({
				inline: true,
				showOtherMonths: true,
				dateFormat: 'dd/mm/yy',
				dayNamesMin: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab'],
			});
			$('.hora_inicial').timepicker();
			$('.hora_final').timepicker();
		});
	</script>

    <script>
    $( ".target" ).submit(function( event ) {
    	 
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
    			  $( ".result_edit_evento" ).empty().append( "Evento atualizado com sucesso" );
    		  else if(data == "not")
    			  $( ".result_edit_evento" ).empty().append( "Evento n&atilde;o atualizado" );
    		  else
    			  $( ".result_edit_evento" ).empty().append( "Usu&aacute;rio n&atilde;o tem permiss&atilde;o para executar essa opera&ccedil;&atilde;o" );
    	  });
    	});
    </script>

</body>
</html>