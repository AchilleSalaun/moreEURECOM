package theory;

public class Theory 
{
	public final static double E_S = (1.*.98+201.*.02) ;
	public final static double E_S2 = (Math.pow(1.,2))*.98+(Math.pow(201., 2))*.02;

	public static double esperance_T_MM1(double lambda, double mu)
	{
		double rho = lambda/mu ;
		return (rho/(1-rho))*(1/lambda) ;
	}
	
	public static double esperance_T_MG1_FCFS(double rho)
	{		
		return E_S+.5*(rho/(1-rho))*E_S2/E_S ;
	}
	
	public static double esperance_T_MG1_SJF(double rho)
	{
		double lambda = rho/E_S ;
		double rho_star = .98*lambda*1+.02*rho ;
		return E_S+.5*(rho/(1-rho_star))*E_S2/E_S ;
	}
}
