package run;

import loader.ErrorTypeLoader;
import loader.SessionLoader;
import loader.SessionSelector;
import manager.UserManager;
import db.DataBaseManager;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

//		runProcess("files/ALG2014/","Alg_p11", 42, 7);
//		runProcess("files/ALG2014/","Alg_p12", 42, 7); <- FALTA
//		runProcess("files/ALG2014/","Alg_p20", 43, 7);
//		runProcess("files/ALG2014/","Alg_p30", 44, 7);
//		runProcess("files/ALG2014/","Alg_p40", 45, 7);
//		runProcess("files/ALG2014/","Alg_p50", 46, 7);
//		runProcess("files/ALG2014/","Alg_p60", 47, 7);
//		runProcess("files/ALG2014/","Alg_p70", 48, 7);
	
//		runProcess("files/MP2014/","MP_s01", 49, 7, "colmena_marker_mp_2014");
//		runProcess("files/MP2014/","MP_s02", 50, 7, "colmena_marker_mp_2014");
//		runProcess("files/MP2014/","MP_s03", 51, 7, "colmena_marker_mp_2014");
//		runProcess("files/MP2014/","MP_s04", 52, 7, "colmena_marker_mp_2014");
//		runProcess("files/MP2014/","MP_s05", 53, 7, "colmena_marker_mp_2014");
//		runProcess("files/MP2014/","MP_s06", 54, 7, "colmena_marker_mp_2014");
//		runProcess("files/MP2014/","MP_s08", 55, 7, "colmena_marker_mp_2014");
//		runProcess("files/MP2014/","MP_s09", 56, 7, "colmena_marker_mp_2014");
//		runProcess("files/MP2014/","MP_s10", 57, 7, "colmena_marker_mp_2014");
//		runProcess("files/MP2014/","MP_s11", 58, 7, "colmena_marker_mp_2014");
//		runProcess("files/MP2014/","MP_s12", 59, 7, "colmena_marker_mp_2014");
		
		
//		runProcess("files/","AL", 1, 1, "colmena_marker_al_2015", "colmena_compilations_al_2015");
		
//		runProcess("files/","MP2015EX/L6", 78, 9, "colmena_marker_mp_2015", "colmena_compilations_mp_2015");
		runProcess("files/","MPFicheros/p", 77, 9, "colmena_marker_mp_2015", "colmena_compilations_mp_2015");
		
	}

	private static void runProcess(String packageName, String lessonName, Integer sessionId, Integer subjectID, String subjectTableName, String subjectCompilationTableName) {
		// CARGADOR DE TIPOS DE ERROR
		new ErrorTypeLoader("ColmenaErrorTypes.txt");

		// PRINTER
		//Printer p = new Printer();

		// DATABASE MANAGER
		DataBaseManager dbm = new DataBaseManager();

		// ARCHIVOS DETECTADOS
		// p.printDetectedFiles();

		// SESION A CARGAR (POR CARPETA DE USUARIO O POR CARPETA DE PRACTICA)
		SessionLoader sl = new SessionSelector().getSessionLoader(packageName, lessonName, sessionId);

		// ESTADISTICAS DE CARGA
		// p.printLoadingStatics(sl);

		// IMPRIMIMOS DATOS DE LOS USUARIOS
		// p.printUserData();

		// ERRORRES TOTALES
		// p.printTotalErrorReport(sl);
		dbm.saveUserData(UserManager.getInstance().getUserList(), sl, subjectID, subjectTableName, subjectCompilationTableName);

		// USUARIOS VERSION CLUSTERING
		// p.printUserClustering();

		// USUARIOS VERSION REPORT
		// p.printUserForReports();

		// TODOS LOS MARCADORES COMPLETOS
		// p.printDetailedMarkers(sl);
	}

}
