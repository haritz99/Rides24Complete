

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import dataAccess.DataAccess;
import domain.Driver;
import domain.Ride;
import domain.Traveler;
import domain.User;
import testOperations.TestDataAccess;

public class bookRideWhiteTest {
	//4 de caja blanca, el resto caja negra
	
	//sut:System under test
	static DataAccess sut= new DataAccess();
	
	static TestDataAccess testDA = new TestDataAccess();

	private DataAccess db;
	private	Ride ride;
	private User user;
	private String username;
	private Traveler traveler;
	private int seats;
	private double desk;
	private Driver d;
	
	

	@Test
	public void test1() {
		username = null;
		seats = 2;
		desk = 1.0;
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
			
			boolean resultado = sut.bookRide(username, ride, seats, desk);
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
		ride = null;
		seats = 2;
		desk = 0.0;
		user = new User("TestUser5", "contraseña", "tipo");
		testDA.open();
		testDA.addUser(user);
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
			testDA.close();
			sut.close();
		}
		
	}
	@Test
	public void test3() {
		seats = 2;
		desk = 0.0;
		user = new User("TestUser2", "contraseña", "tipo");
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
	
	@Test
	public void test4() {
		seats = 2;
		desk = 0.0;
		user = new User("TestUser9", "contraseña", "tipo");
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
				assertEquals(true, resultado);
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
		
	} //Expected true but was false, había que añadir el viajero en la base de datos
	
}
