package main;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.beans.property.SimpleBooleanProperty;

public class Patient implements Serializable, Cloneable, Comparable {

	private static int pID;
	private static int patNo = 1; 
	private static String patName;
	private String patAddress;
	private String patPhoneNo;
	private static ArrayList<Invoice> PatInList;
	 private SimpleBooleanProperty writePending=new SimpleBooleanProperty();
	public Patient(String name, String address, String num) {
		setPatName(name);
		setPatPhoneNo(num);
		setPatAddress(address);

		patNo = getCounter();
		pID = patNo;

		PatInList = new ArrayList<Invoice>();
	}
	public boolean isSaved(){
        return pID != -1;
    }
    public boolean isDirty(){
        return writePending.get();
    }
    public SimpleBooleanProperty isWritePending() {
        return writePending;
    }
	
	private static void loadFromFile() throws IOException {
		// TODO Auto-generated method stub
		int amountOfmembers = 0;
		File info = new File("information.txt");
		Scanner inputFromFile = new Scanner(info);
	     info = new File("rinformation.txt");
         inputFromFile = new Scanner(info);
         while (inputFromFile.hasNext()) {
        	String output = inputFromFile.nextLine();
        	PatInList =output;
 			amountOfmembers++;
		

		}
		inputFromFile.close();
	}
	public String getPatName() {
		return patName;
	}

	public void setPatName(String patientName) {
		this.patName = patientName;
	}

	public String getPatAddress() {
		return patAddress;
	}

	public void setPatAddress(String patientAdd) {
		this.patAddress = patientAdd;
	}

	public String getPatPhoneNo() {
		return patPhoneNo;
	}

	public void setPatPhoneNo(String patientPhone) {
		this.patPhoneNo = patientPhone;
	}

	public void addInvoice(Invoice in) {
		PatInList.add(in);
	}

	public ArrayList<Invoice> getInvoiceList() {
		return PatInList;
	}

	public int getpID() {
		return pID;
	}

	String getSurname() {
		int nameInd = this.getPatName().lastIndexOf(" ");

		String name = this.getPatName().substring(nameInd);

		return name;
	}

	
	public int compareTo(Object o1) {

		return this.getSurname().compareTo(((Patient) o1).getSurname());

	}


	public int getCounter() {
		
		try {
			int a = Main.pList.get(Main.pList.size() - 1)
					.getpID();
			return a + 1;
		} catch (ArrayIndexOutOfBoundsException e) {
			return 1;
		}

	}
	
	    public void reloadPatient()throws SQLException{
	        final String reloadQuery="SELECT * FROM patient WHERE patient_id=?";
	    if (pID!=-1){
	        Connection connection=DbHelper.getInstance().getConnection();
	        PreparedStatement reload= connection.prepareStatement(reloadQuery);
	        reload.setLong(1, patNo);
	        ResultSet rs=reload.executeQuery();
	        rs.next();
	        patName.set(rs.getString("name"));
	        pID.set(rs.getString("ID"));
	        patPhoneNo.set(rs.getString("phone_number"));
	        patAddress.set(rs.getString("address"));
	      
	        connection.close();
	    }
	    else{
	       System.out.println("Cannot reload; no objects had been saved!");
	    }
	    }
	    public void revertToSaved() throws SQLException{
	        reloadPatient();
	        writePending.setValue(false);
	    }
	  
	    public void setPatientId(int id){
	        this.patNo=patNo;
	    }
	

	public String toString() {
		return getpID() + " " + getPatName() + " " + getPatAddress()
				+ " " + getPatPhoneNo() + "\n";
	}

	public void print() {
		System.out.println(toString());
	}

}