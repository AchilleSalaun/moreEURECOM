package mains;
import scheduler.Choice;
import scheduler.Item;
import scheduler.MG1_SJF;
import scheduler.Scheduler;
import theory.Theory;

public class Test 
{
	public static void main(String[] args) 
	{
		/*double lambda = 1./1000. ;
		double mu = 1./1. ;
		
		System.out.println(Theory.esperance_T_MM1(lambda, mu));
		System.out.println("############################################################################") ;
		for(int k=1 ; k<11 ; k++ )
		{
			lambda = k/10. ;
			System.out.println("lambda = "+lambda);
			for(int i=0 ; i<20 ; i++)
			{
				double l = Choice.exponentielle(lambda) ;
				System.out.println(l+" --> "+(long)(l*Scheduler.SCALE)) ;
			}
		}*/
		
		/*MG1_SJF queue = new MG1_SJF() ;
		
		Item item1 = new Item(queue, 2) ;
			System.out.println("item1 : "+item1) ;
		Item item2 = new Item(queue, 2) ;
			System.out.println("item2 : "+item2) ;
		Item item3 = new Item(queue, 2) ;
			System.out.println("item3 : "+item3) ;

		
		queue.getListeItems().add(item2) ;
		queue.getListeItems().add(item1) ;
		queue.getListeItems().add(item3) ;
		
		System.out.println("first : "+queue.getFirstItem()) ;*/
		
		double rho = .3 ;
		double E = Theory.esperance_T_MG1_SJF(rho) ;
		System.out.println(E+" < "+Theory.esperance_T_MG1_FCFS(rho)) ;
		System.out.println(Theory.E_S +" "+Theory.E_S2 );
		
		double A = Theory.E_S2/(2*Theory.E_S) ;
		System.out.println(A) ;
		double B =  .3/(1-.3);
		System.out.println(B) ;
		System.out.println(A*B) ;
	}
}
