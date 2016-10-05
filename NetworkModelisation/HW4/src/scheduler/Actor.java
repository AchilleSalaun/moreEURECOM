package scheduler;
import java.util.Date;

public abstract class Actor 
{	
	public void execute(Scheduler scheduler)
	{
		int action = scheduler.getCurrentEvent().getAction();
		switch(action)
		{
	  		case 0: this.generate(scheduler) ;// generate new item
	  			break ;
	  		case 1: this.flush(scheduler) ; //empty "Pit"
	  			break ;
	  		case 2: this.go(scheduler) ; //move from a case to an other one
	  			break ;
	  		case 4: this.stop(scheduler) ; //final event : empty the scheduler
	  			break ;
	  		case 5: this.serve(scheduler) ; // simulate the service of an item
	  			break ; 
	  		default : System.out.println("default") ; //do nothing  	
		}
	}

	public void stop(Scheduler scheduler) 
	{
		// TODO Auto-generated method stub
	}

	public void go(Scheduler scheduler) 
	{
		// TODO Auto-generated method stub	
	}
	
	public void serve(Scheduler scheduler)
	{
		// TODO Auto-generated method stub
	}

	public void flush(Scheduler scheduler) 
	{
		// TODO Auto-generated method stub	
	}

	public void generate(Scheduler scheduler) 
	{
		// TODO Auto-generated method stub	
	}
	
	public Date creationNextDate(Scheduler scheduler, double lambda)
	{
		Date nextDate = new Date();
		long nextTime = scheduler.getCurrentEvent().getDate().getTime();
		long tau = (long)(Choice.exponentielle(lambda)*Scheduler.SCALE); //getTime return milliseconds
		nextTime = nextTime + tau ;
		nextDate.setTime(nextTime);
		return nextDate;
	}
}
