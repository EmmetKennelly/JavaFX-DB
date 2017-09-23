package main;

public class Procedure {

	private static int currentProcNo = 0;
	private int procNo;
	private String procName;
	private double procCost;

	public Procedure() {
	}

	public Procedure(String procName, double procCost) {
		currentProcNo++;
		setProcNo(currentProcNo);
		setProcName(procName);
		setProcCost(procCost);
	}



	public void setProcNo(int procNo) {
		this.procNo = procNo;
	}

	public int getProcNo() {
		return procNo;
	}

	public void setProcCost(double procCost) {
		this.procCost = procCost;
	}

	public String getProcName() {
		return procName;
	}

	public static void setcurrentProcNo(int procNo) {
		Procedure.currentProcNo = procNo;
	}

	public static int getcurrentProcNo() {
		return currentProcNo;
	}

	public void setProcName(String procName) {
		this.procName = procName;
	}

	public double getProcCost() {
		return procCost;
	}

	public String toString() {
		return ("Number: " + this.procNo + " Name: " + this.procName
				+ " Cost: " + this.procCost);
	}
}