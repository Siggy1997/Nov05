package com.drhome.free;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FreeBoardService {
	@Autowired 
	private FreeBoardDAO freeBoardDAO;


	public List<Map<String, Object>> freeList() {
		return freeBoardDAO.freeList();
	}


	public Map<String, Object> freePosting(int bno) {
		return freeBoardDAO.freePosting(bno);
	}


	public List<Map<String, Object>> freeComment(int bno) {
		return freeBoardDAO.freeComment(bno);
	}


	public void postFree(Map<String, Object> freeData) {
		freeBoardDAO.postFree(freeData);
		
	}


	public int commentCount(int bno) {
		return freeBoardDAO.commentCount(bno);
	}


	public void writeFreeComment(Map<String, Object> freeCommentData) {
		freeBoardDAO.writeFreeComment(freeCommentData);
		
	}


	public void deleteFreeComment(Map<String, Object> deleteFreeCommentData) {
		freeBoardDAO.deleteFreeComment(deleteFreeCommentData);		
	}


	public void reportFreePost(Map<String, Object> reportData) {
		freeBoardDAO.reportFreePost(reportData);	
		
	}

	public void addFreePostLike(Map<String, Object> freePostLikeData) {
		freeBoardDAO.addFreePostLike(freePostLikeData);	
		
	}

	public void delFreePostLike(Map<String, Object> freePostLikeData) {
		freeBoardDAO.delFreePostLike(freePostLikeData);	
		
	}

	
	public int commentReportCount(Map<String, Object> commentReportCountData) {
		return freeBoardDAO.commentReportCount(commentReportCountData);	
	}

	public void reportFreeComment(Map<String, Object> commentReportData) {
		freeBoardDAO.reportFreeComment(commentReportData);	
		
	}


	public int reportCount(Map<String, Object> reportCountData) {
		return freeBoardDAO.reportCount(reportCountData);	
	}






	
}
