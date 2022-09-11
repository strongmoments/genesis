package com.genesis.genesisapi.model;

import java.util.Date;

public class BillByInvoice {
	private double total_amount;
	private String invoice_no;
	public double getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
	}
	public String getInvoice_no() {
		return invoice_no;
	}
	public void setInvoice_no(String invoice_no) {
		this.invoice_no = invoice_no;
	}
	
}
