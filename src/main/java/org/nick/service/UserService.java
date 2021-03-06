package org.nick.service;

import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.nick.form.LeaveRequestForm;
import org.nick.form.RegisterForm;
import org.nick.model.EmailSubscription;
import org.nick.model.Notification;
import org.nick.model.TimeSheet;
import org.nick.model.User;

public interface UserService {

	public void updateTimeSheet(TimeSheet timesheet);
	
	public void insertTimeSheet(TimeSheet timesheet);
	
	public boolean insertOrUpdateTimeSheet(TimeSheet timesheet);
	
	public boolean registerUser(RegisterForm form);
	
	public boolean insertOrUpdateUser(User user);
	
	public User getAuthenticatedUser();

	public Date getCurrentTime();

	public Date getLastUpdateOfTimesheetForMonth(User user, long month);
	
	public List<TimeSheet> getStatisticsByUserId(long userID);

	public void deleteTimeSheetByMonth(long id, long userId);
	
	//USERS CRUD
	public List<User> findAllUsers();
	
	public User findById(long id);
	
	public User findByUsername(String username);
	
	public User findByEmail(String email);
	
	public void writeChangePassRequestInDB(User user, String id);
	
	public boolean isUserExist(User user);
	
	public void saveUser(User user);
	
	public void updateUserUsername(User currentUser);
	
	public void deleteUserById(long id);
	
	public void deleteAllUsers();
	
	public long getLastUserId();
	
	public boolean usernameExists(User user);
	
	public boolean userAwaitsEnable(String username);
	
	public void activateUser(boolean enabled, String username,String password, String confirmid);

	void downloadTimeSheetByMonth(long id, long userId, HttpServletResponse response);

	void createXLS(String month, File file, HttpServletResponse response);
	
	void createPDF(File file, HttpServletResponse response);

	public String getUserImageBase64(User user);
	
	public void uploadPhoto();
	
	public void deletePhoto();
	
	public byte[] getDefaultImage();
	
	public EmailSubscription isUserSubscribed(User user);
	
	public void subscribeUser(User user);

	public void unsubscribeUser(User user);
	
	public String getUserCurrentMean(User user);
	
	public User searchConfirmUserByID(String id);
	
	public User searchChangeRequestPasswordUserByID(String id);
	
	public void changePasswordUsingCRid(String id, String newpassword);

	public boolean mailExists(String mail);

	public void createXML(File file, HttpServletResponse response);
	
	public User customUserlXMLAuthentication(String username, String password);

	boolean registerUserObj(User user);

	public void changeLoggedInAuthenticatedUser(String username, String password);
	
	public List<User> excludeCurrentUser();

	public void createLeaveRequestNoticications(String[] recipientsIds, long referredId, LeaveRequestForm form) throws ParseException;

	public void mailNotifications(String[] recipientsIds, User referrer, LeaveRequestForm form);

	public boolean compareDates(String dateFrom, String dateTo);

	public Set<Notification> getUserNotifications(User user);

	public Set<Notification> getUnwatchedNotifications(User user);

	public void readNotifications(User user);

	public Notification getNotificationById(long id);

	public void createNotificationReply(String answer, long notification_id);

	
	
	
}
