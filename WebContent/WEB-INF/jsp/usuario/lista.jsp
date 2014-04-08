<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Usu&aacute;rios</title>
</head>
<body>

<script>
$(document).ready(function(){
	$(".campos").hide();
	$(".tipos").hide();
});
</script>
<c:forEach var="item" items="${usuarios}">
<script>
$(document).ready(function(){
	$("#edit_usuario_${item.id}").hide();
	$("#edit_autorizacao_${item.id}").hide();
});
</script>
</c:forEach>

<p>
<button type="button" class="btn btn-primary" onclick="edit_campos()">Atributos do usu&aacute;rio</button>
<button type="button" class="btn btn-primary" onclick="edit_tipos()">Tipos de usu&aacute;rio</button>
</p>

<p>
	<table id="hor-minimalist-a" class="campos">
	  <thead>
	  <tr>    
	      <th>Campo</th>
	      <th>#</th>
	  </tr>
	  </thead>
	  <tfoot>
	  <tr>
	  	<td> <input type="text" name="nome_campo"> </td>
	  	<td> <button type="button" id="incluir_campo" class="btn btn-link">Incluir</button> </td>
	  </tr>
	  <tr>
	  	<td> <div id="result_incluir_campo"></div> </td>
	  	<td> <div id="result_excluir_campo"></div> </td>
	  </tr>
	  </tfoot>
	  
	  <c:forEach var="item_key" items="${campos}">
	  <tr id="linha_campo_${item_key}">
		<td> <input type="text" value="${item_key}"> </td>
		<td> <button type="button" id="excluir_campo_${item_key}" class="btn btn-link">Excluir</button> </td>
	  </tr>
	  </c:forEach>
	</table>
</p>

<script>
$("#incluir_campo").on("click", function () {
	$.ajax({
		type: "GET",
		url: "<c:out value="${pageContext.request.contextPath}/key/cadastra_campo"/>",
		data: {nome: $("input[name=nome_campo]").val() }
	}).done(function(data){
		var obj = jQuery.parseJSON( data );
		
		if(obj.Key[0].id > 0) {
			var newRow = $('<tr id="linha_campo_'+obj.nome+'">');
			
			cols = '<td> <input type="text" name="'+obj.Key[0].nome+'" value="'+obj.Key[0].nome+'"> </td>';
	        cols += '<td> <button type="button" id="excluir_campo_'+obj.Key[0].nome+'" class="btn btn-link">Excluir</button> </td>';
	        
	        newRow.append(cols);
	        $("table.campos").append(newRow);
	        $("input[name=nome_campo]").val("");
		}
		else {
			$("#result_incluir_campo").empty().append("erro");
		}
	});
});
</script>

<c:forEach var="item_key" items="${campos}">
<script>
$("#excluir_campo_${item_key}").on("click", function () {
	$.ajax({
		type: "GET",
		url: "<c:out value="${pageContext.request.contextPath}/key/remove_campo"/>",
		data: {nome: "${item_key}"}
	}).done(function(data){
		if(data == "yes") {
			$("#linha_campo_${item_key}").remove();
		}
		else if(data == "not"){
			$("#result_excluir_campo").empty().append("erro");
		}
		else {
			$("#result_excluir_campo").empty().append("sem acesso");
		}
	});
});
</script>
</c:forEach>

<p>
	<table id="hor-minimalist-a" class="tipos">
	  <thead>
	  <tr>    
	      <th>Tipo</th>
	      <th>#</th>
	  </tr>
	  </thead>
	  <tfoot>
	  <tr>
	  	<td> <input type="text" name="nome_tipo"> </td>
	  	<td> <button type="button" id="incluir_tipo" class="btn btn-link">Incluir</button> </td>
	  </tr>
	  <tr>
	  	<td> <div id="result_incluir_tipo"></div> </td>
	  	<td> <div id="result_excluir_tipo"></div> </td>
	  </tr>
	  </tfoot>
	  
	  <c:forEach var="item_tipo" items="${tipos}">
	  <tr id="linha_tipo_${item_tipo.nome}">
		<td> <input type="text" name="${item_tipo.nome}" value="${item_tipo.nome}"> </td>
		<td> <button type="button" id="excluir_tipo_${item_tipo.id}" class="btn btn-link">Excluir</button> </td>
	  </tr>
	  </c:forEach>
	</table>
</p>

<script>
$("#incluir_tipo").on("click", function () {
	$.ajax({
		type: "GET",
		url: "<c:out value="${pageContext.request.contextPath}/tipo/cadastra_tipo"/>",
		data: {id: $("input[name=nome_tipo]").val() }
	}).done(function(data){
		var obj = jQuery.parseJSON( data );
		
		if(obj.Tipo[0].id > 0) {
			var newRow = $('<tr id="linha_tipo_'+obj.nome+'">');
			
			cols = '<td> <input type="text" name="'+obj.Tipo[0].nome+'" value="'+obj.Tipo[0].nome+'"> </td>';
	        cols += '<td> <button type="button" id="excluir_tipo_'+obj.Tipo[0].id+'" class="btn btn-link">Excluir</button> </td>';
	        
	        newRow.append(cols);
	        $("table.tipos").append(newRow);
	        $("input[name=nome_tipo]").val("");
		}
		else {
			$("#result_incluir_tipo").empty().append("erro");
		}
	});
});
</script>

<c:forEach var="item_tipo" items="${tipos}">
<script>
$("#excluir_tipo_${item_tipo.id}").on("click", function () {
	$.ajax({
		type: "GET",
		url: "<c:out value="${pageContext.request.contextPath}/tipo/remove_tipo"/>",
		data: {id: "${item_tipo.id}"}
	}).done(function(data){
		if(data == "yes") {
			$("#linha_tipo_${item_tipo.nome}").remove();
		}
		else if(data == "not"){
			$("#result_excluir_tipo").empty().append("erro");
		}
		else {
			$("#result_excluir_tipo").empty().append("erro");
		}
	});
});
</script>
</c:forEach>

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
     	
     		<div id="edit_autorizacao_${item.id}" class="row">
     			<div class="col-md-3"> Editar autoriza&ccedil;&otilde;es </div>
     			
     			<div class="col-md-6">
     				<table id="auth-${item.id}" class="hor-minimalist-a">
						    <thead>
							    <tr>    
							        <th>#</th>
							        <th>Nome</th>
							    </tr>
						    </thead>
						    
						    <tfoot>
							    <tr>
							        <td></td>        
							        <td></td>
							    </tr>
						    </tfoot>
						    
     				</table>
     			</div>
     			
     			<div class="col-md-3"> ${item.login} </div>
     		</div>
       		
       		<div id="edit_usuario_${item.id}" class="row">
       			<form method="post" action="<c:out value="${pageContext.request.contextPath}/usuario/altera_usuario"/>" id="target">
	        	<div class="col-md-3">
	        		Editar dados de <br/> <i> ${item.primeiroNome} ${item.ultimoNome} </i>
        		</div>
	        	<div class="col-md-6">
						<table id="cadastro-${item.id}" class="hor-minimalist-a">
						    <thead>
							    <tr>    
							        <th>Atributo</th>
							        <th>Valor</th>
							    </tr>
						    </thead>
						    
						    <tfoot>
							    <tr>
							        <td></td>        
							        <td></td>
							    </tr>
						    </tfoot>
						    
						    <tbody>
						    	<tr>
						    		<td>Login</td>
						    		<td> <input type="text" name="senha" value="${item.login}"> </td>
						    	</tr>
						    	
						    	<tr>
						    		<td>Senha</td>
						    		<td> <input type="password" name="senha" value="${item.senha}"> </td>
						    	</tr>
						    	
						    	<tr>
						    		<td>Primeiro Nome</td>
						    		<td> <input type="text" name="pnome" value="${item.primeiroNome}"> </td>
						    	</tr>
						    	
						    	<tr>
						    		<td>Ultimo Nome</td>
						    		<td> <input type="text" name="unome" value="${item.ultimoNome}"> </td>
						    	</tr>
						    	
						    </tbody>
						</table>
	       		</div>
	        	<div class="col-md-3">
	        		<button type="submit" class="btn btn-lg btn-primary">Cadastrar</button> <br/> <div id="result"></div>
        		</div>
	        	</form>
       		</div>
		</c:forEach>

<script>
function edit_campos() {
	$(".campos").toggle();
}

function edit_tipos() {
	$(".tipos").toggle();
}

function editar(id_usuario) {
	var div = "#edit_usuario_"+id_usuario;
	$(div).toggle();
	$('#extra-cad-'+id_usuario).remove();
	var newRow = $('<tr id="extra-cad-'+id_usuario+'">');
	
	$.ajax({
		type: "GET",
		url: "<c:out value="${pageContext.request.contextPath}/tipo/lista_tipo"/>"
	}).done(function(data){
		var obj_tipo = jQuery.parseJSON( data );
		
		col_1 = '<td> Tipo: </td>';
		col_2 = $('<td></td>');
		var select = $('<select name="tipo">');
		for(var item in obj_tipo.Tipo)
		    select.append('<option value="'+obj_tipo.Tipo[item].nome+'">'+obj_tipo.Tipo[item].nome+'</option>');

		select.appendTo(col_2);
		newRow.append(col_1);
		newRow.append(col_2);

		$("#cadastro-"+id_usuario).append(newRow);
	});
	
	$.ajax({
		type: "GET",
		url: "<c:out value="${pageContext.request.contextPath}/key/lista_key_value"/>",
		data: {id: id_usuario}
	}).done(function(data){
		var obj_campo = jQuery.parseJSON( data );

		for(var item in obj_campo.Key)
			$("#cadastro-"+id_usuario).append('<tr> <td> '+obj_campo.Key[item].key+' : </td> <td> <input type="text" name="'+obj_campo.Key[item].key+'" value="'+obj_campo.Key[item].value+'" size=20 maxlenght=40> </td> <tr>');
	});
}

function autorizacao(id_usuario) {
	var div = "#edit_autorizacao_"+id_usuario;
	$(div).toggle();
	$('#extra-auth-'+id_usuario).remove();
	var newRow = $('<tr id="extra-auth-'+id_usuario+'">');
	
	$.ajax({
		type: "GET",
		url: "<c:out value="${pageContext.request.contextPath}/usuario/lista_autorizacao"/>",
	}).done(function(data){
		var obj_auth = jQuery.parseJSON( data );
		
		for(var item in obj_auth.Auth) {
			var checkbox = $('<tr>');
			checkbox.append('<td><input type="checkbox" name="'+obj_auth.Auth[item].nome+'"></td> <td>'+obj_auth.Auth[item].nome+'</td>');
			checkbox.appendTo(newRow);
		}

		$("#auth-"+id_usuario).append(newRow);
	});
}

function remover(data) {
	alert("remover usuario: "+data);
}
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
    			  $( "#result" ).empty().append( "Usu&aacute;rio atualizado com sucesso" );
    		  else
    			  $( "#result" ).empty().append( "Usu&aacute;rio n&atilde;o atualizado" );
    		  
    		  $("#target").each (function(){
    			  this.reset();
    		  });
    	  });
    	});
    </script>

</body>
</html>