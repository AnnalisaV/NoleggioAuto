package polito.it.noleggio.model;

import java.time.LocalTime;

import javax.print.attribute.standard.MediaSize.Other;

// singolo evento della simulazione
public class Event implements Comparable<Event>{


	//sempre c'e' la marcatura temporale ed il tipo di evento
	//(in aggiunta altri parametri)
	
	
	       //class -> versione semplificata per avere delle costanti
	public enum EventType{
		//costanti quindi scritte maiuscole
		NEW_CLIENT, CAR_RETURNED
		//java li converte in zeri ma per noi e' meglio leggerli così
		}
	
	private LocalTime time; 
	private EventType type;
	
	
	/**
	 * @param time
	 * @param type
	 */
	public Event(LocalTime time, EventType type) {
		super();
		this.time = time;
		this.type = type;
	}


	public LocalTime getTime() {
		return time;
	}


	public void setTime(LocalTime time) {
		this.time = time;
	}


	public EventType getType() {
		return type;
	}


	public void setType(EventType type) {
		this.type = type;
	}


	//FONDAMENTALE DEFINIRE UN CRITERIO DI ORDINAMENTO -> perche' andranno in code da ordinare per priorita'
	@Override
	public int compareTo(Event other) {
		
		return this.time.compareTo(other.time);
	}


	@Override
	public String toString() {
		return "Event [time=" + time + ", type=" + type + "]";
	} 
	
	
	//equals() e hashCode() no perche' e' lecito avere un nuovo cliente nello stesso tempo, questo
	//non definisce lo stesso evento quindi dunque per ora non lo si implementa
	
	
}
