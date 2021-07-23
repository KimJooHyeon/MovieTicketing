package util;

public class SessionUtil {
	private static SessionUtil instance = new SessionUtil();
	private String userId;
	private boolean login;
	private int currentOrdersheetNum;
	private int selectedTime;

	private SessionUtil() {
	}

	public int getCurrentOrdersheetNum() {
		return currentOrdersheetNum;
	}

	public void setCurrentOrdersheetNum(int currentOrdersheetNum) {
		this.currentOrdersheetNum = currentOrdersheetNum;
	}

	public static SessionUtil getInstance() {
		return instance;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isLogin() {
		return login;
	}

	public void setLogin(boolean login) {
		this.login = login;
	}

	public int getSelectedTime() {
		return selectedTime;
	}

	public void setSelectedTime(int selectedTime) {
		this.selectedTime = selectedTime;
	}
	
	
	
}