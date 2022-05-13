package loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;

import manager.ErrorTypesManager;
import model.ColmenaError;

public class ErrorTypeLoader {
	private final String SEPARATOR = "\t";

	public ErrorTypeLoader(String pathFile) {
		try {

			loadErrorTypes(pathFile);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadErrorTypes(String pathFile) throws Exception {
		System.out.print("reading error types");
		URL urlToFile = getClass().getResource(pathFile);
		File f = new File(urlToFile.getPath());
		BufferedReader br = new BufferedReader(new FileReader(f));
		while (br.ready()) {
			String line = br.readLine();
			parserErrorType(line);
		}
		br.close();
		System.out.println("...done");
	}

	private void parserErrorType(String line) {
		String[] parts = line.split(SEPARATOR);
		ErrorTypesManager.getInstance().addError(new ColmenaError(parts));
	}

}
