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


	<div class="question">
		<div class="boardNum">${qnaQuestion.bno}</div>
		<div class="btitle">${qnaQuestion.btitle}</div>
		익명
		<div class="bdetail">${qnaQuestion.bdetail}</div>
		<div class="bdate">${qnaQuestion.bdate}</div>
	</div>

	<c:if test="${not empty hno}">
		<button type="button" id="answerToggleButton">답변 작성하기</button>
	</c:if>

	<div id="formContainer" style="display: none;">
		<form action="./writeQnaAnswer" method="post" id="qnaAnswerForm">
			<div>
				내용
				<textarea rows="5" cols="13" name="ccontent" id="ccontent"
					style="display: none;"></textarea>
			</div>
			<input type="hidden" name="cdate" id="cdate"> <input
				type="hidden" name="bno" id="bno" value="${qnaQuestion.bno}">
			<button type="submit" id="submitAnswerButton">완료</button>
			<button type="button" id="cancelAnswerButton">취소</button>
		</form>
	</div>


	<br> 의료인 답변
	<div class="answer">
		<c:forEach items="${qnaAnswer}" var="answer">
			<div class="hospitalNum">${answer.hno}</div>
			<div class="doctorNum">${answer.dno}</div>
			<div class="cdetail">${answer.ccontent}</div>
			<div class="cdate">${answer.cdate}</div>
			<c:if test="${answer.hno eq hno}">
				<form action="deleteQnaAnswer" method="post" id="deleteQnaAnswer">
				<input type="hidden" name="cno" id="cno" value="${answer.cno}">
					<input type="hidden" name="bno" id="bno" value="${qnaQuestion.bno}">
					<button class="cdelete">삭제하기</button>
				</form>
			</c:if>
			<br>
		</c:forEach>
	</div>




	<script>
		//날짜, 시간 변환하기
		function updateDate(element, dateString) {
			const postTime = new Date(dateString);
			const currentTime = new Date();
			const timeDiff = currentTime - postTime;
			const minutesDiff = Math.floor(timeDiff / (1000 * 60));

			if (minutesDiff < 1) {
				element.textContent = "방금 전";
			} else if (minutesDiff < 60) {
				element.textContent = minutesDiff + "분 전";
			} else if (minutesDiff < 24 * 60) {
				const hoursDiff = Math.floor(minutesDiff / 60);
				element.textContent = hoursDiff + "시간 전";
			} else {
				const year = postTime.getFullYear();
				const month = postTime.getMonth() + 1; // 월은 0부터 시작하므로 1을 더해줌
				const day = postTime.getDate();
				const formattedDate = year + "." + month + "." + day;
				element.textContent = formattedDate;
			}
		}

		document.addEventListener("DOMContentLoaded", function() {
			// bdate, cdate에 적용
			const bdateElements = document.querySelectorAll(".bdate");
			bdateElements.forEach(function(element) {
				updateDate(element, element.textContent);
			});

			const cdateElements = document.querySelectorAll(".cdate");
			cdateElements.forEach(function(element) {
				updateDate(element, element.textContent);
			});
		});

		// 폼이 제출될 때 현재 날짜와 시간을 입력란에 추가
		document.getElementById('qnaAnswerForm').addEventListener(
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

					document.getElementById('cdate').value = formattedDatetime
							.toISOString().slice(0, 19).replace("T", " ");

					const content = document
							.querySelector('textarea[name="ccontent"]').value;

					// 폼 제출
					this.submit();
				});

		// "답변 작성하기" 버튼 클릭 시 답변 입력창 나타내기
		document.getElementById('answerToggleButton').addEventListener(
				'click',
				function() {

					const textarea = document.getElementById('ccontent');
					const formContainer = document
							.getElementById('formContainer');

					// textarea와 formContainer(나타나거나 숨기기)
					if (textarea.style.display === 'none') {
						textarea.style.display = 'block';
						formContainer.style.display = 'block';
					} else {
						textarea.style.display = 'none';
						formContainer.style.display = 'none';
					}
				});

		// "취소" 버튼 클릭 시 답변 입력창 가리기 
		document
				.getElementById('cancelAnswerButton')
				.addEventListener(
						'click',
						function() {
							// textarea와 formContainer를 숨김
							document.getElementById('ccontent').style.display = 'none';
							document.getElementById('formContainer').style.display = 'none';
						});
	</script>

</body>
</html>