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
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import testOperations.TestDataAccess;
import domain.Driver;
import domain.User;

public class GauzatuEragiketaWhiteTest {

	//sut:system under test
		 static DataAccess sut=new DataAccess();
		 
		 //additional operations needed to execute the test 
		 static TestDataAccess testDA=new TestDataAccess();

		 @Test
			//sut.realizarOperacion:  User exists on the DB and the method must deposit 
		 	//the amount. If the result is different of true and the amount is not well updated, 
		 	//its not properly implemented 
			public void test1() {
			 	double previousUserMoney = 0.0;
			 	
			 	//meter a user en la BD
			 	//testDA.addUser2(user);
			 	testDA.open();
			 	testDA.addUserWithMoney("Jon", "1234", "driver", previousUserMoney);
			 	System.out.println("Dinero inicial del usuario: " + previousUserMoney);
			 	testDA.close();
				try {
					// define parameters
					User user2 = null;
					String username = "Jon";
					double amount = 10.0;
					boolean deposit = true;
					double actualUserMoney = -1.0;
					
					// invoke System Under Test (sut)
					sut.open();
					boolean res = sut.gauzatuEragiketa(username, amount, deposit);
					testDA.open();
					if ((user2 = testDA.getUser("Jon", "1234", "driver")) != null) {
				 		actualUserMoney = user2.getMoney();
				 	}
					testDA.close();
					//double actualUserMoney = user.getMoney();
					System.out.println("Dinero final del usuario: " + actualUserMoney);
					if ((actualUserMoney - amount) == previousUserMoney) {
						assertTrue(res);
					} else {
						fail();
					}
					

				} catch (Exception e) {
					// if the program goes to this point fail
					e.printStackTrace();
					fail();
				} finally {
					testDA.open();
					testDA.removeUser("Jon");
					testDA.close();
					sut.close();
				}
			}
		 
		 
		 @Test
			//sut.realizarOperacion:  User exists on the DB and the method must set the user 
		 	//amount to 0. If the result is different of true, its not properly implemented 
			public void test2() {
			 	User user = null;
			 	double previousUserMoney = 5.0;
			 	
			 	
			 	//meter a user en la BD
			 	testDA.open();
			 	testDA.addUserWithMoney("Jon", "1234", "driver", previousUserMoney);
			 	if ((user = testDA.getUser("Jon", "1234", "driver")) != null) {
			 		previousUserMoney = user.getMoney();
			 		System.out.println("Dinero inicial del usuario: " + previousUserMoney);
			 	}
			 	
			 	testDA.close();
				try {
					// define parameters
					String username = "Jon";
					double amount = 10.0;
					boolean deposit = false;
					double actualUserMoney = -1.0;
					User user2 = null;
					
					// invoke System Under Test (sut)
					sut.open();
					boolean res = sut.gauzatuEragiketa(username, amount, deposit);
					testDA.open();
					if ((user2 = testDA.getUser("Jon", "1234", "driver")) != null) {
				 		actualUserMoney = user2.getMoney();
				 	}
					testDA.close();
					if (actualUserMoney  == 0.0) {
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
			//sut.realizarOperacion:  User exists on the DB and the method must set the user 
		 	//amount to user current money - amount. If the result is different of true, 
		 	//its not properly implemented 
		 public void test3() {
			 	User user = null;
			 	double previousUserMoney = 10.0;
			 	
			 	
			 	//meter a user en la BD
			 	testDA.open();
			 	testDA.addUserWithMoney("Jon", "1234", "driver", previousUserMoney);
			 	if ((user = testDA.getUser("Jon", "1234", "driver")) != null) {
			 		previousUserMoney = user.getMoney();
			 		System.out.println("Dinero inicial del usuario: " + previousUserMoney);
			 	}
			 	
			 	testDA.close();
				try {
					// define parameters
					String username = "Jon";
					double amount = 5.0;
					boolean deposit = false;
					double actualUserMoney = -1.0;
					User user2 = null;
					
					// invoke System Under Test (sut)
					sut.open();
					boolean res = sut.gauzatuEragiketa(username, amount, deposit);
					testDA.open();
					if ((user2 = testDA.getUser("Jon", "1234", "driver")) != null) {
				 		actualUserMoney = user2.getMoney();
				 	}
					testDA.close();
					if ((actualUserMoney + amount) == previousUserMoney) {
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
			//sut.realizarOperacion:  User does not exists on the DB. If the result is 
		 	//different of false, its not properly implemented 
			public void test4() {
			 	
				try {
					// define parameters
					String username = "Jon";
					double amount = 5.0;
					boolean deposit = false;
					
					// invoke System Under Test (sut)
					sut.open();
					boolean res = sut.gauzatuEragiketa(username, amount, deposit);
					// verify the results
					//tiene que devolver false
					assertFalse(res);

				} catch (Exception e) {
					// if the program goes to this point fail
					fail();
				} finally {
					sut.close();
				}
			}
			
			
}
