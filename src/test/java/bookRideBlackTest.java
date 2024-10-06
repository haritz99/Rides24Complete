import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import dataAccess.DataAccess;
import domain.Driver;
import domain.Ride;
import domain.Traveler;
import domain.User;
import testOperations.TestDataAccess;

public class bookRideBlackTest {
	
	static DataAccess sut= new DataAccess();
	
	static TestDataAccess testDA = new TestDataAccess();

	private String username;
	private User user;
	private Traveler traveler;
	private int seats;
	private double desk;
	private DataAccess db;
	private	Ride ride;
	private Driver d;

	@Test
	public void test1() {
		//Primer test de caja negra, el usuario no está en la BD
		seats = 2;
		desk = 0.1;
		user = new User("TestUser11", "contraseña", "tipo");
		traveler = new Traveler(user.getUsername(), user.getPassword());
		traveler.setMoney(10.0);
		String from = "Donostia";
		String to = "Bilbao";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try {
			rideDate = sdf.parse("05/10/2026");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		testDA.open();
		d = testDA.addDriverWithRide("TestDriver", from, to, rideDate, 5, (float) 5.0);
		ride = d.getCreatedRides().get(0);
		testDA.close();
		try {
			sut.open();
			
			boolean resultado = sut.bookRide(user.getUsername(), ride, seats, desk);
				assertEquals(false, resultado);
		}catch(Exception e) {
			fail();
		}finally {
			testDA.open();
			testDA.removeRide(d.getUsername(), from, to, rideDate);
			testDA.removeDriver(d.getUsername());
			testDA.close();
			sut.close();
		}
		
	}
	
	@Test
	public void test2() {
		//Segundo test caja negra, en este caso el número de asientos es negativo
		seats = -1;
		desk = 0.0;
		user = new User("TestUser12", "contraseña", "tipo");
		traveler = new Traveler(user.getUsername(), user.getPassword());
		traveler.setMoney(10.0);
		String from = "Donostia";
		String to = "Bilbao";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try {
			rideDate = sdf.parse("05/10/2026");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		testDA.open();
		testDA.addUser(user);
		testDA.addTraveler(traveler);
		d = testDA.addDriverWithRide("TestDriver", from, to, rideDate, 5, (float) 5.0);
		ride = d.getCreatedRides().get(0);
		testDA.close();
		try {
			sut.open();
				boolean resultado = sut.bookRide(user.getUsername(), ride, seats, desk);
				fail();
		}catch(IllegalArgumentException e) {
			assertTrue(true);
		}finally {
			testDA.open();
			testDA.removeUser(user);
			testDA.removeTraveler(traveler);
			testDA.removeRide(d.getUsername(), from, to, rideDate);
			testDA.removeDriver(d.getUsername());
			testDA.close();
			sut.close();
		}
		
	}
	
	@Test
	public void test3() {
		//Tercer test caja negra, en este caso ride no está en la BD
		seats = 2;
		desk = 0.0;
		user = new User("TestUser13", "contraseña", "tipo");
		traveler = new Traveler(user.getUsername(), user.getPassword());
		traveler.setMoney(10.0);
		String from = "Donostia";
		String to = "Bilbao";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try {
			rideDate = sdf.parse("05/10/2026");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		testDA.open();
		testDA.addUser(user);
		testDA.addTraveler(traveler);
		d = testDA.createDriver("TestDriver", "aaa");
		testDA.close();
		ride = new Ride(from, to, rideDate, 10, 5.0, d);
		try {
			sut.open();
			boolean resultado = sut.bookRide(user.getUsername(), ride, seats, desk);
			fail();
		}catch(Exception e) {
			assertTrue(true);
		}finally {
			testDA.open();
			testDA.removeUser(user);
			testDA.removeTraveler(traveler);
			testDA.removeDriver(d.getUsername());
			testDA.close();
			sut.close();
		}
		
	}
	
	@Test
	public void test4() {
		//Cuarto test caja negra, en este caso el descuento es negativo
		seats = 2;
		desk = -0.1;
		user = new User("TestUser12", "contraseña", "tipo");
		traveler = new Traveler(user.getUsername(), user.getPassword());
		traveler.setMoney(10.0);
		String from = "Donostia";
		String to = "Bilbao";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try {
			rideDate = sdf.parse("05/10/2026");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		testDA.open();
		testDA.addUser(user);
		testDA.addTraveler(traveler);
		d = testDA.addDriverWithRide("TestDriver", from, to, rideDate, 5, (float) 5.0);
		ride = d.getCreatedRides().get(0);
		testDA.close();
		try {
			sut.open();
				boolean resultado = sut.bookRide(user.getUsername(), ride, seats, desk);
				fail();
		}catch(IllegalArgumentException e) {
			assertTrue(true);
		}finally {
			testDA.open();
			testDA.removeUser(user);
			testDA.removeTraveler(traveler);
			testDA.removeRide(d.getUsername(), from, to, rideDate);
			testDA.removeDriver(d.getUsername());
			testDA.close();
			sut.close();
		}
		
	}
	
	
	
	@Test
	public void test5() {
		//Quinto test de caja negra, todos los parámetros son correctos, pero el viajero no tiene dinero así que no debería poder reservar
		seats = 2;
		desk = 0.5;
		user = new User("TestUser9", "contraseña", "tipo");
		traveler = new Traveler(user.getUsername(), user.getPassword());
		traveler.setMoney(0.0);
		String from = "Donostia";
		String to = "Bilbao";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try {
			rideDate = sdf.parse("05/10/2026");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		testDA.open();
		testDA.addUser(user);
		testDA.addTraveler(traveler);
		d = testDA.addDriverWithRide("TestDriver", from, to, rideDate, 5, (float) 5.0);
		ride = d.getCreatedRides().get(0);
		testDA.close();
		try {
			sut.open();
			
			boolean resultado = sut.bookRide(user.getUsername(), ride, seats, desk);
				assertEquals(false, resultado);
		}catch(Exception e) {
			fail();
		}finally {
			testDA.open();
			testDA.removeUser(user);
			testDA.removeTraveler(traveler);
			testDA.removeRide(d.getUsername(), from, to, rideDate);
			testDA.removeDriver(d.getUsername());
			testDA.close();
			sut.close();
		}
		
	}

}
