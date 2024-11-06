package adapter;

import javax.swing.table.AbstractTableModel;

import domain.Driver;
import domain.Ride;


import java.util.List;

public class DriverAdapter extends AbstractTableModel{
	
	private List<Ride> datalist;
	
	public DriverAdapter(Driver d) {
		datalist = d.getCreatedRides();
		
	}

	@Override
	public int getRowCount() {
		return datalist.size();
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Ride fila = datalist.get(rowIndex);
		switch (columnIndex) {
			case 0: 
				return fila.getFrom();
			case 1:
				return fila.getTo();
			case 2:
				return fila.getDate();
			case 3: 
				return fila.getnPlaces();
			case 4:
				return fila.getPrice();
			default: 
				return null;
		}
	}
	
}
