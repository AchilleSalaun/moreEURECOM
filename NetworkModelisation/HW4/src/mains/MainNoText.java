package mains;
import java.util.ArrayList;

import schedulerNoText.MG1_FCFS;
import schedulerNoText.MG1_SJF;
import schedulerNoText.Pit;
import schedulerNoText.Scheduler;
import schedulerNoText.Source;
import theory.Theory;
	
public class MainNoText 
{	
	public static void main(String[] args)
	{
		long duree =  19*1000000000 ; //duree is the number of events before the scheduler stops (it gives a better control on the calculation time than asking a simulation on a given interval of time)
		ArrayList<Double> experiment = new ArrayList<>() ;
		ArrayList<Double> theory = new ArrayList<>() ;
	
		for(int i=1 ; i<10 ; i++)
		{
			System.out.println(i);
			/** Creation of the system **/	
			double rho = i*.1 ;
			double lambda = rho/((1.*98.+201.*2.)/100.);
	
			Source source = new Source(lambda) ;
			Pit pit = new Pit() ;		
			MG1_SJF queue = new MG1_SJF() ;
			/*MG1_FCFS queue = new MG1_FCFS() ;*/
	
			source.linkExit(queue) ;
			queue.linkExit(pit);
	
			/*****************************************************************************************************/
			/** Creation of the scheduler **/		
			ArrayList<Source> sourceList = new ArrayList<Source>();
			sourceList.add(source);
	
			Scheduler scheduler = new Scheduler(sourceList) ;
	
			/*****************************************************************************************************/
			/** Simulation **/
				
			int ctr = 0;
			
			/** permanent **/
			do
			{
				scheduler.nextEvent();
				ctr++ ;
			}
			while(scheduler.size()>0 && ctr<duree);
	
			scheduler.end(source);
			long esperance = pit.getTimeSum()/source.getN();
			double e = (double)(esperance)/Scheduler.SCALE ;
			double t = Theory.esperance_T_MG1_SJF(rho) ;
			/*double t = Theory.esperance_T_MG1_FCFS(rho) ;*/
			experiment.add(e) ;
			theory.add(t) ;
		}

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
			System.out.println((k+1)*.1+" "+experiment.get(k)+" "+theory.get(k)) ;
		}
	}
}

