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

<table id="hor-zebra" border = 2>

<tr>
	<th>  </th>
	<c:forEach var="item" items="${lista_data}">
	    <th> <c:out value="${item}"/> </th>
	</c:forEach>
</tr>

<c:set var='counter' value='1'/>

<c:forEach var="item2" items="${lista_hora}">
<tr>
	<td>
		<c:out value="${item2}"/>
	</td>
	
	<c:forEach var="item" items="${lista_data}">
    <td>
          	<c:set var="isChecked" value="${false}"/>
          	<c:forEach var="user" items="${lista_horarios}">
          		<c:choose>
				    <c:when test="${item2 eq user.hora && item eq user.data}">
				       <c:set var="isChecked" value="${true}"/>
				    </c:when>
				    <c:otherwise>
				        <c:set var="isChecked" value="${false}"/>
				    </c:otherwise>
				</c:choose>
    		</c:forEach>
    		
	    	<input type="checkbox" <c:if test="${isChecked==true}">checked="checked"</c:if> id="cb_${counter}">
	    	
		    <script>
				$("#cb_${counter}").click(function(){
					$.ajax({
						  url: "<c:out value="${pageContext.request.contextPath}/horario/toggle_horario"/>",
						  data: { data: "${item}", hora: "${item2}" },
						  cache: false
						}).done(function(data) {
							$("#result").empty().append( data+" ${counter}" );
						});
				});
		    </script>
		    <c:set var='counter' value='${counter+1}'/>    
    </td>
	</c:forEach>
</tr>
</c:forEach>

</table>

</body>
</html>