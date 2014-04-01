<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Usu&aacute;rios</title>

    <!-- Bootstrap core CSS -->
    <link href="<c:out value="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="<c:out value="${pageContext.request.contextPath}/extras/css/starter-template.css"/>" rel="stylesheet">
    <link href="<c:out value="${pageContext.request.contextPath}/extras/css/table.css"/>" rel="stylesheet">

</head>
<body>

<script>
$( document ).ready(function() {
    // Your code here.
    $("#campos").hide();
    $("#result_1").hide();
    $(".autorizacoes").hide();
    $("#result_2").hide();
    $("#result_3").hide();
    $("#btn_altera").hide();
});
</script>

<button type="button" class="btn btn-xs btn-link" id="btn_001">Editar atributos dos usu&aacute;rios</button>
<script>
$("#btn_001").click(function(){
	$("#campos").toggle();
});
</script>

	<table class="bordered" id="campos">
	    <thead>
	    <tr>    
	        <th>Campo</th>
	        <th>#</th>
	    </tr>
	    </thead>
	    <tfoot>
	    <tr>
	        <td></td>        
	        <td></td>
	    </tr>
	    </tfoot>
	    <c:forEach var="item_campo" items="${campos}">
	    <tr id="${item_campo.id}">
	        <td> <input type="text" name='<c:out value="${item_campo.id}"/>' value='<c:out value="${item_campo.campo}"/>' size=20 maxlength=40> </td>        
	        <td> <button type="button" class="btn btn-xs btn-link" id="btn_002_${item_campo.id}">Excluir</button> </td>
	    </tr>
		    <script>
				$("#btn_002_${item_campo.id}").click(function(){
					$.ajax({
						  url: "del_campo.html",
						  data: { campo: "${item_campo.campo}" },
						  cache: false
						}).done(function(data) {
							$("#${item_campo.id}").hide();
							$("#result_1").show();
							$("#result_1").empty().append( data );
							$("#result_1").hide(3000);
							$("linha_${item_campo.id}").hide();
						});
				});
		    </script>
	    </c:forEach>
	    <tr id="novo">
	    	<td> <input type="text" name="novo_campo" size=20 maxlength=40> </td>
	    	<td> <button type="button" class="btn btn-xs btn-link" id="btn_003">Incluir</button> </td>
	    </tr>
	    	<script>
				$("#btn_003").click(function(){
					var nome_campo = $('input[name=novo_campo]').val();
					$.ajax({
						  url: "cad_campo.html",
						  data: { campo: nome_campo },
						  cache: false
						}).done(function(data) {
							$("#result_1").show();
							$("#result_1").empty().append( data );
							$("#result_1").hide(3000);
							$("#novo").hide();
						});
				});
	    	</script>
	</table>
	
	<div id="result_1">   </div>
	
<div class="alert alert-info">
	<strong>Usu&aacute;rios</strong> Segue a lista de usu&aacute;rios cadastrados.
</div>

<div class="container">
		<div class="row">
        	<div class="col-md-3">Login</div>
        	<div class="col-md-3">Nome</div>
        	<div class="col-md-3">Tipo</div>
        	<div class="col-md-3">#</div>
      	</div>
		<c:forEach var="item" items="${usuarios}">
			<div class="row" id="${item.id}">
	        	<div class="col-md-3"><c:out value="${item.login}"/></div>
	        	<div class="col-md-3"><c:out value="${item.primeiroNome}"/> <c:out value="${item.ultimoNome}"/></div>
	        	<c:forEach var="item_tipo" items="${item.tipoUsuarios}">
	        		<div class="col-md-3"><c:out value="${item_tipo.nome}"/></div>
	        	</c:forEach>
	        	<div class="col-md-3">
	        		<button type="button" class="btn btn-xs btn-link" id="btn_004_${item.id}">Autoriza&ccedil;&otilde;es</button>
	        		<button type="button" class="btn btn-xs btn-link" id="btn_005_${item.id}">Excluir</button>
	        		<button type="button" class="btn btn-xs btn-link" id="btn_006_${item.id}">Editar</button>
	        	</div>
	        </div>
	        <div class="row autorizacoes" id="row_001_${item.id}">
						<table id="hor-zebra" border = 2>
							<tr>
								<th> # </th>
								<th> Nome </th>
								<th> Descri&ccedil;&atilde;o </th>
							</tr>
						<c:forEach var="item_auth" items="${autorizacoes}">
			            	<c:set var="isChecked" value="${false}"/>
					        <c:forEach var="user_auth" items="${item.autorizacoesUsuarios}">
				                <c:if test="${user_auth.nome == item_auth.nome}">
				                	<c:set var="isChecked" value="${true}"/>
				                </c:if>
					        </c:forEach>
							<tr>
								<td> <input type="checkbox" name="${item_auth.nome}" <c:if test="${isChecked}">checked="checked"</c:if> id="cb_001_${item.id}_${item_auth.id}"/> </td>
								<td>  ${item_auth.nome} </td>
								<td> ${item_auth.descricao} </td>
							</tr>
						</c:forEach>
						</table>
	        </div>
		</c:forEach>
		
		<div id="result_2">   </div>

		<c:forEach var="item" items="${usuarios}">
       		<script>
       			$("#btn_004_${item.id}").click(function(){
       				$("#row_001_${item.id}").toggle();
       			});
       		</script>
		</c:forEach>
		
		<c:forEach var="item" items="${usuarios}">
				<script>
					$("#btn_005_${item.id}").click(function(){
						$.ajax({
						  url: "del_usuario.html",
						  data: { id_usuario: "${item.id}" },
						  cache: false
						}).done(function(data) {
							$("#${item.id}").hide();
							$("#result_2").show();
							$("#result_2").empty().append( data );
							$("#result_2").hide(3000);
						});
				    });
				</script>
		</c:forEach>
		
		<script>
			$("#cb_001_${item.id}").click(function(){
				$.ajax({
				  url: "cad_autorizacao.html",
				  data: { usuario: "${item.id}", autorizacao: "${auth.nome}" },
				  cache: false
				}).done(function(data) {
					$("#result_2").show();
					$("#result_2").empty().append( data );
					$("#result_2").hide(3000);
				});
		    });
		</script>
		
		<c:forEach var="item" items="${usuarios}">
			<c:forEach var="auth" items="${autorizacoes}">
				<script>
					$("#cb_001_${item.id}_${auth.id}").click(function(){
						$.ajax({
						  url: "cad_autorizacao.html",
						  data: { usuario: "${item.id}", autorizacao: "${auth.nome}" },
						  cache: false
						}).done(function(data) {
							$("#result_2").show();
							$("#result_2").empty().append( data );
							$("#result_2").hide(3000);
						});
				    });
				</script>
			</c:forEach>
		</c:forEach>
		
</div>

<div class="row">
	<div class="col-md-4">
			<div class="alert alert-info" id="title">
				<strong>Novo</strong> Cadastre um novo usuario.
			</div>
		
			<div class="container">
				<form method="post" action="cad_usuario.html" id="target">
				
					<p><input type="hidden" name="id_usuario"></p>
					
					<p><input type="text" name="login" placeholder="Login" size=20 maxlength=40> </p>
					
					<p><input type="text" name="senha" placeholder="Senha" size=20 maxlength=40> </p>
					
					<p><input type="text" name="pnome" placeholder="Primeiro Nome" size=20 maxlength=40> </p>
					
					<p><input type="text" name="unome" placeholder="Ultimo Nome" size=20 maxlength=40> </p>
					
					<p><select name="tipo">
						<c:forEach var="tipo" items="${tipos}">
							<option value="<c:out value="${tipo.id}"/>"> <c:out value="${tipo.nome}"/> </option>
					    </c:forEach>
				    </select></p>
					
					<c:forEach var="item2" items="${campos}">
						<p><input type="text" name="${item2.campo}" placeholder="${item2.campo}" size=20 maxlength=40> </p>
					</c:forEach>
						
					<p><button type="submit" class="btn btn-lg btn-primary" id="btn_cadastra">Cadastrar</button></p>
					<p><button type="submit" class="btn btn-lg btn-primary" id="btn_altera">Alterar</button></p>
				</form>
				
				<div id="result_3"> </div>
			</div>
	</div>
	
</div>


<c:forEach var="item" items="${usuarios}">
<script>
	$("#btn_006_${item.id}").click(function(){
		$("#btn_cadastra").hide();
		$("#btn_altera").show();
		$("#title").empty().append("<strong>Altera&ccedil;&atilde;o</strong> Atualiza&ccedil&atilde;o do usuario ${item.login}.");
		$("#target").attr("action", "edit_usuario.html");

		$("input[name=id_usuario]").val("${item.id}");
		$("input[name=login]").val("${item.login}");
		$("input[name=pnome]").val("${item.primeiroNome}");
		$("input[name=unome]").val("${item.ultimoNome}");
	});
</script>
</c:forEach>


<!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="js/jquery-2.1.0.min.js"></script>
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
    		$("#target")[0].reset();
    	    $("#result_3").show();
    	    $( "#result_3" ).empty().append( data );
    	    $("#result_3").hide(5000);
    	    if(url == "edit_usuario.html") {
    	    	$("#btn_cadastra").show();
    	    	$("#btn_altera").hide();
    	    	$("#title").empty().append("<strong>Novo</strong> Cadastre um novo usuario.");
    	    	$("#target").attr("action", "cad_usuario.html");
    	    }
    	  });
    });  
    </script>

</body>
</html>