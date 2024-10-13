package gui;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Booking;
import domain.Complaint;
import domain.Driver;
import domain.Traveler;

public class BezeroGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private static BLFacade appFacadeInterface;
	private JTable taula;
	private JButton jButtonBaloratu;
	private JButton jButtonErreklamatu;
	private JButton jButtonClose;
	private JScrollPane scrollPane;
<<<<<<< HEAD
	
	final String completed = "Completed";
	final String accepted = "Accepted";
	final String rejected = "Rejected";
	final String notCompleted = "NotCompleted";
	final String complained = "Complained";
	final String valued = "Valued";
	final String notDefined = "NotDefined";
	final String etiquetas = "Etiquetas";
=======
	private static String valued = "Valued";
>>>>>>> branch 'master' of https://github.com/haritz99/Rides24Complete.git

	public static BLFacade getBusinessLogic() {
		return appFacadeInterface;
	}

	public static void setBussinessLogic(BLFacade afi) {
		appFacadeInterface = afi;
	}

	public BezeroGUI(String username) {

		setBussinessLogic(DriverGUI.getBusinessLogic());
		this.setSize(new Dimension(600, 537));
		this.setResizable(false);
		getContentPane().setLayout(new BorderLayout(0, 0));

		// Lista
		taula = new JTable();
		List<Booking> TravelsList = appFacadeInterface.getBookingFromDriver(username);
		List<Booking> BezeroLista = new ArrayList<>();

		scrollPane = new JScrollPane(taula);
		getContentPane().add(scrollPane, BorderLayout.NORTH);

		// Etiketak
		String[] columnNames = { ResourceBundle.getBundle(etiquetas).getString("EgoeraGUI.BookingNumber"),
				ResourceBundle.getBundle(etiquetas).getString("CreateRideGUI.RideDate"),
				ResourceBundle.getBundle(etiquetas).getString("BezeroGUI.Bezeroa"),
				ResourceBundle.getBundle(etiquetas).getString("EgoeraGUI.Egoera"),
				ResourceBundle.getBundle(etiquetas).getString("BezeroGUI.Zergatia") };
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

		if (TravelsList != null) {
			for (Booking bo : TravelsList) {
				
				String status;
				switch (bo.getStatus()) {
				case completed:
					status = ResourceBundle.getBundle(etiquetas).getString(completed);
					break;
				case accepted:
					status = ResourceBundle.getBundle(etiquetas).getString(accepted);
					break;
				case rejected:
					status = ResourceBundle.getBundle(etiquetas).getString(rejected);
					break;
				case notCompleted:
					status = ResourceBundle.getBundle(etiquetas).getString(notCompleted);
					break;
				case complained:
					status = ResourceBundle.getBundle(etiquetas).getString(complained);
					break;
<<<<<<< HEAD
				case valued:
					status = ResourceBundle.getBundle(etiquetas).getString(valued);
=======
				case "valued":
					status = ResourceBundle.getBundle("Etiquetas").getString(valued);
>>>>>>> branch 'master' of https://github.com/haritz99/Rides24Complete.git
					break;
				default:
					status = ResourceBundle.getBundle(etiquetas).getString(notDefined);
					break;
				}
				
				if (bo.getStatus().equals(notCompleted)) {
					Complaint er = appFacadeInterface.getComplaintsByBook(bo);
						if (er.getAurkeztua()) {
							er.setEgoera("Erreklamazioa");
						} else {
							er.setEgoera("Ez aurkeztua");
						}

						Object[] rowData = { bo.getBookNumber(), dateFormat.format(bo.getRide().getDate()),
								bo.getTraveler().getUsername(), status, er.getEgoera() };
						model.addRow(rowData);
						BezeroLista.add(bo);

<<<<<<< HEAD
				} else if (bo.getStatus().equals(completed) || bo.getStatus().equals(valued)
						|| bo.getStatus().equals(complained)) {
=======
				} else if (bo.getStatus().equals("Completed") || bo.getStatus().equals(valued)
						|| bo.getStatus().equals("Complained")) {
>>>>>>> branch 'master' of https://github.com/haritz99/Rides24Complete.git
					Object[] rowData = { bo.getBookNumber(), dateFormat.format(bo.getRide().getDate()),
							bo.getTraveler().getUsername(), status, "" };
					model.addRow(rowData);
					BezeroLista.add(bo);
				}

			}
		}
		taula.setModel(model);

		taula.getTableHeader().setReorderingAllowed(false);
		taula.setColumnSelectionAllowed(false);
		taula.setRowSelectionAllowed(true);
		taula.setDefaultEditor(Object.class, null);
		taula.setModel(model);

		// Erroreen textua
		JLabel lblErrorea = new JLabel();
		this.getContentPane().add(lblErrorea, BorderLayout.CENTER);

		this.setTitle(ResourceBundle.getBundle(etiquetas).getString("BezeroGUI.Bezeroak"));

		// Baloratu botoia
		jButtonBaloratu = new JButton(ResourceBundle.getBundle(etiquetas).getString("BezeroGUI.Baloratu"));
		jButtonBaloratu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int pos = taula.getSelectedRow();
					Booking bo = BezeroLista.get(pos);
<<<<<<< HEAD
					if (bo.getStatus().equals(completed)) {
=======
					if (bo.getStatus().equals("Completed")) {
>>>>>>> branch 'master' of https://github.com/haritz99/Rides24Complete.git
						bo.setStatus(valued);
						appFacadeInterface.updateBooking(bo);
						JFrame a = new BaloraGUI(bo.getTraveler().getUsername());
						a.setVisible(true);
<<<<<<< HEAD
						model.setValueAt(ResourceBundle.getBundle(etiquetas).getString(valued), pos, 3);
					} else if (bo.getStatus().equals(ResourceBundle.getBundle(etiquetas).getString(valued))) {
=======
						model.setValueAt(ResourceBundle.getBundle("Etiquetas").getString(valued), pos, 3);
					} else if (bo.getStatus().equals(ResourceBundle.getBundle("Etiquetas").getString(valued))) {
>>>>>>> branch 'master' of https://github.com/haritz99/Rides24Complete.git
						lblErrorea.setForeground(Color.RED);
						lblErrorea.setText(
								ResourceBundle.getBundle(etiquetas).getString("BezeroGUI.BezeroaJadanikBaloratuta"));
					} else {
						lblErrorea.setForeground(Color.RED);
						lblErrorea.setText(
								ResourceBundle.getBundle(etiquetas).getString("BezeroGUI.AukeratuOsatutakoBidaia"));
					}
		 if(pos == -1) {
					lblErrorea.setForeground(Color.RED);
					lblErrorea.setText(ResourceBundle.getBundle(etiquetas).getString("BezeroGUI.Erroraukera"));
				}

			}
		});

		this.getContentPane().add(jButtonBaloratu, BorderLayout.WEST);

		// Erraklamazio botoia
		jButtonErreklamatu = new JButton(ResourceBundle.getBundle(etiquetas).getString("BezeroGUI.Onartu")
				+ " / " + ResourceBundle.getBundle(etiquetas).getString("BezeroGUI.Erreklamatu"));
		jButtonErreklamatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int pos = taula.getSelectedRow();
						
					Booking booking = BezeroLista.get(pos);
						
						if (taula.getValueAt(pos, 4).equals("Erreklamazioa")) {
							Traveler traveler = booking.getTraveler();
							double prez = booking.prezioaKalkulatu();
							
							booking.setStatus(complained);
							appFacadeInterface.updateBooking(booking);

							traveler.setIzoztatutakoDirua(traveler.getIzoztatutakoDirua() - prez);
							appFacadeInterface.updateTraveler(traveler);

							appFacadeInterface.gauzatuEragiketa(traveler.getUsername(), prez, true);
							appFacadeInterface.addMovement(traveler, "UnfreezeNotComplete", prez);

							Driver driver = appFacadeInterface.getDriver(username);
							driver.setErreklamaKop(driver.getErreklamaKop() + 1);

							lblErrorea.setText(
									ResourceBundle.getBundle(etiquetas).getString("BezeroGUI.ComplaintAccepted"));
							lblErrorea.setForeground(Color.BLACK);

							model.setValueAt(ResourceBundle.getBundle(etiquetas).getString(complained), pos, 3);
							model.setValueAt("", pos, 4);
						} else if (taula.getValueAt(pos, 4).equals("Ez aurkeztua")) {
							Driver driver = booking.getRide().getDriver();
							Traveler traveler = booking.getTraveler();
							double prez = booking.prezioaKalkulatu();

							booking.setStatus(complained);
							appFacadeInterface.updateBooking(booking);

							traveler.setIzoztatutakoDirua(traveler.getIzoztatutakoDirua() - prez);
							traveler.setErreklamaKop(traveler.getErreklamaKop() + 1);
							appFacadeInterface.updateTraveler(traveler);

							appFacadeInterface.gauzatuEragiketa(driver.getUsername(), prez, true);
							appFacadeInterface.addMovement(traveler, "UnfreezeCompleteT", 0);
							appFacadeInterface.addMovement(driver, "UnfreezeCompleteD", prez);
							lblErrorea.setText(
									ResourceBundle.getBundle(etiquetas).getString("BezeroGUI.ComplaintComplete"));
							lblErrorea.setForeground(Color.BLACK);

							model.setValueAt(ResourceBundle.getBundle(etiquetas).getString(complained), pos, 3);
							model.setValueAt("", pos, 4);
						} else {
							lblErrorea.setForeground(Color.RED);
							lblErrorea.setText(ResourceBundle.getBundle(etiquetas)
									.getString("BezeroGUI.AukeratuEzOsatutakoBidaia"));
						}
					 if (booking.getStatus()
							.equals(ResourceBundle.getBundle(etiquetas).getString(complained))) {
						lblErrorea.setForeground(Color.RED);
						lblErrorea.setText(
								ResourceBundle.getBundle(etiquetas).getString("BezeroGUI.BezeroaErreklamazioa"));
					} else {
						lblErrorea.setForeground(Color.RED);
						lblErrorea.setText(
								ResourceBundle.getBundle(etiquetas).getString("BezeroGUI.AukeratuEzOsatutakoBidaia"));
					}
				 if(pos== -1) {
					lblErrorea.setForeground(Color.RED);
					lblErrorea.setText(ResourceBundle.getBundle(etiquetas).getString("BezeroGUI.Erroraukera"));
				}
			}
				 
		});

		this.getContentPane().add(jButtonErreklamatu, BorderLayout.EAST);

		// Itxi botoia
		jButtonClose = new JButton(ResourceBundle.getBundle(etiquetas).getString("EgoeraGUI.Close"));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		this.getContentPane().add(jButtonClose, BorderLayout.SOUTH);

	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

}
