package mains;
import java.util.ArrayList;
import java.util.Date;
import scheduler.Event;
import scheduler.MM1;
import scheduler.MG1_FCFS;
import scheduler.MG1_SJF;
import scheduler.Pit;
import scheduler.Scheduler;
import scheduler.Source;
import theory.Theory;

public class Main 
{
	public static void main(String[] args) 
	{			
		long duree = 5000 ; //duree is the number of events before the scheduler stops (it gives a better control on the calculation time than asking a simulation on a given interval of time)
		ArrayList<Double> experiment = new ArrayList<>() ;
		ArrayList<Double> theory = new ArrayList<>() ;
		
		/*for(int i=1 ; i<10 ; i++)
		{*/
			/** Creation of the system **/	
			double rho = .3 /*i*.1*/ ;
			double lambda = rho/((1.*98.+201.*2.)/100.);
		
			Source source = new Source(lambda) ;
			Pit pit = new Pit() ;		
			MG1_FCFS queue = new MG1_FCFS() ;
			MG1_FCFS queue2 = new MG1_FCFS() ;
		
			source.linkExit(queue) ;
			source.linkExit(queue2) ;
			queue.linkExit(pit);
			queue2.linkExit(pit);
		
			/*****************************************************************************************************/
			/** Creation of the scheduler **/		
			ArrayList<Source> sourceList = new ArrayList<Source>();
			sourceList.add(source);
		
			Scheduler scheduler = new Scheduler(sourceList) ;
		
			/*****************************************************************************************************/
			/** Simulation **/
			System.out.println("START");
					
			int ctr = 0;
				
			/** permanent **/
			do
			{
				System.out.println("****************************************************************************");
				System.out.println("Start loop n°"+ctr);
				scheduler.nextEvent();
				System.out.println("End loop : queue size = "+queue.getListeItems().size()+" / size scheduler = "+scheduler.size());
				ctr++ ;
			}
			while(scheduler.size()>0 && ctr<duree);
		
			scheduler.end(source);
		
			System.out.println("****************************************************************************");
			long esperance = pit.getTimeSum()/source.getN();
			double e = (double)(esperance)/Scheduler.SCALE ;
			double t = Theory.esperance_T_MG1_FCFS(rho) ;
			experiment.add(e) ;
			theory.add(t) ;
		
			System.out.println("STOP");
		/*}*/
	
		/*****************************************************************************************************/
		/** transitory **/
		/*for(int i=0 ; i<20
				; i++)
		{
			ctr++ ;
			System.out.println("******************************************************");
			System.out.println("Début boucle n°"+ctr);
			s = scheduler.size();
			scheduler.nextEvent();
			System.out.println("Fin boucle n°"+ctr+": taille puit = " + puit.getListeObjets().size()+" / taille poubelle = "+poubelle.getListeObjets().size());
		}*/
		
		for(int k=0 ; k<experiment.size() ; k++)
		{
			System.out.println(k+" "+experiment.get(k)+" "+theory.get(k)) ;
		}
		
	}
}
