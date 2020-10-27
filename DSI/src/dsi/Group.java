
package dsi;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Group {

    private String group_name;
    private String group_type;
    private int group_duration;
    private String group_meeting_time;
    private String meetingDates;
    private double enrollment_fee;
    private double down_payment;
    
    
    //group members
    private ArrayList<Adult> groupAdults = new ArrayList<Adult>();
    private String members;
    //private String [] membersArray;
    private ArrayList<Child> groupChildren = new ArrayList<Child>();
    
    //contains all children in database
    public static ArrayList<Group> allGroups = new ArrayList<Group>();
    public static ObservableList<String> groupNames = FXCollections.observableArrayList();
    
    //group names seperated by child and adult
    public static ObservableList<String> childGroupsAvail = FXCollections.observableArrayList();
    public static ObservableList<String> adultGroupsAvail = FXCollections.observableArrayList();
    
    //child ID info
    private int group_ID;
    public static int nextID;
    
    public Group(String name, String type, int duration, String meetingtime, double enroll_fee, double down_pmt, String meetingDates)
    {
        this.group_name = name;
        this.group_type = type;
        this.group_duration = duration;
        this.group_meeting_time = meetingtime;
        this.enrollment_fee = enroll_fee;
        this.down_payment = down_pmt;
        this.meetingDates = meetingDates;
        this.members = "none";
        
        this.group_ID = ++nextID;
        allGroups.add(this);
        groupNames.add(name);
    }
    
    public Group(int group_ID, String name, String type, int duration, String meetingtime, double enroll_fee, double down_pmt, String meetingDates, String members)
    {
        this.group_name = name;
        this.group_type = type;
        this.group_duration = duration;
        this.group_meeting_time = meetingtime;
        this.enrollment_fee = enroll_fee;
        this.down_payment = down_pmt;
        this.meetingDates = meetingDates;
        
        this.members = members;
        
        if(this.group_type.equals("Child"))
        {
            String [] membersArray = this.members.split(",");
            Child temp = null;
            for(int i=0; i<Child.allChildren.size(); i++)
            {
                temp = Child.allChildren.get(i);
                
                for(int k=0; k<membersArray.length; k++)
                {
                    if(membersArray[k].equals(temp.getChild_name()))
                        this.groupChildren.add(temp);
                }
            }
        }
        else if(this.group_type.equals("Adult"))
        {
            String [] membersArray = this.members.split(",");
            Adult temp = null;
            for(int i=0; i<Adult.allAdults.size(); i++)
            {
                temp = Adult.allAdults.get(i);
                
                for(int k=0; k<membersArray.length; k++)
                {
                    if(membersArray[k].equals(temp.getAdult_name()))
                        this.groupAdults.add(temp);
                }
            }
        }
        
        this.group_ID = group_ID;
        ++nextID;
        
        allGroups.add(this);
        groupNames.add(name);
    }
    
    public void addAdultMember(Adult ille)
    {
        if(this.members.equals("none"))
            this.members = "";
        
        this.groupAdults.add(ille);
        this.members += ille.getAdult_name() + ",";
    }
    
    public void addChildMember(Child ille)
    {
        if(this.members.equals("none"))
            this.members = "";
        
        this.groupChildren.add(ille);
        this.members += ille.getChild_name() + ",";
        
    }
    
    //adds a meeting date for the group
    public void addMeetingDate(String data)
    {
        this.meetingDates += data + " ";
    }
    
    public String getGroup_name() {
        return group_name;
    }

    public String getGroup_type() {
        return group_type;
    }

    public int getGroup_duration() {
        return group_duration;
    }

    public String getGroup_meeting_time() {
        return group_meeting_time;
    }

    public double getEnrollment_fee() {
        return enrollment_fee;
    }

    public double getDown_payment() {
        return down_payment;
    }

    public int getGroup_ID() {
        return group_ID;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public void setGroup_type(String group_type) {
        this.group_type = group_type;
    }

    public void setGroup_duration(int group_duration) {
        this.group_duration = group_duration;
    }

    public void setGroup_meeting_time(String group_meeting_time) {
        this.group_meeting_time = group_meeting_time;
    }

    public void setEnrollment_fee(double enrollment_fee) {
        this.enrollment_fee = enrollment_fee;
    }

    public void setDown_payment(double down_payment) {
        this.down_payment = down_payment;
    }
    
    public String getMembers() {
        return members;
    }

    public ArrayList<Adult> getGroupAdults() {
        return groupAdults;
    }

    public ArrayList<Child> getGroupChildren() {
        return groupChildren;
    }

    public void setMembers(String members) {
        this.members = members;
    }
    
    
    public String toString()
    {
        String groupinfo = "";

        groupinfo += "Group Name: " + this.group_name + "\n"
                + "Group Type: " + this.group_type + "\n"
                + "Group Duration: " + this.group_duration + " weeks\n"
                + "Meeting Time: " + this.group_meeting_time + "\n"
                + "Group Enrollment Fee: $" + this.enrollment_fee + "\n"
                + "Group Down Payment Required: $" + this.down_payment + "\n"
                + "-----------------------------\n";
        
        groupinfo += "Group Members: \n";
        groupinfo += "-----------------------------\n";

        String[] membersArray = this.members.split(",");

        for (int i = 0; i < membersArray.length; i++) {
            groupinfo += "  " + membersArray[i] + "\n";
        }
        

        
        groupinfo += "-----------------------------\n";
        groupinfo += "Meeting Dates: \n";
        groupinfo += "-----------------------------\n";
        String[] datesArray = this.meetingDates.split(" ");
        for(int y=0; y<datesArray.length; y++)
        {
            groupinfo += "  " + datesArray[y] + "\n";
        }

        return groupinfo;
    }
    
    
    
}
