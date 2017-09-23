package main;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Payment implements Serializable, Cloneable {

	private int paymentNum, month, year;
	private static int paymentId = 1;
	private double paymentAmt;
	private String paymentDate;
	private String date;
	SimpleDateFormat form = new SimpleDateFormat("dd MM yyyy");

	
	public Payment(int d, int m, int y, double am) {
		Calendar calendar = new GregorianCalendar(y, m - 1, d);
		date = form.format(calendar.getTime());
		setPayDate(date);
		setPaymentAmt(am);
		paymentNum = paymentId;
		paymentId++;
		setMonth(m);
		setYear(y);
	}

	public void setPayDate(String d) {
		paymentDate = d;
	}

	public String getPaymentDate(String newValue) {
		return paymentDate;
	}

	public void setPaymentAmt(double pay) {
		paymentAmt += pay;

	}

	public double getPaymentAmt() {
		return paymentAmt;
	}

	public void setPaymentNum(int paymentNum) {
		paymentNum =  paymentNum;
	}

	
	public int getPaymentNum() {
		return paymentNum;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getMonth() {
		return month;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getYear() {
		return year;
	}

	public String toString() {
		return null;
	}

	public boolean isPaid() {
		
		return false;
	}
}
	