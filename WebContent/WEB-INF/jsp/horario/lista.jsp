<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lista Hor&aacute;rios Livres</title>
</head>
<body>

<script>
$( document ).ready(function() {
	$("#result").hide();
	$("#result_horarios").hide();
});
</script>

	<div align="center">
		<form name="form_lista_horario">
			<select name="id_evento" id="id_evento">
				<c:forEach var="evento" items="${lista_eventos}">
				  <option value="<c:out value="${evento.id}"/>"> ${evento.nome} - ${evento.descricao} </option>
				</c:forEach>
			</select>
			
			<table class="bordered">
			<tr>
				<td>
					<select name="lista_usuarios" id="usuarios" size="10" multiple="multiple">
						<c:forEach var="usuario" items="${lista_usuarios}">
					    	<option value="${usuario.id}">${usuario.primeiroNome} ${usuario.ultimoNome}</option>
					    </c:forEach>
					</select>
				</td>
				
			<td>
				<p> <button type="button" class="btn btn-lg btn-default" id="for_left"> << </button> </p>
				<p> <button type="button" class="btn btn-lg btn-default" id="for_right"> >> </button> </p>
			</td>
			
				<td>
					<select name="selecao_usuarios" id="selecao" size="10" multiple="multiple">
					</select>
				</td>
			</tr>
			</table>
			
			<button type="button" class="btn btn-lg btn-default" id="btn_enviar">OK</button>
		</form>
	</div>

	<div class="jumbotron" id="result">
	
			<div id="result_horarios"></div>
	
			<table class="bordered">
				<thead>
					<tr>
						<th>Data</th>
						<th>Hora</th>
					</tr>
				</thead>
				
				<tfoot>
					<tr>
						<td></td>
						<td></td>
					</tr>
				</tfoot>
				
				<tbody class="horarios">
					<tr>
						<td></td>
						<td></td>
					</tr>
				</tbody>
			</table>
	
	</div>

<script>
    $('#for_right').click(function(e) {
        var selectedOpts = $('#usuarios option:selected');
        if (selectedOpts.length == 0) {
            e.preventDefault();
        }

        $('#selecao').append($(selectedOpts).clone());
        $(selectedOpts).remove();
        e.preventDefault();
    });

    $('#for_left').click(function(e) {
        var selectedOpts = $('#selecao option:selected');
        if (selectedOpts.length == 0) {
            e.preventDefault();
        }

        $('#usuarios').append($(selectedOpts).clone());
        $(selectedOpts).remove();
        e.preventDefault();
    });
    
	$("#btn_enviar").click(function(){
		var selecao_usuario=[];
		$('#selecao option').each(function(){
			selecao_usuario.push($(this).val());
		});
		$.ajax({
			type: "GET",
			url: "<c:out value="${pageContext.request.contextPath}/horario/find_horario"/>",
			data: { id_evento: $('#id_evento option:selected').val(), id_usuarios: selecao_usuario }
		}).done(function(data) {
			if(data == "not") {
				$('#result').show();
				$('#result_horarios').show();
				$('#result_horarios').empty().append('<div class="alert alert-danger"><strong>Erro!</strong> Nenhum hor&aacute;rio encontrado para esse evento e usu&aacute;rios.</div>');
			}
			else if(data == "no_permit") {
				$('#result').show();
				$('#result_horarios').show();
				$('#result_horarios').empty().append('<div class="alert alert-danger"><strong>Erro!</strong> Usu&aacute;rio n&atilde;o autorizado.</div>');
			}
			else {
				$('#result').show();
				$('#result_horarios').show();
				$('#result_horarios').empty().append('<div class="alert alert-success"><strong>Pronto!</strong> Os hor&aacute;rios dispon&iacute;veis para esse evento s&atilde;o:</div>');
				var obj_data = jQuery.parseJSON( data );
				for(var item in obj_data.Horario) {
					var newRow1 = $('<tr>');
					var newCol1 = "";
					newCol1 += '<td>' + obj_data.Horario[item].data + '</td>';
					newCol1 += '<td>' + obj_data.Horario[item].hora + '<td>';
					newRow1.append(newCol1);
					$("tbody.horarios").append(newRow1);
				}
			}
		});
	});
</script>

</body>
</html>