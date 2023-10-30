package com.drhome.qna;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QnaBoardService {
	
	@Autowired 
	private QnaBoardDAO qnaBoardDAO;

	public List<Map<String, Object>> qnaList() {
		return qnaBoardDAO.qnaList();
	}

	public Map<String, Object> qnaQuestion(int bno) {
		return qnaBoardDAO.qnaQuestion(bno);
	}

	public List<Map<String, Object>> qnaAnswer(int bno) {
		return qnaBoardDAO.qnaAnswer(bno);
	}

	public void postQna(Map<String, Object> qnaData) {
		qnaBoardDAO.postQna(qnaData);
	}

	public void writeQnaAnswer(Map<String, Object> qnaAnswerData) {
		qnaBoardDAO.writeQnaAnswer(qnaAnswerData);

	}

	public int commentCount(int bno) {
		return qnaBoardDAO.commentCount(bno);
	}

	public void deleteQnaAnswer(Map<String, Object> deleteQnaAnswerData) {
		qnaBoardDAO.deleteQnaAnswer(deleteQnaAnswerData);

	}

	public List<Map<String, Object>> boardSearch(String searchWord) {
		return qnaBoardDAO.boardSearch(searchWord);
	}
}
