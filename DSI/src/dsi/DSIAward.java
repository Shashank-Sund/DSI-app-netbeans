
package dsi;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DSIAward {

    public String award_name;
    public String award_description;
    
    private String adults_who_won;
    private String children_who_won;
    public ArrayList<Adult> adultWinners = new ArrayList<Adult>();
    public ArrayList<Child> childWinners = new ArrayList<Child>();
    
    private int award_ID;
    public static int nextID;
    
    //contains all awards offered by DSI
    public static ArrayList<DSIAward> allAwards = new ArrayList<DSIAward>();
    public static ObservableList<String> awardNames = FXCollections.observableArrayList();
    
    public DSIAward(String name, String desc)
    {
        this.award_name = name;
        this.award_description = desc;
        
        this.adults_who_won = "none";
        this.children_who_won = "none";
        
        this.award_ID = ++nextID;
        
        allAwards.add(this);
        awardNames.add(name);
    }
    
    public DSIAward(int id, String name, String desc, String adults, String childs)
    {
        this.award_name = name;
        this.award_description = desc;
        this.adults_who_won = adults;
        this.children_who_won = childs;
        
        /*//populates award winners array for adults
        String[] adultArray = this.adults_who_won.split(",");
        for(int i=0; i<Adult.allAdults.size(); i++)
        {
            for(int j=0; j<adultArray.length; j++)
            {
                if(Adult.allAdults.get(i).getAdult_name().equals(adultArray[j]))
                    this.adultWinners.add(Adult.allAdults.get(i));
            }
        }
        
        //populates award winners array for children
        String[] childArray = this.children_who_won.split(",");
        for(int i=0; i<Child.allChildren.size(); i++)
        {
            for(int j=0; j<childArray.length; j++)
            {
                if(Child.allChildren.get(i).getChild_name().equals(childArray[j]))
                    this.childWinners.add(Child.allChildren.get(i));
            }
        }*/
        
        this.award_ID = id;
        ++nextID;
        
        allAwards.add(this);
        awardNames.add(name);
    }

    public void addAdultWinner(Adult ille)
    {
        for(int i=0; i<this.adults_who_won.length(); i++)
        {
            if(this.adults_who_won.equals("none"))
                this.adults_who_won = "";
        }
        
        this.adultWinners.add(ille);
        this.adults_who_won += ille.getAdult_name() + ",";
    }
    
    public void addChildWinner(Child ille)
    {
        if(this.children_who_won.equals("none"))
            this.children_who_won = "";
        
        this.childWinners.add(ille);
        this.children_who_won += ille.getChild_name() + ",";
    }
    
    public String getAward_name() {
        return award_name;
    }

    public String getAward_description() {
        return award_description;
    }

    public void setAward_name(String award_name) {
        this.award_name = award_name;
    }

    public void setAward_description(String award_description) {
        this.award_description = award_description;
    }

    public int getAward_ID() {
        return award_ID;
    }

    public String getAdults_who_won() {
        return adults_who_won;
    }

    public String getChildren_who_won() {
        return children_who_won;
    }
    
    
    public String toString()
    {
        
        String awardinfo = "";
        awardinfo += "DSI Award: " + this.award_name + "\n" +
                     "Description: " + this.award_description + "\n\n" +
                     "Adult Winners: " + this.adults_who_won + "\n\n" +
                     "Child Winners: " + this.children_who_won;
        return awardinfo;
    }
    
    
}
