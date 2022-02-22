package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.FileboardReplyDTO;

public class FileboardReplyDAO {
	
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;

	public Connection getConnection() {

		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/funweb";
		String user = "root";
		String password = "1234";

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return con;

	}

	public void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void close(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int insertReply(FileboardReplyDTO dto) {
		int insertReplyCount = 0;
		con = getConnection();

		// 현재 게시물의 가장 큰 번호를 정수형 변수 num에 저장!
		// 단, 기존 게시물이 하나도 없을 경우 기본값으로 사용할 값(1)을 미리 저장하기
		try {
			
			String sql = "INSERT INTO fileboard_reply VALUES (null,?,?,?,now())";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getRoot_num());
			pstmt.setString(2, dto.getId());
			pstmt.setString(3, dto.getFileboard_reply());

			insertReplyCount = pstmt.executeUpdate();
					
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류!");
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(con);
		}
		
		return insertReplyCount;

	}
	
	public ArrayList<FileboardReplyDTO> replyList(int num) {
		ArrayList<FileboardReplyDTO> replyDTOList = new ArrayList<FileboardReplyDTO>();
		con = getConnection();
		try {
			String sql = "SELECT * FROM fileboard_reply WHERE root_num=? ORDER BY date DESC";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				FileboardReplyDTO replyDTO = new FileboardReplyDTO();
				replyDTO.setNum(rs.getInt("num"));
				replyDTO.setId(rs.getString("id"));
				replyDTO.setFileboard_reply(rs.getString("fileboard_reply"));
				replyDTO.setDate(rs.getDate("date"));
				replyDTOList.add(replyDTO);
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류!");
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}
		return replyDTOList;
	}
	
	public int deleteReply(int num) {
		int deleteReplyCount = 0;
		con = getConnection();
		try {
			String sql = "SELECT * FROM fileboard_reply WHERE num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				sql = "DELETE FROM fileboard_reply WHERE num = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, num);
				deleteReplyCount = pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류!");
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}
		return deleteReplyCount;
	}
	
	public int updateReply(int num, String re_reply) {
		int updateReplyCount = 0;
		con = getConnection();
		try {
			String sql = "SELECT * FROM fileboard_reply WHERE num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				sql = "UPDATE fileboard_reply SET fileboard_reply = ? WHERE num = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, re_reply);
				pstmt.setInt(2, num);
				updateReplyCount = pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류!");
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}
		return updateReplyCount;
	}
	
	public void deleteAllReply(int num) {
		con = getConnection();
		try {
			String sql = "DELETE FROM fileboard_reply WHERE root_num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류!");
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(con);
		}
	}

}
