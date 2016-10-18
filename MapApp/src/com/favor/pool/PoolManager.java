/*
 * 
 * JNDI�� �̿��Ͽ�, �ܺ��� �ڿ����� �и����� ���� DB������ �˻����� �����ͺ���.!!
       �츮�� ��� ) jdbc/oracle �� �˻� Ű�����̴�.
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
	InitialContext context; //JNDI�� �̿��Ͽ� �ܺ��� �ڿ��� �˻��� �� �ִ� ��ü
	DataSource ds; // Conntion�� ���� �� �ִ� ConnectionPool ��ü�̴�.
	
	
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
	
	//DataSource�κ��� Connection ��ü�� ��ȯ
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
	
	//DML ���� �� �ݳ� ��
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
	
	//select ���� �� �ݳ� ��
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
