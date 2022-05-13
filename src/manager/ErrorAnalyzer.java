package manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.ColmenaError;
import model.ColmenaMarker;

public class ErrorAnalyzer {

	private Map<String, Integer> errorFamilies;
	private Map<String, Integer> errorRepetitions;
	private Map<String, String> errorMessages;
	private Map<String, String> errorTypes;

	private List<ColmenaMarker> errorList;

	public ErrorAnalyzer(List<ColmenaMarker> errorList) {
		this.errorFamilies = new HashMap<String, Integer>();
		this.errorRepetitions = new HashMap<String, Integer>();
		this.errorMessages = new HashMap<String, String>();

		this.errorTypes = new HashMap<String, String>();

		this.errorList = errorList;

	}

	public void analyzeErrorList() {
		// System.out.println("Analizamos una lista de errores de " +
		// errorList.size()+ " elementos");
		for (ColmenaMarker cm : errorList) {
			asignFamily(cm.getErrorId());
			analyzeErrors(cm);
		}
	}

	private void analyzeErrors(ColmenaMarker cm) {
		String errorId = cm.getErrorId();
		// System.out.println("Analizamos las repeticiones del error " +
		// errorId);
		Integer value = errorRepetitions.get(errorId);
		if (value == null) {
			// System.out.println("Este es nuevo");
			value = 0;
		}
		value++;

		// System.out.println("Lo guardamos con el valor" + value);
		errorRepetitions.put(errorId, value);

		String message = errorMessages.get(errorId);
		if (message == null || message.equals("")) {
			message = cm.getMessage();
			errorMessages.put(errorId, message);
		}
		
		String type = errorTypes.get(errorId);
		if(type == null || type.equals("")){
			type = cm.getType();
			errorTypes.put(errorId, type);
		}

	}

	private void asignFamily(String errorId) {
		ColmenaError ce = ErrorTypesManager.getInstance().searchError(
				Integer.parseInt(errorId));

		String key = " Unclasified";

		if (ce != null) {
			key = ce.getFirstFamily();
		}

		Integer value = errorFamilies.get(key);

		if (value == null) {
			value = 1;
		} else {
			++value;
		}

		errorFamilies.put(key, value);

	}

	public void printErrorCategories() {
		System.out
				.println("-------------------------------------------------------------");
		System.out.println("CATEGORIAS DE ERROR");
		System.out
				.println("-------------------------------------------------------------");
		Set<String> keys = errorFamilies.keySet();

		int totalCategorized = 0;
		for (String s : keys) {
			int valueCategory = errorFamilies.get(s);
			System.out.println(s + ": " + valueCategory + " ("
					+ (valueCategory * 100) / errorList.size() + "%)");
			totalCategorized += valueCategory;
		}
		System.out.println("__________________________");
		System.out.println("Errores Totales: " + errorList.size());
		System.out.println("Errores Totatles categorizados: "
				+ totalCategorized);

		System.out
				.println("-------------------------------------------------------------\n\n");
	}

	public void printErrorsStatics(boolean isGlobal) {

		System.out
				.println("-------------------------------------------------------------");
		System.out.println("RECUENTO DE ERRORES");
		System.out
				.println("-------------------------------------------------------------");

		if (isGlobal) {
			System.out.println("ID ERROR\tTYPE\tAPAR.\tDIS.\tFAM.\tMENSAJE\n----------------------------------------------------------");
		}else{
			System.out.println("ID ERROR\tTYPE\tAPAR.\tFAM.\tMENSAJE\n----------------------------------------------------------");
		}
			
		DispersionManager dm = new DispersionManager();
		dm.analyzeDispersion();

		for (String s : errorRepetitions.keySet()) {

			Integer repetitions = errorRepetitions.get(s);

			Integer dispersion =0;
			if (isGlobal) {
				dispersion = dm.getDispersion(s);
			}

			String type = errorTypes.get(s).substring(0,3);
			String message = errorMessages.get(s);
			ColmenaError ce = ErrorTypesManager.getInstance().searchError(
					Integer.parseInt(s));

			String family = "---.";

			if (ce != null) {
				family = ce.getFirstFamily().split(" ")[1].substring(0,3)+".";
			}

			String dispersionText = "";
			if (isGlobal) {
				dispersionText = "\t"+ dispersion + "/"
						+ UserManager.getInstance().getUserList().size() ;
			}

			System.out.println(s + "\t" + type + "\t" + repetitions + dispersionText
					+ "\t" + family + "\t" + message);
		}

		Set<String> keys = errorRepetitions.keySet();

		int totalCategorized = 0;

		for (String s : keys) {
			int valueCategory = errorRepetitions.get(s);
			totalCategorized += valueCategory;
		}
		System.out.println("__________________________");
		System.out
				.println("Total de errores detectados al analizar repeticiones: "
						+ totalCategorized);

		System.out
				.println("-------------------------------------------------------------\n");

	}

	public void printErrorsForClustering(String userId, String[] families) {
		
		System.out.print("\n"+ userId + "\t");
		
		for(String s: families){
			if (errorFamilies.get(s) == null ){
				System.out.print("0" + "\t");
			}else{
				System.out.print(errorFamilies.get(s)+ "\t");
			}
		}
		System.out.print("\n");
		
	}
}
