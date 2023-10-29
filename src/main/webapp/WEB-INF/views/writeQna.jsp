<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>[QnA 게시판 글쓰기]</h2>
	<form action='<c:url value='/postQna'/>' method="post" id="qnaForm">
		<div>
			제목<input type="text" name="btitle">
		</div>
		<div>
			내용
			<textarea rows="5" cols="13" name="bcontent"></textarea>
		</div>
		<input type="hidden" name="bdate" id="bdate">
		<button type="submit">완료</button>
		<button type="button" onclick="location.href='qna'">목록</button>
	</form>

	<div id="imagePreview"></div>

	<script>
		// 폼이 제출될 때 현재 날짜와 시간을 입력란에 추가
		document.getElementById('qnaForm').addEventListener(
				'submit',
				function(event) {
					event.preventDefault(); // 기본 제출 동작을 막음

					// 현재 날짜와 시간을 가져오기
					const currentDatetime = new Date();
					const utcDatetime = new Date(currentDatetime.toISOString()
							.slice(0, 19)
							+ "Z"); // UTC 시간으로 변환
					const formattedDatetime = new Date(utcDatetime.getTime()
							+ 9 * 60 * 60 * 1000);

					document.getElementById('bdate').value = formattedDatetime
							.toISOString().slice(0, 19).replace("T", " ");

					const title = document
							.querySelector('input[name="btitle"]').value;
					const content = document
							.querySelector('textarea[name="bcontent"]').value;

					// 제목이나 내용 중 하나라도 비어있으면 경고창을 띄우고 전송을 막음
					if (title.trim() === '') {
						alert('제목을 입력해주세요.');
						event.preventDefault(); // 폼 전송 막기
						return false;
					}
					if (content.trim() === '') {
						alert('내용을 입력해주세요.');
						event.preventDefault(); // 폼 전송 막기
						return false;
					}

					// 폼 제출
					this.submit();
				});
	</script>

</body>
</html>