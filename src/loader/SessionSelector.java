package loader;

public class SessionSelector {
	public SessionLoader getSessionLoader(String packageName,
			String lessonName, Integer sessionId) {
		// new StudentFilesLoader("D:\\Medina\\Desktop\\alg\\all", "P7", 1);
		return new SessionFilesLoader(packageName, lessonName, sessionId);
	}
}