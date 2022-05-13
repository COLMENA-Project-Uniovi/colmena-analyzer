package loader;

import java.util.List;

import model.ColmenaMarker;

public abstract class SessionLoader {

	public abstract List<ColmenaMarker> getListOfColmenaStudentMarkers();

	public abstract void printFileStatics();
	
	public abstract String getSessionName();
	
	public abstract int getSessionId();

}
