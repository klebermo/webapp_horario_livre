<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Usu&aacute;rios</title>
</head>
<body>

<script>
$(document).ready(function(){
	$("#edit_campos").hide();
	$("#edit_tipos").hide();
})
</script>

<button type="button" class="btn btn-primary" onclick="edit_campos()">Atributos do usu&aacute;rio</button>
<button type="button" class="btn btn-primary" onclick="edit_tipos()">Tipos de usu&aacute;rio</button>

<table class="bordered" id="edit_campos">
  <thead>
  <tr>    
      <th>Campo</th>
      <th>#</th>
  </tr>
  </thead>
  <tfoot>
  <tr>
  	<td> <input type="text" name="nome_campo"> </td>
  	<td> <button type="button" class="btn btn-link" onclick="add_campo()">Incluir</button> </td>
  </tr>
  </tfoot>
  
  <c:forEach var="item_key" items="${campos}">
  <tr>
	<td> <input type="text" name="${item_key.id}" value="${item_key.nome}"> </td>
	<td> <button type="button" class="btn btn-link" onclick="del_campo(${item_key.id})">Excluir</button> </td>
  </tr>
  </c:forEach>
  
</table>

<table class="bordered" id="edit_tipos">
  <thead>
  <tr>    
      <th>Tipo</th>
      <th>#</th>
  </tr>
  </thead>
  <tfoot>
  <tr>
      <td></td>        
      <td></td>
  </tr>
  </tfoot>
  
  <c:forEach var="item_tipo" items="${tipos}">
  <tr>
	<td> <input type="text" name="item_tipo.id" value="item_tipo.nome"> </td>
	<td> <button type="button" class="btn btn-link">Salvar</button> </td>
  </tr>
  </c:forEach>
  
  <tr>
  	<td> <input type="text" name="nome_tipo"> </td>
  	<td> <button type="button" class="btn btn-link" onclick="add_tipo()">Incluir</button> </td>
  </tr>
</table>
 
		<div class="row">
        	<div class="col-md-3">
        		username
       		</div>
       		
        	<div class="col-md-6">
        		Nome / Tipo
       		</div>
       		
        	<div class="col-md-3">
        		#
       		</div>
      	</div>

		<c:forEach var="item" items="${usuarios}">
		<div class="row">
        	<div class="col-md-3">
        		<c:out value="${item.login}"/>
       		</div>
       		
        	<div class="col-md-6">
        		<c:out value="${item.primeiroNome}"/> <c:out value="${item.ultimoNome}"/>, <c:out value="${item.tipo.nome}"/> <br/>
       		</div>
       		
        	<div class="col-md-3">
        		<table>
        		<tr>
	        		<td><a href="#" onclick="editar('<c:out value="${item.id}"/>')"> <span class="ui-icon ui-icon-pencil"> </span> </a></td>
	        		<td><a href="#" onclick="remover('<c:out value="${item.id}"/>')"> <span class="ui-icon ui-icon-trash"> </span> </a></td>
	        		<td><a href="#" onclick="autorizacao('<c:out value="${item.id}"/>')"> <span class="ui-icon ui-icon-wrench"> </span> </a></td>
        		</tr>
        		</table>
       		</div>
        </div>
		</c:forEach>
		
		<script>
		function editar(id) {
			alert("clicou para editar usuario: "+id);
		}
		
		function remover(id) {
			alert("clicou para remover usuario: "+id);
		}
		
		function autorizacao(id) {
			alert("clicou visualizar autorizacoes de: "+id);
		}
		function edit_campos() {
			if ($("#edit_campos").is(":visible"))
				$("#edit_campos").hide();
			else
				$("#edit_campos").show();
		}
		
		function del_campo(data) {
			//
		}
		
		function add_campo(data) {
			$.ajax({
				  type: "GET",
				  url: "<c:out value="${pageContext.request.contextPath}/usuario/cadastra_campo"/>",
				  data: { nome: $(data) }
			}).done(function( msg ) {
				    alert( "Data Saved: " + data );
			});
		}
		
		function del_tipo(data) {
			//
		}
		
		function edit_tipos() {
			if ($("#edit_tipos").is(":visible"))
				$("#edit_tipos").hide();
			else
				$("#edit_tipos").show();
		}
		
		function add_tipo(data) {
			$.ajax({
				  type: "GET",
				  url: "<c:out value="${pageContext.request.contextPath}/usuario/cadastra_tipo"/>",
				  data: { nome: $(data) }
			}).done(function( msg ) {
				    alert( "Data Saved: " + data );
			});
		}
		</script>

</body>
</html>