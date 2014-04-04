<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Eventos</title>
</head>
<body>

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
		<div class="row">
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
		</c:forEach>
</div>

		<script>
		function editar(id) {
			alert("clicou para editar evento: "+id);
		}
		
		function remover(id) {
			alert("clicou para remover evento: "+id);
		}
		</script>

</body>
</html>