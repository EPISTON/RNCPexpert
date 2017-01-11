<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>recherche</title>
</head>
<body>
<h2>recherche de message</h2>
<sf:form action="search" method="post" >
	<label for="serchTerm">recherche: </label>
	<input name="searchTerm" type="text" id="searchTerm"/><br />
	<input type="submit" value="chercher" />
</sf:form>

</body>
</html>