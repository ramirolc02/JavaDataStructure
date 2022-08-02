package aed.airport;

import es.upm.aedlib.Entry;
import es.upm.aedlib.Pair;
import es.upm.aedlib.priorityqueue.*;
import es.upm.aedlib.map.*;
import es.upm.aedlib.positionlist.*;

/**
 * A registry which organizes information on airplane arrivals.
 */
public class IncomingFlightsRegistry {

	private PriorityQueue<Long, String> cola;
	private Map<String, Entry<Long, String>> mapa;

	/**
	 * Constructs an class instance.
	 */

	public IncomingFlightsRegistry() {
		this.cola = new HeapPriorityQueue<Long, String>();
		this.mapa = new HashTableMap<>();

	}

	/**
	 * A flight is predicted to arrive at an arrival time (in seconds).
	 */
	public void arrivesAt(String flight, long time) {

		if (mapa.containsKey(flight)) {
			cola.replaceKey(mapa.get(flight), time);
		} else {
			mapa.put(flight, cola.enqueue(time, flight));
		}
	}

	/**
	 * A flight has been diverted, i.e., will not arrive at the airport.
	 */
	public void flightDiverted(String flight) {

		if (mapa.containsKey(flight)) {
			cola.remove(mapa.get(flight));
			mapa.remove(flight);
		}
	}

	/**
	 * Returns the arrival time of the flight.
	 * 
	 * @return the arrival time for the flight, or null if the flight is not
	 *         predicted to arrive. if(mapa.get(flight) != null){ return
	 *         mapa.get(flight).getValue(); } return null;
	 */

	public Long arrivalTime(String flight) {

		if (mapa.get(flight) != null) {
			return mapa.get(flight).getKey();
		}
		return null;
	}

	/**
	 * Returns a list of "soon" arriving flights, i.e., if any is predicted to
	 * arrive at the airport within nowTime+180 then adds the predicted earliest
	 * arriving flight to the list to return, and removes it from the registry.
	 * Moreover, also adds to the returned list, in order of arrival time, any other
	 * flights arriving withinfirstArrivalTime+120; these flights are also removed
	 * from the queue of incoming flights.
	 * 
	 * @return a list of soon arriving flights.
	 */

    public PositionList<FlightArrival> arriving(long nowTime) {
		PositionList<FlightArrival> aviones = new NodePositionList<FlightArrival>();
		if (cola.isEmpty()) {
			return aviones;
		}
		int n = 180;
		Entry<Long, String> primero = cola.first();
		while (cola.first().getKey() <= nowTime + n && cola.first().getKey() >= nowTime) {
			FlightArrival pares = new FlightArrival(cola.first().getValue(), cola.first().getKey());
			aviones.addLast(pares);
			nowTime = primero.getKey();
			n = 180-60; // Comparando el tiempo del primer avion +120 para el siguiente caso. 
			mapa.remove(cola.first().getValue());
			cola.dequeue();
			if (cola.isEmpty()) {
				return aviones;
			}
		}
		return aviones;
	}
}
