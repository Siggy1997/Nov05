<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<c:forEach items="${boardSearchData}" var="search">
			<div class="btitle">${search.btitle}</div>
			<div class="bcontent">${search.bcontent}</div>
			<div class="ccontent">${search.ccontent}</div>
			<br>
		</c:forEach>

</body>
</html>