package aed.airport;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import es.upm.aedlib.positionlist.*;


public class Tests {
	@Test
	public void test1() {
	IncomingFlightsRegistry airport = new IncomingFlightsRegistry(); 
	airport.arrivesAt("Avion",1050);
	airport.arrivesAt("Avion",1200);
	assertEquals(1200, airport.arrivalTime("Avion")); 
	
	}
	@Test
	public void test2() {
		IncomingFlightsRegistry airport = new IncomingFlightsRegistry(); 
		airport.arrivesAt("Avion1",20);
		airport.arrivesAt("Avion2",10);
		assertEquals(new es.upm.aedlib.positionlist.NodePositionList<FlightArrival>(new FlightArrival[] { new FlightArrival(new String("Avion2"),10),new FlightArrival(new String("Avion1"),20) }), airport.arriving(0)); 
		
		}
	
}

