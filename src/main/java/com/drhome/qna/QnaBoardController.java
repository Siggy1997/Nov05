package com.drhome.qna;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QnaBoardController {
	@Autowired
	QnaBoardService qnaBoardService;

	@GetMapping("/qnaBoard")
	public String qnaBoard(Model model) {

		List<Map<String, Object>> qnaList = qnaBoardService.qnaList();
		model.addAttribute("qnaList", qnaList);

		return "/qnaBoard";
	}

	@PostMapping("/qnaBoard")
	public String qnaList(@RequestParam("bno") int bno) {

		return "redirect:/qnaDetail?bno=" + bno;

	}

	@GetMapping("/qnaDetail")
	public String qnaDetail(@RequestParam("bno") int bno, Model model, HttpSession session) {

		// int hno = (int) session.getAttribute("hno");
		int hno = 1; // 추후 세션값으로 변경 예정 // 답변 삭제, 수정
		int mno = 4; // 추후 세션값으로 변경 예정
		model.addAttribute("hno", hno);
		model.addAttribute("mno", mno);

		Map<String, Object> qnaQuestion = qnaBoardService.qnaQuestion(bno);
		model.addAttribute("qnaQuestion", qnaQuestion);

		String bCallDibs = (String) qnaQuestion.get("bcalldibs");
		String[] mnoArray = bCallDibs.split(",");
		boolean isDibsTrue = Arrays.asList(mnoArray).contains(String.valueOf(mno));
		model.addAttribute("isDibsTrue", isDibsTrue);

		List<Map<String, Object>> qnaAnswer = qnaBoardService.qnaAnswer(bno);
		model.addAttribute("qnaAnswer", qnaAnswer);

		Map<String, Object> reportCountData = new HashMap<>();
		reportCountData.put("mno", mno);
		reportCountData.put("bno", bno);

		int reportCount = qnaBoardService.reportCount(reportCountData);
		model.addAttribute("reportCount", reportCount);

		if (!qnaAnswer.isEmpty()) {
			List<Integer> dnoList = new ArrayList<>();
			for (Map<String, Object> answer : qnaAnswer) {
				int dno = (int) answer.get("dno");
				dnoList.add(dno);

			}
			// 병원 및 의사 정보 가져오기
			List<Map<String, Object>> doctorInfo = qnaBoardService.doctorInfo(dnoList);
			model.addAttribute("doctorInfo", doctorInfo);
		}

		return "qnaDetail";
	}

	@PostMapping("/searchWord")
	public String searchWord(@RequestParam("searchWord") String searchWord,
			@RequestParam("selectOption") String selectOption, Model model) {

		if ("all".equals(selectOption)) {
			List<Map<String, Object>> boardSearchData = qnaBoardService.boardSearchAll(searchWord);
			model.addAttribute("boardSearchData", boardSearchData);
		}

		else if ("title".equals(selectOption)) {
			List<Map<String, Object>> boardSearchTitleData = qnaBoardService.boardSearchTitle(searchWord);
			model.addAttribute("boardSearchData", boardSearchTitleData);
		}

		else if ("content".equals(selectOption)) {
			List<Map<String, Object>> boardSearchContent = qnaBoardService.boardSearchContent(searchWord);
			model.addAttribute("boardSearchData", boardSearchContent);
		}

		model.addAttribute("searchWord", searchWord);

		return "boardSearch";
	}

	@GetMapping("/writeQna")
	public String writeQna(Model model) {

		return "/writeQna";
	}

	@RequestMapping(value = "/postQna", method = RequestMethod.POST)
	public String postQna(@RequestParam("btitle") String btitle, @RequestParam("bcontent") String bcontent,
			@RequestParam("bdate") String bdate) {

		Map<String, Object> qnaData = new HashMap<>();
		qnaData.put("btitle", btitle);
		qnaData.put("bcontent", bcontent);
		qnaData.put("bdate", bdate);
		qnaData.put("btype", 0);
		qnaData.put("mno", 4); // 추후 세션값으로 변경 예정

		qnaBoardService.postQna(qnaData);

		return "redirect:/qnaBoard";
	}

	@PostMapping("/writeQnaAnswer")
	public String writeQnaAnswer(@RequestParam("bno") int bno, @RequestParam("ccontent") String ccontent,
			@RequestParam("cdate") String cdate) {

		// 게시물당 댓글 수 조회
		int commentCount = qnaBoardService.commentCount(bno);

		// 새 댓글의 cno 설정
		int cno = commentCount + 1;

		Map<String, Object> qnaAnswerData = new HashMap<>();

		  LocalDateTime currentDatetime = LocalDateTime.now();

		qnaAnswerData.put("bno", bno);
		qnaAnswerData.put("dno", 1); // 추후 세션값으로 변경 예정
		qnaAnswerData.put("hno", 1); // 추후 세션값으로 변경 예정
		qnaAnswerData.put("cno", cno);
		qnaAnswerData.put("ccontent", ccontent);
		qnaAnswerData.put("cdate", currentDatetime);

		qnaBoardService.writeQnaAnswer(qnaAnswerData);

		return "redirect:/qnaDetail?bno=" + bno;
	}

	@PostMapping("/deleteQnaQuestion")
	public String deleteQnaQuestion(@RequestParam("bno") int bno) {

		Map<String, Object> deleteQnaQuestionData = new HashMap<>();

		deleteQnaQuestionData.put("bno", bno);
		deleteQnaQuestionData.put("btype", 2);

		qnaBoardService.deleteQnaQuestion(deleteQnaQuestionData);

		return "redirect:/qnaBoard";
	}

	@PostMapping("/deleteQnaAnswer")
	public String deleteQnaAnswer(@RequestParam("bno") int bno, @RequestParam("cno") int cno) {

		Map<String, Object> deleteQnaAnswerData = new HashMap<>();

		deleteQnaAnswerData.put("bno", bno);
		deleteQnaAnswerData.put("cno", cno);

		qnaBoardService.deleteQnaAnswer(deleteQnaAnswerData);

		return "redirect:/qnaDetail?bno=" + bno;
	}

	@PostMapping("/qnaCallDibs")
	public String qnaCallDibs(@RequestParam("bno") int bno, @RequestParam("callDibsInput") boolean callDibsInput,
			HttpSession session) {

		Map<String, Object> qnaCallDibsData = new HashMap<>();

		int mno = 4; // 추후 세션값으로 변경 예정

		qnaCallDibsData.put("bno", bno);
		qnaCallDibsData.put("mno", mno);

		if (callDibsInput == true) {

			System.out.println(callDibsInput);
			System.out.println(qnaCallDibsData);
			qnaBoardService.delQnaCallDibs(qnaCallDibsData);

		} else {
			qnaBoardService.addQnaCallDibs(qnaCallDibsData);
		}

		return "redirect:/qnaDetail?bno=" + bno;
	}

	
	@PostMapping("/reportPost")
	public String reportPost(@RequestParam("bno") int bno, @RequestParam("rpcontent") String rpcontent,
			@RequestParam("rpdate") String rpdate) {

		Map<String, Object> reportData = new HashMap<>();

		  LocalDateTime currentDatetime = LocalDateTime.now();
		
		reportData.put("bno", bno);
		reportData.put("mno", 4); // 추후 세션값으로 변경 예정
		reportData.put("rpcontent", rpcontent);
		reportData.put("rpurl", "http://localhost:8080/qnaDetail?bno=" + bno);
		reportData.put("rpdate", currentDatetime);

		qnaBoardService.reportPost(reportData);

		return "redirect:/qnaDetail?bno=" + bno;
	}

}
