package domain;

import java.util.Date;

public class ValoresViaje {
	private String from;
	private String to;
	private Date date;
	private int nPlaces;
	private float price;
	private String driverName;
	
	public ValoresViaje(String from, String to, Date date, int nPlaces, float price, String driverName) {
		this.from = from;
		this.to = to;
		this.date = date;
		this.nPlaces = nPlaces;
		this.price = price;
		this.driverName = driverName;
	}
	
	public String getFrom() {
		return this.from;
	}
	
	public String getTo() {
		return this.to;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public int getNPlaces() {
		return this.nPlaces;
	}
	public float getPrice() {
		return this.price;
	}
	public String getDriverName() {
		return this.driverName;
	}
	
}
