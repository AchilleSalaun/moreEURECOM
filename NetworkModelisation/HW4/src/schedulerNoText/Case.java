package schedulerNoText;
import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Case extends Actor
{
	private LinkedList<Item> listeItems;
    private ArrayList<Case> exit;
    private ArrayList<Case> entrance = new ArrayList<Case>();
    private int capacity; 
    
    private boolean serving = false ;
	public void free()
	{
		serving = false ;
	}
	
	public void occupy()
	{
		serving = true ;
	}
	
	public boolean isServing()
	{
		return serving ;
	}
	
    /** Constructor **/
    public Case(int capacity)
    {
    	LinkedList<Item> liste = new LinkedList<Item>();
    	this.listeItems = liste ;
    	
    	ArrayList<Case> listeExit = new ArrayList<Case>();
    	this.exit = listeExit ;
    	
    	this.capacity = capacity ;
    }
    
    /************************************************************************************/
    /** capacity **/
	public int getCapacity()
	{
		return this.capacity;
	}

	public void setCapacity(int capacity2)
	{
		this.capacity = capacity2;
	}

	/************************************************************************************/
	/** listeItem **/
    public LinkedList<Item> getListeItems()
    {
    	return this.listeItems;
    }
    
    public Item getFirstItem()
	{
		Item firstItem = this.listeItems.peek();
		return firstItem;
	}
    
    /************************************************************************************/
    /** entrance **/
    
    public ArrayList<Case> getEntrance() 
    {
    	return this.entrance;
    }
    
    public void setEntree(ArrayList<Case> entrance)
    {
		this.entrance = entrance;
	}
    
    /************************************************************************************/
    /** exit **/
    public ArrayList<Case> getExit() 
    {
    	return this.exit;
    }
    
    public void setExit(ArrayList<Case> Exit)
    {
		this.exit = Exit;
	}
	
	public void linkExit(Case caseExit)
	{
		this.exit.add(caseExit);
		caseExit.getEntrance().add(this);
	}
   
	public int compareCase(Case case2)
	{
		if ((this.getListeItems()).size() < (case2.getListeItems()).size()){
			return -1;
		}
		else if ((this.getListeItems()).size() == (case2.getListeItems()).size()){
			return 0;
		}
		else 
			return 1;
	}
}
