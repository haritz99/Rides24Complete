import static org.junit.Assert.*;
import java.util.*;
import domain.Booking;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.Ride;
import domain.Traveler;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import testOperations.TestDataAccess;
import domain.Driver;
public class CancelRideBDWhiteTest {

	// sut:system under test
	static DataAccess sut = new DataAccess();

	// additional operations needed to execute the test
	static TestDataAccess testDA = new TestDataAccess();

	private EntityManager db;
	private Ride ride;
	
/*
	@Test
	public void test1() {
		// define parameters
		ride = null;

		try {
			sut.open();
			sut.cancelRide(ride);
			fail("Deberia lanzar una excepción");
		} catch (NullPointerException e) {
			fail("No se ha gestionado NullPointerException");
		} catch (Exception e) {
			assertTrue(true);			
		} finally {
			sut.close();
		}
	}
	
	
	@Test
	public void test2() {
		// define parameters
		ride = null;
		try {
			sut.open();
			sut.cancelRide(ride);
			fail("Deberia haber lanzado una excepción");	
		} catch (NullPointerException e) {
			fail("No se ha gestionado NullPointerException");
		} catch (Exception e) {
			assertTrue(db.getTransaction().isActive());
		} finally {
			sut.close();
		}
	}
	
	@Test
	public void test3() {
		// define parameters
		String from = "Donostia";
		String to = "Bilbo";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			 date = sdf.parse("05/10/2026");
		} catch (ParseException e) {
			e.printStackTrace();
		}	
		testDA.open();
		Driver d = testDA.addDriverWithRide("TestDriver2", from, to, date, 5, 5);
		testDA.close();
		
		ride = d.getCreatedRides().get(0);
		ride.setBookings(new ArrayList<Booking>());
		try {	
			sut.open();
			sut.cancelRide(ride);
			assertFalse("El viaje deberia desactivarse", ride.isActive());			
		} catch (Exception e) {
			fail("Deberia dejar cancelar el viaje aunque no tenga reservas");
		} finally {
			testDA.open();
			testDA.removeRide("TestDriver2", from, to, date);
			testDA.removeDriver("TestDriver2");
			testDA.close();
			sut.close();
		}
	}
	
	@Test
	public void test4() {
		// define parameters
		String from = "Donostia";
		String to = "Bilbo";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			 date = sdf.parse("05/10/2026");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		testDA.open();
		Driver d = testDA.addDriverWithRide("TestDriver2", from, to, date, 5, 5);
		testDA.close();
		
		ride = d.getCreatedRides().get(0);
		
		Traveler t1 = new Traveler("t1", "p1");
		Traveler t2 = new Traveler("t2", "p2");
		Traveler t3 = new Traveler("t3", "p3");
		
		Booking b1 = new Booking(ride, t1, 1);
		b1.setStatus("Rejected");
		Booking b2 = new Booking(ride, t2, 1);
		b2.setStatus("Rejected");
		Booking b3 = new Booking(ride, t3, 1);
		b3.setStatus("Rejected");
		
		List<Booking> lb = new ArrayList<Booking>();
		lb.add(b1); lb.add(b2); lb.add(b3);
		ride.setBookings(lb);
		
		try {
			sut.open();
			sut.cancelRide(ride);
			assertFalse(ride.isActive());
			
			for (Booking booking : ride.getBookings()) {
	            assertEquals("Rejected", booking.getStatus());
	        }
		} catch (Exception e) {
			fail();
		} finally {
			testDA.open();
			testDA.removeRide("TestDriver2", from, to, date);
			testDA.removeDriver("TestDriver2");
			testDA.close();
			sut.close();
		}
	}
	
	
	
	@Test
	public void test5() {
		// define parameters
		String from = "Donostia";
		String to = "Bilbo";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			 date = sdf.parse("05/10/2026");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		testDA.open();
		Driver d = testDA.addDriverWithRide("TestDriver2", from, to, date, 5, 5);
		testDA.close();
		
		ride = d.getCreatedRides().get(0);
		
		Traveler t1 = new Traveler("t1", "p1");
		Traveler t2 = new Traveler("t2", "p2");
		Traveler t3 = new Traveler("t3", "p3");
		
		Booking b1 = new Booking(ride, t1, 1);
		b1.setStatus("Accepted");
		Booking b2 = new Booking(ride, t2, 1);
		b2.setStatus("Accepted");
		Booking b3 = new Booking(ride, t3, 1);
		b3.setStatus("NotDefined");
		
		List<Booking> lb = new ArrayList<Booking>();
		lb.add(b1); lb.add(b2); lb.add(b3);
		ride.setBookings(lb);
		
		try {
			sut.open();
			sut.cancelRide(ride);
			assertFalse(ride.isActive());
			
			for (Booking booking : ride.getBookings()) {
	            assertEquals("Rejected", booking.getStatus());
	        }
		} catch (Exception e) {
			fail();
		} finally {
			testDA.open();
			testDA.removeRide("TestDriver2", from, to, date);
			testDA.removeDriver("TestDriver2");
			testDA.close();
			sut.close();
		}
	}
	*/
	
}
