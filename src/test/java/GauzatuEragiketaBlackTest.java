
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.Ride;
import domain.User;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import testOperations.TestDataAccess;
import domain.Driver;

public class GauzatuEragiketaBlackTest {

	 //sut:system under test
	 static DataAccess sut=new DataAccess();
	 
	 //additional operations needed to execute the test 
	 static TestDataAccess testDA=new TestDataAccess();

	@SuppressWarnings("unused")
	private Driver driver; 

	@Test
	//sut.realizarOperacion:  username exist in DB and its not "" or null, 
	//amount is positive. Must return true and update correctly the user amount
	public void test1() {
	 	double previousUserMoney = 0.0;
	 	
	 	//meter a user en la BD
	 	testDA.open();
	 	testDA.addUserWithMoney("Jon", "1234", "driver", previousUserMoney);
	 	System.out.println("Dinero inicial del usuario: " + previousUserMoney);
	 	testDA.close();
		try {
			// define parameters
			User user = null;
			String username = "Jon";
			double amount = 100.0;
			boolean deposit = true;
			double actualUserMoney = -1.0;
			
			// invoke System Under Test (sut)
			sut.open();
			boolean res = sut.gauzatuEragiketa(username, amount, deposit);
			// verify the results
			testDA.open();
			if ((user = testDA.getUser("Jon", "1234", "driver")) != null) {
		 		actualUserMoney = user.getMoney();
		 	}
			testDA.close();
			System.out.println("Dinero final del usuario: " + actualUserMoney);
			if ((actualUserMoney != -1.0) && ((actualUserMoney - amount) == previousUserMoney)) {
				assertTrue(res);
			}
			else fail();
			

		} catch (Exception e) {
			// if the program goes to this point fail
			fail();
		} finally {
			testDA.open();
			testDA.removeUser("Jon");
			testDA.close();
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
			boolean deposit = true;

			// invoke System Under Test (sut)
			sut.open();
			boolean res = sut.gauzatuEragiketa(username, amount, deposit);
			// verify the results
			assertFalse(res);
			

		} catch (Exception e) {
			// if the program goes to this point fail
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
			String username = "Jon";
			double amount = 100.0;
			boolean deposit = true;
			
			// invoke System Under Test (sut)
			sut.open();
			boolean res = sut.gauzatuEragiketa(username, amount, deposit);
			// verify the results
			assertFalse(res);

		} catch (Exception e) {
			// if the program goes to this point fail
			fail();
		} finally {
			sut.close();
		}
	}
		   
	@Test
	//sut.realizarOperacion:  username exist in DB and its not "" or null, 
	//amount is negative. Must return true a Exception
	public void test4() {
	 	double previousUserMoney = 100.0;
	 	
	 	//meter a user en la BD
	 	testDA.open();
	 	testDA.addUserWithMoney("Jon", "1234", "driver", previousUserMoney);
	 	System.out.println("Dinero inicial del usuario: " + previousUserMoney);
	 	testDA.close();
		try {
			// define parameters
			String username = "Jon";
			double amount = -20.0;
			boolean deposit = true;
			
			// invoke System Under Test (sut)
			sut.open();
			sut.gauzatuEragiketa(username, amount, deposit);
			// verify the results
			fail();
			

		} catch (Exception e) {
			// if the program goes to this point fail
			assertTrue(true);
		} finally {
			testDA.open();
			testDA.removeUser("Jon");
			testDA.close();
			sut.close();
		}
	}
	
	@Test
	//sut.realizarOperacion:  username exist in DB and its not "" or null, 
	//amount is positive and deposit is false. Must return true and update 
	//correctly the user amount
	public void test5() {
	 	double previousUserMoney = 100.0;
	 	
	 	//meter a user en la BD
	 	testDA.open();
	 	testDA.addUserWithMoney("Jon", "1234", "driver", previousUserMoney);
	 	System.out.println("Dinero inicial del usuario: " + previousUserMoney);
	 	testDA.close();
		try {
			// define parameters
			User user = null;
			String username = "Jon";
			double amount = 20.0;
			boolean deposit = false;
			double actualUserMoney = -1.0;
			
			// invoke System Under Test (sut)
			sut.open();
			boolean res = sut.gauzatuEragiketa(username, amount, deposit);
			// verify the results
			testDA.open();
			if ((user = testDA.getUser("Jon", "1234", "driver")) != null) {
		 		actualUserMoney = user.getMoney();
		 	}
			testDA.close();
			System.out.println("Dinero final del usuario: " + actualUserMoney);
			if ((actualUserMoney != -1.0) && ((actualUserMoney + amount) == previousUserMoney)) {
				assertTrue(res);
			}
			else fail();
			

		} catch (Exception e) {
			// if the program goes to this point fail
			fail();
		} finally {
			testDA.open();
			testDA.removeUser("Jon");
			testDA.close();
			sut.close();
		}
	}
	
	@Test
	//sut.realizarOperacion:  username exist in DB and its not "" or null, 
	//amount is positive but greater than user money and deposit is false. 
	//Must return a exception
	public void test6() {
	 	double previousUserMoney = 20.0;
	 	
	 	//meter a user en la BD
	 	testDA.open();
	 	testDA.addUserWithMoney("Jon", "1234", "driver", previousUserMoney);
	 	System.out.println("Dinero inicial del usuario: " + previousUserMoney);
	 	testDA.close();
		try {
			// define parameters
			String username = "Jon";
			double amount = 100.0;
			boolean deposit = false;
			
			// invoke System Under Test (sut)
			sut.open();
			sut.gauzatuEragiketa(username, amount, deposit);
			// verify the results
			fail();
			

		} catch (Exception e) {
			// if the program goes to this point fail
			assertTrue(true);
		} finally {
			testDA.open();
			testDA.removeUser("Jon");
			testDA.close();
			sut.close();
		}
	}
	
	@Test
	//sut.realizarOperacion:  Valor limite amount -0.01. Must return a Exception
	public void test7() {
	 	double previousUserMoney = 100.0;
	 	
	 	//meter a user en la BD
	 	testDA.open();
	 	testDA.addUserWithMoney("Jon", "1234", "driver", previousUserMoney);
	 	System.out.println("Dinero inicial del usuario: " + previousUserMoney);
	 	testDA.close();
		try {
			// define parameters
			String username = "Jon";
			double amount = -0.01;
			boolean deposit = true;
			
			// invoke System Under Test (sut)
			sut.open();
			sut.gauzatuEragiketa(username, amount, deposit);
			// verify the results
			fail();
			

		} catch (Exception e) {
			// if the program goes to this point ok
			assertTrue(true);
		} finally {
			testDA.open();
			testDA.removeUser("Jon");
			testDA.close();
			sut.close();
		}
	}
	
	@Test
	//sut.realizarOperacion:  Valor limite amount 0.0. Must return true
	public void test8() {
	 	double previousUserMoney = 100.0;
	 	
	 	//meter a user en la BD
	 	testDA.open();
	 	testDA.addUserWithMoney("Jon", "1234", "driver", previousUserMoney);
	 	System.out.println("Dinero inicial del usuario: " + previousUserMoney);
	 	testDA.close();
		try {
			User user = null;
			String username = "Jon";
			double amount = 0.0;
			boolean deposit = false;
			double actualUserMoney = -1.0;
			
			// invoke System Under Test (sut)
			sut.open();
			boolean res = sut.gauzatuEragiketa(username, amount, deposit);
			// verify the results
			testDA.open();
			if ((user = testDA.getUser("Jon", "1234", "driver")) != null) {
		 		actualUserMoney = user.getMoney();
		 	}
			testDA.close();
			System.out.println("Dinero final del usuario: " + actualUserMoney);
			if ((actualUserMoney != -1.0) && (actualUserMoney  == previousUserMoney)) {
				assertTrue(res);
			}
			else fail();

		} catch (Exception e) {
			// if the program goes to this point ok
			fail();
		} finally {
			testDA.open();
			testDA.removeUser("Jon");
			testDA.close();
			sut.close();
		}
	}
	
	@Test
	//sut.realizarOperacion:  Valor limite amount 0.01. Must return true
	public void test9() {
	 	double previousUserMoney = 100.0;
	 	
	 	//meter a user en la BD
	 	testDA.open();
	 	testDA.addUserWithMoney("Jon", "1234", "driver", previousUserMoney);
	 	System.out.println("Dinero inicial del usuario: " + previousUserMoney);
	 	testDA.close();
		try {
			// define parameters
			User user = null;
			String username = "Jon";
			double amount = 0.01;
			boolean deposit = false;
			double actualUserMoney = -1.0;
			
			// invoke System Under Test (sut)
			sut.open();
			boolean res = sut.gauzatuEragiketa(username, amount, deposit);
			// verify the results
			testDA.open();
			if ((user = testDA.getUser("Jon", "1234", "driver")) != null) {
		 		actualUserMoney = user.getMoney();
		 	}
			testDA.close();
			System.out.println("Dinero final del usuario: " + actualUserMoney);
			if ((actualUserMoney != -1.0) && ((actualUserMoney + amount) == previousUserMoney)) {
				assertTrue(res);
			}
			else fail();
			

		} catch (Exception e) {
			// if the program goes to this point ok
			fail();
		} finally {
			testDA.open();
			testDA.removeUser("Jon");
			testDA.close();
			sut.close();
		}
	}
}

