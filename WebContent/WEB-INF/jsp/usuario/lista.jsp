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
<c:forEach var="item" items="${usuarios}">
<script>
$(document).ready(function(){
	$("#edit_usuario_${item.id}").hide();
	$("#edit_autorizacao_${item.id}").hide();
})
</script>
</c:forEach>

<p>
<button type="button" class="btn btn-primary" onclick="edit_campos()">Atributos do usu&aacute;rio</button>
<button type="button" class="btn btn-primary" onclick="edit_tipos()">Tipos de usu&aacute;rio</button>
</p>

<p>
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
	  	<td> <button type="button" class="btn btn-link">Incluir</button> </td>
	  </tr>
	  </tfoot>
	  
	  <c:forEach var="item_key" items="${campos}">
	  <tr>
		<td> <input type="text" name="${item_key.id}" value="${item_key.nome}"> </td>
		<td> <button type="button" class="btn btn-link">Excluir</button> </td>
	  </tr>
	  </c:forEach>
	</table>
</p>

<p>
	<table class="bordered" id="edit_tipos">
	  <thead>
	  <tr>    
	      <th>Tipo</th>
	      <th>#</th>
	  </tr>
	  </thead>
	  <tfoot>
	  <tr>
	  	<form id="incluir_campo" method="post">
	  	<td> <input type="text" name="nome_tipo"> </td>
	  	<td> <button type="submit" class="btn btn-link">Incluir</button> </td>
	  	</form>
	  </tr>
	  </tfoot>
	  
	  <c:forEach var="item_tipo" items="${tipos}">
	  <tr>
	  	<form id="incluir_tipo" method="post">
		<td> <input type="text" name="item_tipo.id" value="item_tipo.nome"> </td>
		<td> <button type="submit" class="btn btn-link">Excluir</button> </td>
		</form>
	  </tr>
	  </c:forEach>
	</table>
</p>

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
     				<table class="bordered">
						    <thead>
							    <tr>    
							        <th>#</th>
							        <th>Nome</th>
							        <th>Descri&ccedil;&atilde;o</th>
							    </tr>
						    </thead>
						    
						    <tfoot>
							    <tr>
							        <td></td>        
							        <td></td>
							        <td></td>
							    </tr>
						    </tfoot>
						    
							<c:forEach var="item_auth" items="${autorizacao}">
				            	<c:set var="isChecked" value="${false}"/>
						        <c:forEach var="user_auth" items="${item.autorizacao}">
					                <c:if test="${user_auth.nome == item_auth.nome}">
					                	<c:set var="isChecked" value="${true}"/>
					                </c:if>
						        </c:forEach>
								<tr>
									<td> <input type="checkbox" name="${item_auth.nome}" <c:if test="${isChecked}">checked="checked"</c:if> /> </td>
									<td>  ${item_auth.nome} </td>
									<td> ${item_auth.descricao} </td>
								</tr>
							</c:forEach>
     				</table>
     			</div>
     			
     			<div class="col-md-3"> ${item.login} </div>
     		</div>
       		
       		<div id="edit_usuario_${item.id}" class="row">
	        	<div class="col-md-3"> Editar dados </div>
	       		
	        	<div class="col-md-6">
					<form method="post" action="<c:out value="${pageContext.request.contextPath}/usuario/altera_usuario"/>" id="target">
						<table class="bordered">
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
					    
						    <tr>
								<td> Digite uma Senha:</td> <td> <input type="password" name="senha1" size=20 maxlength=40> </td>
							</tr>
							
							<tr>
								<td> Repita a Senha: </td> <td> <input type="password" name="senha2" size=20 maxlength=40> </td>
							</tr>
							
							<tr>
								<td> Primeiro Nome: </td> <td> <input type="text" name="pnome" value="${item.primeiroNome}" size=20 maxlength=40> </td>
							</tr>
							
							<tr>
								<td> Ultimo Nome: </td> <td> <input type="text" name="unome" value="${item.ultimoNome}" size=20 maxlength=40> </td>
							</tr>
							
							<tr>
								<td> Tipo: </td> <td> <select name="tipo">
									<c:forEach var="tipos" items="${tipos}">
										<option value=<c:out value="${tipos.id}"/> > <c:out value="${tipos.nome}"/> </option>
								    </c:forEach>
								</select> </td>
							</tr>
							
							<c:forEach var="campo" items="${campos}">
							<tr>
								<td>${campo}:</td> <td> <input type="text" name="${campo}" value="${item.atributo.value.conteudo}" size=20 maxlength=40> </td>
							</tr>
							</c:forEach>
							
							<tr>
								<td> <button type="submit" class="btn btn-lg btn-primary">Cadastrar</button> </td>
								<td> <div class="alert alert-info"> 		<strong>Resultado</strong> <div id="result"></div>	</div> </td>
							</tr>
						</table>
					</form>
	       		</div>
       		
	        	<div class="col-md-3"> Login: ${item.login} </div>
       		</div>
		</c:forEach>

<script>
function edit_campos() {
	$("#edit_campos").toggle();
}

function edit_tipos() {
	$("#edit_tipos").toggle();
}

function editar(data) {
	var div = "#edit_usuario_"+data
	$(div).toggle();
}

function remover(data) {
	alert("remover usuario: "+data);
}

function autorizacao(data) {
	var div = "#edit_autorizacao_"+data;
	$(div).toggle();
}
</script>

</body>
</html>