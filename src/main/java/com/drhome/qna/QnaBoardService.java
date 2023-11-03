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

	public void addQnaCallDibs(Map<String, Object> qnaCallDibsData) {
		qnaBoardDAO.addQnaCallDibs(qnaCallDibsData);
	}

	public void delQnaCallDibs(Map<String, Object> qnaCallDibsData) {
		qnaBoardDAO.delQnaCallDibs(qnaCallDibsData);

	}

	public List<Map<String, Object>> doctorInfo(List<Integer> dnoList) {
		return qnaBoardDAO.doctorInfo(dnoList);
	}

	public void deleteQnaQuestion(Map<String, Object> deleteQnaQuestionData) {
		qnaBoardDAO.deleteQnaQuestion(deleteQnaQuestionData);

	}

	public void reportPost(Map<String, Object> reportData) {
		qnaBoardDAO.reportPost(reportData);

	}

	public List<Map<String, Object>> boardSearchAll(String searchWord) {
		return qnaBoardDAO.boardSearchAll(searchWord);
	}

	public List<Map<String, Object>> boardSearchTitle(String searchWord) {
		return qnaBoardDAO.boardSearchTitle(searchWord);
	}

	public List<Map<String, Object>> boardSearchContent(String searchWord) {
		return qnaBoardDAO.boardSearchContent(searchWord);
	}

	public int reportCount(Map<String, Object> reportCountData) {
		return qnaBoardDAO.reportCount(reportCountData);
	}
}
