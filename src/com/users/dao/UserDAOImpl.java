package com.users.dao;

import java.sql.SQLException;

import com.users.db.MysqlDB;
import com.users.entity.User;
import com.users.util.Utils;

public class UserDAOImpl implements UserDAO {
	private MysqlDB db = new MysqlDB();

	@Override
	public void connect() throws SQLException {
		db.getDBConnection();
	}

	@Override
	public void newUser(User u) throws SQLException {
		db.signUp(u);
	}

	@Override
	public void signIn(String id, String password) throws SQLException {
		Utils u = new Utils();
		if (u.validateEmail(id)) {
			db.signInEmail(id, password);
		} else if (u.validateMobile(id)) {
			db.signInMobile(id, password);
		}else {
			SQLException sql = new SQLException("Invalid UserName");
			throw sql;
		}
		
	}

	@Override
	public User viewUser() throws SQLException {
		return db.getUserProfile();
	}

	@Override
	public String[] getUserByName(String name) throws SQLException {
		return db.searchByName(name);
	}

	@Override
	public void updateUserEmail(String oldEmail, String newEmail) throws SQLException {
		db.updateEmail(oldEmail, newEmail);
	}

	@Override
	public void updateUserMob(String oldMob, String newMob) throws SQLException {
		db.updateMob(oldMob, newMob);
	}

	@Override
	public void deleteUser() throws SQLException {
		db.delete();
	}

	@Override
	public void logout() throws SQLException {
		db.signOut();
	}

	@Override
	public void disconnect() throws SQLException {
		db.disconnect();
	}

}
