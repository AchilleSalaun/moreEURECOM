package schedulerNoText;

import java.util.ArrayList;
import java.util.Comparator;

public class MG1_SJF extends MG1_FCFS
{	
	@Override
	public Item getFirstItem()
	{
		ArrayList<Item> small = new ArrayList<>() ;
		ArrayList<Item> big = new ArrayList<>() ;
		
		for(int i=0 ; i<this.getListeItems().size() ; i++)
		{
			Item item = this.getListeItems().get(i) ;
			if(item.getTimeToBeServed()==1000)
			{
				small.add(item) ;
			}
			else
			{
				big.add(item) ;
			}
		}
		
		if(small.isEmpty())
		{
			return big.get(0) ;
		}
		else
		{
			return small.get(0) ;
		}
		/*ArrayList<Long> list = new ArrayList<>()  ;
		for(Item i : this.getListeItems())
		{
			list.add(i.getTimeToBeServed());
		}
			
		Comparator<? super Long> c = new Comparator<Long>() 
		{
     		@Override
			public int compare(Long o1, Long o2) 
     		{
				return Long.compare(o1, o2) ;
			}
        }; 
		list.sort(c);
		
		long reference = list.get(0) ;
		
		System.out.println(list) ;
		System.out.println(this.getListeItems()) ;
		
		for(int i=0 ;  i<this.getListeItems().size(); i++)
		{
			Item item = this.getListeItems().get(i) ;
			System.out.println(item);
			if(item.getTimeToBeServed()==reference)
			{
				System.out.println("return "+item+" ("+item.getTimeToBeServed()+")") ;
				return item ;	
			}
		}
		return null;*/
	}
}
