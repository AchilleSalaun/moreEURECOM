package schedulerNoText;
import java.util.Comparator;

public class EventComparator implements Comparator<Event> 
{
	@Override
	public int compare(Event e1, Event e2) 
	{
		//Be CAREFUL to the order : we want to get the EARLIEST event
		return e1.getDate().compareTo(e2.getDate());
	}
}
