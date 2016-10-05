package schedulerNoText;
import java.util.Date;
    
public  class Event 
{ 
	private Actor Actor;
    private int action ;
    private Date time ;
    private Case currentCase;
    private Case targetCase ;
        
    public Event(Actor Actor,int action,Date time, Case currentCase, Case targetCase )
    {
      	this.Actor = Actor ;
       	this.action = action ;
       	this.time = time ;
       	this.currentCase = currentCase;
       	this.targetCase = targetCase ;
    }
        
    /********************************************************************************************************/
    /** getters & setters **/
    public Actor getActor()
    {
        return this.Actor;
    }
        
    public int getAction()
    {
        return this.action;
    } 
        
    public Date getDate()
    {
        return this.time;
    } 
        
    public Case getcaseActuelle() 
    {
      	return this.currentCase;
    }
        
    public Case getCaseCible()
    {
       	return this.targetCase;
    }
        
    public void setDate(Date date) 
    {
       	this.time=date;
    }
        
    public void setAction(int action) 
    {
       	this.action=action;
    }
        
    public void setActor(Actor Actor) 
    {
       	this.Actor=Actor;
    }
}

