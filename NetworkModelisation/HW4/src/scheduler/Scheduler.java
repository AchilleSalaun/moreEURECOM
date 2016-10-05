package scheduler;
import java.util.ArrayList;
import java.util.Date;
import java.util.PriorityQueue;

public class Scheduler extends PriorityQueue<Event>
{
	private static final long serialVersionUID = 1L;
	public static final double SCALE = 1000. ; // 1 second = 1000 milliseconds
	private Event currentEvent ;
	private static EventComparator comparator = new EventComparator();

	public Scheduler(ArrayList<Source> sourceList/** , long duree **/)
	{
		super(/*Integer.MAX_VALUE*/ 1000, comparator);
		/*Creation of the "starter" event */
		Date date = new Date();
		for(Source source : sourceList)
		{
			Event starter = new Event(source,0,date,source, source);
			this.setCurrentEvent(starter);
			this.add(starter);
			date.setTime(date.getTime()+1);
		}
		
		/*Creation of the "finisher" event */
 		/**Date endDate = new Date();
 		Source source = sourceList.get(0);
		endDate.setTime(date.getTime()+duree);
		Event end = new Event(source,4,endDate,source, source);
		this.add(end);**/
	}
	
	public void end(Source source)
	{
		Date endDate = new Date(this.getCurrentEvent().getDate().getTime()-1);
		Event end = new Event(source,4,endDate,source, source);
		this.add(end);
		System.out.println("****************************************************************************");
		this.nextEvent();
		System.out.println("Size scheduler = "+this.size());
	}
	
	public void nextEvent()
	{
		this.currentEvent = this.poll() ;
		this.currentEvent.getActor().execute(this);
	}
	
	public Event getCurrentEvent() 
	{
		return currentEvent;
	}

	public void setCurrentEvent(Event currentEvent) 
	{
		this.currentEvent = currentEvent;
	}

	/** Mesure déchets**/
	private long obsolete = 0 ;

	public void incrementeObsolete()
	{
		obsolete++;
	}
	
	public long getObsolete()
	{
		return this.obsolete;
	}
	
	
}