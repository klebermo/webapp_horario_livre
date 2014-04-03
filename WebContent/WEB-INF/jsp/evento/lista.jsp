<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Eventos</title>

    <!-- Bootstrap core CSS -->
    <link href="<c:out value="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="<c:out value="${pageContext.request.contextPath}/extras/css/starter-template.css"/>" rel="stylesheet">
    <link href="<c:out value="${pageContext.request.contextPath}/extras/css/table.css"/>" rel="stylesheet">
    <link href="<c:out value="${pageContext.request.contextPath}/extras/css/dialog.css"/>" rel="stylesheet">
    <link href="<c:out value="${pageContext.request.contextPath}/jquery/css/ui-lightness/jquery-ui-1.10.4.custom.min.css"/>" rel="stylesheet">

</head>
<body>

<div class="alert alert-info">
	<strong>Eventos</strong> Segue a lista de eventos cadastrados.
</div>

<div class="container">
		<div class="row">
        	<div class="col-md-3">Nome</div>
        	<div class="col-md-3">Descri&ccedil;&atilde;o</div>
        	<div class="col-md-3">Periodo</div>
        	<div class="col-md-3">Dura&ccedil;&atilde;o</div>
      	</div>

		<c:forEach var="item" items="${lista}">
		<div class="row">
        	<div class="col-md-3"><c:out value="${item.nome}"/></div>
        	<div class="col-md-3"><c:out value="${item.descricao}"/></div>
        	<div class="col-md-3"><c:out value="${item.dataInicial}"/> - <c:out value="${item.dataFinal}"/></div>
        	<div class="col-md-3"><c:out value="${item.duracao}"/></div>
        </div>
		</c:forEach>
</div>

</body>
</html>