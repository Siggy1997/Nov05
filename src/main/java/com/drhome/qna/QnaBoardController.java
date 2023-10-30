package com.drhome.qna;

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
	public String qnaDetail(@RequestParam("bno") int bno, Model model,  HttpSession session) {
		 
		//int hno = (int) session.getAttribute("hno");
		int hno = 1; // 추후 세션값으로 변경 예정
		model.addAttribute("hno", hno);
		
		Map<String, Object> qnaQuestion = qnaBoardService.qnaQuestion(bno);
		model.addAttribute("qnaQuestion", qnaQuestion);

		List<Map<String, Object>> qnaAnswer = qnaBoardService.qnaAnswer(bno);
		model.addAttribute("qnaAnswer", qnaAnswer);

		return "qnaDetail";
	}
	
	@PostMapping("/searchWord")
	public String searchWord(@RequestParam("searchWord") String searchWord, Model model) {
	    List<Map<String, Object>> boardSearchData = qnaBoardService.boardSearch(searchWord);
	    model.addAttribute("boardSearchData", boardSearchData);
	    model.addAttribute("searchWord", searchWord); 
	    return "boardSearch"; 
	}
	
	
	
	@GetMapping("/writeQna")
	public String writeQna(Model model) {


		return "/writeQna";
	}
	
	
	@RequestMapping(value="/postQna", method = RequestMethod.POST)
	public String postQna(@RequestParam("btitle") String btitle, @RequestParam("bcontent") String bcontent, @RequestParam("bdate") String bdate) {
		

	    Map<String, Object> qnaData = new HashMap<>();
	    qnaData.put("btitle", btitle);
	    qnaData.put("bcontent", bcontent);
	    qnaData.put("bdate", bdate);
	    qnaData.put("btype", 0);
	    qnaData.put("mno", 4); // 추후 세션값으로 변경 예정

	    qnaBoardService.postQna(qnaData);
	
	    return "redirect:/qna";
	}
	
	   @PostMapping("/writeQnaAnswer")
	    public String writeQnaAnswer(@RequestParam("bno") int bno, @RequestParam("ccontent") String ccontent, @RequestParam("cdate") String cdate) {
	       
		   // 게시물당 댓글 수 조회
		    int commentCount = qnaBoardService.commentCount(bno);

		    // 새 댓글의 cno 설정
		    int cno = commentCount + 1;
		   
		   Map<String, Object> qnaAnswerData = new HashMap<>();
		   
		   qnaAnswerData.put("bno", bno);
		   qnaAnswerData.put("dno", 1); // 추후 세션값으로 변경 예정
		   qnaAnswerData.put("hno", 1); // 추후 세션값으로 변경 예정
		   qnaAnswerData.put("cno", cno); 
		   qnaAnswerData.put("ccontent", ccontent);
		   qnaAnswerData.put("cdate", cdate);

		    qnaBoardService.writeQnaAnswer(qnaAnswerData);
		   
		   
		    return "redirect:/qnaDetail?bno=" + bno;
	    }
	   
	   
	   @PostMapping("/deleteQnaAnswer")
	    public String deleteQnaAnswer(@RequestParam("bno") int bno, @RequestParam("cno") int cno) {
	       

		   Map<String, Object> deleteQnaAnswerData = new HashMap<>();
		   
		   deleteQnaAnswerData.put("bno", bno);
		   deleteQnaAnswerData.put("cno", cno); 

		    qnaBoardService.deleteQnaAnswer(deleteQnaAnswerData);
		   
		   
		    return "redirect:/qnaDetail?bno=" + bno;
	    }
	
	
	
	

}
