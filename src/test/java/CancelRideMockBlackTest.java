import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dataAccess.DataAccess;
import domain.Booking;
import domain.Driver;
import domain.Ride;
import domain.Traveler;
import testOperations.TestDataAccess;

public class CancelRideMockBlackTest {

static DataAccess sut;
	
	protected MockedStatic<Persistence> persistenceMock;

	@Mock
	protected  EntityManagerFactory entityManagerFactory;
	@Mock
	protected  EntityManager db;
	@Mock
    protected  EntityTransaction  et;
	
	static TestDataAccess testDA = new TestDataAccess();
	
	@Before
    public  void init() {
        MockitoAnnotations.openMocks(this);
        persistenceMock = Mockito.mockStatic(Persistence.class);
		persistenceMock.when(() -> Persistence.createEntityManagerFactory(Mockito.any()))
        .thenReturn(entityManagerFactory);
        
        Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();
		Mockito.doReturn(et).when(db).getTransaction();
	    sut=new DataAccess(db);
    }
	@After
    public  void tearDown() {
		persistenceMock.close();
    }
	
	
	/*
	Ride ride;
	
	@Test
	public void test1() {
		try {
			
			//define parameters

			ride = null;
			
			Mockito.when(db.find(Ride.class, null)).thenReturn(null);

			
			//invoke System Under Test (sut)  
			sut.open();
			sut.cancelRide(ride);
			
			//verify the results
			fail("Deberia haber lanzado una excepción");
			
		   } catch (NullPointerException e) {
			   fail("NullPointerException no se ha tratado");
			} catch (Exception e) {
				e.toString();
				assertTrue(true);

			} finally {
				sut.close();
			}
		
	}
	
	@Test
	public void test2() {
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
		
		Driver d = new Driver("TestDriver2", "123");
		ride = new Ride(from, to, date, 5,5,d);
		ride.setBookings(new ArrayList<Booking>());
		
		try {	
			sut.open();
			sut.cancelRide(ride);
			fail("No debe dejar cancelar un viaje que no esta en BD");			
		} catch (Exception e) {
			assertTrue(true);
		} finally {
			//testDA.open();
			//testDA.removeRide("TestDriver2", from, to, date);
			//testDA.removeDriver("TestDriver2");
			//testDA.close();
			sut.close();
		}
	}
	
	
	@Test
	public void test3() {
		
		
		//define parameters

		String from = "Donostia";
		String to = "Bilbo";
		
		Driver d = new Driver("TestDriver", "123");
		Mockito.when(db.find(Driver.class, d.getUsername())).thenReturn(d);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			 date = sdf.parse("05/10/2026");
		} catch (ParseException e) {
			e.printStackTrace();
		}	
		
		ride = new Ride(from, to, date, 5, 5, d);
		ride.setBookings(new ArrayList<Booking>());
		Mockito.when(db.find(Ride.class, ride.getRideNumber())).thenReturn(ride);
		Mockito.when(db.find(Driver.class, d.getUsername())).thenReturn(d);
		
		try {
		//invoke System Under Test (sut)  
		sut.open();
		sut.cancelRide(ride);
		
		//verify the results
		assertFalse("El viaje deberia desactivarse", ride.isActive());
		
		} catch (Exception e) {
			fail("Deberia dejar cancelar el viaje aunque no tenga reservas");
			e.toString();
		} finally {
			sut.close();
		}
	
}
	
	
	@Test
	public void test4() {
		try {
			
			//define parameters

			String from = "Donostia";
			String to = "Bilbo";
			
			Driver d = new Driver("TestDriver2", null);
			Mockito.when(db.find(Driver.class, d.getUsername())).thenReturn(d);
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date = null;
			try {
				 date = sdf.parse("05/10/2026");
			} catch (ParseException e) {
				e.printStackTrace();
			}	
			ride = new Ride(from, to, date, 5, 5, d);
			
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
			
			Mockito.when(db.find(Ride.class, ride.getRideNumber())).thenReturn(ride);
			Mockito.when(db.find(Driver.class, d.getUsername())).thenReturn(d);
			
			//invoke System Under Test (sut)  
			sut.open();
			sut.cancelRide(ride);
			
			//verify the results
			assertFalse(ride.isActive());
			
			for (Booking booking : ride.getBookings()) {
	            assertEquals("Rejected", booking.getStatus());
	        }
			
			} catch (Exception e) {
				e.toString();
				fail("Excepción inesperada");
			} finally {
				sut.close();
			}
		
	}
	
	
	@Test
	public void test5() {
		try {
			
			//define parameters

			String from = "Donostia";
			String to = "Bilbo";
			Driver d = new Driver("TestDriver", null);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date = null;
			try {
				 date = sdf.parse("05/10/2026");
			} catch (ParseException e) {
				e.printStackTrace();
			}	
			ride = new Ride(from, to, date, 5, 5, d);
			
			Traveler t1 = new Traveler("t1", "p1");
			Traveler t2 = new Traveler("t2", "p2");
			Traveler t3 = new Traveler("t3", "p3");
			
			Booking b1 = new Booking(ride, t1, 1);
			b1.setStatus("Rejected");
			Booking b2 = new Booking(ride, t2, 1);
			b2.setStatus("Accepted");
			Booking b3 = new Booking(ride, t3, 1);
			b3.setStatus("Rejected");
			
			List<Booking> lb = new ArrayList<Booking>();
			lb.add(b1); lb.add(b2); lb.add(b3);
			ride.setBookings(lb);
			
			Mockito.when(db.find(Ride.class, ride.getRideNumber())).thenReturn(ride);
			Mockito.when(db.find(Driver.class, d.getUsername())).thenReturn(d);
			
			//invoke System Under Test (sut)  
			sut.open();
			sut.cancelRide(ride);
			
			//verify the results
			assertFalse(ride.isActive());
			
			for (Booking booking : ride.getBookings()) {
	            assertEquals("Rejected", booking.getStatus());
	        }
			
			} catch (Exception e) {
				e.toString();
				fail("Excepción inesperada");
			} finally {
				sut.close();
			}
		
	}  
	
	
	@Test
	public void test6() {
		try {
			
			//define parameters

			String from = "Donostia";
			String to = "Bilbo";
			Driver d = new Driver("TestDriver", null);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date = null;
			try {
				 date = sdf.parse("05/10/2026");
			} catch (ParseException e) {
				e.printStackTrace();
			}	
			ride = new Ride(from, to, date, 5, 5, d);
			
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
			
			Mockito.when(db.find(Ride.class, ride.getRideNumber())).thenReturn(ride);
			Mockito.when(db.find(Driver.class, d.getUsername())).thenReturn(d);
			
			//invoke System Under Test (sut)  
			sut.open();
			sut.cancelRide(ride);
			
			//verify the results
			assertFalse(ride.isActive());
			
			for (Booking booking : ride.getBookings()) {
	            assertEquals("Rejected", booking.getStatus());
	        }
			
			} catch (Exception e) {
				e.toString();
				fail("Excepción inesperada");
			} finally {
				sut.close();
			}
		
	}  
	*/
	

}
