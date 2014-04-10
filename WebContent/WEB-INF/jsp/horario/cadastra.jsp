<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cadastra Hor&aacute;rios Livres</title>
</head>
<body>

<div id="result"></div>

<table class="horarios" id="hor-zebra" border = 2>

</table>

<script>
$('document').ready(function(){
	var obj_data = jQuery.parseJSON( '${lista_data}' );
	var obj_hora = jQuery.parseJSON( '${lista_hora}' );
	var obj_horario = jQuery.parseJSON( '${lista_horarios}' );
	
	var newRow1 = $('<tr>');
	for(var item in obj_hora.Hora) {
		newCol1 = "<td></td>";
		for(var item2 in obj_data.Data) {
			newCol1 += '<td>' + obj_data.Data[item2].string + '</td>';
		}
	}
	newRow1.append(newCol1);
	$("table.horarios").append(newRow1);
	
	var counter = 1;
	var newRow2 = "";
	for(var item in obj_hora.Hora) {
		newRow2 = $('<tr>');
		newCol2 = '<td>' + obj_hora.Hora[item].string + '</td>';
		for(var item2 in obj_data.Data) {
			newCol2 += '<td>' + '<input type="checkbox" class="horario" data-key_data="'+obj_data.Data[item2].data+'" data-key_hora="'+obj_hora.Hora[item].hora+'" name="'+counter+'">' + '</td>';
			counter++;
		}
		newRow2.append(newCol2);
		$("table.horarios").append(newRow2);
	}
	
	counter = 1;
	for(var item3 in obj_horario.Horario) {
		for(var item2 in obj_data.Data) {
			for(var item in obj_hora.Hora) {
				if(obj_data.Data[item2].data == obj_horario.Horario[item3].data && obj_hora.Hora[item].hora == obj_horario.Horario[item3].hora) {
					var checkbox = $('input[name='+counter+']');
					$(checkbox).attr('checked', 'true');
				}
				counter++;
			}
		}
	}
	
});

$('.horarios').on('click', '.horario', function(event){
	var hora = $(this).data('key_hora');
	var data = $(this).data('key_data');
	$.ajax({
		type: "GET",
		url: "<c:out value="${pageContext.request.contextPath}/horario/toggle_horario"/>",
		cache: false,
		data: {data: data, hora: hora}
	}).done(function(result){
		if(result == "added") {
			$("#result").empty().append("ok").hide(3000);
		}
		else {
			$("#result").empty().append("erro").hide(3000);
		}
	});
});
</script>

</body>
</html>