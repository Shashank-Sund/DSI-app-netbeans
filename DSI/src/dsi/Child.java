
package dsi;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Child {

    private String child_first_name;
    private String child_last_name;
    private int child_grade;
    
    private String contact_1_name;
    private String contact_1_email;
    private String contact_1_phone;
    
    private String contact_2_name;
    private String contact_2_email;
    private String contact_2_phone;
    
    //group the child is in
    private Group childGroup;
    private int group_ID;
    private String enlisted_group_name;
    private String previous_groups = "";
    
    //how much of the enrollment fee the student has paid
    private double amount_paid;
    private double current_enrollment_fee;
    
    //accomplishments the child has done outside of DSI
    private String childAccomplishments;
    
    //DSI Awards the child has won
    public ArrayList<DSIAward> childDSIAwards = new ArrayList<DSIAward>();
    private String awardsWon;
    
    //contains all children in database
    public static ArrayList<Child> allChildren = new ArrayList<Child>();
    public static ObservableList<String> childNames = FXCollections.observableArrayList();
    
    //child ID info
    private int child_ID;
    public static int nextID;
    
    public Child(String name, int grade, String c1name, String c1email, String c1phone, String c2name, String c2email, String c2phone, double amtpaid, String groupname){
        
        int space = name.indexOf(" ");
        this.child_first_name = name.substring(0, space);
        this.child_last_name = name.substring(space+1);
        this.child_grade = grade;

        this.awardsWon = "none";
        this.childAccomplishments = "none";
        
        this.enlisted_group_name = groupname;
        for(int i=0; i<Group.allGroups.size(); i++)
        {
            if(enlisted_group_name.equals(Group.allGroups.get(i).getGroup_name()))
                this.childGroup = Group.allGroups.get(i);
        }
        this.group_ID = childGroup.getGroup_ID();
        this.current_enrollment_fee = this.childGroup.getEnrollment_fee();
        
        this.previous_groups += groupname + ",";
        
        this.contact_1_name = c1name;
        this.contact_1_email = c1email;
        this.contact_1_phone = c1phone;
        this.contact_2_name = c2name;
        this.contact_2_email = c2email;
        this.contact_2_phone = c2phone;
        
        this.amount_paid = amtpaid;
        
        this.child_ID = ++nextID;
        allChildren.add(this);
        childNames.add(this.getChild_name());
    }
    
    public Child(int child_ID, String name, int grade, String c1name, String c1email, String c1phone, String c2name, String c2email, String c2phone, double amtpaid, String awards, String acc, String groupname, String prev_groups){
        
        int space = name.indexOf(" ");
        this.child_first_name = name.substring(0, space);
        this.child_last_name = name.substring(space+1);
        this.child_grade = grade;
        this.awardsWon = awards;
        this.childAccomplishments = acc;
        
        this.enlisted_group_name = groupname;
        for(int i=0; i<Group.allGroups.size(); i++)
        {
            if(enlisted_group_name.equals(Group.allGroups.get(i).getGroup_name()))
                this.childGroup = Group.allGroups.get(i);
        }
        this.group_ID = childGroup.getGroup_ID();
        this.current_enrollment_fee = this.childGroup.getEnrollment_fee();
        
        this.previous_groups = prev_groups;
        
        this.contact_1_name = c1name;
        this.contact_1_email = c1email;
        this.contact_1_phone = c1phone;
        this.contact_2_name = c2name;
        this.contact_2_email = c2email;
        this.contact_2_phone = c2phone;
        
        this.amount_paid = amtpaid;
        
        this.child_ID = child_ID;
        ++nextID;
        allChildren.add(this);
        childNames.add(this.getChild_name());
    }
    
    public void addChildToGroup(Group ille)
    {
        this.childGroup = ille;
        this.group_ID = this.childGroup.getGroup_ID();
        this.enlisted_group_name = this.childGroup.getGroup_name();
        this.previous_groups += ille.getGroup_name() + ",";
    }
    
    //add an accomplishment to a child
    public void addAccomplishment (String acc)
    {
        this.childAccomplishments += acc + ", ";
    }
    
    //add a dsi award to a child
    public void addAward(DSIAward award)
    {
        this.childDSIAwards.add(award);
        this.awardsWon += award.getAward_name() + ", ";
    }
    
    //chang contact 1 info
    public void changeContact1info(String name, String email, String phone)
    {
        this.contact_1_name = name;
        this.contact_1_email = email;
        this.contact_1_phone = phone;         
    }
    
    //change contact 2 info
    public void changeContact2info(String name, String email, String phone)
    {
        this.contact_2_name = name;
        this.contact_2_email = email;
        this.contact_2_phone = phone;         
    }
    
    //set the amount paid for the student
    public void setAmountPaid(double amt)
    {
        this.amount_paid = amt;
    }
    
    public void specifyChildsGroup(Group ille)
    {
        this.childGroup = ille;
    }
    
    public String getChild_first_name() {
        return child_first_name;
    }

    public String getChild_last_name() {
        return child_last_name;
    }

    public double getCurrent_enrollment_fee() {
        return current_enrollment_fee;
    }
    
    public String getChild_name() {
        return this.child_first_name + " " + this.child_last_name;
    } 

    public int getChild_grade() {
        return child_grade;
    }

    public String getContact_1_name() {
        return contact_1_name;
    }

    public String getContact_1_email() {
        return contact_1_email;
    }

    public String getContact_1_phone() {
        return contact_1_phone;
    }

    public String getContact_2_name() {
        return contact_2_name;
    }

    public String getContact_2_email() {
        return contact_2_email;
    }

    public String getContact_2_phone() {
        return contact_2_phone;
    }

    public Group getChildGroup() {
        return childGroup;
    }

    public double getAmount_paid() {
        return amount_paid;
    }

    public String getChildAccomplishments() {
        return childAccomplishments;
    }

    public ArrayList<DSIAward> getChildDSIAwards() {
        return childDSIAwards;
    }

    public static ArrayList<Child> getAllChildren() {
        return allChildren;
    }

    public int getChild_ID() {
        return child_ID;
    }

    public int getGroup_ID() {
        return group_ID;
    }

    public String getEnlisted_group_name() {
        return enlisted_group_name;
    }

    public String getAwardsWon() {
        return awardsWon;
    }
    
    
    public String toString()
    {
        String childinfo ="";
                     
        childinfo += "Name: " + this.getChild_name() + "\n" +
                     "Current Group: " + this.enlisted_group_name + "\n"+
                     "------------------------------\n" +
                     "Grade: " + this.getChild_grade() + "\n" +
                     "Current Amount Paid: $" + this.amount_paid + "\n" +
                     "Contact 1 info: " + this.contact_1_name +", "+this.contact_1_email+", "+this.contact_1_phone+"\n"+
                     "Contact 2 info: " + this.contact_2_name +", "+this.contact_2_email+", "+this.contact_2_phone+"\n"+
                     "------------------------------\n";
        
        childinfo += "Accomplishments: \n";
        childinfo += "------------------------------\n";
        childinfo += "  " + this.childAccomplishments + "\n";
        
        //add previous groups display
        
        childinfo += "------------------------------\n";
        childinfo += "DSI Awards won: \n";
        childinfo += "------------------------------\n";
        
        String [] awardsArray = this.awardsWon.split(",");
        for(int i=0;i<awardsArray.length;i++)
        {
            childinfo += "  " + awardsArray[i] + "\n";
        }
        
        return childinfo;
    }
    
    
}
