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
	$("result").hide();
});
</script>

	<div align="center">
		<form name="form_lista_horario">
			<select name="id_evento" id="id_evento">
				<c:forEach var="evento" items="${lista_eventos}">
				  <option value="<c:out value="${evento.id}"/>"> ${evento.nome} - ${evento.descricao} </option>
				</c:forEach>
			</select>
			
			<table id="hor-zebra" border = 2>
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

<div class="jumbotron" id="result"> </div>

<!-- Bootstrap core JavaScript -->
<!-- Placed at the end of the document so the pages load faster -->

<script src="js/jquery-2.1.0.min.js"></script>
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
			$("#result").show();
			$("#result").empty().append( data );
		});
	});
</script>

</body>
</html>