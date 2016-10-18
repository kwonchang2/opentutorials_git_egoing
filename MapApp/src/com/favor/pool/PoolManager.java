/*
 * 
 * JNDI를 이용하여, 외부의 자원으로 분리시켜 놓은 DB정보를 검새갛여 가져와보자.!!
       우리의 경우 ) jdbc/oracle 이 검색 키워드이다.
 */

package com.favor.pool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class PoolManager {
	private static PoolManager instance;
	InitialContext context; //JNDI를 이용하여 외부의 자원을 검색할 수 있는 객체
	DataSource ds; // Conntion을 얻을 수 있는 ConnectionPool 객체이다.
	
	
	private PoolManager() {
		try {
			context = new InitialContext();
			ds = (DataSource)context.lookup("java:/comp/env/jdbc/oracle");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static PoolManager getInstance() {
		if(instance==null){
			instance = new PoolManager();
		}
		return instance;
	}
	
	//DataSource로부터 Connection 객체를 반환
	public Connection getConnetion(){
		Connection con=null;
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
	
	//DML 수행 후 반납 시
	public void freeConnection(Connection con,PreparedStatement pstmt){
		if(pstmt!=null){
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(con!=null){
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//select 수행 후 반납 시
	public void freeConnection(Connection con,PreparedStatement pstmt,ResultSet rs){
		if(pstmt!=null){
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(con!=null){
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
}
