/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsi;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import oracle.jdbc.pool.OracleDataSource;

public class CreateChildController implements Initializable {

    //used for database
    ResultSet dbResults;
    Connection dbConn;
    Statement commStmt;
    PreparedStatement pst;
    
    @FXML
    private AnchorPane mainPane;
    @FXML
    private Label addChildlbl;
    @FXML
    private TextField nameEntry;
    @FXML
    private TextField gradeEntry;
    @FXML
    private TextField amtEntry;
    @FXML
    private TextField c2name;
    @FXML
    private TextField c2email;
    @FXML
    private TextField c2phone;
    @FXML
    private TextField c1name;
    @FXML
    private TextField c1email;
    @FXML
    private TextField c1phone;
    @FXML
    private Label contact2lbl;
    @FXML
    private Label contact1lbl;
    @FXML
    private Button createChildBtn;
    @FXML
    private ComboBox<String> groupCB;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.groupCB.setItems(Group.childGroupsAvail);
    }    

    @FXML
    private void actuallyCreateChild(ActionEvent event) 
    {
        if (nameEntry.getText().contains(" ") == false)
            displayAlertWarning("Invalid Entries", "Please fill in fields with at least 1 contact.", "");
        else if (gradeEntry.getText() == null)
            displayAlertWarning("Invalid Entries", "Please fill in fields with at least 1 contact.", "");
        else if (amtEntry.getText() == null)
            displayAlertWarning("Invalid Entries", "Please fill in fields with at least 1 contact.", "");
        else if (c1name.getText() == null)
            displayAlertWarning("Invalid Entries", "Please fill in fields with at least 1 contact.", "");
        else if (c1email.getText().contains("@") == false)
            displayAlertWarning("Invalid Entries", "Please fill in fields with at least 1 contact.", "");
        else if (c1phone.getText() == null)
            displayAlertWarning("Invalid Entries", "Please fill in fields with at least 1 contact.", "");
        else if (c2name.getText() == null)
            displayAlertWarning("Invalid Entries", "Please fill in fields with at least 1 contact.", "");
        else if (c2email.getText() == null)
            displayAlertWarning("Invalid Entries", "Please fill in fields with at least 1 contact.", "");
        else if (c2phone.getText() == null)
            displayAlertWarning("Invalid Entries", "Please fill in fields with at least 1 contact.", "");
        else if (groupCB.getSelectionModel().getSelectedIndex() == -1)
            displayAlertWarning("Invalid Entries", "Please fill in fields with at least 1 contact.", "");
        else
        {
            
            int grade = Integer.parseInt(gradeEntry.getText());

            if (existingChildCheck(nameEntry.getText(), grade) == true) {
                displayAlertWarning("Invalid Entries", "The person you have entered already exists in the database.", "");
            } else {
                
                String name = nameEntry.getText();
                int grade2 = Integer.parseInt(gradeEntry.getText());
                double amt = Double.parseDouble(amtEntry.getText());

                String c1names = c1name.getText();
                String c1emails = c1email.getText();
                String c1phones = c1phone.getText();

                String c2names = c2name.getText();
                String c2emails = c2email.getText();
                String c2phones = c2phone.getText();
                
                
                String groupname = groupCB.getSelectionModel().getSelectedItem();

                Child child = new Child(name, grade2, c1names, c1emails, c1phones, c2names, c2emails, c2phones, amt, groupname);

                child.getChildGroup().addChildMember(child);

                String query = "INSERT INTO SYSTEM.STUDENTCHILD(CHILD_ID, GROUP_ID,CHILD_FIRST_NAME,"
                        + " CHILD_LAST_NAME, CONTACT_1_NAME,CONTACT_1_EMAIL, CONTACT_1_PHONE, CONTACT_2_NAME,CONTACT_2_EMAIL, CONTACT_2_PHONE,AMOUNT_PAID,AWARDS_WON, CHILD_ACCOMPLISHMENTS,CHILD_GRADE,ENLISTED_GROUP_NAME)"
                        + "VALUES ("
                        + child.getChild_ID() + ","
                        + child.getGroup_ID() + ",'"
                        + child.getChild_first_name() + "','"
                        + child.getChild_last_name() + "','"
                        + c1names + "','"
                        + c1emails + "','"
                        + c1phones + "','"
                        + c2names + "','"
                        + c2emails + "','"
                        + c2phones + "',"
                        + child.getAmount_paid() + ",'"
                        + child.getAwardsWon() + "','"
                        + child.getChildAccomplishments() + "',"
                        + child.getChild_grade() + ",'"
                        + child.getEnlisted_group_name() + "')";

                sendDBCommand(query);

                String mems="";
                String yung="";
                for(int i=0; i<Group.allGroups.size(); i++)
                {
                    if (Group.allGroups.get(i).getGroup_name().equals(child.getEnlisted_group_name())){
                        mems = Group.allGroups.get(i).getMembers();
                        yung = child.getEnlisted_group_name();
                    }
                        //mems = Group.allGroups.get(i).getMembers();
                }
                
                String query2 = "UPDATE SYSTEM.STUDENTGROUP SET MEMBERS = '" + mems + "' WHERE GROUP_NAME = '"+ yung +"'";
                sendDBCommand(query2);

                nameEntry.clear();
                gradeEntry.clear();
                amtEntry.clear();
                c1name.clear();
                c1email.clear();
                c1phone.clear();
                c2name.clear();
                c2email.clear();
                c2phone.clear();
            }
                
                
        }
    }
    
    public void displayAlertWarning(String title, String header, String context) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
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
    
    public boolean existingChildCheck(String name, int grade)
    {
        boolean exist = false;
        
        for(int i=0; i<Child.allChildren.size(); i++)
        {
            if((Child.allChildren.get(i).getChild_name().equals(name)) && (Child.allChildren.get(i).getChild_grade() == grade))
                exist = true;
        }
        
        return exist;
    }
}
