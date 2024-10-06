import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dataAccess.DataAccess;
import domain.Driver;
import domain.Ride;
import domain.Traveler;
import domain.User;

public class bookRideMockBlackTest {

    @Mock
    private DataAccess db;

    @Mock
    private Ride mockRide;

    @Mock
    private Traveler traveler;

    @Mock
    private Driver d;

    static DataAccess sut;

    private String username;
    @Mock
    private User user;
    
    private int seats;
    private double desk;

    @Before
    public void setUp() {
     
        MockitoAnnotations.openMocks(this);

        seats = 2;
        desk = 0.1;
        user = new User("TestUser11", "contraseña", "tipo");
        username = user.getUsername();
        sut = new DataAccess();
    }

    @Test
    public void test5() {
        // Primer test de caja negra, el usuario no está en la BD

        // Preparar datos para la prueba
        String from = "Donostia";
        String to = "Bilbao";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date rideDate = null;
        try {
            rideDate = sdf.parse("05/10/2026");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
        // Configurar el comportamiento de los mocks

        // 1. El método getTraveler debe retornar null porque el usuario no existe en la BD
        	Mockito.when(db.getTraveler(username)).thenReturn(null);

        // 2. El ride tiene suficientes asientos
        Mockito.when(mockRide.getnPlaces()).thenReturn(5);
        Mockito.when(mockRide.getPrice()).thenReturn(5.0);
        sut.open();

        // Ejecutar el método a probar
        boolean resultado = sut.bookRide(username, mockRide, seats, desk);
        sut.close();

        // Verificar el resultado
        assertEquals(false, resultado);
    }catch(Exception e) {
    	fail();
    }

  }
}
