package scheduler;
import java.util.ArrayList;
import java.util.Date;

public  class Item extends Actor 
{
	private Case etat ;
	private ArrayList<Date> dateList = new ArrayList<Date>() ;
	private long timeToBeServed ;
		
	public Item(Case etat, long time)
	{
		this.etat = etat ;
		this.timeToBeServed = time ;
	}
	
	/********************************************************************************************************/
	/** getters and setters **/
	public Case getEtat()
	{
		return etat ;
	}
	
	public void setEtat(Case etat)
	{
		this.etat = etat ;
	}
	
	public ArrayList<Date> getDateList()
	{
		return dateList ;
	}
	
	public long getTimeToBeServed()
	{
		return this.timeToBeServed ;
	}
	
	public void setTimeToBeServed(long time)
	{
		this.timeToBeServed = time ;
	}
	
	/*****************************************************************************************************/
	/** Gestion Boolean **/
	private boolean isFirst() 
	{
		return (this==this.getEtat().getFirstItem());
	}

	private boolean isIn(Case etat) 
	{
		return (etat.getListeItems().contains(this));
	}
	
	/*****************************************************************************************************/
	/** Actions... **/
	@Override
	public void go( Scheduler scheduler)
	{
		super.go(scheduler);
		System.out.println("Start going : "+ scheduler.getCurrentEvent().getDate()+" by "+scheduler.getCurrentEvent().getActor());
		System.out.println("I'm going from "+scheduler.getCurrentEvent().getcaseActuelle()+" to "+scheduler.getCurrentEvent().getCaseCible());
		Case current = this.getEtat() ;
		Case cible = scheduler.getCurrentEvent().getCaseCible();
		
		/** Is the event obsolete ? **/
		if (current != scheduler.getCurrentEvent().getcaseActuelle())
		{
			System.out.println("Obsolete event (go)");
			scheduler.incrementeObsolete();
			return;
		}
		
		if(current instanceof MG1_FCFS)
		{
			((MG1_FCFS) current).free();
		}

		/** Update attributes from Cases & Items **/
		if(cible.getCapacity()>cible.getListeItems().size())
		{
			this.getEtat().getListeItems().remove(this);
			this.setEtat(cible);
			cible.getListeItems().add(this);
			dateList.add(scheduler.getCurrentEvent().getDate()) ;
		}
		else
		{
			System.out.println("Obsolete event (go)");
			scheduler.incrementeObsolete();
			return;
		}
		
		/** Pit ? **/
		if (cible instanceof Pit)
		{
			((Pit) cible).incrementer();
			if(this.isIn(cible))
			{
				Date nextDateEvacuation = new Date();
				nextDateEvacuation.setTime(scheduler.getCurrentEvent().getDate().getTime());
				
				Event newEvent= new Event(cible,1,nextDateEvacuation,cible,cible);
				scheduler.add(newEvent);
				System.out.println("Flushing asked by "+cible+", planned for "+ nextDateEvacuation);
			}
			else
			{
				System.out.println("Obsolete event (flush)");
				scheduler.incrementeObsolete();						
			}
		}		
		else if(cible instanceof MG1_FCFS)
		{
			/** Forward : Can I already be served ? **/
			// Am I the first ?
			if(this.isFirst()&&!((MG1_FCFS)cible).isServing())
			{
				Date nextDate1 = new Date();
				nextDate1.setTime(scheduler.getCurrentEvent().getDate().getTime());
				
				Event newEvent= new Event(cible,5,nextDate1,cible,cible);
				scheduler.add(newEvent);
				System.out.println(this+" will be served by "+cible+" on the "+nextDate1);	
			}
			// 	I'm blocked : I have to be patient
			else
			{
				System.out.println(this+" is waiting...");
			}
		}
		
			/** Backward  : Can others follow me ? **/
			if(!current.getListeItems().isEmpty())
			{
				Date nextDate1 = new Date();
				nextDate1.setTime(scheduler.getCurrentEvent().getDate().getTime()+1);
			
				Event newEvent= new Event(current,5,nextDate1,current,current);
				scheduler.add(newEvent);
				System.out.println("Next service by "+current+" on the "+nextDate1);
			}
	}
}
