import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import domain.Driver;
import domain.Ride;
import domain.Traveler;
import domain.User;

public class bookRideWhiteMockTest {
	
	static DataAccess sut;
	private	Ride ride;
	private User user;
	private String username;
	private Traveler traveler;
	private int seats;
	private double desk;
	private Driver d;
	
	
	
	protected MockedStatic<Persistence> persistenceMock;

	@Mock
	protected  EntityManagerFactory entityManagerFactory;
	@Mock
	protected  EntityManager db;
	@Mock
    protected  EntityTransaction  et;
	

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
	
	@Test
	public void test1() {
		username = null;
		seats = 2;
		desk = 1.0;
		String from = "Donostia";
		String to = "Bilbao";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		d = new Driver("TestDriver3", "bbbb");
		try {
			rideDate = sdf.parse("05/10/2026");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ride = new Ride(from, to, rideDate, 10, 5.0, d);
		Mockito.when(db.find(Driver.class, d.getUsername())).thenReturn(d);
		Mockito.when(db.find(Ride.class, ride.getRideNumber())).thenReturn(ride);
		try {
			sut.open();
			
			boolean resultado = sut.bookRide(username, ride, seats, desk);
				assertEquals(false, resultado);
		}catch(Exception e) {
			fail();
		}finally {
			sut.close();
		}
		
	}
	
	@Test
	public void test2() {
		ride = null;
		seats = 2;
		desk = 0.0;
		user = new User("TestUser5", "contraseña", "tipo");
		d = new Driver("TestDriver3", "bbbb");
		Mockito.when(db.find(User.class, user.getUsername())).thenReturn(user);
		Mockito.when(db.find(Driver.class, d.getUsername())).thenReturn(d);
		try {
			sut.open();
			
			boolean resultado = sut.bookRide(user.getUsername(), ride, seats, desk);
				assertEquals(false, resultado);
		}catch(Exception e) {
			fail();
		}finally {
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
		d = new Driver("TestDriver3", "bbbb");
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
		
		ride = new Ride(from, to, rideDate, 10, 5.0, d);
		Mockito.when(db.find(User.class, user.getUsername())).thenReturn(user);
		Mockito.when(db.find(Traveler.class, traveler.getUsername())).thenReturn(traveler);
		Mockito.when(db.find(Driver.class, d.getUsername())).thenReturn(d);
		Mockito.when(db.find(Ride.class, ride.getRideNumber())).thenReturn(ride);
		try {
			sut.open();
			
			boolean resultado = sut.bookRide(user.getUsername(), ride, seats, desk);
				assertEquals(false, resultado);
		}catch(Exception e) {
			fail();
		}finally {
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
		d = new Driver("TestDriver3", "bbbb");
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
		ride = new Ride(from, to, rideDate, 10, 5.0, d);
		Mockito.when(db.find(User.class, user.getUsername())).thenReturn(user);
		Mockito.when(db.find(Traveler.class, traveler.getUsername())).thenReturn(traveler);
		Mockito.when(db.find(Driver.class, d.getUsername())).thenReturn(d);
		Mockito.when(db.find(Ride.class, ride.getRideNumber())).thenReturn(ride);
		try {
			sut.open();
			
			boolean resultado = sut.bookRide(user.getUsername(), ride, seats, desk);
				assertEquals(true, resultado);
		}catch(Exception e) {
			fail();
		}finally {
			sut.close();
		}
		
	}

}
