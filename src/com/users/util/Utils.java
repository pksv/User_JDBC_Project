package com.users.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	public boolean validateName(String name) {
		String patternName = "((\\p{Lower}{3,}\\s?)(\\p{Lower}+\\s?)?){1,}";
		return name.matches(patternName);
	}

	public boolean validateEmail(String email) {
		Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.com", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(email);
		return matcher.find();
	}

	public boolean validateMobile(String mobile) {
		String pattern = "[6-9][0-9]{9}";
		return mobile.matches(pattern);
	}

	public boolean validatePassword(String password) {
		Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$");
		Matcher matcher = pattern.matcher(password);
		return matcher.find();
	}

}
