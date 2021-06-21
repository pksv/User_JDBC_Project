package com.users.dao;

import java.sql.SQLException;

import com.users.entity.User;

public interface UserDAO {
	public void connect() throws SQLException;

	public void newUser(User u) throws SQLException;

	public void signIn(String id, String password) throws SQLException;

	public User viewUser() throws SQLException;

	public String[] getUserByName(String name) throws SQLException;

	public void updateUserEmail(String oldEmail, String newEmail) throws SQLException;

	public void updateUserMob(String oldMob, String newMob) throws SQLException;

	public void deleteUser() throws SQLException;
	
	public void logout() throws SQLException;

	public void disconnect() throws SQLException;
}
