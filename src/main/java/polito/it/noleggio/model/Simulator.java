package polito.it.noleggio.model;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.PriorityQueue;

import polito.it.noleggio.model.Event.EventType;

public class Simulator {
	
	//CODA DEGLI EVENTI
	private PriorityQueue<Event> queue= new PriorityQueue();  //struttura dati principale 
	
	//PARAMETRI DI SIMULAZIONE (conviene inserire valori di default, nel caso il chiamante non li impostasse)
	private int NUMCARS=10; //numeor totale di cars
	private Duration TIMEIN=Duration.of(10, ChronoUnit.MINUTES); //tempo arrivo del cliente (frequenza)
	
	//devono essere impostati dall'esterno
	public void setNumCars(int NUMCARS) {
		this.NUMCARS = NUMCARS;
	}
	public void setClientFrequency(Duration d) {
		this.TIMEIN = d;
	}
	
	/*scelti da me*/
	private final LocalTime oraApertura= LocalTime.of(8,00);
	private final LocalTime oraChiusura= LocalTime.of(17, 00); 
	
	
	//MODELLO DEL MONDO
	private int availCars; //numero auto disponibili (0, numCars) -> ma sara' calcolato di volta in volta
	                                                                //non e' da impostare
	
	
	
	//VALORI DA CALCOLARE
	private int clienti; //quanto clienti arrivano 
	private int insoddisfatti; //quanto clienti non sono contenti perche' non hanno ottenuto l'auto

	//da restituire all'esterno, ma non da impostare da lì
	public int getClienti() {
		return clienti;
	}
	public int getInsoddisfatti() {
		return insoddisfatti;
	} 
	
	//SIMULAZIONE VERA E PROPRIA
	/**
	 * Avvio della simulazione e gestione di questa 
	 */
	public void run() {
		
		//preparazione iniziale 
		//(preparazione variabili del mondo e della codaEventi)
		/*impostazione delle variabili al loro valore iniziale*/
		this.availCars= NUMCARS; 
		this.clienti=this.insoddisfatti=0; 
		
		/*per la coda*/
		this.queue.clear(); //la pulisco subito
		LocalTime oraArrivoCliente= this.oraApertura; //ipotizzo che il primo cliente arrivi subito
		
		do {
			Event e= new Event(oraArrivoCliente,EventType.NEW_CLIENT); 
			queue.add(e); 
			oraArrivoCliente= oraArrivoCliente.plus(this.TIMEIN); //aggiorno con la frequenza
		}while(oraArrivoCliente.isBefore(this.oraChiusura)); 
		
		//esecuzione del ciclo di simulazione
		while(!this.queue.isEmpty()) {
			//l'estrazione e' ordinata per il time (com ee' stato implementato in compareTo di Event)
			Event e= this.queue.poll(); //lo estraggo finche' c'e'
			System.out.println(e); // a scopo di debug
		    this.processEvent(e); //eleborazione dell'evento
			
		}
		
	}
	//piu' pulito fare un metodo a parte
	/**
	 * Dato un evento, fa qualcosa a seconda del tipo di evento
	 * @param e tipo di {@code Event}
	 */
	private void processEvent(Event e) {
		
		//che tipo di evento e'
		switch(e.getType()) {
		
		case NEW_CLIENT : 
			// quando arriva il cliente , c'e' l'auto per lui o no?
			if (this.availCars >0) {
				// ci sono 
				//lo servo (aggiorno il mondo, i paramentri della simulazione ed eventualmente genero un nuovo Event conseguente)
				this.clienti++; 
				this.availCars--; 
				//il nuovo evento e' la restituzione dell'auto, dopo quanto tempo? A caso
				double numero=Math.random(); //genera un numero random tra [0,1)
				Duration taken; //quanto tiene la Car
				if (numero<1.0/3.0) {
					//un'ora
					taken= Duration.of(1, ChronoUnit.HOURS); 
				}
				else if (numero<2.0/3.0) {
					//due ore
					taken= Duration.of(2, ChronoUnit.HOURS); 
				}
				else {
					//tre ore
					taken= Duration.of(3, ChronoUnit.HOURS); 
				}
				                       //tempo di arrivo + di noleggio
				Event nuovo= new Event(e.getTime().plus(taken), EventType.CAR_RETURNED); 
				this.queue.add(nuovo); 
			}else {
				//non posso dargliela, faccio niente 
				this.clienti++; 
				this.insoddisfatti++; 
				//nessun nuovo evento da aggiungere 
			}
			break; 
			
			
		case CAR_RETURNED: 
			//cosa fare quando arriva l'auto?
			//la metto in deposito
			this.availCars++; 
			// on ci sono eventi di conseguenza qui 
			break; 
		}
		
	}

}
