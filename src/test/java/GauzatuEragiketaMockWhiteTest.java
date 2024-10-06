import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

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
import domain.User;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

public class GauzatuEragiketaMockWhiteTest {
	
	static DataAccess sut;
	
	protected MockedStatic<Persistence> persistenceMock;

	@Mock
	protected  EntityManagerFactory entityManagerFactory;
	@Mock
	protected  EntityManager db;
	@Mock
    protected  EntityTransaction  et;
	
	@Mock
	TypedQuery<User> typedQuery;

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
	//sut.realizarOperacion:  User exists on the DB and the method must deposit 
 	//the amount. If the result is different of true and the amount is not well updated, 
 	//its not properly implemented 
	public void test1() {
		String userUsername = "Jon";
		User u = new User(userUsername, "1234", "driver");
		double previousUserMoney = 0.0;
		u.setMoney(previousUserMoney);
		
		//Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(typedQuery);		
		//Mockito.when(typedQuery.getSingleResult()).thenReturn(u);
		
		Mockito.when(db.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)).thenReturn(typedQuery);

        // Configurar el comportamiento del mock para setParameter y getSingleResult
		Mockito.when(typedQuery.setParameter("username", userUsername)).thenReturn(typedQuery);
		Mockito.when(typedQuery.getSingleResult()).thenReturn(u);
		
		try {
			// define parameters
			String username = "Jon";
			double amount = 10.0;
			boolean deposit = true;

			// invoke System Under Test (sut)
			sut.open();
			boolean res = sut.gauzatuEragiketa(username, amount, deposit);
			
			User user = sut.getUser(username);
			double currentUserMoney = user.getMoney();
			
			if ((currentUserMoney - amount) == previousUserMoney) {
				assertTrue(res);
			}
			else fail();
			
		} catch (Exception e) {
			// if the program goes to this point fail
			e.printStackTrace();
			fail();
		} finally {
			sut.close();
		}

	}
	
	
	@Test
	//sut.realizarOperacion:  User exists on the DB and the method must remove 
 	//the amount. If the result is different of true and the amount is not well updated, 
 	//its not properly implemented 
	public void test2() {
		
		User u = new User("Jon", "1234", "driver");
		double previousUserMoney = 5.0;
		u.setMoney(previousUserMoney);
		
		Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(typedQuery);		
		Mockito.when(typedQuery.getSingleResult()).thenReturn(u);
		
		try {
			// define parameters
			String username = "Jon";
			double amount = 10.0;
			boolean deposit = false;

			// invoke System Under Test (sut)
			sut.open();
			boolean res = sut.gauzatuEragiketa(username, amount, deposit);
			
			User user = sut.getUser(username);
			double currentUserMoney = user.getMoney();
			
			if (currentUserMoney == 0.0) {
				assertTrue(res);
			}
			else fail();
			
		} catch (Exception e) {
			// if the program goes to this point fail
			e.printStackTrace();
			fail();
		} finally {
			sut.close();
		}

	}
	
	@Test
	//sut.realizarOperacion:  User exists on the DB and the method must remove 
 	//the amount. If the result is different of true and the amount is not well updated, 
 	//its not properly implemented 
	public void tes3() {
		
		User u = new User("Jon", "1234", "driver");
		double previousUserMoney = 15.0;
		u.setMoney(previousUserMoney);
		
		Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(typedQuery);		
		Mockito.when(typedQuery.getSingleResult()).thenReturn(u);
		
		try {
			// define parameters
			String username = "Jon";
			double amount = 10.0;
			boolean deposit = false;

			// invoke System Under Test (sut)
			sut.open();
			boolean res = sut.gauzatuEragiketa(username, amount, deposit);
			
			User user = sut.getUser(username);
			double currentUserMoney = user.getMoney();
			
			if ((currentUserMoney + amount) == previousUserMoney) {
				assertTrue(res);
			}
			else fail();
			
		} catch (Exception e) {
			// if the program goes to this point fail
			e.printStackTrace();
			fail();
		} finally {
			sut.close();
		}

	}
	
	@Test
	//sut.realizarOperacion:  User does not exists on the DB. If the result is 
	//different of false and the amount its not properly implemented 
	public void tes4() {
		
		try {
			// define parameters
			String username = "Jon";
			double amount = 10.0;
			boolean deposit = false;

			// invoke System Under Test (sut)
			sut.open();
			boolean res = sut.gauzatuEragiketa(username, amount, deposit);
			
			assertFalse(res);
		
			
		} catch (Exception e) {
			// if the program goes to this point fail
			e.printStackTrace();
			fail();
		} finally {
			sut.close();
		}

	}
	

}