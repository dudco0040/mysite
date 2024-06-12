package com.poscodx.mysite.repository;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.UserVo;

@Repository
public class UserRepository {
	private SqlSession sqlSession;
	
	public UserRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	
	public int insert(UserVo vo) {
		return sqlSession.insert("user.insert", vo);
	}

	
	public UserVo findByNoAndPassword(String email, String password) {
		return sqlSession.selectOne(
				"findByNoAndPassword"
				, Map.of("email", email, "password", password));
	}

	
	public UserVo findByNo(Long no) {
		return sqlSession.selectOne("user.findByNo", no);
	}


	public int update(UserVo vo) {	
		return sqlSession.selectOne("user.udate", vo);

	}

	public UserVo findByEmail(String email) {
		return sqlSession.selectOne("user.findByEmail", email);
	}
	

}
