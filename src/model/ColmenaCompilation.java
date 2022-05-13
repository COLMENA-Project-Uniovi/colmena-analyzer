package model;

public class ColmenaCompilation {
	private String id;
	private String userId;
	private String sessionId;
	private String type;
	private String timestamp;
	private String numMarkers;
	private String projectName;
	private String className;
	private String ip;
	
	public ColmenaCompilation(String id, String userId, String sessionId,
			String type, String timestamp, String numMarkers, String className,
			String projectName, String ip) {
		this.id = id;
		this.userId = userId;
		this.sessionId = sessionId;
		this.type = type;
		this.timestamp = timestamp;
		this.numMarkers = numMarkers;
		this.className = className;
		this.projectName = projectName;
		this.ip = ip;
	}
	
	public ColmenaCompilation(String[] parts, String userId) {
		
		this.type = formatType(parts[0]);
		this.timestamp = parts[1];
		this.numMarkers = formatNumMarkers(parts[2]);
		this.projectName = parts[3];
		this.className = parts[4];
		this.ip = parts[5];
		this.userId = userId;
		this.id = this.userId + formatTimestamp(this.timestamp);

	}
	
	private String formatNumMarkers(String numMarkers) {
		return numMarkers.replace("markers", "");
	}

	private String formatType(String type){
		return type.replace("_COMPILATION", "");
	}
	
	private String formatTimestamp(String timestamp){
		String formatted = timestamp.replace("-", "");
		formatted = formatted.replace(":", "");
		return formatted;
	}
	
	public String getId() {
		return id;
	}

	public String getUserId() {
		return userId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public String getType() {
		return type;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getNumMarkers() {
		return numMarkers;
	}

	public String getProjectName() {
		return projectName;
	}

	public String getClassName() {
		return className;
	}

	public String getIp() {
		return ip;
	}

	@Override
	public String toString() {
		return "ColmenaCompilation \n\t id :\t " + id
				+ " \n\t userId :\t " + userId + " \n\t num markers :\t "
				+ numMarkers + " \n\t type :\t\t " + type
				+ " \n\t timestamp :\t " + timestamp + " \n\t class name \t\t "
				+ className + " \n\t project name :\t " + projectName + " \n\t ip :\t\t "
				+ ip;
	}

}
