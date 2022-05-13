package manager;
import java.util.List;

import model.ColmenaMarker;

public class ColmenaMarkerManager {
	private List<ColmenaMarker> listOfMarkers;

	public ColmenaMarkerManager(List<ColmenaMarker> listOfMarkers) {
		this.listOfMarkers = listOfMarkers;
	}

	public void printListOfMarkers() {
		for (ColmenaMarker cm : listOfMarkers) {
			System.out.println(cm);
		}
	}
}
