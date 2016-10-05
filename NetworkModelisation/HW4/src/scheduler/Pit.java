package scheduler;

public class Pit extends Case
{
	private long timeSum = 0 ;
	public Pit() 
	{
		super(Integer.MAX_VALUE);
	}

	private int compteur = 0;
	
	public void incrementer()
	{
		compteur++;
	}
	
	@Override
	public void flush(Scheduler scheduler)
	{
		for(Item item : this.getListeItems())
		{
			timeSum += item.getDateList().get(item.getDateList().size()-1).getTime()-item.getDateList().get(0).getTime();
		}
		
		super.flush(scheduler);
		System.out.println("Flushing : "+scheduler.getCurrentEvent().getDate()+" by "+scheduler.getCurrentEvent().getActor());
		(this.getListeItems()).clear();
	}

	public int getCompteur() 
	{
		return compteur;
	}
	
	public long getTimeSum()
	{
		return timeSum ;
	}
}


