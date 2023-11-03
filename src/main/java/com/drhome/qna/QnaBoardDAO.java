package com.drhome.qna;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QnaBoardDAO {

	List<Map<String, Object>> qnaList();

	Map<String, Object> qnaQuestion(int bno);

	List<Map<String, Object>> qnaAnswer(int bno);

	void postQna(Map<String, Object> qnaData);

	void writeQnaAnswer(Map<String, Object> qnaAnswerData);

	int commentCount(int bno);

	void deleteQnaAnswer(Map<String, Object> deleteQnaAnswerData);

	List<Map<String, Object>> boardSearchAll(String searchWord);

	void addQnaCallDibs(Map<String, Object> qnaCallDibsData);

	void delQnaCallDibs(Map<String, Object> qnaCallDibsData);

	List<Map<String, Object>> doctorInfo(List<Integer> dnoList);

	void deleteQnaQuestion(Map<String, Object> deleteQnaQuestionData);

	void reportPost(Map<String, Object> reportData);

	List<Map<String, Object>> boardSearchTitle(String searchWord);

	List<Map<String, Object>> boardSearchContent(String searchWord);

	int reportCount(Map<String, Object> reportCountData);


}
