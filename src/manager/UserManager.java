package manager;

import java.util.ArrayList;
import java.util.List;

import model.ColmenaUser;

public class UserManager {
	private List<ColmenaUser> userList;
	private static UserManager instance;

	private UserManager() {
		this.userList = new ArrayList<ColmenaUser>();
	}

	public static UserManager getInstance() {
		if (instance == null) {
			instance = new UserManager();
		}
		return instance;
	}

	public void addUser(ColmenaUser cu) {
		userList.add(cu);
	}

	public void removeUser(ColmenaUser cu) {
		userList.remove(cu);
	}

	public void printUserList() {
		for (ColmenaUser cu : userList) {
			System.out.println(cu);
		}
	}

	public List<ColmenaUser> getUserList() {
		return userList;
	}

}
