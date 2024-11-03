package gui;

import java.net.URL;
import java.util.Locale;

import javax.swing.UIManager;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import factory.BLFactory;
import iterator.ExtendedIterator;
import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;

public class ApplicationLauncher {

	public static void main(String[] args) {

		ConfigXML c = ConfigXML.getInstance();

		System.out.println(c.getLocale());

		Locale.setDefault(new Locale(c.getLocale()));

		System.out.println("Locale: " + Locale.getDefault());

		try {

			BLFacade appFacadeInterface;
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			/*
			if (c.isBusinessLogicLocal()) {

				DataAccess da = new DataAccess();
				appFacadeInterface = new BLFacadeImplementation(da);

			}

			else { // If remote

				String serviceName = "http://" + c.getBusinessLogicNode() + ":" + c.getBusinessLogicPort() + "/ws/"
						+ c.getBusinessLogicName() + "?wsdl";

				URL url = new URL(serviceName);

				// 1st argument refers to wsdl document above
				// 2nd argument is service name, refer to wsdl document above
				QName qname = new QName("http://businessLogic/", "BLFacadeImplementationService");

				Service service = Service.create(url, qname);

				appFacadeInterface = service.getPort(BLFacade.class);
			}
			*/
			boolean isLocal = c.isBusinessLogicLocal();
			appFacadeInterface = new BLFactory().getBusinessLogicFactory(isLocal);
			
			ExtendedIterator<String>	i =	appFacadeInterface.getDepartingCitiesIterator();
			String ci;
			System.out.println("_____________________");
			System.out.println("FROM	LAST	TO	FIRST");
			i.goLast();	//	Go	to	last	element
			while (i.hasPrevious())	{
			ci =	i.previous();
			System.out.println(ci);
			}
			System.out.println();
			System.out.println("_____________________");
			System.out.println("FROM	FIRST	TO	LAST");
			i.goFirst();	//	Go	to	first	element
			while (i.hasNext())	{
			ci =	i.next();
			System.out.println(ci);			
			}
			MainGUI.setBussinessLogic(appFacadeInterface);
			MainGUI a = new MainGUI();
			a.setVisible(true);

		} catch (Exception e) {
			// a.jLabelSelectOption.setText("Error: "+e.toString());
			// a.jLabelSelectOption.setForeground(Color.RED);

			System.out.println("Error in ApplicationLauncher: " + e.toString());
		}
		// a.pack();

	}

}
