package com.drhome.free;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FreeBoardController {

	@Autowired
	FreeBoardService freeBoardService;

	@GetMapping("/freeBoard")
	public String freeBoard(Model model) {

		List<Map<String, Object>> freeList = freeBoardService.freeList();
		model.addAttribute("freeList", freeList);

		return "/freeBoard";
	}

	@PostMapping("/freeBoard")
	public String freeBoardList(@RequestParam("bno") int bno) {

		return "redirect:/freeDetail?bno=" + bno;

	}

	@GetMapping("/freeDetail")
	public String freeDetail(@RequestParam("bno") int bno, Model model, HttpSession session) {

		// int mno = (int) session.getAttribute("mno");
		int mno = 4; // 추후 세션값으로 변경 예정
		model.addAttribute("mno", mno);

		Map<String, Object> freePosting = freeBoardService.freePosting(bno);
		model.addAttribute("freePosting", freePosting);
		
		Map<String, Object> reportCountData = new HashMap<>();
		reportCountData.put("mno", mno);
		reportCountData.put("bno", bno);
		
		int reportCount = freeBoardService.reportCount(reportCountData);
		model.addAttribute("reportCount", reportCount);
		
		
		String bCallDibs = (String) freePosting.get("bcalldibs");
		
		if(bCallDibs != null) {

		String[] mnoArray = bCallDibs.split(",");
		boolean isDibsTrue = Arrays.asList(mnoArray).contains(String.valueOf(mno));
		model.addAttribute("isDibsTrue", isDibsTrue);

		} else {
		model.addAttribute("isDibsTrue", false);
		}
		

		List<Map<String, Object>> freeComment = freeBoardService.freeComment(bno);
		model.addAttribute("freeComment", freeComment);

		

		return "freeDetail";
	}

	@GetMapping("/writeFree")
	public String writeFree(Model model) {

		return "/writeFree";
	}

	@RequestMapping(value = "/postFree", method = RequestMethod.POST)
	public String postQna(@RequestParam("btitle") String btitle, @RequestParam("bcontent") String bcontent,
			@RequestParam("bdate") String bdate) {

		Map<String, Object> freeData = new HashMap<>();
		freeData.put("btitle", btitle);
		freeData.put("bcontent", bcontent);
		freeData.put("bdate", bdate);
		freeData.put("btype", 1);
		freeData.put("mno", 4); // 추후 세션값으로 변경 예정

		freeBoardService.postFree(freeData);

		return "redirect:/freeBoard";
	}

	@PostMapping("/writeFreeComment")
	public String writeFreeComment(@RequestParam("bno") int bno, @RequestParam("ccontent") String ccontent) {

		// 게시물당 댓글 수 조회
		// int commentCount = freeBoardService.commentCount(bno);

		// 새 댓글의 cno 설정
		// int cno = commentCount + 1;

		Map<String, Object> freeCommentData = new HashMap<>();

		  LocalDateTime currentDatetime = LocalDateTime.now();
		
		freeCommentData.put("bno", bno);
		freeCommentData.put("mno", 4); // 추후 세션값으로 변경 예정
		freeCommentData.put("ccontent", ccontent);
		freeCommentData.put("cdate", currentDatetime);

		freeBoardService.writeFreeComment(freeCommentData);

		return "redirect:/freeDetail?bno=" + bno;
	}

	@PostMapping("/deleteFreeComment")
	public String deleteFreeComment(@RequestParam("bno") int bno, @RequestParam("cno") int cno) {

		Map<String, Object> deleteFreeCommentData = new HashMap<>();

		deleteFreeCommentData.put("bno", bno);
		deleteFreeCommentData.put("cno", cno);

		freeBoardService.deleteFreeComment(deleteFreeCommentData);

		return "redirect:/freeDetail?bno=" + bno;
	}

	@PostMapping("/freePostLike")
	public String qnaCallDibs(@RequestParam("bno") int bno, @RequestParam("likePostInput") boolean likePostInput,
			HttpSession session) {

		Map<String, Object> freePostLikeData = new HashMap<>();

		int mno = 4; // 추후 세션값으로 변경 예정

		freePostLikeData.put("bno", bno);
		freePostLikeData.put("mno", mno);

		if (likePostInput == true) {

			freeBoardService.delFreePostLike(freePostLikeData);

		} else {
			freeBoardService.addFreePostLike(freePostLikeData);
		}

		return "redirect:/freeDetail?bno=" + bno;
	}

	@PostMapping("/reportFreePost")
	public String reportFreePost(@RequestParam("bno") int bno, @RequestParam("rpcontent") String rpcontent) {

		Map<String, Object> reportData = new HashMap<>();

		  LocalDateTime currentDatetime = LocalDateTime.now();
		
		reportData.put("bno", bno);
		reportData.put("mno", 4); // 추후 세션값으로 변경 예정
		reportData.put("rpcontent", rpcontent);
		reportData.put("rpurl", "http://localhost:8080/qnaDetail?bno=" + bno);
		reportData.put("rpdate", currentDatetime);

		freeBoardService.reportFreePost(reportData);

		return "redirect:/freeDetail?bno=" + bno;
	}

	@ResponseBody
	@PostMapping("/commentReportCount")
	public String commentReportCount(@RequestParam("bno") int bno, @RequestParam("cno") int cno, Model model,
			HttpSession session) {

		// int mno = (int) session.getAttribute("mno");
		int mno = 4; // 추후 세션값으로 변경 예정

		Map<String, Object> commentReportCountData = new HashMap<>();
		commentReportCountData.put("mno", mno);
		commentReportCountData.put("cno", cno);
		
		System.out.println(mno);
		System.out.println(cno);

		int commentReportCount = freeBoardService.commentReportCount(commentReportCountData);
		JSONObject json = new JSONObject();
		json.put("result", commentReportCount);
		
		System.out.println(commentReportCount);

		return json.toString();
	}

	@PostMapping("/reportFreeComment")
	public String reportFreeComment(@RequestParam("bno") int bno, @RequestParam("cno") int cno,
			@RequestParam("rpcontent") String rpcontent, Model model,
			HttpSession session) {

		// int mno = (int) session.getAttribute("mno");
		int mno = 4; // 추후 세션값으로 변경 예정

		Map<String, Object> commentReportData = new HashMap<>();

		  LocalDateTime currentDatetime = LocalDateTime.now();

		
		commentReportData.put("bno", bno); // 추후 세션값으로 변경 예정
		commentReportData.put("cno", cno); // 추후 세션값으로 변경 예정
		commentReportData.put("mno", mno); // 추후 세션값으로 변경 예정
		commentReportData.put("rpcontent", rpcontent);
		commentReportData.put("rpurl", "http://localhost:8080/qnaDetail?bno=" + bno);
		commentReportData.put("rpdate", currentDatetime);

		freeBoardService.reportFreeComment(commentReportData);

		return "redirect:/freeDetail?bno=" + bno;
	}

}
