package polito.it.noleggio.model;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

public class TestSimulator {

	public static void main(String args[]) {
		Simulator sim = new Simulator();
		
		//impostazione dei parametri 
		sim.setNumCars(10) ; //numero cars costante
		sim.setClientFrequency(Duration.of(10, ChronoUnit.MINUTES)) ; //frequenza arrivo del clienti
		
		sim.run() ; //avvio simulazione 
		
		//risultati
		int totClients = sim.getClienti() ;
		int dissatisfied = sim.getInsoddisfatti() ;
		
		System.out.format("Arrived %d clients, %d were dissatisfied\n", 
				totClients, dissatisfied);
	}
	
}
