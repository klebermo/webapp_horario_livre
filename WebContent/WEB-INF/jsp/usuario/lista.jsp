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
	  <tbody>
		  <c:forEach var="item_key" items="${campos}">
			  <tr id="linha_campo_${item_key}">
				<td> <input type="text" value="${item_key}"> </td>
				<td> <button type="button" class="excluir_campo btn btn-link" data-key="${item_key}">Excluir</button> </td>
			  </tr>
		  </c:forEach>
	  </tbody>
	</table>
</p>

<script>
$("#incluir_campo").on("click", function () {
	$.ajax({
		type: "GET",
		url: "<c:out value="${pageContext.request.contextPath}/key/cadastra_campo"/>",
		cache: false,
		data: {nome: $("input[name=nome_campo]").val() }
	}).done(function(data){
		var obj = jQuery.parseJSON( data );
		
		if(obj.Key[0].id > 0) {
			var newRow = $('<tr id="linha_campo_'+obj.Key[0].key+'">');
			
			cols = '<td> <input type="text" value="'+obj.Key[0].key+'"> </td>';
	        cols += '<td> <button type="button" class="excluir_campo btn btn-link" data-key="'+obj.Key[0].key+'">Excluir</button> </td>';
	        newRow.append(cols);
	        $("table.campos tbody").append(newRow);
	        $("input[name=nome_campo]").val("");
		}
		else {
			$("#result_incluir_campo").empty().append("erro");
		}
	});
});
</script>

<script>
$('.excluir_campo').each(function(index, elem){
    $(elem).click(function(){
        //do you stuff here!
        var index = $(elem).data('key'); //this will read data-key attribute
    	$.ajax({
    		type: "GET",
    		url: "<c:out value="${pageContext.request.contextPath}/key/remove_campo"/>",
    		cache: false,
    		data: {nome: index}
    	}).done(function(data){
    		if(data == "yes") {
    			$("#linha_campo_"+index).remove();
    		}
    		else if(data == "not"){
    			$("#result_excluir_campo").empty().append("erro");
    		}
    		else {
    			$("#result_excluir_campo").empty().append("sem acesso");
    		}
    	});
    });

});
</script>

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
	  <tbody>
		  <c:forEach var="item_tipo" items="${tipos}">
			  <tr id="linha_tipo_${item_tipo.id}">
				<td> <input type="text" name="${item_tipo.nome}" value="${item_tipo.nome}"> </td>
				<td> <button type="button" class="excluir_tipo btn btn-link" data-key="${item_tipo.id}">Excluir</button> </td>
			  </tr>
		  </c:forEach>
	  </tbody>
	</table>
</p>

<script>
$("#incluir_tipo").on("click", function () {
	$.ajax({
		type: "GET",
		url: "<c:out value="${pageContext.request.contextPath}/tipo/cadastra_tipo"/>",
		cache: false,
		data: {id: $("input[name=nome_tipo]").val() }
	}).done(function(data){
		var obj = jQuery.parseJSON( data );
		
		if(obj.Tipo[0].id > 0) {
			var newRow = $('<tr id="linha_tipo_'+obj.Tipo[0].id+'">');
			
			cols = '<td> <input type="text" name="'+obj.Tipo[0].nome+'" value="'+obj.Tipo[0].nome+'"> </td>';
	        cols += '<td> <button type="button" class="excluir_tipo btn btn-link" data-key="'+obj.Tipo[0].id+'">Excluir</button> </td>';
	        
	        newRow.append(cols);
	        $("table.tipos tbody").append(newRow);
	        $("input[name=nome_tipo]").val("");
		}
		else {
			$("#result_incluir_tipo").empty().append("erro");
		}
	});
});
</script>

<script>
$('.excluir_tipo').each(function(index, elem) {
    $(elem).click(function(){
        //do you stuff here!
        var index = $(elem).data('key'); //this will read data-key attribute
    	$.ajax({
    		type: "GET",
    		url: "<c:out value="${pageContext.request.contextPath}/tipo/remove_tipo"/>",
    		cache: false,
    		data: {id: index}
    	}).done(function(data){
    		if(data == "yes") {
    			$("#linha_tipo_"+index).remove();
    		}
    		else if(data == "not"){
    			$("#result_excluir_tipo").empty().append("erro");
    		}
    		else {
    			$("#result_excluir_tipo").empty().append("erro");
    		}
    	});
    });
});
</script>

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
		<div class="row" id="row-${item.id}">
        	<div class="col-md-3">
        		<c:out value="${item.login}"/>
       		</div>
       		
        	<div class="col-md-6">
        		<c:out value="${item.primeiroNome}"/> <c:out value="${item.ultimoNome}"/>, <c:out value="${item.tipo.nome}"/> <br/>
       		</div>
       		
        	<div class="col-md-3">
        		<table>
        		<tr>
	        		<td><a href="#" class="edit" data-key="${item.id}"> <span class="ui-icon ui-icon-pencil"> </span> </a></td>
	        		<td><a href="#" class="del" data-key="${item.id}"> <span class="ui-icon ui-icon-trash"> </span> </a></td>
	        		<td><a href="#" class="auth" data-key="${item.id}"> <span class="ui-icon ui-icon-wrench"> </span> </a></td>
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
						    
						    <tbody class="auth">
						    
						    </tbody>
						    
     				</table>
     			</div>
     			
     			<div class="col-md-3"> ${item.login} </div>
     		</div>
       		
       		<div id="edit_usuario_${item.id}" class="row">
       			<form method="post" action="<c:out value="${pageContext.request.contextPath}/usuario/altera_usuario"/>" id="target">
       			<input type="hidden" name="id" value="${item.id}">
	        	<div class="col-md-3">
	        		Editar dados de <br/> <i> ${item.primeiroNome} ${item.ultimoNome} </i>
        		</div>
	        	<div class="col-md-6">
						<table id="cad-${item.id}" class="hor-minimalist-a">
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
						    
						    <tbody class="basic">
						    	<tr>
						    		<td>Login</td>
						    		<td> <input type="text" name="login" value="${item.login}"> </td>
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
						    
						    <tbody class="extra">
						    
						    </tbody>
						    
						</table>
	       		</div>
	        	<div class="col-md-3">
	        		<button type="submit" class="btn btn-lg btn-primary">Salvar</button> <br/> <div id="result"></div>
        		</div>
	        	</form>
       		</div>
		</c:forEach>

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
    			  $( "#result" ).empty().append( "Usuario atualizado com sucesso" );
    		  else
    			  $( "#result" ).empty().append( "Usuario n&atilde;o atualizado" );
    		  
    		  $('#target').each (function(){
    			  this.reset();
    		  });
    	  });
    	});
    </script>
    
<script>
function edit_campos() {
	$(".campos").toggle();
}

function edit_tipos() {
	$(".tipos").toggle();
}

$('.edit').each(function(index, elem) {
    $(elem).click(function(){
        //do you stuff here!
        var index = $(elem).data('key'); //this will read data-key attribute
        
    	var div = "#edit_usuario_"+index;
    	$(div).toggle();
    	
    	$('#cad-'+index+' tbody.extra').remove();
    	var newRow = $('<tr>');
    	
    	$.ajax({
    		type: "GET",
    		url: "<c:out value="${pageContext.request.contextPath}/tipo/lista_tipo"/>",
    		cache: false
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

    		$('#cad-'+index).append('<tbody class="extra">');
    		$('#cad-'+index+' tbody.extra').append(newRow);
    	});
    	
    	$.ajax({
    		type: "GET",
    		url: "<c:out value="${pageContext.request.contextPath}/key/lista_key_value"/>",
    		cache: false,
    		data: {id: index}
    	}).done(function(data){
    		var obj_campo = jQuery.parseJSON( data );

    		for(var item in obj_campo.Key) {
    			$('#cad-'+index+' tbody.extra').append('<tr> <td> '+obj_campo.Key[item].key+' : </td> <td> <input type="text" name="'+obj_campo.Key[item].key+'" value="'+obj_campo.Key[item].value+'" size=20 maxlenght=40> </td> <tr>');
    		}
    	});
    });
});

$('.auth').each(function(index, elem) {
    $(elem).click(function(){
        //do you stuff here!
        var index = $(elem).data('key'); //this will read data-key attribute

    	var div = "#edit_autorizacao_"+index;
    	$(div).toggle();
    	
    	$('#auth-'+index+' tbody.auth').remove();
    	var newRow = $('<tr>');
    	
    	$.ajax({
    		type: "GET",
    		url: "<c:out value="${pageContext.request.contextPath}/usuario/lista_autorizacao"/>",
    		cache: false
    	}).done(function(data){
    		var obj_auth = jQuery.parseJSON( data );
    		
    		for(var item in obj_auth.Auth) {
    			var checkbox = $('<tr>');
    			checkbox.append('<td><input type="checkbox" id="'+obj_auth.Auth[item].nome+'" name="'+obj_auth.Auth[item].nome+'"></td> <td>'+obj_auth.Auth[item].nome+'</td>');
    			checkbox.appendTo(newRow);
    		}

    		$('#auth-'+index).append('<tbody class="auth">')
    		$('#auth-'+index+' tbody.auth').append(newRow);
    	});
    	
    	$.ajax({
    		type: "GET",
    		url: "<c:out value="${pageContext.request.contextPath}/usuario/lista_autorizacao_usuario"/>",
    		cache: false,
    		data: {id: index}
    	}).done(function(data){
    		var obj_auth = jQuery.parseJSON( data );
    		
    		for(var item in obj_auth.Auth) {
    			var checkbox = getElementById(obj_auth.Auth[item].nome);
    			$(checkbox).attr("checked","true");
    		}
    	});
    });
});

$('.del').each(function(index, elem) {
	$(elem).click(function() {
		var index = $(elem).data('key'); //this will read data-key attribute
		
    	$.ajax({
    		type: "GET",
    		url: "<c:out value="${pageContext.request.contextPath}/usuario/remover_usuario"/>",
    		cache: false,
    		data: {id: index}
    	}).done(function(data){
    		var div1 = "#edit_autorizacao_"+index;
        	$(div1).remove();
        	var div2 = "#edit_usuario_"+index;
        	$(div2).remove();
        	var row = "row-"+index;
        	$(row).remove();
    	});
	});
});

</script>

</body>
</html>