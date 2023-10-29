package com.drhome.qna;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QnaController {
	@Autowired
	QnaService qnaService;

	@GetMapping("/qna")
	public String qna(Model model) {

		List<Map<String, Object>> qnaList = qnaService.qnaList();
		model.addAttribute("qnaList", qnaList);

		return "/qna";
	}

	@PostMapping("/qna")
	public String qnaPost(@RequestParam("bno") int bno) {
		
		return "redirect:/qnaDetail?bno=" + bno;

	}

	@GetMapping("/qnaDetail")
	public String qnaDetail(@RequestParam("bno") int bno, Model model) {

		Map<String, Object> qnaQuestion = qnaService.qnaQuestion(bno);
		model.addAttribute("qnaQuestion", qnaQuestion);

		List<Map<String, Object>> qnaAnswer = qnaService.qnaAnswer(bno);
		model.addAttribute("qnaAnswer", qnaAnswer);

		return "qnaDetail";
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
	    qnaData.put("mno", 1); // 추후 세션값으로 변경 예정

	    qnaService.postQna(qnaData);
	
	    return "redirect:/qna";
	}
	
	   @PostMapping("/writeQnaAnswer")
	    public String writeQnaAnswer(@RequestParam("bno") int bno, @RequestParam("ccontent") String ccontent, @RequestParam("cdate") String cdate) {
	       
		   // 게시물당 댓글 수 조회
		    int commentCount = qnaService.commentCount(bno);

		    // 새 댓글의 cno 설정
		    int cno = commentCount + 1;
		   
		   Map<String, Object> qnaAnswerData = new HashMap<>();
		   
		   System.out.println(qnaAnswerData);
		   
		   qnaAnswerData.put("bno", bno);
		   qnaAnswerData.put("dno", 1); // 추후 세션값으로 변경 예정
		   qnaAnswerData.put("hno", 1); // 추후 세션값으로 변경 예정
		   qnaAnswerData.put("cno", cno); 
		   qnaAnswerData.put("ccontent", ccontent);
		   qnaAnswerData.put("cdate", cdate);

		    qnaService.writeQnaAnswer(qnaAnswerData);
		   
		   
		    return "redirect:/qnaDetail?bno=" + bno;
	    }
	
	
	
	

}
