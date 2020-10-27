/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsi;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.*;
import oracle.jdbc.pool.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

/**
 * FXML Controller class
 *
 * @author sunda
 */
public class MenuFXMLController implements Initializable {
    
    //used for database
    ResultSet dbResults;
    Connection dbConn;
    Statement commStmt;
    PreparedStatement pst;
    
    @FXML
    private AnchorPane menuPane;
    
    @FXML
    private TabPane mainDbPane;
    @FXML
    private Tab homeTab;
    @FXML
    private AnchorPane homeTabPane;
    @FXML
    private ComboBox<String> adultCB;
    @FXML
    private Button viewAdultInfoBtn;
    @FXML
    private Label selectAdultLbl;
    @FXML
    private ComboBox<String> childCB;
    @FXML
    private ComboBox<String> groupCB;
    @FXML
    private ComboBox<String> awardCB;
    @FXML
    private Button viewChildInfoBtn;
    @FXML
    private Button viewGroupInfoBtn;
    @FXML
    private Button viewAwardInfoBtn;
    @FXML
    private Label selectChildLbl;
    @FXML
    private Label selectGroupLbl;
    @FXML
    private Label selectAwardLbl;
    @FXML
    private Button incPmtAdultsBtn;
    @FXML
    private Button incPmtChildBtn;
    @FXML
    private Label incompletePmtLbl;
    @FXML
    private AnchorPane addNotesTabPane;
    @FXML
    private TextArea homeOutPutBox;
    @FXML
    private AnchorPane mainTextAreaPane;
    @FXML
    private AnchorPane incPmtPane;
    @FXML
    private AnchorPane individualViewsPane;
    @FXML
    private Label incompletePmtLbl1;
    
    //Create individual Object Buttons
    @FXML
    private Button createAdultBtn;
    @FXML
    private Button createChildBtn;
    @FXML
    private Button createGroupBtn;
    @FXML
    private Button createAwardBtn;
    @FXML
    private Tab addTab;
    @FXML
    private Label addChildGrouplbl;
    @FXML
    private Label addAdultGrpLbl;
    @FXML
    private ComboBox<String> childGroupCB;
    @FXML
    private ComboBox<String> groupChildCB;
    @FXML
    private ComboBox<String> adultGroupCB;
    @FXML
    private ComboBox<String> groupAdultCB;
    @FXML
    private Label addChildAwdLbl;
    @FXML
    private Label addAdultAwdLbl;
    @FXML
    private ComboBox<String> childAwdCB;
    @FXML
    private ComboBox<String> awdChildCB;
    @FXML
    private ComboBox<String> adultAwdCB;
    @FXML
    private ComboBox<String> awdAdultCB;
    @FXML
    private Button childGroupBtn;
    @FXML
    private Button adultGroupBtn;
    @FXML
    private Button childAwdBtn;
    @FXML
    private Button adultAwdBtn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        createTables();
        loadGroupData();
        loadAdultData();
        loadChildData();
        loadAwardData();
        
        //Home Tab Combo Boxes
        this.adultCB.setItems(Adult.adultNames);
        this.childCB.setItems(Child.childNames);
        this.groupCB.setItems(Group.groupNames);
        this.awardCB.setItems(DSIAward.awardNames);

        //Add Tab Combo Boxes
        this.childGroupCB.setItems(Child.childNames);
        this.adultGroupCB.setItems(Adult.adultNames);
        this.childAwdCB.setItems(Child.childNames);
        this.adultAwdCB.setItems(Adult.adultNames);
        this.awdChildCB.setItems(DSIAward.awardNames);
        this.awdAdultCB.setItems(DSIAward.awardNames);
        
        for (int i=0; i<Group.allGroups.size(); i++)
        {
            if(Group.allGroups.get(i).getGroup_type().equals("Adult"))
                Group.adultGroupsAvail.add((Group.allGroups.get(i).getGroup_name()));
            else
                Group.childGroupsAvail.add((Group.allGroups.get(i).getGroup_name()));
        }
        
        this.groupChildCB.setItems(Group.childGroupsAvail);
        this.groupAdultCB.setItems(Group.adultGroupsAvail);
        
        //System.out.println();
    }

    public void sendDBCommand(String query)
    {
        String URL = "jdbc:oracle:thin:@localhost:1521:XE";
        String userID = "system";
        String userPassword = "Shaiman9797!";
        OracleDataSource ds;
        System.out.println(query);
        try
        {         
        ds = new OracleDataSource();
        
        ds.setURL(URL);
        
        dbConn = ds.getConnection(userID, userPassword);
        
        commStmt = dbConn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        
        dbResults = commStmt.executeQuery(query);
        }
        catch (SQLException e)
        {
            System.out.println(e.toString());
        }
    }
    
    //handles viewing single adult info on homepage
    @FXML
    private void handleViewAdultInfoBtn(ActionEvent event) 
    {
        homeOutPutBox.clear();
        
        if(adultCB.getSelectionModel().getSelectedIndex() == -1) 
            homeOutPutBox.setText("Please select a an Adult!");
        else{
            String nameSelect = adultCB.getSelectionModel().getSelectedItem();
        
            for(int i=0; i<Adult.allAdults.size(); i++)
            {
                if (Adult.allAdults.get(i).getAdult_name().equals(nameSelect))
                    homeOutPutBox.setText(Adult.allAdults.get(i).toString());
                else{}   
            }
        }
    }

    //handles viewing single child info on homepage
    @FXML
    private void handleViewChildInfoBtn(ActionEvent event) 
    {
        homeOutPutBox.clear();
        
        if(childCB.getSelectionModel().getSelectedIndex() == -1) 
            homeOutPutBox.setText("Please select a Child!");
        else{
            String nameSelect = childCB.getSelectionModel().getSelectedItem();
        
            for(int i=0; i<Child.allChildren.size(); i++)
            {
                if (Child.allChildren.get(i).getChild_name().equals(nameSelect))
                    homeOutPutBox.setText(Child.allChildren.get(i).toString());
                else{}   
            }
        }
    }

    //handles viewing sing group info on homepage
    @FXML
    private void handleViewGroupInfoBtn(ActionEvent event) 
    {
        homeOutPutBox.clear();
        
        if(groupCB.getSelectionModel().getSelectedIndex() == -1) 
            homeOutPutBox.setText("Please select a Group!");
        else{
            String nameSelect = groupCB.getSelectionModel().getSelectedItem();
        
            for(int i=0; i<Group.allGroups.size(); i++)
            {
                if (Group.allGroups.get(i).getGroup_name().equals(nameSelect))
                    homeOutPutBox.setText(Group.allGroups.get(i).toString());
                else{}   
            }
        }
    }

    //handles viewing single award info on home page
    @FXML
    private void handleViewAwardInfoBtn(ActionEvent event) 
    {
        homeOutPutBox.clear();
        
        if(awardCB.getSelectionModel().getSelectedIndex() == -1) 
            homeOutPutBox.setText("Please select an Award!");
        else{
            String nameSelect = awardCB.getSelectionModel().getSelectedItem();
        
            for(int i=0; i<DSIAward.allAwards.size(); i++)
            {
                if (DSIAward.allAwards.get(i).getAward_name().equals(nameSelect))
                    homeOutPutBox.setText(DSIAward.allAwards.get(i).toString());
                else{}   
            }
        }
    }

    //handle incomplete payments view for adults
    @FXML
    private void handleIncAdultsBtn(ActionEvent event) 
    {
        homeOutPutBox.clear();
        String incAdults = "";
        
        for(int i=0;i<Adult.allAdults.size(); i++)
        {
            if(Adult.allAdults.get(i).getAdultGroup() != null)
            {
                if(Adult.allAdults.get(i).getAdultGroup().getEnrollment_fee() > Adult.allAdults.get(i).getAmount_paid())
                    homeOutPutBox.appendText(Adult.allAdults.get(i).getAdult_name() + ", " + Adult.allAdults.get(i).getPhone() + ", " + Adult.allAdults.get(i).getEmail()+ "\n");
            }
        } 
    }

    //handle incomplete payments view for children
    @FXML
    private void handleIncChildBtn(ActionEvent event) 
    {
        homeOutPutBox.clear();
        
        for(int i=0;i<Child.allChildren.size(); i++)
        {
            if(Child.allChildren.get(i).getChildGroup() != null)
            {
                if(Child.allChildren.get(i).getChildGroup().getEnrollment_fee() > Child.allChildren.get(i).getAmount_paid())
                    homeOutPutBox.appendText(Child.allChildren.get(i).getChild_name() + ", " + Child.allChildren.get(i).getContact_1_phone() + ", " + Child.allChildren.get(i).getContact_1_email()+ "\n");
            }
        }
    }

    @FXML
    private void handleCreateAdultBtn(ActionEvent event) 
    {
        try
        {
            //loads create adult window
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("createAdult.fxml"));
            Parent root1 = (Parent) fxmlloader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        }
        catch(Exception ex){}
    }

    @FXML
    private void handleCreateChildBtn(ActionEvent event) 
    {
        try
        {
            //loads create adult window
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("createChild.fxml"));
            Parent root1 = (Parent) fxmlloader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        }
        catch(Exception ex){} 
    }

    @FXML
    private void handleCreateGroupBtn(ActionEvent event) 
    {
        try
        {
            //loads create adult window
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("createGroup.fxml"));
            Parent root1 = (Parent) fxmlloader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        }
        catch(Exception ex){}
    }

    @FXML
    private void handleCreateAwardBtn(ActionEvent event) 
    {
        try
        {
            //loads create adult window
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("createAward.fxml"));
            Parent root1 = (Parent) fxmlloader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        }
        catch(Exception ex){}
    }
    
     public void createTables()
    {
        String crGroup = "CREATE TABLE STUDENTGROUP(" 
                +"group_ID INT NOT NULL," 
                +"group_name VARCHAR(50)," 
                +"group_type VARCHAR(30),"
                +"group_duration INTEGER,"
                +"group_meeting_time VARCHAR(30),"
                +"enrollment_fee NUMERIC,"
                +"down_payment NUMERIC,"
                +"meeting_dates VARCHAR(120),"
                +"members VARCHAR(120),"
                +"CONSTRAINT PK_GROUP PRIMARY KEY (group_ID))";
        sendDBCommand(crGroup);
        
        String crAdult = "CREATE TABLE STUDENTADULT(" 
                +"adult_ID INT NOT NULL,"
                +"group_ID INT," 
                +"adult_first_name VARCHAR(50)," 
                +"adult_last_name VARCHAR(30),"
                +"email VARCHAR(30),"
                +"phone VARCHAR(30),"
                +"amount_paid NUMERIC,"
                +"adult_accomplishments VARCHAR(120),"
                +"enlisted_group_name VARCHAR(20),"
                +"previous_groups VARCHAR(20),"
                +"CONSTRAINT PK_ADULT PRIMARY KEY (adult_ID),"
                +"CONSTRAINT FK_ADULT FOREIGN KEY (group_ID) REFERENCES STUDENTGROUP (group_ID))";
        sendDBCommand(crAdult);
        
        String crChild = "CREATE TABLE STUDENTCHILD(" 
                +"child_ID INT NOT NULL,"
                +"group_ID INT," 
                +"child_first_name VARCHAR(50)," 
                +"child_last_name VARCHAR(30),"
                +"contact_1_name VARCHAR(30),"
                +"contact_1_email VARCHAR(30),"
                +"contact_1_phone VARCHAR(30),"
                +"contact_2_name VARCHAR(30),"
                +"contact_2_email VARCHAR(30),"
                +"contact_2_phone VARCHAR(30),"
                +"amount_paid NUMERIC,"
                +"awards_won VARCHAR(120),"
                +"child_accomplishments VARCHAR(120),"
                +"child_grade INTEGER,"
                +"enlisted_group_name VARCHAR(20),"
                +"previous_groups VARCHAR(20),"
                +"CONSTRAINT PK_CHILD PRIMARY KEY (child_ID),"
                +"CONSTRAINT FK_CHILD FOREIGN KEY (group_ID) REFERENCES STUDENTGROUP (group_ID))";
        sendDBCommand(crChild);
        
        String crAward = "CREATE TABLE STUDENTAWARD(" 
                +"award_ID INT NOT NULL," 
                +"award_name VARCHAR(50)," 
                +"award_description VARCHAR(130),"
                +"adult_winners VARCHAR(200)," 
                +"child_winners VARCHAR(200),"
                +"CONSTRAINT PK_AWARD PRIMARY KEY (award_ID))";
        sendDBCommand(crAward);
    }

     public void loadGroupData()
     {
        String sqlQuery = "SELECT * FROM SYSTEM.STUDENTGROUP";
        sendDBCommand(sqlQuery);
        
        try
        {
            while(dbResults.next())
            {                
               int group_ID = dbResults.getInt(1);
               String group_name = dbResults.getString(2);
               String group_type = dbResults.getString(3);
               int group_duration = dbResults.getInt(4);
               String group_meeting_time = dbResults.getString(5);
               double enrollment_fee = dbResults.getDouble(6);
               double down_payment = dbResults.getDouble(7);
               String group_meeting_dates = dbResults.getString(8);
               String members = dbResults.getString(9);
               
               //call constructor
               Group gr = new Group(group_ID, group_name, group_type, group_duration, group_meeting_time, enrollment_fee, down_payment, group_meeting_dates, members);
               
               
            }     
        }
        catch (SQLException sqle){}
     }
     
     public void loadAdultData()
     {
        String sqlQuery = "SELECT * FROM SYSTEM.STUDENTADULT";
        sendDBCommand(sqlQuery);
        
        try
        {
            while(dbResults.next())
            {                
               int adult_ID = dbResults.getInt(1);
               int group_ID = dbResults.getInt(2);
               String adult_fname = dbResults.getString(3);
               String adult_lname = dbResults.getString(4);
               String email = dbResults.getString(5);
               String phone = dbResults.getString(6);
               double amount_paid = dbResults.getDouble(7);
               String accomplishments = dbResults.getString(8);
               String group_name = dbResults.getString(9);
               String prev_groups = dbResults.getString(10);
               
               //call constructor
               Adult ille = new Adult(adult_ID, (adult_fname + " " + adult_lname), email, phone, amount_paid, group_ID, accomplishments, group_name, prev_groups);
            }     
        }
        catch (SQLException sqle){}
     }
     
     public void loadChildData()
     {
        String sqlQuery = "SELECT * FROM SYSTEM.STUDENTCHILD";
        sendDBCommand(sqlQuery);
        
        try
        {
            while(dbResults.next())
            {                
               int child_ID = dbResults.getInt(1);
               int group_ID = dbResults.getInt(2);
               String child_fname = dbResults.getString(3);
               String child_lname = dbResults.getString(4);
               String name = dbResults.getString(5);
               String email = dbResults.getString(6);
               String phone = dbResults.getString(7);
               String name2 = dbResults.getString(8);
               String email2 = dbResults.getString(9);
               String phone2 = dbResults.getString(10);
               double amount_paid = dbResults.getDouble(11);
               String awardsWon = dbResults.getString(12);
               String accomplishments = dbResults.getString(13);
               int grade = dbResults.getInt(14);
               String en_name = dbResults.getString(15);
               String prev_groups = dbResults.getString(16);
               
               //call constructor
               Child ille = new Child(child_ID, (child_fname + " " + child_lname), grade, name, email, phone, name2, email2, phone2, amount_paid, awardsWon, accomplishments, en_name, prev_groups);
            }     
        }
        catch (SQLException sqle){}
     }
     
     public void loadAwardData()
     {
        String sqlQuery = "SELECT * FROM SYSTEM.STUDENTAWARD";
        sendDBCommand(sqlQuery);
        
        try
        {
            while(dbResults.next())
            {                
               int award_ID = dbResults.getInt(1);
               String award_name = dbResults.getString(2);
               String award_desc = dbResults.getString(3);
               String adultwinners = dbResults.getString(4);
               String childwinners = dbResults.getString(5);
               
               //call constructor
               DSIAward ille = new DSIAward(award_ID, award_name, award_desc, adultwinners, childwinners);
            }     
        }
        catch (SQLException sqle){}
     }

    @FXML
    private void handleAddChildToGroup(ActionEvent event) 
    {
        String Cname = this.childGroupCB.getSelectionModel().getSelectedItem();
        String Gname = this.groupChildCB.getSelectionModel().getSelectedItem();
        Child selectedC = null;
        Group selectedG = null;
        
        for (int i=0; i<Child.allChildren.size(); i++)
        {
            if(Child.allChildren.get(i).getChild_name().equals(Cname))
                selectedC = Child.allChildren.get(i);
        }
        
        for (int i=0; i<Group.allGroups.size(); i++)
        {
            if(Group.allGroups.get(i).getGroup_name().equals(Gname))
                selectedG = Group.allGroups.get(i);
        }

        if (selectedC.getChildGroup().getGroup_name().equals(Gname)) {
            displayAlertWarning("Not Allowed", "This child is already in the selected group.", "");
        } else {

            String newmembers = "";
            String[] memsArray = selectedC.getChildGroup().getMembers().split(",");
            for (int y = 0; y < memsArray.length; y++) {
                if (memsArray.length == 1) {
                    newmembers += "none";
                    break;
                } else if (memsArray[y].equals(selectedC.getChild_name())) {
                    newmembers += "";
                } else {
                    newmembers += memsArray[y] + ",";
                }
            }

            //updates child information in the arraylist
            selectedC.getChildGroup().setMembers(newmembers);
            //removes child from its previous group in arraylist
            selectedC.getChildGroup().getGroupChildren().remove(selectedC);

            //updates old group information in db
            String query = "UPDATE SYSTEM.STUDENTGROUP SET MEMBERS = '" + newmembers + "' WHERE GROUP_NAME = '" + selectedC.getEnlisted_group_name() + "'";
            sendDBCommand(query);

            //adds child to the new selected group in arraylist
            selectedC.addChildToGroup(selectedG);
            selectedG.addChildMember(selectedC);

            //finds child's new group in the database and updates its information in db
            String mems = "";
            String yung = "";
            for (int i = 0; i < Group.allGroups.size(); i++) {
                if (Group.allGroups.get(i).getGroup_name().equals(selectedC.getEnlisted_group_name())) {
                    mems = Group.allGroups.get(i).getMembers();
                    yung = selectedC.getEnlisted_group_name();
                }
            }

            //update new group information in db
            String query2 = "UPDATE SYSTEM.STUDENTGROUP SET MEMBERS = '" + mems + "' WHERE GROUP_NAME = '" + yung + "'";
            sendDBCommand(query2);

            //update child information in db
            String query3 = "UPDATE SYSTEM.STUDENTCHILD SET ENLISTED_GROUP_NAME = '" + selectedC.getEnlisted_group_name() + "' WHERE CHILD_FIRST_NAME = '" + selectedC.getChild_first_name() + "' AND CHILD_LAST_NAME = '" + selectedC.getChild_last_name() + "' AND CHILD_GRADE = " + selectedC.getChild_grade();
            sendDBCommand(query3);
        }
    }

    @FXML
    private void handleAddAdultToGroup(ActionEvent event) 
    {
        String Aname = this.adultGroupCB.getSelectionModel().getSelectedItem();
        String Gname = this.groupAdultCB.getSelectionModel().getSelectedItem();
        Adult selectedA = null;
        Group selectedG = null;
        
        for (int i=0; i<Adult.allAdults.size(); i++)
        {
            if(Adult.allAdults.get(i).getAdult_name().equals(Aname))
                selectedA = Adult.allAdults.get(i);
        }
        
        for (int i=0; i<Group.allGroups.size(); i++)
        {
            if(Group.allGroups.get(i).getGroup_name().equals(Gname))
                selectedG = Group.allGroups.get(i);
        }

        if (selectedA.getAdultGroup().getGroup_name().equals(Gname)) {
            displayAlertWarning("Not Allowed", "This adult is already in the selected group.", "");
        } else {
            String newmembers = "";
            String[] memsArray = selectedA.getAdultGroup().getMembers().split(",");
            for (int y = 0; y < memsArray.length; y++) {
                if (memsArray.length == 1) {
                    newmembers += "none";
                    break;
                } else if (memsArray[y].equals(selectedA.getAdult_name())) {
                    newmembers += "";
                } else {
                    newmembers += memsArray[y] + ",";
                }
            }

            //updates adult information in the arraylist
            selectedA.getAdultGroup().setMembers(newmembers);
            //removes adult from its previous group in arraylist
            selectedA.getAdultGroup().getGroupAdults().remove(selectedA);

            //updates old group information in db
            String query = "UPDATE SYSTEM.STUDENTGROUP SET MEMBERS = '" + newmembers + "' WHERE GROUP_NAME = '" + selectedA.getEnlisted_group_name() + "'";
            sendDBCommand(query);

            //adds adult to the new selected group in arraylist
            selectedA.addAdultToGroup(selectedG);
            selectedG.addAdultMember(selectedA);

            //finds adult's new group in the database and updates its information in db
            String mems = "";
            String yung = "";
            for (int i = 0; i < Group.allGroups.size(); i++) {
                if (Group.allGroups.get(i).getGroup_name().equals(selectedA.getEnlisted_group_name())) {
                    mems = Group.allGroups.get(i).getMembers();
                    yung = selectedA.getEnlisted_group_name();
                }
            }

            //update new group information in db
            String query2 = "UPDATE SYSTEM.STUDENTGROUP SET MEMBERS = '" + mems + "' WHERE GROUP_NAME = '" + yung + "'";
            sendDBCommand(query2);

            //update adult information in db
            String query3 = "UPDATE SYSTEM.STUDENTADULT SET ENLISTED_GROUP_NAME = '" + selectedA.getEnlisted_group_name() + "' WHERE ADULT_FIRST_NAME = '" + selectedA.getAdult_first_name() + "' AND ADULT_LAST_NAME = '" + selectedA.getAdult_last_name() + "' AND PHONE = '" + selectedA.getPhone() + "'";
            sendDBCommand(query3);
        }
    }

    @FXML
    private void handleAddAwdToChild(ActionEvent event) 
    {
        String Cname = this.childAwdCB.getSelectionModel().getSelectedItem();
        String AWname = this.awdChildCB.getSelectionModel().getSelectedItem();
        Child selectedC = null;
        DSIAward selectedAW = null;
        
        for (int i=0; i<Child.allChildren.size(); i++)
        {
            if(Child.allChildren.get(i).getChild_name().equals(Cname))
                selectedC = Child.allChildren.get(i);
        }
        
        for (int i=0; i<DSIAward.allAwards.size(); i++)
        {
            if(DSIAward.allAwards.get(i).getAward_name().equals(AWname))
                selectedAW = DSIAward.allAwards.get(i);
        }
        
        selectedC.addAward(selectedAW);
        selectedAW.addChildWinner(selectedC);
        
        //update child information in db
        String query3 = "UPDATE SYSTEM.STUDENTCHILD SET AWARDS_WON = '" + selectedC.getAwardsWon() + "' WHERE CHILD_FIRST_NAME = '" + selectedC.getChild_first_name() + "' AND CHILD_LAST_NAME = '" + selectedC.getChild_last_name() + "' AND CHILD_GRADE = " + selectedC.getChild_grade();
        sendDBCommand(query3);
        
        //update new group information in db
        String query2 = "UPDATE SYSTEM.STUDENTAWARD SET CHILD_WINNERS = '" + selectedAW.getChildren_who_won() + "' WHERE AWARD_NAME = '" + selectedAW.award_name + "'";
        sendDBCommand(query2);
        
    }

    //NEED TO UPDATE CREATE TABLE STATEMENTS FOR ADULT TO INCORPORATE EARNING AWARDS
    
    @FXML
    private void handleAddAwdToAdult(ActionEvent event) 
    {
        /*String Aname = this.adultAwdCB.getSelectionModel().getSelectedItem();
        String AWname = this.awdAdultCB.getSelectionModel().getSelectedItem();
        Adult selectedA = null;
        DSIAward selectedAW = null;
        
        for (int i=0; i<Adult.allAdults.size(); i++)
        {
            if(Adult.allAdults.get(i).getAdult_name().equals(Aname))
                selectedA = Adult.allAdults.get(i);
        }
        
        for (int i=0; i<DSIAward.allAwards.size(); i++)
        {
            if(DSIAward.allAwards.get(i).getAward_name().equals(AWname))
                selectedAW = DSIAward.allAwards.get(i);
        }
        
        selectedA.addAward(selectedAW);
        selectedAW.addAdultWinner(selectedA);
        
        //update adult information in db
        String query3 = "UPDATE SYSTEM.STUDENTADULT SET AWARDS_WON = '" + selectedA.getAwardsWon() + "' WHERE ADULT_FIRST_NAME = '" + selectedA.getAdult_first_name() + "' AND ADULT_LAST_NAME = '" + selectedA.getAdult_last_name() + "' AND PHONE = '" + selectedA.getPhone() + "'";
        sendDBCommand(query3);
        
        String query2 = "UPDATE SYSTEM.STUDENTAWARD SET ADULT_WINNERS = '" + selectedAW.getAdults_who_won() + "' WHERE AWARD_NAME = '" + selectedAW.award_name + "'";
        sendDBCommand(query2);*/
    }
    
    public void displayAlertWarning(String title, String header, String context) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
    }
}
