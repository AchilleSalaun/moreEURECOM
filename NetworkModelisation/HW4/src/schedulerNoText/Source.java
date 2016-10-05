package schedulerNoText;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Source extends Case
{
	private double lambda ;
	private int n = 0 ;

	public Source(double lambda)
	{
		super(1);
		this.lambda = lambda ;
	}
	
	public int getN()
	{
		return n ;
	}
	
	@Override
	public void generate(Scheduler scheduler)
	{	
		super.generate(scheduler);		
		Random r = new Random();
		double d = r.nextDouble() ;
		Item item = new Item(this,0);
		
		if(d<.98)
		{
			item.setTimeToBeServed(1*(long)Scheduler.SCALE);
		}
		else
		{
			item.setTimeToBeServed(201*(long)Scheduler.SCALE);
		}
		
		if(this.getCapacity()>this.getListeItems().size())
		{
			this.getListeItems().add(item);
			n++ ;
		}
		else
		{
			scheduler.incrementeObsolete();
			//return ;
			//item = this.getFirstItem() ;
		}
		
		/** Forward **/
		ArrayList<Case> liste = new ArrayList<Case>();
		liste.addAll(this.getExit()) ;
		Case forward = this ;
		int s = liste.size() ;
		boolean available = false ;
			
		while(s>0 && !available )
		{	
			// Where will I go  ? (uniform law)
			int i = Choice.ChoiceCase(0,liste) ;		
			forward = liste.remove(i);
			available = ((forward.getCapacity()>forward.getListeItems().size()));
			s = liste.size();
		}
			
		// if I find an available exit
		if (available)
		{
			Date nextDate1 = this.creationNextDate(scheduler, lambda);
			
			Event newEvent= new Event(item,2,nextDate1,this,forward);
			scheduler.add(newEvent);			
			Date nextDate2 = new Date(nextDate1.getTime()+1) ;
			Event newGeneration= new Event(this,0,nextDate2,this,this);
			scheduler.add(newGeneration);
		}
	}
		
	
	@Override
	public void stop(Scheduler scheduler)
	{
		super.stop(scheduler);
		scheduler.clear();
	}

}
