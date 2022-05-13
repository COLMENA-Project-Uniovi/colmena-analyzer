package manager;

import java.util.ArrayList;
import java.util.List;

import model.ColmenaError;

public class ErrorTypesManager {

	private static ErrorTypesManager instance;
	private List<ColmenaError> errorList;

	private ErrorTypesManager() {
		errorList = new ArrayList<ColmenaError>();
	}

	public static ErrorTypesManager getInstance() {
		if (instance == null) {
			instance = new ErrorTypesManager();
		}
		return instance;
	}

	public void addError(ColmenaError ce) {
		errorList.add(ce);
	}

	public void removeError(ColmenaError ce) {
		errorList.remove(ce);
	}

	public ColmenaError searchError(int errorId) {
		for (ColmenaError ce : errorList) {
			if (Integer.parseInt(ce.getId()) == errorId) {
				return ce;
			}
		}
		return null;
	}

	public void printErrorTypesList() {
		for (ColmenaError ce : errorList) {
			System.out.println(ce);
		}
	}

	public List<ColmenaError> getErrorList() {
		return errorList;
	}

}
