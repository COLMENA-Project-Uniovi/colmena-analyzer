package model;
import java.util.ArrayList;
import java.util.List;

public class ColmenaUser {
	private String id;
	private List<ColmenaMarker> markerList;
	private List<ColmenaCompilation> compilationList;

	public ColmenaUser(String id) {
		this.id = id;
		this.markerList = new ArrayList<ColmenaMarker>();
		this.compilationList = new ArrayList<ColmenaCompilation>();
	}

	public List<ColmenaMarker> getMarkerList() {
		return markerList;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "ColmenaUser \n\t id :\t " + id + " \n\t markerList size :\t "
				+ markerList.size();
	}

	public List<ColmenaCompilation> getCompilationList() {
		return compilationList;
	}

}
