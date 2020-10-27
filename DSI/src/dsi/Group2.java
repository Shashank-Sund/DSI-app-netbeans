
package dsi;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Group2 {

    private String group_name;
    private String group_type;
    private int group_duration;
    private String group_meeting_time;
    private String meetingDates;
    private double enrollment_fee;
    private double down_payment;
    
    //group members
    private ArrayList<Person> groupMembers = new ArrayList<Person>();
    private String members;
    private String [] membersArray;
    
    //contains all groups and group names in database
    public static ArrayList<Group2> allGroups = new ArrayList<Group2>();
    public static ObservableList<String> groupNames = FXCollections.observableArrayList();
    
    //group names seperated by child and adult
    public static ObservableList<String> childGroupsAvail = FXCollections.observableArrayList();
    public static ObservableList<String> adultGroupsAvail = FXCollections.observableArrayList();
    
    //group ID info
    private int group_ID;
    public static int nextID;
    
    public Group2(String name, String type, int duration, String meetingtime, double enroll_fee, double down_pmt, String meetingDates)
    {
        this.group_name = name;
        this.group_type = type;
        this.group_duration = duration;
        this.group_meeting_time = meetingtime;
        this.enrollment_fee = enroll_fee;
        this.down_payment = down_pmt;
        this.meetingDates = meetingDates;
        this.members = "";
        
        this.group_ID = ++nextID;
        allGroups.add(this);
        groupNames.add(name);
    }
    
    /*public Group2(String name, String type, int duration, String meetingtime, double enroll_fee, double down_pmt, String meetingDates)
    {
        this.group_name = name;
        this.group_type = type;
        this.group_duration = duration;
        this.group_meeting_time = meetingtime;
        this.enrollment_fee = enroll_fee;
        this.down_payment = down_pmt;
        this.meetingDates = meetingDates;
        this.members = "";
        
        this.group_ID = ++nextID;
        allGroups.add(this);
        groupNames.add(name);
    }*/
    
}
