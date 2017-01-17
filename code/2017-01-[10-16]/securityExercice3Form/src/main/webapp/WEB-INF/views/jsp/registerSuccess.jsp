<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Utilisateur cree</title>
<s:url value="/resources/css/bonjour.css" var="coreStyle" />
<link href="${coreStyle}" rel="stylesheet" />
</head>
<body>
<h2>Utilisateur ${nom} a été crée</h2>
<s:url value="/registerForm" var="registerUrl" />
<s:url value="/welcome" var="welcomeUrl" />
<a href="${registerUrl}" >enregister un autre utilisateur</a><br />
<a href="${welcomeUrl}" >aller sur la page d'accueil</a><br />
</body>
</html>