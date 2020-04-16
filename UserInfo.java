public class UserInfo {
	
	private int user_id;
	private String firstName;
	private String lastName;
	private String groupid;
	private int result;
	
	public UserInfo(int user_id, String firstName, String lastName, String groupid, int result) {
		
		this.user_id = user_id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.groupid = groupid;
		this.result = result;
	}
	public int getId() {
		return user_id;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getGroup() {
		return groupid;
	}
	public int getResult() {
		return result;
	}

}
