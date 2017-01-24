<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>liste des posts</title>
</head>
<body>
<p>utilisateur logge: <sec:authentication property="name"/></p>
<h2>liste des posts</h2>
<s:url value="/posts/search" var="searchUrl" />
<s:url value="/posts/edit/" var="editUrl" />
<s:url value="/posts/remove/" var="removeUrl" />
<s:url value="/posts/create/" var="createUrl" />
<s:url value="/posts/" var="listeUrl" />


<sf:form action="${searchUrl}" method="get">
	<input type="text" name="searchTerm" id="searchterm" />
	<input type="submit" value="chercher" />
</sf:form>
<table border="1">
	<tr><th>ID</th><th>TITRE</th><th>CORPS</th><th>PUBLISHED</th><th>AUTEUR</th><th>actions</th></tr>
	<c:forEach items="${posts}" var="p" >
	<tr>
		<td>${p.id}</td>
		<td>${p.titre}</td>
		<td>${p.corps}</td>
		<td>${p.published}</td>
		<td>${p.auteur.nom}</td>
		<td>
			<a href="${editUrl}${p.id}">editer</a><br />
			
			<sf:form method="post" action="${removeUrl}${p.id}">
				<input type="submit"
						value="supprimer"
						onclick="return confirm('etes vous sur?');" />
			</sf:form> 
			</td>
	</tr>
	</c:forEach>
</table>
<a href="${createUrl}">creation de post</a><br />
<a href="${listeUrl}">retour a la liste</a><br />
</body>
</html>