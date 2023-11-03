<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="./js/jquery-3.7.0.min.js"></script>
<link rel="stylesheet" href="./css/freeDetail.css">
<title>Insert title here</title>
</head>
<body>


	<div class="freePosting">
		<div class="boardNum">글번호 ${freePosting.bno}</div>
		<br> 제목
		<div class="btitle">${freePosting.btitle}</div>
		<br> 닉네임
		<div class="mnickname">${freePosting.mnickname}</div>
		<br> 내용
		<div class="bdetail">${freePosting.bcontent}</div>
		<br> 작성 날짜
		<div class="bdate">${freePosting.bdate}</div>
		<br>
	</div>

<c:if test="${isDibsTrue eq false}">
	<form id="callDibsForm" action="/freePostLike" method="POST">
		<input type="hidden" id="likePostInput" name="likePostInput"
			value="false"> <input type="hidden" name="bno" id="bno"
			value="${freePosting.bno}">
		<button type="submit" id="dibsButtonFalse">♡ 공감하기</button>
	</form>
		</c:if>

<c:if test="${isDibsTrue eq true}">
	<form id="callDibsForm" action="/freePostLike" method="POST">
		<input type="hidden" id="likePostInput" name="likePostInput"
			value="true"> <input type="hidden" name="bno" id="bno"
			value="${freePosting.bno}">
		<button type="submit" id="dibsButtonTrue">♥ 공감하기</button>
	</form>
	</c:if>


	<c:if test="${freePosting.mno ne mno}">
		<button type="button" id="reportButton">신고하기</button>
	</c:if>

	<div id="reportModal" class="modal">
		<div class="modal-content">
			<span class="close" id="closeModal">&times;</span>
			<h2>신고하기</h2>

			신고 사유
			<form action="/reportFreePost" method="post" id="reportForm">
				<input type="hidden" name="rpdate" id="rpdate"> <input
					type="hidden" name="bno" id="bno" value="${freePosting.bno}">
				<textarea rows="5" cols="13" name="rpcontent" id="rpcontent"></textarea>
				<button type="submit">신고하기</button>
			</form>
		</div>
	</div>





	<div id="formContainer">
		<form action="/writeFreeComment" method="post" id="freeCommentForm">
			<div>
				댓글작성<br>
				<textarea rows="5" cols="13" name="ccontent" id="ccontent"></textarea>
			</div>
			<input type="hidden" name="cdate" id="cdate"> <input
				type="hidden" name="bno" id="bno" value="${freePosting.bno}">
			<button type="submit" id="submitCommnetButton">완료</button>

		</form>
	</div>



	<br> 댓글
	<div class="comment">
		<c:forEach items="${freeComment}" var="comment">
			<div class="cdetail">${comment.ccontent}</div>
			<div class="cdate">${comment.cdate}</div>

			<c:if test="${comment.mno ne mno}">
				<button type="submit" class="commentReportButton"
					data-bno="${freePosting.bno}" data-cno="${comment.cno}">신고하기</button>
			</c:if>

			<c:if test="${comment.mno eq mno}">
				<form action="/deleteFreeComment" method="post"
					id="deleteFreeComment">
					<input type="hidden" name="cno" id="cno" value="${comment.cno}">
					<input type="hidden" name="bno" id="bno" value="${freePosting.bno}">
					<button class="cdelete" onclick="deleteConfirm()">삭제하기</button>
				</form>
			</c:if>
			<br>

			<div id="commentReportModal" class="modal">
				<div class="modal-content">
					<span class="close" id="closeModal2">&times;</span>
					<h2>신고하기</h2>

					신고 사유
					<form action="/reportFreeComment" method="post"
						id="commentReportForm">
						<input type="hidden" name="crpdate" id="crpdate"> <input
							type="hidden" name="cno" id="cno" value="${comment.cno}">
						<input type="hidden" name="bno" id="bno"
							value="${freePosting.bno}">
						<textarea rows="5" cols="13" name="rpcontent" id="rpcontent"></textarea>
						<button type="submit">신고하기</button>
					</form>
				</div>
			</div>
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


		

		
		function deleteConfirm() {
			if (confirm("삭제하시겠습니까?")) {
				return true;
			} else {
				event.preventDefault(); // 기본 제출 동작을 막음
			}
		}

	
		
		
		$(document).on("click",".commentReportButton",function() {
			
							const bno = $(this).data("bno");
							const cno = $(this).data("cno");

							$.ajax({
										url : "/commentReportCount",
										type : "post",
										data : {
											bno : bno,
											cno : cno
										},
										success : function(result) {
											const data = JSON.parse(result);

											if (data.result !== 0) {
												alert("이미 신고한 댓글 입니다");
											} else {
												document
														.getElementById("commentReportModal").style.display = "block";
												
												// 닫기 버튼 클릭 시 모달 닫기
												closeModal2.addEventListener(
																"click",
																function() {
																	document.getElementById("commentReportModal").style.display = "none";
																});

												// 모달 외부 클릭 시 모달 닫기
												window.addEventListener(
																"click",
																function(event) {
																	if (event.target == document
																			.getElementById("commentReportModal")) {
																		document.getElementById("commentReportModal").style.display = "none";
																	}
																});
												
											}

										},
										error : function() {
											// 오류 처리
										}
									});
						});

		
		
		
		

		// 버튼 클릭 시 모달 열기
		reportButton.addEventListener("click", function() {
			const reportCount = "${reportCount}";


	
			if (reportCount !== "0") {
				alert("이미 신고한 게시글 입니다");
			} else {

				document.getElementById("reportModal").style.display = "block";

			}
		});

		// 닫기 버튼 클릭 시 모달 닫기
		closeModal.addEventListener("click", function() {
			document.getElementById("reportModal").style.display = "none";
		});

		// 모달 외부 클릭 시 모달 닫기
		window.addEventListener("click", function(event) {
			if (event.target == document.getElementById("reportModal")) {
				document.getElementById("reportModal").style.display = "none";
			}
		});
	</script>


</body>
</html>