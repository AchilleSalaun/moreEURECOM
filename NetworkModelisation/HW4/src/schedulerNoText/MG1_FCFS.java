package schedulerNoText;

import java.util.ArrayList;
import java.util.Date;

public class MG1_FCFS extends Case
{
	public MG1_FCFS() 
	{
		super(Integer.MAX_VALUE);
	}

	@Override
	public void serve(Scheduler scheduler)
	{
		super.serve(scheduler);		
		
		if(this.getListeItems().isEmpty())
		{
			return ;
		}
		
		if(this.isServing())
		{
			return ;
		}
		
		this.occupy();
		
		ArrayList<Case> liste = new ArrayList<Case>() ;
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
			Date nextDate1 = new Date(scheduler.getCurrentEvent().getDate().getTime()+first.getTimeToBeServed()) ;			
			Event newEvent= new Event(first,2,nextDate1,this,forward);
			scheduler.add(newEvent);
		}
	}
}
