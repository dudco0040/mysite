package com.poscodx.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.poscodx.mysite.vo.BoardVo;

public class BoardDao {
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
		int result = 0;

		try (
			Connection conn = getConnection();
			PreparedStatement pstmt1 = conn.prepareStatement("INSERT INTO board (title, contents, hit, reg_date, g_no, o_no, depth, user_no) "
					+ "SELECT ?, ?, 0, NOW(), IFNULL(MAX(g_no), 0) + 1, 1, 0, ? FROM board");
					//("insert into board values(null, ?, ?, 0, now(), ?, 1, 0, ?)");  // null, ?, ?, 0, now(), max(g_no)+1, 1, 0, ?)
			
				
				
			PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");
			) {

			System.out.println("[Dao vo] " + vo.getTitle() + vo.getContents() + vo.getgNo() + vo.getUserNo());
			// binding
			// param 작성한 제목과 글
			pstmt1.setString(1, vo.getTitle());
			pstmt1.setString(2, vo.getContents());
//			pstmt1.setLong(3, vo.getgNo());  // vo.getgNo()
			pstmt1.setLong(3, vo.getUserNo());


			result = pstmt1.executeUpdate();

			ResultSet rs = pstmt2.executeQuery();
			vo.setNo(rs.next() ? rs.getLong(1) : null);
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		}

		return result;
	}


	// 글 보기(view)
	public BoardVo findByTitleAndUsernoView(Long no) {
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
				Long userNo = rs.getLong(6);
				
				
				// 기능에 따라 필요한 값만 vo에 저장하기 
				// 1) 글 보기  : no, title, contents
				result = new BoardVo();
				result.setNo(no);
				result.setTitle(viewTitle);
				result.setContents(contents);
//				result.setgNo(g_no);
//				result.setoNo(o_no);
//				result.setDepth(depth);
				result.setUserNo(userNo);
				//System.out.println("[View] no: " + no + " Title: " + viewTitle + "  Contents: " + contents + "  g_no: " + g_no + "  o_no: " + o_no + "  depth: " + depth + "  userNo: " + user_no);
				System.out.println(result);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		}

		return result;
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


	// 글 수정
	public int update(BoardVo vo) {
		int result = 0;
		System.out.println("no: " + vo.getgNo() + " Title: " + vo.getTitle());

		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("update board set title = ?, contents = ?, reg_date=now() where no = ?");
			) {

			// binding
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getNo());  //글 번호

			result = pstmt.executeUpdate();

		}catch (SQLException e) {
			System.out.println("Error:" + e);
		}

		return result;
	}

	// 글 삭제
	public int delete(Long no, Long userNo) {
		int result = 0;

		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("delete from board where no = ? and user_no = ?");
			) {

			//4. binding
			pstmt.setLong(1, no);
			pstmt.setLong(2, userNo);

			//5. SQL 실행
			result = pstmt.executeUpdate();


		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	
	// 답글 달기 
	public int reply(BoardVo vo) {
		int result = 0;
		
		// vo로 받은 값을 update
		 Long UpdateGNo = vo.getgNo();
		 Long UpdateONo = vo.getoNo()+1;
		 Long UpdateDepth = vo.getDepth()+1;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("update board set o_no=o_no+1 where g_no=? and o_no>?");
			
			PreparedStatement pstmt1 = conn.prepareStatement("insert into board(title, contents, hit, reg_date, g_no, o_no, depth, user_no) values(?, ?, 0, now(), ?, ?, ?, ?)");
			PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");
			) {

			System.out.println("[Dao vo] " + vo.getTitle() + vo.getContents() + vo.getgNo() + vo.getUserNo());
			// binding
			// param 작성한 제목과 글
			// update
			// pstmt.setLong(1, vo.getoNo());
			pstmt.setLong(1, vo.getgNo());
			pstmt.setLong(2, vo.getoNo());
			
			// insert
			pstmt1.setString(1, vo.getTitle());
			pstmt1.setString(2, vo.getContents());
			pstmt1.setLong(3, UpdateGNo);
			pstmt1.setLong(4, UpdateONo);
			pstmt1.setLong(5, UpdateDepth);
			pstmt1.setLong(6, vo.getUserNo());

			result = pstmt.executeUpdate();
			result = pstmt1.executeUpdate();

			ResultSet rs = pstmt2.executeQuery();
			vo.setNo(rs.next() ? rs.getLong(1) : null);
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		}

		return result;
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
	    List<BoardVo> result = new ArrayList<>();

	    String sql = "select a.no, a.title, a.user_no, b.name, a.hit, date_format(a.reg_date, '%Y/%m/%d %h:%i:%s'), a.g_no, a.o_no, a.depth "
	    		+ "from board a, user b "
	    		+ "where a.user_no = b.no "
	    		+ "order by a.g_no desc, o_no asc "
	    		+ "limit ?, ?";
	    		
	    try (Connection conn = getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setInt(1, offset);
	        pstmt.setInt(2, limit);

	        ResultSet rs = pstmt.executeQuery();
	        		
            while (rs.next()) {
            	Long no = rs.getLong(1);
				String title = rs.getString(2);
				Long user_no = rs.getLong(3);
				String user_name = rs.getString(4);
				Long hit = rs.getLong(5);
				String reg_date = rs.getString(6);
				Long g_no = rs.getLong(7);
				Long o_no = rs.getLong(8);
				Long depth = rs.getLong(9);

				// vo를 생성
				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setUserName(user_name);   //vo에는 userNo를 넣어줘야 함
				vo.setHit(hit);
				vo.setRegDate(reg_date);
				vo.setgNo(g_no);
				vo.setoNo(o_no);
				vo.setDepth(depth);		// 답글 
				vo.setUserNo(user_no);  

				result.add(vo);

            }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return result;
	}
	
	

}
