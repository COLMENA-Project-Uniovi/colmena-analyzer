package manager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.ColmenaMarker;
import model.ColmenaUser;

public class DispersionManager {
	private Map<String, Integer> errorDispersion;

	public DispersionManager() {

		this.errorDispersion = new HashMap<String, Integer>();

	}

	public void analyzeDispersion() {
		UserManager um = UserManager.getInstance();

		for (ColmenaUser cu : um.getUserList()) {
			analyzeUserErros(cu.getMarkerList());
		}
	}

	private void analyzeUserErros(List<ColmenaMarker> markerList) {
		Set<String> errorsNotDuplicated = new HashSet<String>();
		for (ColmenaMarker cm : markerList) {
			errorsNotDuplicated.add(cm.getErrorId());
		}
		for (String s : errorsNotDuplicated) {
			Integer value = errorDispersion.get(s);
			if (value == null) {
				value = 0;
			}
			value++;
			errorDispersion.put(s, value);
		}
	}

	public Integer getDispersion(String errorId) {
		return errorDispersion.get(errorId);
	}

}
