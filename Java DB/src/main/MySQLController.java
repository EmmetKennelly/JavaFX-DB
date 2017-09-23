package main;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import main.Patient;
import main.Payment;
import main.Procedure;

public class MySQLController {

	private static MySQLController self = new MySQLController();
	private String host;
	private String port;
	private String database;
	private String username;
	private String password;
	private Connection conn;
	private Statement stmt;
	private ArrayList<Patient> patList;
	private ArrayList<Procedure> procList;

	private MySQLController() {
		setupConfig();
		startConn();
		checkTables();
	}
	

	public static MySQLController getInstance() {
		return self;
	}
	
	public ArrayList<Patient> getPatList() {
		return patList;
	}
	
	public ArrayList<Procedure> getProcList() {
		return procList;
	}
	
	
	public Procedure findProcedureByNo(int procNo) {
		for(Procedure current: procList) {
			if(current.getProcNo() == procNo) {
				return current;
			}
		}
		return null;
	}
	

	private void setupConfig() {
		host ="125.23.37";
		port = "7095";
		database = "dentalsurgery";
		username = "root";
		password = "root";
	}

	private void startConn() {
		String connection = "mysql://" + host + ":"  + port +  "/" + database;
		try {
			conn = DriverManager.getConnection(connection, username, password);
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}



	public void savePatient(Patient pat) {
		int no = pat.getpID();
		String name = pat.getPatName();
		String addr = pat.getPatAddress();
		String numb = pat.getPatPhoneNo();

		String sqlStat = String.format(
				"INSERT INTO Patients VALUES ('%d',' %s', '%s', '%s')", no,
				name, addr, numb);
		try {
			stmt.executeUpdate(sqlStat);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public void savePayment(int patientNo, Payment pment) {
		int no = pment.getPaymentNum();
		double amount = pment.getPaymentAmt();
		int paid = 0;
		Date sqlDate =  pment.getPaymentDate(username).getTime();

		if (pment.isPaid()) {
			paid = 1;
		}
		try {
			String sqlStatement = String.format(
					"INSERT INTO Payments VALUES ('%d','%f','%s','%d','%d')",
					no, amount, sqlDate, paid, patientNo);
			stmt.executeUpdate(sqlStatement);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	public void saveProcedure(Procedure proc) {
		int no = proc.getProcNo();
		String name = proc.getProcName();
		double cost = proc.getProcCost();

		String sqlStatement = String.format(
				"INSERT INTO Procedures VALUES ('%d','%s','%f')", no, name,
				cost);

		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sqlStatement);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	


	
	
	public void loadData() {
		patList = retrievePatients();
		procList = retrieveProcedures();
		addPatientPayments(patList);
		addPatientProcedures(patList);
	}

	private void addPatientPayments(ArrayList<Patient> patList) {
		try {
			for(Patient current: patList) {
				int patNo = current.getpID();
				String sqlStatement = String.format("SELECT * FROM Payments WHERE patient_no = %d", patNo);
				ResultSet patientPaymentsRs = stmt.executeQuery(sqlStatement);
					
				while(patientPaymentsRs.next()) {
					Date date = patientPaymentsRs.getDate("payment_date");
					int no = patientPaymentsRs.getInt("payment_no");
					boolean paid = patientPaymentsRs.getBoolean("is_paid");
					double amount = patientPaymentsRs.getDouble("amount");
					Payment currentPayment = new Payment(date);
					currentPayment.setPaymentNum(no);
				    Invoice currentInvoice = new Invoice(patNo, no, 0);
					currentPayment.setPaymentAmt(amount);
					currentInvoice.getPaymentList().add(currentPayment);
				}
			
			
		
	private void addPatientProcedures(ArrayList<Patient> patList) {
		for(Patient current: patList) {
			int patNo = current.getpID();
			String sqlStatement = String.format("SELECT DISTINCT proc_no FROM Procedurables WHERE patient_no = %d", patNo);
			try {
				ResultSet patientProceduresRs = stmt.executeQuery(sqlStatement);
				while(patientProceduresRs.next()) {
					int procNo =  patientProceduresRs.getInt("proc_no");
					Procedure proc = findProcedureByNo(procNo);
					 Invoice currentInvoice = new Invoice(patNo, procNo, 0);
					if(proc != null) {
						current.getProcList().add(proc);
					}
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	

	public ArrayList<Patient> retrievePatients() {
		ArrayList<Patient> list = new ArrayList<Patient>();
		
		try{
			ResultSet patientRs = stmt.executeQuery("SELECT * FROM Patients");
			
			while(patientRs.next()) {
				int patient_no = patientRs.getInt("patient_no");
				String name = patientRs.getString("name");
				String addr = patientRs.getString("address");
				String phoneNum = patientRs.getString("phone_number");
				Patient cur = new Patient(name, addr, phoneNum);
				cur.setPatientId(patient_no);
				list.add(cur);				
			}
			
			return list;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Procedure> retrieveProcedures() {
		ArrayList<Procedure> list = new ArrayList<Procedure>();
		
		try {
			ResultSet procRs = stmt.executeQuery("Select * FROM Procedures");
			
			while(procRs.next()) {
				int procNo = procRs.getInt("proc_no");
				String name = procRs.getString("name");
				double cost = procRs.getDouble("cost");
				Procedure current = new Procedure(name, cost);
				current.setProcNo(procNo);
				list.add(current);
			}

		
			return list;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public void editProcedure(Procedure proc) {
		int procNo = proc.getProcNo();
		String name = proc.getProcName();
		double cost = proc.getProcCost();
		
		String sqlStatement = String.format("UPDATE Procedure "
				+ "SET name = '%s', cost = '%f' "
				+ "WHERE proc_no = %d", 
				name, cost, procNo);
		
		try {
			stmt.executeUpdate(sqlStatement);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	
	public void removePatient(int patNo) {
		String patStatement = String.format("DELETE FROM Patients WHERE patient_no = %d", patNo);
		String paymentStatement = String.format("DELETE FROM Payments WHERE patient_no = %d", patNo);
		String procStatement = String.format("DELETE FROM Procedurables WHERE patient_no = %d", patNo);
		
		try {
			stmt.executeUpdate(patStatement);
			stmt.executeUpdate(paymentStatement);
			stmt.executeUpdate(procStatement);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void removeProcedure(int procNo) {
		String procStatement = String.format("DELETE FROM Procedures WHERE proc_no = %d", procNo);
		String patProcStatement = String.format("DELETE FROM Procedurables WHERE proc_no = %d", procNo);
		
		try {
			stmt.executeUpdate(procStatement);
			stmt.executeUpdate(patProcStatement);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void removePayment(int paymentNo) {
		String sqlStatement = String.format("DELETE FROM Payments WHERE payment_no = %d", paymentNo);
		
		try {
			stmt.executeUpdate(sqlStatement);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	

	public void checkTables() {
		try {
			String[] tables = {"Patients", "Procedures", "Payments"};
			DatabaseMetaData metadata = conn.getMetaData();
			
			for(int i=0; i< tables.length; i++) {
				ResultSet rs = metadata.getTables(null, null, tables[i], null);
				if(!rs.next()) {
					createTable(tables[i]);
				}
			}
		} catch(SQLException e) {
			
		}
	}
	

	public void createTable(String table) {
		if(table.equals("Patients")) {
			createPatientTable();
		} else if (table.equals("Procedures")) {
			createProcedureTable();
		} else if (table.equals("Payments")) {
			createPaymentTable();
		} 
	}
	

	public void createTables() {
		createPatientTable();
		createProcedureTable();
		createPaymentTable();
	
	}
	

	private void createPatientTable() {
		String patientTable = "CREATE TABLE Patients ( "
				+ "patient_no INT NOT NULL, "
				+ "name VARCHAR (40) NOT NULL, "
				+ "address VARCHAR (80) NOT NULL, "
				+ "phone_number VARCHAR (40) NOT NULL, "
				+ "PRIMARY KEY (patient_no) "
				+ ");";
		
		try {
			stmt.executeUpdate(patientTable);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	

	private void createProcedureTable() {
		String procedureTable = "CREATE TABLE Procedures ( "
				+ "proc_no INT NOT NULL, "
				+ "name VARCHAR (40) NOT NULL, "
				+ "cost FLOAT(6, 2) NOT NULL, "
				+ "PRIMARY KEY (proc_no) "
				+ ");";
		
		try {
			stmt.executeUpdate(procedureTable);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	private void createPaymentTable() {
		String paymentTable = "CREATE TABLE Payments ( "
				+ "payment_no INT NOT NULL, "
				+ "amount FLOAT(6, 2) NOT NULL, "
				+ "payment_date DATE, "
				+ "is_paid TINYINT(1) NOT NULL, "
				+ "patient_no INT NOT NULL, "
				+ "PRIMARY KEY(payment_no), "
				+ "FOREIGN KEY(patient_no) REFERENCES Patients(patient_no) "
				+ ");";
		try {
			stmt.executeUpdate(paymentTable);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	

	
	}
