package com.users.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import com.users.entity.User;

public class MysqlDB {
	private final String dbUrl = "jdbc:mysql://localhost:3306/userdb";
	private final String user = "root";
	private final String password = "pksv";
	private Connection con;
	private PreparedStatement p;
	private ResultSet rs;

	private String query;

	public void getDBConnection() throws SQLException {
		if (con == null) {
			con = DriverManager.getConnection(dbUrl, user, password);
		}
	}

	public void disconnect() throws SQLException {
		con.close();
	}

	public void signUp(User u) throws SQLException {
		Random r = new Random();
		int a = r.nextInt(5000);
		query = "insert into userdb.user values (?,?,?,?,?)";
		p = con.prepareStatement(query);
		p.setInt(1, a);
		p.setString(2, u.getName());
		p.setString(3, u.getEmail());
		p.setString(4, u.getMobile());
		p.setString(5, u.getPassword());
		int c = p.executeUpdate();
		if (c == 0) {
			SQLException sql = new SQLException("Signup Failed");
			throw sql;
		}
	}
	
	public void signInEmail(String email, String password) throws SQLException {
		query = "select * from userdb.user where email = ?";
		p = con.prepareStatement(query);
		p.setString(1, email);
		rs = p.executeQuery();
		rs.next();
		User u = new User();
		int id = rs.getInt("id");
		u.setName(rs.getString("name"));
		u.setEmail(rs.getString("email"));
		u.setMobile(rs.getString("mobile"));
		u.setPassword(rs.getString("password"));
		if(!u.getEmail().equalsIgnoreCase(email)) {
			SQLException sql = new SQLException("Invalid Email");
			throw sql;
		}
		if (u.getPassword().equals(password)) {
			query = "insert into userdb.temp values (?,?,?,?,?)";
			p = con.prepareStatement(query);
			p.setInt(1, id);
			p.setString(2, u.getName());
			p.setString(3, u.getEmail());
			p.setString(4, u.getMobile());
			p.setString(5, u.getPassword());
			int c = p.executeUpdate();
			if (c == 0) {
				SQLException sql = new SQLException("Signin Failed");
				throw sql;
			}
		} else {
			SQLException sql = new SQLException("Wrong password");
			throw sql;
		}
	}

	public void signInMobile(String mobile, String password) throws SQLException {
		query = "select * from userdb.user where mobile = ?";
		p = con.prepareStatement(query);
		p.setString(1, mobile);
		rs = p.executeQuery();
		rs.next();
		User u = new User();
		int id = rs.getInt("id");
		u.setName(rs.getString("name"));
		u.setEmail(rs.getString("email"));
		u.setMobile(rs.getString("mobile"));
		u.setPassword(rs.getString("password"));
		if(!u.getMobile().equals(mobile)) {
			SQLException sql = new SQLException("Invalid Mobile");
			throw sql;
		}
		if (u.getPassword().equals(password)) {
			query = "insert into userdb.temp values (?,?,?,?,?)";
			p = con.prepareStatement(query);
			p.setInt(1, id);
			p.setString(2, u.getName());
			p.setString(3, u.getEmail());
			p.setString(4, u.getMobile());
			p.setString(5, u.getPassword());
			int c = p.executeUpdate();
			if (c == 0) {
				SQLException sql = new SQLException("Signin Failed");
				throw sql;
			}
		} else {
			SQLException sql = new SQLException("Wrong password");
			throw sql;
		}
	}

	public User getUserProfile() throws SQLException {
		query = "select * from userdb.temp; ";
		p = con.prepareStatement(query);
		rs = p.executeQuery();
		rs.next();
		User u = new User();
		u.setName(rs.getString("name"));
		u.setEmail(rs.getString("email"));
		u.setMobile(rs.getString("mobile"));
		u.setPassword(rs.getString("password"));
		return u;
	}
	
	public String[] searchByName(String name) throws SQLException {
		query = "select count(*) from userdb.user where name like ?;";
		p = con.prepareStatement(query);
		p.setString(1, "%" + name + "%");
		rs = p.executeQuery();
		rs.next();
		int count = rs.getInt(1), i = 0;
		if (count == 0) {
			SQLException sql = new SQLException("No Record");
			throw sql;
		}
		String s[] = new String[count];
		query = "select * from userdb.user where name like ?;";
		p = con.prepareStatement(query);
		p.setString(1, "%" + name + "%");
		rs = p.executeQuery();
		rs.next();
		while (i < count) {
			s[i++] = rs.getString("name");
			rs.next();
		}
		return s;
	}

	public void updateEmail(String oldEmail, String newEmail) throws SQLException {
		query = "update userdb.user set email = ? where email = ?;";
		p = con.prepareStatement(query);
		p.setString(1, newEmail);
		p.setString(2, oldEmail);
		int c = p.executeUpdate();
		if (c == 0) {
			SQLException sql = new SQLException("Update Failed");
			throw sql;
		}
		query = "update userdb.temp set email = ? where email = ?;";
		p = con.prepareStatement(query);
		p.setString(1, newEmail);
		p.setString(2, oldEmail);
		c = p.executeUpdate();
		if (c == 0) {
			query = "update userdb.user set email = ? where email = ?;";
			p = con.prepareStatement(query);
			p.setString(1, oldEmail);
			p.setString(2, newEmail);
			SQLException sql = new SQLException("Update Failed");
			throw sql;
		}
	}

	public void updateMob(String oldMob, String newMob) throws SQLException {
		query = "update userdb.user set mobile = ? where mobile = ?;";
		p = con.prepareStatement(query);
		p.setString(1, newMob);
		p.setString(2, oldMob);
		int c = p.executeUpdate();
		if (c == 0) {
			SQLException sql = new SQLException("Update Failed");
			throw sql;
		}
		query = "update userdb.user set mobile = ? where mobile = ?;";
		p = con.prepareStatement(query);
		p.setString(1, newMob);
		p.setString(2, oldMob);
		c = p.executeUpdate();
		if (c == 0) {
			query = "update userdb.user set mobile = ? where mobile = ?;";
			p = con.prepareStatement(query);
			p.setString(1, oldMob);
			p.setString(2, newMob);
			SQLException sql = new SQLException("Update Failed");
			throw sql;
		}
	}

	public void delete() throws SQLException {
		query = "select * from userdb.temp";
		p = con.prepareStatement(query);
		rs = p.executeQuery();
		rs.next();
		String email = rs.getString("email");
		query = "delete from userdb.user where email = ?;";
		p = con.prepareStatement(query);
		p.setString(1, email);
		int c = p.executeUpdate();
		if (c == 0) {
			SQLException sql = new SQLException("Delete Failed");
			throw sql;
		}
		signOut();
	}

	public void signOut() throws SQLException {
		query = "delete from userdb.temp;";
		p = con.prepareStatement(query);
		int c = p.executeUpdate();
		if (c == 0) {
			SQLException sql = new SQLException("Logout Failed");
			throw sql;
		}
	}
}
