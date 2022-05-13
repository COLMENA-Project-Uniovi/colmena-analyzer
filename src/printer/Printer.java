package printer;

import loader.SessionLoader;
import manager.ColmenaMarkerManager;
import manager.ErrorAnalyzer;
import manager.ErrorTypesManager;
import manager.UserManager;
import model.ColmenaUser;

public class Printer {

	public void printUserClustering() {

		String[] families = { " Field Related", " Syntax", " Type Related",
				" Import Related", " Method Related", " Uncasified",
				" Construnctor Related", " Internal"

		};

		for (int i = 0; i < families.length; i++) {
			System.out.print("\t" + families[i]);
		}
		for (ColmenaUser cu : UserManager.getInstance().getUserList()) {

			ErrorAnalyzer eau = new ErrorAnalyzer(cu.getMarkerList());
			eau.analyzeErrorList();

			eau.printErrorsForClustering(cu.getId(), families);

		}
	}

	public void printUserForReports() {
		for (ColmenaUser cu : UserManager.getInstance().getUserList()) {

			System.out.println("\n\nAnalizando errores totales de '"
					+ cu.getId() + "'....");

			ErrorAnalyzer eau = new ErrorAnalyzer(cu.getMarkerList());
			eau.analyzeErrorList();

			eau.printErrorsStatics(false);
			eau.printErrorCategories();
		}
	}

	public void printLoadingStatics(SessionLoader sl) {
		sl.printFileStatics();
	}

	public void printUserData() {
		UserManager.getInstance().printUserList();
	}

	public void printTotalErrorReport(SessionLoader sl) {
		System.out.println("\n\nAnalizando errores totales....");
		ErrorAnalyzer ea = new ErrorAnalyzer(
				sl.getListOfColmenaStudentMarkers());
		ea.analyzeErrorList();
		ea.printErrorsStatics(true);
		ea.printErrorCategories();
	}

	public void printDetailedMarkers(SessionLoader sl) {
		ColmenaMarkerManager cmm = new ColmenaMarkerManager(
				sl.getListOfColmenaStudentMarkers());
		cmm.printListOfMarkers();

	}

	public void printDetectedFiles() {
		ErrorTypesManager.getInstance().printErrorTypesList();
	}

}
