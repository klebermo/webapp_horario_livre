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
    <link href="<c:out value="${pageContext.request.contextPath}/extras/css/grid.css"/>" rel="stylesheet">
    <link href="<c:out value="${pageContext.request.contextPath}/extras/css/table.css"/>" rel="stylesheet">
    <link href="<c:out value="${pageContext.request.contextPath}/extras/css/dialog.css"/>" rel="stylesheet">
    <link href="<c:out value="${pageContext.request.contextPath}/jquery/css/ui-lightness/jquery-ui-1.10.4.custom.min.css"/>" rel="stylesheet">

</head>
<body>

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
        		<c:out value="${item.primeiroNome}"/> <c:out value="${item.ultimoNome}"/><br/>
        		<c:forEach var="item_tipo" items="${item.tipoUsuarios}">
        			<c:out value="${item_tipo.nome}"/>.
        		</c:forEach>
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

</body>
</html>