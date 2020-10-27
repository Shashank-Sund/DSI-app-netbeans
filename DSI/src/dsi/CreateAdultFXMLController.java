/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsi.FXML;

import dsi.Adult;
import dsi.Group;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import oracle.jdbc.pool.OracleDataSource;

public class CreateAdultFXMLController implements Initializable {

    //used for database
    ResultSet dbResults;
    Connection dbConn;
    Statement commStmt;
    PreparedStatement pst;
    
    @FXML
    private TextField addNameEntry;
    @FXML
    private TextField addEmailEntry;
    @FXML
    private TextField addPhoneEntry;
    @FXML
    private TextField addAmtEntry;
    @FXML
    private Label addAdultlbl;
    @FXML
    private Button createAdultBtn;
    @FXML
    private ComboBox<String> addAdultGroupCB;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.addAdultGroupCB.setItems(Group.adultGroupsAvail);
    }    

    @FXML
    private void handleCreateAdultBtn(ActionEvent event) {
        //adds adult to the database
        //adds adult to the main arraylist
        
        if (addNameEntry.getText().contains(" ") == false)
            displayAlertWarning("Invalid Entries", "Please fill in all fields.", "");
        else if (addEmailEntry.getText().contains("@") == false)
            displayAlertWarning("Invalid Entries", "Please fill in all fields.", "");
        else if (addPhoneEntry.getText() == null)
            displayAlertWarning("Invalid Entries", "Please fill in all fields.", "");
        else if (addAmtEntry.getText() == null)
            displayAlertWarning("Invalid Entries", "Please fill in all fields.", "");
        else if (addAdultGroupCB.getSelectionModel().getSelectedIndex() == -1)
            displayAlertWarning("Invalid Entries", "Please fill in all fields.", "");
        else {

            if (existingAdultCheck(addNameEntry.getText(), addEmailEntry.getText(), addPhoneEntry.getText()) == true) {
                displayAlertWarning("Invalid Entries", "The person you have entered already exists in the database.", "");
            } else {
                
                String nameEntry = addNameEntry.getText();
                String emailEntry = addEmailEntry.getText();
                String phoneEntry = addPhoneEntry.getText();
                double amtEntry = Double.parseDouble(addAmtEntry.getText());
                String gname = addAdultGroupCB.getSelectionModel().getSelectedItem();

                Adult adult = new Adult(nameEntry, emailEntry, phoneEntry, amtEntry, gname);
                
                adult.getAdultGroup().addAdultMember(adult);

                String query = "INSERT INTO SYSTEM.STUDENTADULT(ADULT_ID, GROUP_ID,ADULT_FIRST_NAME,"
                        + " ADULT_LAST_NAME,EMAIL, PHONE,AMOUNT_PAID, ADULT_ACCOMPLISHMENTS,ENLISTED_GROUP_NAME)"
                        + "VALUES ("
                        + adult.getAdult_ID() + "," 
                        + adult.getGroup_ID() + ",'" 
                        + adult.getAdult_first_name() + "','" 
                        + adult.getAdult_last_name() + "','" 
                        + emailEntry + "','" 
                        + phoneEntry + "'," 
                        + amtEntry + ",'" 
                        + adult.getAdultAccomplishments() 
                        + "','" + adult.getEnlisted_group_name() 
                        + "')";

                sendDBCommand(query);

                String mems="";
                String yung="";
                for(int i=0; i<Group.allGroups.size(); i++)
                {
                    if (Group.allGroups.get(i).getGroup_name().equals(adult.getEnlisted_group_name())){
                        mems = Group.allGroups.get(i).getMembers();
                        yung = adult.getEnlisted_group_name();
                    }
                }
                
                String query2 = "UPDATE SYSTEM.STUDENTGROUP SET MEMBERS = '" + mems + "' WHERE GROUP_NAME = '"+ yung +"'";
                sendDBCommand(query2);

                addNameEntry.clear();
                addEmailEntry.clear();
                addPhoneEntry.clear();
                addAmtEntry.clear();
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
    
    public boolean existingAdultCheck(String name, String email, String phone)
    {
        boolean exist = false;
        
        for(int i=0; i<Adult.allAdults.size(); i++)
        {
            if((Adult.allAdults.get(i).getAdult_name().equals(name)) && (Adult.allAdults.get(i).getEmail().equals(email)) && (Adult.allAdults.get(i).getPhone().equals(phone)))
                exist = true;
        }
        
        return exist;
    }
    
}
