package testOperations;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import configuration.ConfigXML;
import domain.Driver;
import domain.Ride;
import domain.Traveler;
import domain.User;


public class TestDataAccess {
	protected  EntityManager  db;
	protected  EntityManagerFactory emf;

	ConfigXML  c=ConfigXML.getInstance();


	public TestDataAccess()  {
		
		System.out.println("TestDataAccess created");

		//open();
		
	}

	
	public void open(){
		

		String fileName=c.getDbFilename();
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
    	   }
		System.out.println("TestDataAccess opened");

		
	}
	public void close(){
		db.close();
		System.out.println("TestDataAccess closed");
	}

	public boolean removeDriver(String name) {
		System.out.println(">> TestDataAccess: removeDriver");
		Driver d = db.find(Driver.class, name);
		if (d!=null) {
			db.getTransaction().begin();
			db.remove(d);
			db.getTransaction().commit();
			return true;
		} else 
		return false;
    }
	public Driver createDriver(String name, String pass) {
		System.out.println(">> TestDataAccess: addDriver");
		Driver driver=null;
			db.getTransaction().begin();
			try {
			    driver=new Driver(name,pass);
				db.persist(driver);
				db.getTransaction().commit();
			}
			catch (Exception e){
				e.printStackTrace();
			}
			return driver;
    }
	public boolean existDriver(String email) {
		 return  db.find(Driver.class, email)!=null;
		 

	}
		
		public Driver addDriverWithRide(String name, String from, String to,  Date date, int nPlaces, float price) {
			System.out.println(">> TestDataAccess: addDriverWithRide");
				Driver driver=null;
				db.getTransaction().begin();
				try {
					 driver = db.find(Driver.class, name);
					if (driver==null) {
						System.out.println("Entra en null");
						driver=new Driver(name,null);
				    	db.persist(driver);
					}
				    driver.addRide(from, to, date, nPlaces, price);
					db.getTransaction().commit();
					System.out.println("Driver created "+driver);
					
					return driver;
					
				}
				catch (Exception e){
					e.printStackTrace();
				}
				return null;
	    }
		
		
		public boolean existRide(String name, String from, String to, Date date) {
			System.out.println(">> TestDataAccess: existRide");
			Driver d = db.find(Driver.class, name);
			if (d!=null) {
				return d.doesRideExists(from, to, date);
			} else 
			return false;
		}
		public Ride removeRide(String name, String from, String to, Date date ) {
			System.out.println(">> TestDataAccess: removeRide");
			Driver d = db.find(Driver.class, name);
			if (d!=null) {
				db.getTransaction().begin();
				Ride r= d.removeRide(from, to, date);
				db.getTransaction().commit();
				System.out.println("created rides" +d.getCreatedRides());
				return r;

			} else 
			return null;

		}
		
		public void addUser(User user) {
			db.getTransaction().begin();
			db.persist(user);
			db.getTransaction().commit();
		}
		
		public void removeUser(User user) {
			User usuario = db.find(User.class, user.getUsername());
			if(usuario !=null) {
			db.getTransaction().begin();
			db.remove(usuario);
			db.getTransaction().commit();
			}
		}
		
		public void addTraveler(Traveler traveler) {
			db.getTransaction().begin();
			db.persist(traveler);
			db.getTransaction().commit();
		}
		
		public void removeTraveler(Traveler traveler) {
			Traveler viajero = db.find(Traveler.class, traveler.getUsername());
			if(viajero !=null) {
			db.getTransaction().begin();
			db.remove(viajero);
			db.getTransaction().commit();
			}
		}
		public User addUser1(String username, String password, String mota) {
			User user = null;
			try {
				db.getTransaction().begin();
	            db.persist(user); // Añadir el objeto User a la base de datos
	            db.getTransaction().commit(); 
	            return user;// Devolver el user añadido
	        } catch (Exception e) {
	            if (db.getTransaction().isActive()) {
	            	db.getTransaction().rollback(); // Revertir en caso de error
	            }
	            e.printStackTrace();
	            return null;// Mostrar la excepción y devolver null
	        } finally {
	            db.close(); // Cerrar el EntityManager
	        }
		}
		
		public User addUserWithMoney(String username, String password, String mota, double money) {
			System.out.println(">> TestDataAccess: addUser");
			User user=null;
				db.getTransaction().begin();
				try {
					user = new User(username, password, mota);
					user.setMoney(money);
					db.persist(user);
					db.getTransaction().commit();
				}
				catch (Exception e){
					e.printStackTrace();
				}
				return user;
	    }
		
		public User addUser2(User user) {
			
			try {
				db.getTransaction().begin();
	            db.persist(user); // Añadir el objeto User a la base de datos
	            db.getTransaction().commit(); 
	            return user;// Devolver el user añadido
	        } catch (Exception e) {
	            if (db.getTransaction().isActive()) {
	            	db.getTransaction().rollback(); // Revertir en caso de error
	            }
	            e.printStackTrace();
	            return null;// Mostrar la excepción y devolver null
	        } finally {
	            db.close(); // Cerrar el EntityManager
	        }
		}
		
		public boolean removeUser(String name) {
			System.out.println(">> TestDataAccess: removeUser");
			User u = db.find(User.class, name);
			if (u!=null) {
				db.getTransaction().begin();
				db.remove(u);
				db.getTransaction().commit();
				return true;
			} else 
			return false;
	    }
		
		public User getUser(String username, String password, String mota) {
			try {
	            db.getTransaction().begin();
	            Query query = db.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.passwd = :password AND u.mota = :mota");
	            query.setParameter("username", username);
	            query.setParameter("password", password);
	            query.setParameter("mota", mota);

	            User user = (User) query.getSingleResult();
	            db.getTransaction().commit();
	            return user;
	        } catch (Exception e) {
	            db.getTransaction().rollback();
	            e.printStackTrace();
	            return null;
	        }
		}

		
}