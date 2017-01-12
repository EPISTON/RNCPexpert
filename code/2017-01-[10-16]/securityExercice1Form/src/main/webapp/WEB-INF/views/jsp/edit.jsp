<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>edition post</title>
</head>
<body>
<s:url value="/save" var="saveurl" />
<h2>edition post</h2>
<sf:form action="${saveurl}" method="post" commandName="post" >

	<sf:hidden path="id" />
	
	<label for="titre">titre: </label>
	<sf:input path="titre" type="text" id="titre"/><br />
	
	<label for="corps">corps: </label>
	<sf:textarea path="corps" rows="10" cols="50" id="corps"/><br />
	
	<sf:select path="auteurId">
		<sf:option value="0" label="pas d'auteur" />
		<sf:options items="${auteurs}" itemValue="id" itemLabel="nom" />
	</sf:select>	
	
	<sf:hidden path="published" />
	
	<input type="submit" value="sauver" />
</sf:form>

</body>
</html>