<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<form action="/searchWord" method="post" onsubmit="searchForm()">
    <input type="text" name="searchWord" id="searchWordInput" placeholder="검색 할 내용을 입력하세요">
    <button type="submit">검색</button>
</form>

<h1>QnA 게시판</h1>

<button onclick="location.href='writeQna'">작성하기</button>

    <c:forEach items="${qnaList}" var="qna"> 
     <a href="<c:url value='/qnaDetail'>
    <c:param name='bno' value='${qna.bno}' />
  </c:url>">
    <div class="list"> 
      <div class="title">${qna.btitle}</div>
      <div class="content">${qna.bcontent}</div>
     <c:choose>
    <c:when test="${qna.comment_count == 0}">
        "답변 대기 중"
    </c:when>
    <c:otherwise>
        <div class="count">${qna.comment_count}</div>
    </c:otherwise>
</c:choose>
</div>
</a>
<br>
    </c:forEach>

<script>
    var maxLength = 30; // 최대 문자열 길이
    var contentElements = document.querySelectorAll(".content");

    contentElements.forEach(function(contentElement) {
        var text = contentElement.textContent;

        if (text.length > maxLength) {
            var truncatedText = text.slice(0, maxLength) + "...";
            contentElement.textContent = truncatedText;
        }
    });
    
    
    function searchForm() {
        var searchWordInput = document.getElementById("searchWordInput");
        var searchWord = searchWordInput.value.trim(); // 입력값 앞뒤 공백 제거

        if (searchWord === "") {
            alert("검색어를 입력하세요.");
            event.preventDefault(); // 폼 전송 막기
           
        }
    }
</script>
</body>
</html>