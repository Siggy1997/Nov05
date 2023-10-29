package com.drhome.qna;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QnaService {
	@Autowired 
	private QnaDAO qnaDAO;

	public List<Map<String, Object>> qnaList() {
		return qnaDAO.qnaList();
	}

	public Map<String, Object> qnaQuestion(int bno) {
		return qnaDAO.qnaQuestion(bno);
	}

	public List<Map<String, Object>> qnaAnswer(int bno) {
		return qnaDAO.qnaAnswer(bno);
	}

	public void postQna(Map<String, Object> qnaData) {
		qnaDAO.postQna(qnaData);
	}

	public void writeQnaAnswer(Map<String, Object> qnaAnswerData) {
		qnaDAO.writeQnaAnswer(qnaAnswerData);

	}

	public int commentCount(int bno) {
		return qnaDAO.commentCount(bno);
	}
}
