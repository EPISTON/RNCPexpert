<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
<s:url value="/resources/css/bonjour.css" var="coreStyle" />
<link href="${coreStyle}" rel="stylesheet" />
</head>
<body>
<h2>veuillez vous identifier</h2>
<s:url value="/login" var="loginUrl" />
<sf:form action="${loginUrl}" method="post">
	<label for="username" >votre login</label>
	<input type="text" name="username" id="username"><br />
	<label for="password" >votre mot de passe</label>
	<input type="password" name="password" id="password"><br />
	<input type="submit" value="s'identifier" />
</sf:form>
</body>
</html>