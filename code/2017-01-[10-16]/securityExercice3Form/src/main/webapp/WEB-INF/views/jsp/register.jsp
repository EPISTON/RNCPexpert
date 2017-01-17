<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Nouvel utilisateur</title>
<s:url value="/resources/css/bonjour.css" var="coreStyle" />
<link href="${coreStyle}" rel="stylesheet" />
</head>
<body>
<h2>Creation d'un utilisateur</h2>
<s:url value="/register" var="registerUrl" />
<sf:form action="${registerUrl}" method="post">
	<label for="nom" >votre login</label>
	<input type="text" name="nom" id="nom"><br />
	<label for="email" >votre email</label>
	<input type="text" name="email" id="email"><br />
	<label for="password" >votre mot de passe</label>
	<input type="password" name="password" id="password"><br />
	<label for="confirm" >confirmation mot de passe</label>
	<input type="password" name="confirm" id="confirm"><br />
	<input type="submit" value="creer compte" />
</sf:form>
</body>
</html>