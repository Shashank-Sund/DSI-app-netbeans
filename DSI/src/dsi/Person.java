/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsi;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author sunda
 */
public class Person {

    //person ID info
    private int person_ID;
    public static int nextID;
    
    private String first_name;
    private String last_name;
    private String person_type; //Child or Adult
    
    //child information (if child)
    private int child_grade;
    
    //contact information
    private String contact_1_name;
    private String contact_1_email;
    private String contact_1_phone;
    
    private String contact_2_name;
    private String contact_2_email;
    private String contact_2_phone;
    
    //group the person is in
    private Group personGroup;
    private int group_ID;
    private String enlisted_group_name;
    
    //how much of the enrollment fee the person has paid
    private double amount_paid;
    
    //accomplishments the person has done outside of DSI
    private String accomplishments;
    
    //DSI Awards the person has won
    public ArrayList<DSIAward> personDSIAwards = new ArrayList<DSIAward>();
    private String awardsWon;
    
    //contains all people in database
    public static ArrayList<Person> allPeople = new ArrayList<Person>();
    //contains the first and last names of all people in the database
    public static ObservableList<String> personNames = FXCollections.observableArrayList();
    
    //Adult constructor
    public Person(String name, String type, String cname1, String cemail1, String cphone1, String groupname, double amount_paid)
    {
        int space = name.indexOf(" ");
        this.first_name = name.substring(0, space);
        this.last_name = name.substring(space+1);
        this.child_grade = 0;

        //finds the group from allGroups arraylist based on the inputted group name
        this.enlisted_group_name = groupname;
        for(int i=0; i<Group.allGroups.size(); i++)
        {
            if(enlisted_group_name.equals(Group.allGroups.get(i).getGroup_name()))
                this.personGroup = Group.allGroups.get(i);
        }
        this.group_ID = personGroup.getGroup_ID();
        
        this.contact_1_name = cname1;
        this.contact_1_email = cemail1;
        this.contact_1_phone = cphone1;
        
        //initialize null values
        this.contact_2_name = "";
        this.contact_2_email = "";
        this.contact_2_phone = "";
        this.accomplishments = "";
        this.awardsWon = "";
        
        
        this.amount_paid = amount_paid;
        
        //creates primary key for object
        this.person_ID = ++nextID;
        //adds the current object to the master list of all Persons
        allPeople.add(this);
        //addst the name of this object to the master list of all Person names
        personNames.add(this.getPerson_name());
    }
    
    //child constructor
    public Person(String name, String type, String cname1, String cemail1, String cphone1, String groupname, double amount_paid, int grade)
    {
        int space = name.indexOf(" ");
        this.first_name = name.substring(0, space);
        this.last_name = name.substring(space+1);
        this.child_grade = grade;

        this.enlisted_group_name = groupname;
        for(int i=0; i<Group.allGroups.size(); i++)
        {
            if(enlisted_group_name.equals(Group.allGroups.get(i).getGroup_name()))
                this.personGroup = Group.allGroups.get(i);
        }
        this.group_ID = personGroup.getGroup_ID();
        
        //this.addChildToGroup(Group.allGroups.get(1));
        
        this.contact_1_name = cname1;
        this.contact_1_email = cemail1;
        this.contact_1_phone = cphone1;
        
        //initialize null values
        this.contact_2_name = "";
        this.contact_2_email = "";
        this.contact_2_phone = "";
        this.accomplishments = "";
        this.awardsWon = "";
        
        this.amount_paid = amount_paid;
        
        //creates primary key for object
        this.person_ID = ++nextID;
        //adds the current object to the master list of all Persons
        allPeople.add(this);
        //addst the name of this object to the master list of all Person names
        personNames.add(this.getPerson_name());
    }
    
    //loading existing people from sql database
    public Person(int person_ID, String name, int grade, String c1name, String c1email, String c1phone, String c2name, String c2email, String c2phone, double amtpaid, String awards, String acc, String groupname){
        
        int space = name.indexOf(" ");
        this.first_name = name.substring(0, space);
        this.last_name = name.substring(space+1);
        this.child_grade = grade;
        
        this.enlisted_group_name = groupname;
        for(int i=0; i<Group.allGroups.size(); i++)
        {
            if(enlisted_group_name.equals(Group.allGroups.get(i).getGroup_name()))
                this.personGroup = Group.allGroups.get(i);
        }
        this.group_ID = personGroup.getGroup_ID();
        
        this.contact_1_name = c1name;
        this.contact_1_email = c1email;
        this.contact_1_phone = c1phone;
        this.contact_2_name = c2name;
        this.contact_2_email = c2email;
        this.contact_2_phone = c2phone;
        
        this.awardsWon = awards;
        String [] awdnames = this.awardsWon.split(",");
        //if the award names from the regular array match any award in the master award list name then add them back to the award array list
        for(int j=0; j<awdnames.length; j++)
        {
            for(int i=0; i<DSIAward.allAwards.size(); i++)
            {
                if(awdnames[j].equals(DSIAward.allAwards.get(i).getAward_name()))
                {
                    if(this.personDSIAwards.contains(DSIAward.allAwards.get(i)))
                        continue;
                    else
                        this.personDSIAwards.add(DSIAward.allAwards.get(i));
                }
            }
            
        }
        
        this.accomplishments = acc;
        this.amount_paid = amtpaid;
      
        this.person_ID = person_ID;
        ++nextID;
        allPeople.add(this);
        personNames.add(this.getPerson_name());
    }

    //change the person's group
    public void changeGroup(String name)
    {
        
    }
    
    //add accomplishment
    public void addAccomplishment(String acc)
    {
        this.accomplishments += acc +",";
    }
    
    //add an award
    public void addAward(String award_name)
    {
        for(int i=0; i<DSIAward.allAwards.size(); i++)
        {
            //if the selected award name matches an award name in the award database add it to their awards won
            if(award_name.equals(DSIAward.allAwards.get(i).getAward_name()))
            {
                //if the person has already won the award once the only add it to the string
                if(this.personDSIAwards.contains(DSIAward.allAwards.get(i)))
                    this.awardsWon += DSIAward.allAwards.get(i).getAward_name() + ",";
                else
                {
                    this.personDSIAwards.add(DSIAward.allAwards.get(i));
                    this.awardsWon += DSIAward.allAwards.get(i).getAward_name() + ",";
                }
            }
        }
    }
    
    //general getters and setters for all attributes
    public String getPerson_name()
    {
        return first_name + " " + last_name;
    }
    
    public int getPerson_ID() {
        return person_ID;
    }

    public static int getNextID() {
        return nextID;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getPerson_type() {
        return person_type;
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

    public Group getPersonGroup() {
        return personGroup;
    }

    public int getGroup_ID() {
        return group_ID;
    }

    public String getEnlisted_group_name() {
        return enlisted_group_name;
    }

    public double getAmount_paid() {
        return amount_paid;
    }

    public String getAccomplishments() {
        return accomplishments;
    }

    public ArrayList<DSIAward> getPersonDSIAwards() {
        return personDSIAwards;
    }

    public String getAwardsWon() {
        return awardsWon;
    }

    public static ArrayList<Person> getAllPeople() {
        return allPeople;
    }

    public static ObservableList<String> getPersonNames() {
        return personNames;
    }

    public void setPerson_ID(int person_ID) {
        this.person_ID = person_ID;
    }

    public static void setNextID(int nextID) {
        Person.nextID = nextID;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setPerson_type(String person_type) {
        this.person_type = person_type;
    }

    public void setChild_grade(int child_grade) {
        this.child_grade = child_grade;
    }

    public void setContact_1_name(String contact_1_name) {
        this.contact_1_name = contact_1_name;
    }

    public void setContact_1_email(String contact_1_email) {
        this.contact_1_email = contact_1_email;
    }

    public void setContact_1_phone(String contact_1_phone) {
        this.contact_1_phone = contact_1_phone;
    }

    public void setContact_2_name(String contact_2_name) {
        this.contact_2_name = contact_2_name;
    }

    public void setContact_2_email(String contact_2_email) {
        this.contact_2_email = contact_2_email;
    }

    public void setContact_2_phone(String contact_2_phone) {
        this.contact_2_phone = contact_2_phone;
    }

    public void setPersonGroup(Group personGroup) {
        this.personGroup = personGroup;
    }

    public void setGroup_ID(int group_ID) {
        this.group_ID = group_ID;
    }

    public void setEnlisted_group_name(String enlisted_group_name) {
        this.enlisted_group_name = enlisted_group_name;
    }

    public void setAmount_paid(double amount_paid) {
        this.amount_paid = amount_paid;
    }

    public void setAccomplishments(String accomplishments) {
        this.accomplishments = accomplishments;
    }

    public void setPersonDSIAwards(ArrayList<DSIAward> personDSIAwards) {
        this.personDSIAwards = personDSIAwards;
    }

    public void setAwardsWon(String awardsWon) {
        this.awardsWon = awardsWon;
    }

    public static void setAllPeople(ArrayList<Person> allPeople) {
        Person.allPeople = allPeople;
    }

    public static void setPersonNames(ObservableList<String> personNames) {
        Person.personNames = personNames;
    }
    
    
    
}
