package com.drhome.free;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FreeBoardDAO {


	List<Map<String, Object>> freeList();

	Map<String, Object> freePosting(int bno);

	List<Map<String, Object>> freeComment(int bno);

	void postFree(Map<String, Object> freeData);

	int commentCount(int bno);

	void writeFreeComment(Map<String, Object> freeCommentData);

	void deleteFreeComment(Map<String, Object> deleteFreeCommentData);

	void reportFreePost(Map<String, Object> reportData);

	void addFreePostLike(Map<String, Object> freePostLikeData);

	void delFreePostLike(Map<String, Object> freePostLikeData);

	int commentReportCount(Map<String, Object> commentReportCountData);

	void reportFreeComment(Map<String, Object> commentReportData);

	int reportCount(Map<String, Object> reportCountData);

}
