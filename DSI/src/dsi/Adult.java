
package dsi;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Adult {

    private String adult_first_name;
    private String adult_last_name;
    private String email;
    private String phone;
    
    //how much of the enrollment fee the student has paid
    private double amount_paid;
    private double current_enrollment_fee;
    
    //group the adult is in
    private Group adultGroup = null;
    private int group_ID;
    private String enlisted_group_name;
    private String previous_groups = "";
    
    //accomplishments the adult has done outside of DSI
    private String adultAccomplishments;
    
    //DSI Awards the adult has won
    public ArrayList<DSIAward> adultDSIAwards = new ArrayList<DSIAward>();
    private String awardsWon;

    //contains all adults in database
    public static ArrayList<Adult> allAdults = new ArrayList<Adult>();
    public static ObservableList<String> adultNames = FXCollections.observableArrayList();
    
    //child ID info
    private int adult_ID;
    public static int nextID;
    
    public Adult(String name, String email, String phone, double amtpaid, String groupname){
        
        int space = name.indexOf(" ");
        this.adult_first_name = name.substring(0, space);
        this.adult_last_name = name.substring(space+1);
        this.adultAccomplishments = "none";
        
        //this.addAdultToGroup(Group.allGroups.get(0));
        this.enlisted_group_name = groupname;
        for(int i=0; i<Group.allGroups.size(); i++)
        {
            if(enlisted_group_name.equals(Group.allGroups.get(i).getGroup_name()))
                this.adultGroup = Group.allGroups.get(i);
        }
        this.group_ID = adultGroup.getGroup_ID();
        this.current_enrollment_fee = this.adultGroup.getEnrollment_fee();
        
        this.previous_groups += groupname + ",";
        
        this.email = email;
        this.phone = phone;
        
        this.amount_paid = amtpaid;
        
        this.adult_ID = ++nextID;
        allAdults.add(this); 
        adultNames.add(this.getAdult_name());
    }
    
    public Adult(int adult_ID, String name, String email, String phone, double amtpaid, int group_ID, String acc, String group_name, String prev_groups){
        
        int space = name.indexOf(" ");
        this.adult_first_name = name.substring(0, space);
        this.adult_last_name = name.substring(space+1);
        
        this.email = email;
        this.phone = phone;
        this.adultAccomplishments = acc;
        this.group_ID = group_ID;
        
        this.enlisted_group_name = group_name;
        for(int i=0; i<Group.allGroups.size(); i++)
        {
            if(enlisted_group_name.equals(Group.allGroups.get(i).getGroup_name()))
                this.adultGroup = Group.allGroups.get(i);
        }
        this.group_ID = adultGroup.getGroup_ID();
        this.current_enrollment_fee = this.adultGroup.getEnrollment_fee();
        
        this.previous_groups = prev_groups;
        
        this.amount_paid = amtpaid;
        
        this.adult_ID = adult_ID;
        ++nextID;
        allAdults.add(this); 
        adultNames.add(this.getAdult_name());
    }
    
    public void setAmtPaid(double amt)
    {
        this.amount_paid = amt;
    }
    
    public void addAdultToGroup (Group ille)
    {
        this.adultGroup = ille;
        this.group_ID = this.adultGroup.getGroup_ID();
        this.enlisted_group_name = this.adultGroup.getGroup_name();
        this.previous_groups += ille.getGroup_name() + ",";
    }
    
    public void addAccomplishment(String acc)
    {
        this.adultAccomplishments += acc + " ";
    }

    public String getAdult_first_name() {
        return adult_first_name;
    }

    public String getAdult_last_name() {
        return adult_last_name;
    }
    
    public String getAdult_name() {
        return adult_first_name + " " + adult_last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public double getAmount_paid() {
        return amount_paid;
    }

    public int getAdult_ID() {
        return adult_ID;
    }

    public static int getNextID() {
        return nextID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public Group getAdultGroup() {
        return adultGroup;
    }

    public int getGroup_ID() {
        return group_ID;
    }

    public String getAdultAccomplishments() {
        return adultAccomplishments;
    }

    public String getEnlisted_group_name() {
        return enlisted_group_name;
    }
    
    public void addAward(DSIAward award)
    {
        this.adultDSIAwards.add(award);
        this.awardsWon += award.getAward_name() + ", ";
    }

    public String getAwardsWon() {
        return awardsWon;
    }

    public double getCurrent_enrollment_fee() {
        return current_enrollment_fee;
    }
    
    
    public String toString()
    {
        String adultInfo = "";

            adultInfo += "Name: " + this.getAdult_name() + "\n" +
                         "Current Group: " + this.enlisted_group_name + "\n" +
                         "------------------------------\n" +
                         "Amount Paid: $" + this.getAmount_paid() + "\n" +
                         "Email: " + this.getEmail() + "\n" +
                         "Phone: " + this.getPhone() + "\n";
        
        adultInfo += "------------------------------\n";
        adultInfo += "Accomplishments: \n";
        adultInfo += "------------------------------\n";
        adultInfo += "  " + this.adultAccomplishments + "\n";
         
        adultInfo += "------------------------------\n";
        adultInfo += "Previous Groups: \n";
        
        String [] prevArray = this.previous_groups.split(",");
        for(int i = 0; i<prevArray.length;i++)
        {
            adultInfo += "  " + prevArray[i] + "  " + (i+1);
        }
            
        return adultInfo;
    }
    
    
    
}
