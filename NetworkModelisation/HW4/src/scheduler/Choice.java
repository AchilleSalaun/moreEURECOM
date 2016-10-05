package scheduler;
import java.util.ArrayList;
import java.util.Random;

public class Choice 
{
	/** Génération aléatoire **/
	private static Random alea = new Random();
	
	public static double exponentielle(double lambda)
	{
		
		return -1/lambda*Math.log(1 - alea.nextDouble());
	}
	
	/*****************************************************************************************************/
	/** Choice **/
	public static int ChoiceCase(int i,ArrayList<Case> liste)
	{
		switch(i)
		{
			case 0: return Choice.loiUniforme(liste) ;// à partir d'une loi uniforme
	  		case 1: return Choice.moinsRemplie(liste) ; //prendre la case la moins remplie
	  		default : return 0 ;//prendre le premier (ordre ???)
		}
	}

	private static int moinsRemplie(ArrayList<Case> liste) 
	{
		// TODO Auto-generated method stub
		return 0 ;
	}

	public static int loiUniforme(ArrayList<Case> liste)
	{
		double random = alea.nextDouble();
		int sizeListe = liste.size();
		random = random*sizeListe ;
		return (int) random ;
	}
	
	public int compareSortie(ArrayList<Case> liste)
	{
		int fileMin = 0;
		for (int i=0; i<liste.size(); i++){
			for (int j=i+1; j<liste.size(); j++)
			{
				if ( !(liste.get(i)instanceof Pit) 
						&& !(liste.get(j)instanceof Pit) 
						&& (liste.get(i).compareCase(liste.get(j)) <= 0 )
						&& (liste.get(i).compareCase(liste.get(fileMin)) <= 0))
				{
					fileMin = i;
				}
			}
		}
		return fileMin;
	}
}
