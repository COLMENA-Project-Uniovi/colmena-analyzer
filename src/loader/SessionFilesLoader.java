package loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import manager.UserManager;
import model.ColmenaCompilation;
import model.ColmenaMarker;
import model.ColmenaUser;

public class SessionFilesLoader extends SessionLoader {

	private final String PATTERN = "Colmena-";
	private final String LINE_SEPARATOR = "\t";

	private String parentPath;
	private String sessionPath;
	private int sessionId;
	private List<File> listOfStudentFiles;
	private List<ColmenaMarker> listOfColmenaStudentMarkers;
	private List<ColmenaCompilation> listOfColmenaStudentCompilations;
	private File parentFolder;
	private UserManager um;

	public SessionFilesLoader(String parentPath, String sessionPath, int sessionId) {
		try {
			this.parentPath = parentPath;
			this.sessionPath = sessionPath;
			this.sessionId = sessionId;
			this.listOfStudentFiles = new ArrayList<File>();
			this.listOfColmenaStudentCompilations = new ArrayList<ColmenaCompilation>();
			this.listOfColmenaStudentMarkers = new ArrayList<ColmenaMarker>();
			this.parentFolder = new File(getClass().getResource(parentPath)
					.getPath() + sessionPath);
			this.um = UserManager.getInstance();

			initializeLoadOperations();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initializeLoadOperations() throws Exception {
		if (parentFolder.isDirectory()) {
			loadSessionFolder(parentFolder);
			loadStudentFiles(listOfStudentFiles);

		} else {
			System.out.println("Error, no es un directorio");
		}

	}

	private void loadStudentFiles(List<File> listOfStudentFiles)
			throws Exception {
		System.out.println("reading content of files");
		boolean is_compilation = true; // indicates whether the line is a compilation
		String id_compilation = ""; // contains the id of the current compilation
		for (File f : listOfStudentFiles) {
			ColmenaUser cu = createUserStructure(f);
			BufferedReader br = new BufferedReader(new FileReader(f));
			while (br.ready()) {
				String line = br.readLine();
				
				if (line.equals("")){
					System.out.println(".... empty line");
					is_compilation = true;
					continue;
				}
				if(is_compilation){
					System.out.println(".... is compilation");
					id_compilation = parseLineCompilation(cu, line);
					is_compilation = false;
				} else {
					System.out.println(".... is marker");
					parseLineMarker(cu, line, id_compilation);
				}
			}
			br.close();
		}

		System.out.println("...done");
	}

	private ColmenaUser createUserStructure(File f) throws Exception {
		String file_name = f.getName();
		String[] parts = file_name.split("-");
		String userId_with_txt = parts[parts.length - 1];
		String userId = userId_with_txt.substring(0, userId_with_txt.lastIndexOf('.'));
		System.out.println("Nombre del usuario: " + userId);
		ColmenaUser cu = new ColmenaUser(userId);
		um.addUser(cu);
		return cu;
	}
	
	private String parseLineCompilation(ColmenaUser cu, String line) {
		String[] parts = line.split(LINE_SEPARATOR);
		ColmenaCompilation cm = new ColmenaCompilation(parts, cu.getId());
		cu.getCompilationList().add(cm);
		listOfColmenaStudentCompilations.add(cm);
		return cm.getId();
	}

	private void parseLineMarker(ColmenaUser cu, String line, String id_compilation) {
		String[] parts = line.split(LINE_SEPARATOR);
		ColmenaMarker cm = new ColmenaMarker(parts, id_compilation);
		cu.getMarkerList().add(cm);
		listOfColmenaStudentMarkers.add(cm);
	}

	private void loadSessionFolder(File directory) {
		
		FilenameFilter ff = new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				if (name.startsWith(PATTERN) || dir.isDirectory()
						&& name.equals(sessionPath)) {
					return true;
				}

				return false;
			}
		};

		for (File f : directory.listFiles(ff)) {
			if (f.isFile()) {
				System.out.println("\t: " + f.getName());
				listOfStudentFiles.add(f);
			}
		}
	}

	public void printFileStatics() {
		printFilesFound();
	}

	private void printFilesFound() {
		System.out
				.println("\n\n-------------------------------------------------------------");
		System.out.println("ERRORES SOBRE LA SESION '" + sessionPath + "'");
		System.out
				.println("-------------------------------------------------------------");
		System.out.println("Total: " + listOfStudentFiles.size() + "/"
				+ getTotalStudentsInSession());
		System.out.println("Participación: "
				+ (listOfStudentFiles.size() * 100)
				/ getTotalStudentsInSession() + "%");

		System.out.println("Errores totales leidos "
				+ listOfColmenaStudentMarkers.size());

		System.out.println("Ficheros Cargados");

		for (File f : listOfStudentFiles) {
			System.out.println("\t" + f.getName());
		}
		System.out
				.println("-------------------------------------------------------------");
	}

	public int getTotalStudentsInSession() {
		return parentFolder.listFiles().length;

	}

	/* GETTERS AND SETTERS */

	public String getParentPath() {
		return parentPath;
	}

	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}

	public List<ColmenaMarker> getListOfColmenaStudentMarkers() {
		return listOfColmenaStudentMarkers;
	}

	@Override
	public String getSessionName() {
		return sessionPath;
		
	}

	public int getSessionId() {
		return sessionId;
	}

}
