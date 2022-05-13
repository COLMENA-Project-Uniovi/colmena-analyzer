package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

import loader.SessionLoader;
import model.ColmenaCompilation;
import model.ColmenaMarker;
import model.ColmenaUser;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

public class DataBaseManager {

	private Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// con = DriverManager.getConnection(
			// "jdbc:mysql://herbonatura.es/colmena", "colmena", "C0lm3n4");

			// M01.R13-G0n
			con = DriverManager.getConnection(
					"jdbc:mysql://www.pulso.uniovi.es/colmena", "colmena",
					"C0lm3n4");
			
			// System.out.println("conecte");

		} catch (Exception e) {
			e.printStackTrace();

		}
		return con;
	}

	public void saveUserData(List<ColmenaUser> userList, SessionLoader sl,
			Integer subjectID, String subjectTableName, String subjectCompilationTableName) {
		for (ColmenaUser cu : userList) {
			String id = cu.getId();
			List<ColmenaMarker> list = cu.getMarkerList();
			List<ColmenaCompilation> listCompilation = cu.getCompilationList();
			persistUserData(id, subjectID);
			persistUserCompilations(id, listCompilation, sl, subjectCompilationTableName);
			persistUserErrors(id, list, sl, subjectTableName);
			System.out.println("END FILE");
		}

	}

	private void persistUserCompilations(String userId,
			List<ColmenaCompilation> listCompilation, SessionLoader sl,
			String subjectCompilationTableName) {
		try {
			Connection con = getConnection();
			PreparedStatement ps = null;

			for (ColmenaCompilation cc : listCompilation) {
				System.out.println(cc.toString());
				try {
					String insertMarkerStatement = "INSERT INTO " + subjectCompilationTableName +" (`id`, `user_id`, `type`, `timestamp`, `num_markers`, `project_name`, `class_name`, `ip`, `session_id`) "
							+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

					ps = con.prepareStatement(insertMarkerStatement);
					ps.setString(1, cc.getId());
					ps.setString(2, userId);
					ps.setString(3, cc.getType());
					ps.setString(4, cc.getTimestamp());
					ps.setInt(5, Integer.parseInt(cc.getNumMarkers()));
					ps.setString(6, cc.getProjectName());
					ps.setString(7, cc.getClassName());
					ps.setString(8, cc.getIp());
					ps.setInt(9, sl.getSessionId());
					
					ps.execute();

					System.out
							.println("ERROR " + cc.getId() + " inserted");
				} catch (MySQLIntegrityConstraintViolationException duplicate) {
					System.out.println("ERROR " + cc.getId()
							+ " already exists");
					// duplicate.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void persistUserData(String userId, Integer subjectID) {
		try {
			Connection con = getConnection();
			PreparedStatement ps = null;
			String insertUserStatement = "insert into colmena_user (id, password, active, role, dni, displayed_id, name) values (?, ?, ?,?, ?, ?, ?)";

			ps = con.prepareStatement(insertUserStatement);
			ps.setString(1, userId);
			ps.setString(2, userId);
			ps.setInt(3, 1);
			ps.setString(4, "student");
			ps.setInt(5, 0);
			ps.setInt(6, 0);
			ps.setString(7, userId);

			ps.execute();
		
		} catch (MySQLIntegrityConstraintViolationException duplicate) {
			System.out.println("USER " + userId + " already exists");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try{
			Connection con2 = getConnection();
			PreparedStatement ps2 = null;
			String insertUserSubjectStatement = "insert into colmena_user_subject (user_id, subject_id) values (?, ?)";

			ps2 = con2.prepareStatement(insertUserSubjectStatement);
			ps2.setString(1, userId.toUpperCase());
			ps2.setInt(2, subjectID);
	
			ps2.execute();

			System.out.println("USER " + userId + " inserted for subject" + subjectID);
		}catch(MySQLIntegrityConstraintViolationException duplicate){
			System.out.println("USER " + userId + " already exists with subject " + subjectID);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void persistUserErrors(String userId, List<ColmenaMarker> list,
			SessionLoader sl, String subjectTableName) {
		try {
			Connection con = getConnection();
			PreparedStatement ps = null;

			for (ColmenaMarker cm : list) {
				System.out.println(cm.toString());
				// String insertMarkerStatement =
				// "INSERT INTO ColmenaMarker ('user_id', 'error_id', 'gender', 'timestamp', 'path', 'ip', 'project', 'line_number', 'custom_message', 'session_name') VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				try {
					String insertMarkerStatement = "INSERT INTO " + subjectTableName +" (`user_id`, `error_id`, `gender`, `timestamp`, `path`, `class_name`, `ip`, `project`, `project_name`, `line_number`, `custom_message`, `session_id`, `colmena_compilation_id`) "
							+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

					ps = con.prepareStatement(insertMarkerStatement);
					ps.setString(1, userId);
					if (cm.getErrorId().equals("null")) {
						cm.setErrorId("00");
					}
					

					ps.setInt(2, Integer.parseInt(cm.getErrorId()));
					ps.setString(3, cm.getType());
					ps.setString(4, cm.getTimestamp());
					ps.setString(5, cm.getFile());
					ps.setString(6, cm.getClassName());
					ps.setString(7, cm.getIp());
					ps.setString(8, cm.getProject());
					ps.setString(9, cm.getProjectName());
					ps.setInt(10, Integer.parseInt(cm.getLineNumber()));
					ps.setString(11, cm.getMessage());
					ps.setInt(12, sl.getSessionId());
					ps.setString(13, cm.getCompilationId());

					ps.execute();

					System.out
							.println("ERROR " + cm.getErrorId() + " inserted");
				} catch (MySQLIntegrityConstraintViolationException duplicate) {
					System.out.println("ERROR " + cm.getErrorId()
							+ " already exists");
					// duplicate.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
