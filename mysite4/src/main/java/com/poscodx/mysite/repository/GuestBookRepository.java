package com.poscodx.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.GuestbookVo;

@Repository
public class GuestBookRepository {
	private SqlSession sqlSession;
	
	public GuestBookRepository(SqlSession sqlSession){
		this.sqlSession = sqlSession;
	}
	
	public int insert(GuestbookVo vo) {
		int result = sqlSession.insert("guestbook.insert", vo);  // 파라미터가 들어갈 때 - 객체는 하나만 넣을 수 있음
	
		return result;
	}

	public int deleteByNoAnPassword(Long no, String password) {
		return sqlSession.delete("guestbook.deleteByNoAnPassword", Map.of("no", no, "password", password));   // 여러 개의 값을 하나의 객체에담아서 넣어야한다. ex) vo, map
		
	}  // 맵을 사용하는 방법으로 변경하니까 이렇게 됨

	
	public boolean delete(GuestbookVo vo) {
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			//1. JDBC Driver 로딩
			Class.forName("org.mariadb.jdbc.Driver");
			
			//2. 연결하기
			String url = "jdbc:mariadb://192.168.0.202:3306/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");

			//3. Statement 준비
			String sql = "delete from guestbook where no = ? and password = ?";
			pstmt = conn.prepareStatement(sql);
			
			//4. binding
			pstmt.setLong(1, vo.getNo());
			pstmt.setString(2, vo.getPassword());
			
			
			//5. SQL 실행
			int count = pstmt.executeUpdate();
			
			//6. 결과 처리
			result = count == 1;
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

	public List<GuestbookVo> findAll(){
		return sqlSession.selectList("guestbook.findAll");
	}
	
}
