import org.mockito.Mockito;
import businessLogic.BLFacade;
import gui.MainGUI;

public class RideTest {
	static BLFacade appFacadeInterface = Mockito.mock(BLFacade.class);
	
	public static void main(String args[]) {
		MainGUI a = new MainGUI();
		MainGUI.setBussinessLogic(appFacadeInterface);
		Mockito.doReturn(true).when(appFacadeInterface).isRegistered("a", "a");
		Mockito.doReturn("Driver").when(appFacadeInterface).getMotaByUsername
		(Mockito.anyString());
		Mockito.doReturn(true).when(appFacadeInterface).isRegistered("a", "b");
		Mockito.doReturn("Traveler").when(appFacadeInterface).getMotaByUsername
		(Mockito.anyString());
		
		a.setVisible(true);
	}
}
