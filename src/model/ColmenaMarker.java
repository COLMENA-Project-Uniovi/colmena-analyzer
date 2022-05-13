package model;
public class ColmenaMarker {
	private String compilationId;
	private String userId;
	private String errorId;
	private String message;
	private String type;
	private String lineNumber;
	private String timestamp;
	private String file;
	private String className;
	private String project;
	private String projectName;
	private String ip;

	public ColmenaMarker(String userId, String errorId, String message,
			String type, String lineNumber, String timestamp, String file,
			String project, String ip) {
		this.userId = userId;
		this.errorId = errorId;
		this.message = message;
		this.type = type;
		this.lineNumber = lineNumber;
		this.timestamp = timestamp;
		this.file = file;
		this.className = getFinalPartPath(file);
		this.project = project;
		this.projectName = getFinalPartPath(project);
		this.ip = ip;
	}

	private String getFinalPartPath(String url) {
		String last = "";
		String[] parts = url.split("/");
		last = parts[parts.length-1];
		return last;
	}

	public ColmenaMarker(String[] parts, String id_compilation) {

		this.userId = parts[2];
		this.errorId = parts[1];
		this.message = parts[3];
		this.type = parts[4];
		this.lineNumber = parts[5];
		this.timestamp = parts[6];
		this.file = parts[7];
		this.className = getFinalPartPath(file);
		if (parts.length > 7) {
			this.project = parts[8];
			this.ip = parts[9];
		} else {
			this.project = "";
			this.ip = "0.0.0.0";
		}
		this.projectName = getFinalPartPath(project);
		this.compilationId = id_compilation;

	}

	public String getCompilationId() {
		return compilationId;
	}

	public String getUserId() {
		return userId;
	}

	public String getErrorId() {
		return errorId;
	}

	public String getMessage() {
		return message;
	}

	public String getType() {
		return type;
	}

	public String getLineNumber() {
		return lineNumber;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getFile() {
		return file;
	}

	public String getProject() {
		return project;
	}

	public String getIp() {
		return ip;
	}

	@Override
	public String toString() {
		return "ColmenaMarker \n\t userId :\t " + userId
				+ " \n\t compilationId :\t " + compilationId
				+ " \n\t errorId :\t " + errorId + " \n\t message :\t "
				+ message + " \n\t type :\t\t " + type
				+ " \n\t lineNumber :\t " + lineNumber
				+ " \n\t timestamp :\t " + timestamp + " \n\t file \t\t "
				+ file + " \n\t project :\t " + project + " \n\t ip :\t\t "
				+ ip;
	}

	public String getClassName() {
		return className;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}

}
