package org.nick.email.templates;

public class ChangePasswordTemplate {

	public static final String header = "WebTimeSheetCalculator - Change Password Request";
	public static final String start  = "It seems you have made a change password request, "
			+ "if you wish to continue, please click in the link below \n\n\n";
	public static final String end    = "\n\nThe WebTimeSheetCalculator team";
	public static final String prefixUrl = "http://localhost:8080/WebTimeSheetCalculator/changePasswordRequest/";
}
