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

public class GauzatuEragiketaMockBlackTest {
	
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
	//sut.realizarOperacion:  username exist in DB and its not "" or null, 
	//amount is positive. Must return true and update correctly the user amount
	public void test1() {
		String userUsername = "Jon";
		User u = new User(userUsername, "1234", "driver");
		double previousUserMoney = 100.0;
		u.setMoney(previousUserMoney);
		
		Mockito.when(db.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)).thenReturn(typedQuery);

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
			
		}catch (Exception e2) {
			// if the program goes to this point fail
			e2.printStackTrace();
			fail();
		} finally {
			sut.close();
		}

	}
	
	
	@Test
	//sut.realizarOperacion:  username is null. Must return false.
	public void test2() {
				
		try {
			// define parameters
			String username = null;
			double amount = 100.0;
			boolean deposit = false;

			// invoke System Under Test (sut)
			sut.open();
			boolean res = sut.gauzatuEragiketa(username, amount, deposit);
			
			assertFalse(res);
			
		}  catch(NullPointerException e1) {
			e1.printStackTrace();
			fail();
			
		}catch (Exception e) {
			// if the program goes to this point fail
			e.printStackTrace();
			fail();
		} finally {
			sut.close();
		}
	}
	
	
	@Test
	//sut.realizarOperacion:  username does not exist in DB a
	//Must return false
	public void test3() {
		
		try {
			// define parameters
			String username = "Anai";
			double amount = 10.0;
			boolean deposit = true;

			// invoke System Under Test (sut)
			sut.open();
			boolean res = sut.gauzatuEragiketa(username, amount, deposit);
			
			User user = sut.getUser(username);
			double currentUserMoney = user.getMoney();
			System.out.println("Dinero despues:	" + currentUserMoney);
			
			assertFalse(res);
			
		}catch(NullPointerException e1) {
			e1.printStackTrace();
			fail();
		}
		 catch (Exception e2) {
				// if the program goes to this point fail
				e2.printStackTrace();
				fail();
			} 
		finally {
			sut.close();
		}

	}
	
	
	@Test
	//.realizarOperacion:  username exist in DB and its not "" or null, 
	//amount is negative. Must return a Exception
	public void test4() {
		String userUsername = "Jon";
		User u = new User(userUsername, "1234", "driver");
		double previousUserMoney = 100.0;
		u.setMoney(previousUserMoney);
		
		Mockito.when(db.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)).thenReturn(typedQuery);

		Mockito.when(typedQuery.setParameter("username", userUsername)).thenReturn(typedQuery);
		Mockito.when(typedQuery.getSingleResult()).thenReturn(u);
		
		try {
			// define parameters
			String username = "Jon";
			double amount = -20.0;
			boolean deposit = true;

			// invoke System Under Test (sut)
			sut.open();
			sut.gauzatuEragiketa(username, amount, deposit);
			
			fail();
		
			
		} catch (Exception e) {
			// if the program goes to this point fail
			e.printStackTrace();
			assertTrue(true);
		} finally {
			sut.close();
		}

	}
	
	@Test
	//sut.realizarOperacion:  username exist in DB and its not "" or null, 
	//amount is positive and deposit is false. Must return true and update 
	//correctly the user amount
	public void test5() {
		String userUsername = "Jon";
		User u = new User(userUsername, "1234", "driver");
		double previousUserMoney = 100.0;
		u.setMoney(previousUserMoney);
		
		Mockito.when(db.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)).thenReturn(typedQuery);

		Mockito.when(typedQuery.setParameter("username", userUsername)).thenReturn(typedQuery);
		Mockito.when(typedQuery.getSingleResult()).thenReturn(u);
		try {
			// define parameters
			String username = "Jon";
			double amount = 20.0;
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
	//sut.realizarOperacion:  username exist in DB and its not "" or null, 
	//amount is positive but greater than user money and deposit is false. 
	//Must return a exception
	public void test6() {
		String userUsername = "Jon";
		User u = new User(userUsername, "1234", "driver");
		double previousUserMoney = 100.0;
		u.setMoney(previousUserMoney);
		
		Mockito.when(db.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)).thenReturn(typedQuery);

		Mockito.when(typedQuery.setParameter("username", userUsername)).thenReturn(typedQuery);
		Mockito.when(typedQuery.getSingleResult()).thenReturn(u);
		try {
			// define parameters
			String username = "Jon";
			double amount = 20.0;
			boolean deposit = false;

			// invoke System Under Test (sut)
			sut.open();
			sut.gauzatuEragiketa(username, amount, deposit);
			
			fail();
			
		} catch (Exception e) {
			// if the program goes to this point fail
			e.printStackTrace();
			assertTrue(true);
		} finally {
			sut.close();
		}

	}
	
	@Test
	//sut.realizarOperacion:  Valor limite amount -0.01. Must return a Exception
	public void test7() {
		String userUsername = "Jon";
		User u = new User(userUsername, "1234", "driver");
		double previousUserMoney = 100.0;
		u.setMoney(previousUserMoney);
		
		Mockito.when(db.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)).thenReturn(typedQuery);

		Mockito.when(typedQuery.setParameter("username", userUsername)).thenReturn(typedQuery);
		Mockito.when(typedQuery.getSingleResult()).thenReturn(u);
		try {
			// define parameters
			String username = "Jon";
			double amount = -0.01;
			boolean deposit = true;

			// invoke System Under Test (sut)
			sut.open();
			sut.gauzatuEragiketa(username, amount, deposit);
			
			fail();
			
		} catch (Exception e) {
			// if the program goes to this point fail
			e.printStackTrace();
			assertTrue(true);
		} finally {
			sut.close();
		}

	}
	
	@Test
	//sut.realizarOperacion:  Valor limite amount 0.0. Must return true
	public void test8() {
		String userUsername = "Jon";
		User u = new User(userUsername, "1234", "driver");
		double previousUserMoney = 100.0;
		u.setMoney(previousUserMoney);
		
		Mockito.when(db.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)).thenReturn(typedQuery);

		Mockito.when(typedQuery.setParameter("username", userUsername)).thenReturn(typedQuery);
		Mockito.when(typedQuery.getSingleResult()).thenReturn(u);
		try {
			// define parameters
			String username = "Jon";
			double amount = 0.0;
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
	//sut.realizarOperacion:  Valor limite amount 0.01. Must return true
	public void test9() {
		String userUsername = "Jon";
		User u = new User(userUsername, "1234", "driver");
		double previousUserMoney = 100.0;
		u.setMoney(previousUserMoney);
		
		Mockito.when(db.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)).thenReturn(typedQuery);

		Mockito.when(typedQuery.setParameter("username", userUsername)).thenReturn(typedQuery);
		Mockito.when(typedQuery.getSingleResult()).thenReturn(u);
		try {
			// define parameters
			String username = "Jon";
			double amount = 0.01;
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
	

}