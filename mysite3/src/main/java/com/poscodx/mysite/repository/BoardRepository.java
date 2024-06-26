package com.poscodx.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	private SqlSession sqlSession;
	
	public BoardRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://192.168.0.202:3306/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}

		return conn;
	}

	// 글 쓰기
	public int insert(BoardVo vo) {
		System.out.println(vo);
		return sqlSession.insert("board.write", vo);
	}


	// 글 보기(view)
	public BoardVo findByTitleAndUsernoView(Long no) {
		return sqlSession.selectOne("board.view", Map.of("no", no));   // db 에서 select 해오는데, 있는 값을 가져오지 못함 
	}
	

	// 글 수정
	public int update(BoardVo vo) {
		return sqlSession.update("board.update", Map.of("no", vo.getNo() , "title", vo.getTitle(), "contents", vo.getContents()));
	}
	

	// 글 삭제
	public int delete(Long no, Long userNo) {
		return sqlSession.delete("board.delete", Map.of("no", no, "userNo", userNo));
	}
	
	
	// 본문 가져오기(reply)
	public BoardVo findByTitleAndUserno(Long no) {
		BoardVo result = null;

		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select title, contents, g_no, o_no, depth, user_no from board where no = ?");
			) {

			// binding
			pstmt.setLong(1, no);

			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				// no는 input으로 받은 값을 저장
				String viewTitle = rs.getString(1);
				String contents = rs.getString(2);
				Long g_no = rs.getLong(3);
				Long o_no = rs.getLong(4);
				Long depth = rs.getLong(5);
				Long user_no = rs.getLong(6);
				
				
				// 기능에 따라 필요한 값만 vo에 저장하기 
				// 1) 답글 달기" : no, g_no, o_no, depth
				result = new BoardVo();
				result.setNo(no);
				result.setgNo(g_no);
				result.setoNo(o_no);
				result.setDepth(depth);
				// result.setUserNo(user_no);
				//System.out.println("[View] no: " + no + " Title: " + viewTitle + "  Contents: " + contents + "  g_no: " + g_no + "  o_no: " + o_no + "  depth: " + depth + "  userNo: " + user_no);
				System.out.println(result);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		}

		return result;
	}

	public void replyUpdate(Long gNo, Long oNo) {
//		// vo로 받은 값을 update
//		 Long UpdateGNo = gNo;
//		 Long UpdateONo = oNo+1;
//		 Long UpdateDepth = vo.getDepth()+1;
		// 이 부분을 어디에 넣어줄지..!
		Map<String, Long> map = new HashMap<>();
		map.put("gNo", gNo);
		map.put("oNo", oNo);		
		sqlSession.update("board.replyUpdate", Map.of("map", map));
	}
	
	// 답글 달기 
	public int reply(BoardVo vo) {
		return sqlSession.insert("board.reply", vo);   // Map.of를 사용하지 않고 전달 !! 
	}
	
	// 조회수
	public int updateHit(long no) {
		int result = 0;
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE board SET hit = hit + 1 WHERE no = ?");
			) {

			//4. binding
			pstmt.setLong(1, no);

			//5. SQL 실행
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 전체 글 개수 구하기
	public int countRecords() {
	    int count = 0;
	    try (
	            Connection conn = getConnection();
	            PreparedStatement pstmt = conn.prepareStatement("select count(*) from board");
	            ResultSet rs = pstmt.executeQuery();  // ResultSet 객체 생성
	        ) {

	        // ResultSet에서 count 값 추출
	        if (rs.next()) {
	            count = rs.getInt(1);  // 첫 번째 컬럼의 값을 가져옴
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return count;
	}

	
	// List
	public List<BoardVo> getList(int offset, int limit) {
		return sqlSession.selectList("board.findAll", Map.of("offset", offset, "limit", limit));
	}
	
}
