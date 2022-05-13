package loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import manager.UserManager;
import model.ColmenaMarker;
import model.ColmenaUser;

public class StudentFilesLoader extends SessionLoader{

	private final String PATTERN = "Colmena-Dinamic-NonCached-";
	private final String LINE_SEPARATOR = "\t";

	private String parentPath;
	private String sessionPath;
	private int sessionId;
	private List<File> listOfStudentFiles;
	private List<ColmenaMarker> listOfColmenaStudentMarkers;
	private File parentFolder;
	private UserManager um;

	public StudentFilesLoader(String parentPath, String sessionPath, int sessionId) {
		try {
			this.parentPath = parentPath;
			this.sessionPath = sessionPath;
			this.sessionId = sessionId;
			this.listOfStudentFiles = new ArrayList<File>();
			this.listOfColmenaStudentMarkers = new ArrayList<ColmenaMarker>();
			this.parentFolder = new File(parentPath);
			this.um = UserManager.getInstance();

			initializeLoadOperations();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initializeLoadOperations() throws Exception {
		if (parentFolder.isDirectory()) {
			// System.out.println(parentFolder.getName());
			loadStudentFolder(parentFolder);
			loadStudentFiles(listOfStudentFiles);

		} else {
			System.out.println("Error, no es un directorio");
		}

	}

	private void loadStudentFiles(List<File> listOfStudentFiles)
			throws Exception {
		System.out.print("reading content of files");
		for (File f : listOfStudentFiles) {
			ColmenaUser cu = createUserStructure(f);
			BufferedReader br = new BufferedReader(new FileReader(f));
			br.readLine();
			while (br.ready()) {
				String line = br.readLine();
				parseLine(cu, line);
			}
			br.close();
		}

		System.out.println("...done");
	}

	private ColmenaUser createUserStructure(File f) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(f));
		br.readLine();
		String line = br.readLine();
		String[] parts = line.split(LINE_SEPARATOR);
		String userId = parts[0];
		ColmenaUser cu = new ColmenaUser(userId);
		um.addUser(cu);
		br.close();
		return cu;
	}

	private void parseLine(ColmenaUser cu, String line) {
		String[] parts = line.split(LINE_SEPARATOR);
		ColmenaMarker cm = new ColmenaMarker(parts, "none");
		cu.getMarkerList().add(cm);
		listOfColmenaStudentMarkers.add(cm);
	}

	private void loadStudentFolder(File parentDirectory) {
		for (File studentFolder : parentDirectory.listFiles()) {
			readSubFolders(1, studentFolder);
		}
	}

	private void readSubFolders(int index, File directory) {
		// String tabs = "";
		// for (int i = 0; i < index; i++) {
		// tabs += "\t";
		// }

		// System.out.println(tabs + directory.getName());
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
				// SSystem.out.println(tabs + "\t: " + f.getName());
				listOfStudentFiles.add(f);
			} else if (f.isDirectory()) {
				readSubFolders(++index, f);
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
		System.out.println("Participaci�n: "
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
		FilenameFilter ff = new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				if (name.equals(sessionPath)) {
					return true;
				}
				return false;
			}
		};

		int counter = 0;
		for (File f : parentFolder.listFiles()) {
			if (f.listFiles(ff).length != 0) {
				counter++;
			}
		}
		return counter;

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
