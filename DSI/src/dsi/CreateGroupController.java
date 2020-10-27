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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import oracle.jdbc.pool.OracleDataSource;

public class CreateGroupController implements Initializable {

    //used for database
    ResultSet dbResults;
    Connection dbConn;
    Statement commStmt;
    PreparedStatement pst;
    
    public static ObservableList<String> types = FXCollections.observableArrayList("Adult", "Child");
    
    @FXML
    private TextField nameEntry;
    @FXML
    private ComboBox<String> typeCB;
    @FXML
    private TextField durationEntry;
    @FXML
    private TextField meetTimeEntry;
    @FXML
    private TextField enrollmentEntry;
    @FXML
    private TextField downPmtEntry;
    @FXML
    private TextArea datesBox;
    @FXML
    private Button createGroupBtn;
    @FXML
    private Label addGrouplbl;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.typeCB.setItems(types);
    }    

    @FXML
    private void actuallyCreateGroup(ActionEvent event) 
    {
        if (nameEntry.getText() == null)
            displayAlertWarning("Invalid Entries", "Please fill in fields.", "");
        else if (durationEntry.getText() == null)
            displayAlertWarning("Invalid Entries", "Please fill in fields.", "");
        else if (meetTimeEntry.getText() == null)
            displayAlertWarning("Invalid Entries", "Please fill in fields.", "");
        else if (enrollmentEntry.getText() == null)
            displayAlertWarning("Invalid Entries", "Please fill in fields.", "");
        else if (downPmtEntry.getText() == null)
            displayAlertWarning("Invalid Entries", "Please fill in fields.", "");
        else if (datesBox.getText() == null)
            displayAlertWarning("Invalid Entries", "Please fill in fields.", "");
        else if (typeCB.getSelectionModel().getSelectedIndex() == -1)
            displayAlertWarning("Invalid Entries", "Please fill in fields.", "");
        else
        {
            
            if (existingGroupCheck(nameEntry.getText()) == true) {
                displayAlertWarning("Invalid Entries", "The group you have entered already exists in the database.", "");
            } else {
                String name = nameEntry.getText();
                int duration = Integer.parseInt(durationEntry.getText());
                String meetingtime = meetTimeEntry.getText();
                double enrollment = Double.parseDouble(enrollmentEntry.getText());
                double down = Double.parseDouble(downPmtEntry.getText());
                String type = typeCB.getSelectionModel().getSelectedItem();
                String meetingDates = datesBox.getText();

                Group group = new Group(name, type, duration, meetingtime, enrollment, down, meetingDates);

                String query = "INSERT INTO SYSTEM.STUDENTGROUP(GROUP_ID, GROUP_NAME,GROUP_TYPE,"
                        + " GROUP_DURATION,GROUP_MEETING_TIME, ENROLLMENT_FEE,DOWN_PAYMENT, MEETING_DATES, MEMBERS)"
                        + "VALUES ("
                        + group.getGroup_ID() + ",'" + group.getGroup_name() + "','" + type + "'," + duration + ",'" + meetingtime + "'," + enrollment + "," + down + "," + "'" + meetingDates + "'" + ",'" + group.getMembers() + "')";

                sendDBCommand(query);

                //repopulates combobox in second tab
                if(group.getGroup_type().equals("Child"))
                    Group.childGroupsAvail.add(group.getGroup_name());
                else
                    Group.adultGroupsAvail.add(group.getGroup_name());
                
                nameEntry.clear();
                durationEntry.clear();
                meetTimeEntry.clear();
                enrollmentEntry.clear();
                downPmtEntry.clear();
                datesBox.clear();
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
    
    public boolean existingGroupCheck(String name)
    {
        boolean exist = false;
        
        for(int i=0; i<Group.allGroups.size(); i++)
        {
            if((Group.allGroups.get(i).getGroup_name().equals(name)))
                exist = true;
        }
        
        return exist;
    }
}
