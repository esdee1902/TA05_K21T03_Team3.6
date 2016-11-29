package vn.vanlanguni.ponggame;

public class Settings {
	private String userName1, userName2,ball1,ball2,ball3;

	public Settings() {
	}


	public Settings(String userName1, String userName2,String ball1, String ball2, String ball3) {
		super();
		this.userName1 = userName1;
		this.userName2 = userName2;
		this.ball1 = ball1 ;
		this.ball2 = ball2;
		this.ball3 = ball3;
	}	
	

	public String getUserName1() {
		return userName1;
	}

	public void setUserName1(String userName1) {
		this.userName1 = userName1;
	}

	public String getUserName2() {
		return userName2;
	}

	public void setUserName2(String userName2) {
		this.userName2 = userName2;
	}


	public String getBall1() {
		return ball1;
	}


	public void setBall1(String ball1) {
		this.ball1 = ball1;
	}


	public String getBall2() {
		return ball2;
	}


	public void setBall2(String ball2) {
		this.ball2 = ball2;
	}


	public String getBall3() {
		return ball3;
	}


	public void setBall3(String ball3) {
		this.ball3 = ball3;
	}

	

}
