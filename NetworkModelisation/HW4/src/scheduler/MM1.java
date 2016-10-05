package scheduler;
import java.util.ArrayList;
import java.util.Date;

public class MM1 extends Case 
{
	private double mu ;
	
	public MM1(double mu) 
	{
		super(Integer.MAX_VALUE);
		this.mu = mu ;
	}
	
	public double getMu()
	{
		return this.mu;
	}
	
	public void setMu(double coeff)
	{
		this.mu = coeff ;
	}
	
	@Override
	public void serve(Scheduler scheduler)
	{
		super.serve(scheduler);
		System.out.println("Start serving : "+ scheduler.getCurrentEvent().getDate()+" by "+scheduler.getCurrentEvent().getActor());
		
		ArrayList<Case> liste = new ArrayList<Case>();
		liste.addAll(this.getExit()) ;
		Case forward = this ;
		int s = liste.size() ;
		boolean available = false ;
			
		while(s>0 && !available)
		{	
			// Where will the served item go ? (uniform law)
			int i = Choice.ChoiceCase(0,liste) ;		
			forward = liste.remove(i);
			available = ((forward.getCapacity()>forward.getListeItems().size()));
			s = liste.size();
		}
					
		// If I find an available exit
		if (available)
		{
			Item first = this.getFirstItem() ;
			Date nextDate1 = this.creationNextDate(scheduler, this.mu);
			Event newEvent= new Event(first,2,nextDate1,this,forward);
			scheduler.add(newEvent);
			System.out.println(first+" will go from "+this+" to "+forward+" on the "+nextDate1);
		}
	}
}
