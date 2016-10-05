package schedulerNoText;
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
		Case current = this.getEtat() ;
		Case cible = scheduler.getCurrentEvent().getCaseCible();
		
		/** Is the event obsolete ? **/
		if (current != scheduler.getCurrentEvent().getcaseActuelle())
		{
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
			}
			else
			{
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
			}
		}
		
		/** Backward  : Can others follow me ? **/
		if(!current.getListeItems().isEmpty())
		{
			Date nextDate1 = new Date();
			nextDate1.setTime(scheduler.getCurrentEvent().getDate().getTime()+1);
		
			Event newEvent= new Event(current,5,nextDate1,current,current);
			scheduler.add(newEvent);
		}
	}
}
