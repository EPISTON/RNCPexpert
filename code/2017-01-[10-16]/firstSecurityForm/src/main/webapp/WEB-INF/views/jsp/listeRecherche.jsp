<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>resultat recherche</title>
</head>
<body>
<h2>recherche de message</h2>
<table border="1">
	<tr><th>ID</th><th>TITRE</th><th>CORPS</th><th>PUBLISHED</th></tr>
	<c:forEach items="${messages}" var="m" >
	<tr>
		<td>${m.id}</td>
		<td>${m.titre}</td>
		<td>${m.corps}</td>
		<td>${m.published}</td>
	</tr>
	</c:forEach>
</table>
<a href="search">retour a la recherche</a>
</body>
</html>