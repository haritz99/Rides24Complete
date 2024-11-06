package adapter;

import businessLogic.BLFacade;
import domain.Driver;
import factory.BLFactory;

public class Main {
	public static void main(String[] args) {
	    // the BL is local
	    boolean isLocal = true;
	    BLFacade blFacade = new BLFactory().getBusinessLogicFactory(isLocal);
	    Driver d = blFacade.getDriver("Urtzi");
	    DriverTable dt = new DriverTable(d);
	    dt.setVisible(true);
	}

}
