package com.users.main;

import java.sql.SQLException;
import java.util.Scanner;

import com.users.dao.UserDAOImpl;
import com.users.entity.User;
import com.users.util.Utils;

public class MainClass {
	final Scanner sc = new Scanner(System.in);
	final UserDAOImpl udi = new UserDAOImpl();

	public static void main(String[] args) {
		MainClass m = new MainClass();
//		m.home();
		try {
			m.udi.connect();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		m.login();
	}

	private void home() {
		String login;
		try {
			udi.connect();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		while (true) {
			System.out.println("User Application");
			System.out.println("1. Signup\n2. Login\nPress any key to exit");
			login = sc.next();
			switch (login) {
			case "1":
				signup();
				break;
			case "2":
				login();
				break;
			default:
				try {
					udi.disconnect();
				} catch (SQLException e) {

				}
				sc.close();
				System.out.println("Exiting....");
				System.exit(0);
			}
		}
	}

	private void signup() {
		User u = new User();
		Utils ut = new Utils();
		while (true) {
			System.out.print("Enter name: ");
			u.setName(sc.next());
			if (ut.validateName(u.getName())) {
				break;
			}
			System.out.println("Invalid name format. Please try again!!");
		}

		while (true) {
			System.out.print("Enter mobile: ");
			u.setMobile(sc.next());
			if (ut.validateMobile(u.getMobile())) {
				break;
			}
			System.out.println("Invalid mobile. Please try again!!");
		}
		while (true) {
			System.out.print("Enter email: ");
			u.setEmail(sc.next());
			if (ut.validateEmail(u.getEmail())) {
				break;
			}
			System.out.println("Invalid email. Please try again!!");
		}
		while (true) {
			System.out.print("Enter password: ");
			u.setPassword(sc.next());
			if (ut.validatePassword(u.getPassword())) {
				break;
			}
			System.out.println("Invalid Password format!!\n"
					+ "Password should contain atleast 8 characters and at most 20 characters.\n"
					+ "It should contain at least one digit, one upper case alphabet, one lower case alphabet,\n"
					+ "one special character which includes !@#$%&*()-+=^\n"
					+ "It shouldn’t contain any white space.\nPlease try again!!");
		}
		try {
			udi.newUser(u);
			System.out.println("Sign up successful!! Please Login.");
			home();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void login() {
		String id, password;
		System.out.print("Please enter your email/mobile: ");
		id = sc.next();
		System.out.print("Please enter your password: ");
		password = sc.next();
		try {
			udi.signIn(id, password);
			menu();
		} catch (SQLException e) {
			if (e.getMessage().equals("Wrong password")) {
				System.out.println("Incorrect Password!! Please try again!!");
				login();
			} else if (e.getMessage().equals("Signin Failed")) {
				System.out.println("Login Failed!! Please try again!!");
				home();
			} else if (e.getMessage().equals("Invalid UserName")) {
				System.out.println("Invalid User!! Please try again!!");
				login();
//			} else if (e.getMessage().equals("Invalid Email")) {
//				System.out.println("Invalid Email!! Please try again!!");
//				login();
			} else if (e.getMessage().equals("Invalid Mobile")) {
				System.out.println("Invalid Mobile!! Please try again!!");
				login();
			} else {
				System.out.println("User with " + id + " not found!! Please try again!!");
				home();
			}
		}
	}

	private void menu() {

		String menu;
		try {
			udi.connect();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		while (true) {
			System.out.println("Home Page");

			System.out.println("\n\tMenu\n" + "\n1. View Profile" + "\n2. Search users" + "\n3. Update email"
					+ "\n4. Update mob" + "\n5. Delete Account" + "\n6. Logout" + "\nAny key to exit");
			menu = sc.next();

			switch (menu) {
			case "1":
				viewUser();
				break;
			case "2":
				searchUsers();
				break;
			case "3":
				updateEmail();
				break;
			case "4":
				updateMob();
				break;
			case "5":
				deleteUser();
				break;
			case "6":
				logout();
				break;
			default:
				try {
					udi.logout();
					udi.disconnect();
				} catch (SQLException e) {

				}
				sc.close();
				System.out.println("Exiting....");
				System.exit(0);
			}
		}
	}

	private void viewUser() {
		try {
			User u = udi.viewUser();
			System.out.println(u);
		} catch (SQLException e) {

		}
	}

	private void searchUsers() {
		System.out.print("Enter the name of the user to search: ");
		String name = sc.next();
		try {
			String[] si = udi.getUserByName(name);
			for (int i = 0; i < si.length; i++) {
				System.out.println("" + (i + 1) + "." + si[i]);
			}
		} catch (SQLException e) {
			if (e.getMessage().equals("No Record")) {
				System.out.println("No user with name: " + name);
			}
		}
	}

	private void updateEmail() {
		Utils u = new Utils();
		System.out.print("Enter Old Email: ");
		String oldEmail = sc.next(), newEmail;
		while (true) {
			System.out.print("Enter New Email: ");
			newEmail = sc.next();
			if (u.validateEmail(newEmail)) {
				break;
			}
			System.out.println("Email id not valid!! Enter valid email.");
		}
		try {
			udi.updateUserEmail(oldEmail, newEmail);
			System.out.println("Email Updated successfully!!");
		} catch (SQLException e) {
			System.out.println("Email Update Failed!!");
		}
	}

	private void updateMob() {
		Utils u = new Utils();
		System.out.print("Enter Old Mobile: ");
		String oldMob = sc.next(), newMob;
		while (true) {
			System.out.print("Enter New Mobile: ");
			newMob = sc.next();
			if (u.validateMobile(newMob)) {
				break;
			}
			System.out.println("Invalid Mobile Number!! Enter valid mobile.");
		}
		try {
			udi.updateUserMob(oldMob, newMob);
			System.out.println("Mobile Updated successfully");
		} catch (SQLException e) {
			System.out.println("Mobile Update Failed");
		}
	}

	private void deleteUser() {
		System.out.println("Do you really want to delete account??\nYes(y) or No(n)");
		String a = sc.next();
		while (true) {
			if (a.equalsIgnoreCase("yes") || a.equals("y")) {
				try {
					udi.deleteUser();
					System.out.println("Deleted successfully");
					home();
				} catch (SQLException e) {
					System.out.println("Delete Failed");
					menu();
				}
			} else if (a.equalsIgnoreCase("no") || a.equals("n")) {
				menu();
			} else {
				System.out.println("Please enter valid answer!!");
			}
		}
	}

	private void logout() {
		try {
			udi.logout();
			System.out.println("Logout Successful");
			home();
		} catch (SQLException e) {
			e.printStackTrace();
			menu();
		}
	}

}
