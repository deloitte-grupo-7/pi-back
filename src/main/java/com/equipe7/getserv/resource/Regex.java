package com.equipe7.getserv.resource;

public abstract class Regex {
	public static final String ANY = ".+";
	public static final String DIGIT = "\\d+";
	public static final String EMAIL = "^(?=.{1,}$)^[^_.-][\\w\\d.-]{4,}(?<![._-])@\\w{2,}(\\.{1}[a-zA-Z]{2,}){1,2}(?!\\.)$";
	public static final String NAME = "([a-zA-ZÀ-ú]+[\\s]?)+";
	public static final String PASSWORD = "[\\w.@!#$&*-+=_]+";
	public static final String TEXT = "[À-ú\\w\\s]+";
	public static final String USERNAME = "(?=.*[a-zA-Z])+[\\w.-]+";
	
	public static final boolean any(String field, int min, int max, boolean nullable) {
		if (field == null)
			return !nullable;
		return !field.matches("^(?=.{"+ min + "," + max + "}$)" + ANY);
	}
	
	public static final boolean digit(String field, int min, int max, boolean nullable) {
		if (field == null)
			return !nullable;
		return !field.matches("^(?=.{"+ min + "," + max + "}$)" + DIGIT);
	}
	
	public static final boolean email(String field,int min, int max, boolean nullable) {
		if (field == null)
			return !nullable;
		return !field.matches("^(?=.{"+ min + "," + max + "}$)" + EMAIL);
	}
	
	public static final boolean name(String field, int min, int max, boolean nullable) {
		if (field == null)
			return !nullable;
		return !field.matches("^(?=.{"+ min + "," + max + "}$)" + NAME);
	}
	
	public static final boolean password(String field, int min, int max, boolean nullable) {
		if (field == null)
			return !nullable;
		return !field.matches("^(?=.{"+ min + "," + max + "}$)" + PASSWORD);
	}
	
	public static final boolean text(String field, int min, int max, boolean nullable) {
		if (field == null)
			return !nullable;
		return !field.matches("^(?=.{"+ min + "," + max + "}$)" + TEXT);
	}
	
	public static final boolean username(String field, int min, int max, boolean nullable) {
		if (field == null)
			return !nullable;
		return !field.matches("^(?=.{"+ min + "," + max + "}$)" + USERNAME);
	}
	
	/* -- */

	public static final boolean custom(String field, int min, int max, String regex, boolean nullable) {
		if (field == null)
			return !nullable;
		return !field.matches("^(?=.{"+ min + "," + max + "}$)" + regex);
	}
}
