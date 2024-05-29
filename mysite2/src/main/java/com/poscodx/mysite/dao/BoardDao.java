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
			PreparedStatement pstmt1 = conn.prepareStatement("insert into board values(null, ?, ?, 0, now(), ?, 1, 0, ?)");
			PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");
			) {

			System.out.println("[Dao vo] " + vo.getTitle() + vo.getContents() + vo.getgNo() + vo.getUserNo());
			// binding
			// param 작성한 제목과 글
			pstmt1.setString(1, vo.getTitle());
			pstmt1.setString(2, vo.getContents());
			pstmt1.setLong(3, 1);
			pstmt1.setLong(4, vo.getUserNo());


			result = pstmt1.executeUpdate();

			ResultSet rs = pstmt2.executeQuery();
			vo.setNo(rs.next() ? rs.getLong(1) : null);
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		}

		return result;
	}


	// 글 보기
	public BoardVo findByTitleAndUserno(Long no) {
		BoardVo result = null;

		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select a.title, a.contents"
					+ " from board a, user b"
					+ " where a.user_no = b.no"
					+ " and a.no = ?");
			) {

			// binding
			pstmt.setLong(1, no);

			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				String viewTitle = rs.getString(1);
				String contents = rs.getString(2);

				result = new BoardVo();
				result.setNo(no);
				result.setTitle(viewTitle);
				result.setContents(contents);
				System.out.println("[View] no: " + no + " Title: " + viewTitle + "  Contents: " + contents);
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
			PreparedStatement pstmt = conn.prepareStatement("update board set title = ?, contents = ? where no = ?");
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



	// 조회
	public List<BoardVo> findAll() {
		List<BoardVo> result = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			//1. JDBC Driver 로딩
			Class.forName("org.mariadb.jdbc.Driver");

			//2. 연결하기
			String url = "jdbc:mariadb://192.168.0.202:3306/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");

			//3. Statement 준비
			String sql =
					"select a.no, a.title, b.name, a.hit, date_format(a.reg_date, '%Y/%m/%d %H:%i:%s') from board a, user b where a.user_no = b.no";

					//"select no, name, password, contents, date_format(reg_date, '%Y/%m/%d %H:%i:%s') as reg_date from guestbook order by reg_date desc";   // 실제 보여지는 것과 필요한 것...
			pstmt = conn.prepareStatement(sql);

			//5. SQL 실행
			rs = pstmt.executeQuery();

			//6. 결과 처리
			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String user_name = rs.getString(3);
				Long hit = rs.getLong(4);
				String reg_date = rs.getString(5);

				// vo를 생성
				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setUserName(user_name);   //vo에는 userNo를 넣어줘야 함
				vo.setHit(hit);
				vo.setRegDate(reg_date);

//				System.out.println(no + " " + title + " " + title + " " + user_name + " " + reg_date);
				result.add(vo);

			}
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}

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


}
