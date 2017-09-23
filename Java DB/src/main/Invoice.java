package main;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Invoice implements Serializable {

	private int invoiceNo;
	private static int invoiceCount = 1;
	private boolean isPaid;
	private int year;
	private Calendar invoiceDate;
	private ArrayList<Procedure> in_procList;
	private ArrayList<Payment> in_paymentList;

	private SimpleDateFormat form = new SimpleDateFormat("dd MM yyyy");
	private String date;

	public Invoice(int d, int m, int y) {

		invoiceDate = new GregorianCalendar(y, m - 1, d);

		date = form.format(invoiceDate.getTime());
		setPaid(false);
		in_procList = new ArrayList<Procedure>();
		in_paymentList = new ArrayList<Payment>();
		this.year = y;

		invoiceCount = getCounter();
		invoiceNo = invoiceCount;
	}

	public int getInvoiceNo() {
		return invoiceNo;
	}

	public boolean getIsPaid() {

		return isPaid;

	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	public String getInvoiceDate() {
		return date;
	}

	public void setInvoiceDate(Calendar invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public ArrayList<Procedure> getProcList() {
		return in_procList;
	}

	public void setProcList(ArrayList<Procedure> in_procList) {
		this.in_procList = in_procList;
	}

	public ArrayList<Payment> getPaymentList() {
		return in_paymentList;
	}

	public void setPaymentList(ArrayList<Payment> in_paymentList) {
		this.in_paymentList = in_paymentList;
	}

	public void addPayment(Payment p) {
		p.getPaymentAmt();
		in_paymentList.add(p);
		getTotalOwed();
	}
	public void addProcedure(Procedure p) {
		in_procList.add(p);
	}

	public void deleteProc(Procedure p) {
		in_procList.remove(p);
	}

	public double getInvoiceTotal() {
		double total = 0.0;

		for (int i = 0; i < in_procList.size(); i++) {
			total += in_procList.get(i).getProcCost();
		}

		return total;
	}

	public double getTotalPaid() {
		double total = 0.0;

		for (int i = 0; i < in_paymentList.size(); i++) {
			total += in_paymentList.get(i).getPaymentAmt();
		}

		return total;
	}

	public double getTotalOwed() {
		double total = 0.0;

		total = getInvoiceTotal() - getTotalPaid();

		if (getInvoiceTotal() > 0) {
			if (total == 0 || total < 0) {
				setPaid(true);
				total = 0;
			}
		}
		return total;
	}

	public int getCounter() {

		try {
			int a = 1;

			for (int i = Main.pList.size() - 1; i >= 0; i--) {
				if (Main.pList.get(i).getInvoiceList().size() != 0) {
					a = Main.pList.get(i).getInvoiceList().get(Main.pList.get(i).getInvoiceList().size() - 1)
							.getInvoiceNo() + 1;
				} else {
					return a;
				}

			}
			return a;
		} catch (ArrayIndexOutOfBoundsException e) {
			return 1;
		}
	}
}