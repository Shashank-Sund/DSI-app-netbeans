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

public class CreateAwardController implements Initializable {

    //used for database
    ResultSet dbResults;
    Connection dbConn;
    Statement commStmt;
    PreparedStatement pst;
    
    @FXML
    private Label addAwardLbl;
    @FXML
    private TextField nameEntry;
    @FXML
    private TextArea awardBox;
    @FXML
    private Button createAwardBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void actuallyCreateAward(ActionEvent event) 
    {
        if (nameEntry.getText() == null)
            displayAlertWarning("Invalid Entries", "Please fill in fields.", "");
        else if (awardBox.getText() == null)
            displayAlertWarning("Invalid Entries", "Please fill in fields.", "");
        else
        {
            if (existingAwardCheck(nameEntry.getText()) == true) {
                displayAlertWarning("Invalid Entries", "The award you have entered already exists in the database.", "");
            } else {
                String name = nameEntry.getText();
                String desc = awardBox.getText();

                DSIAward award = new DSIAward(name, desc);

                String query = "INSERT INTO SYSTEM.STUDENTAWARD(AWARD_ID, AWARD_NAME,AWARD_DESCRIPTION)"
                        + "VALUES ("
                        + award.getAward_ID()
                        + ",'" + award.getAward_name()
                        + "','" + desc
                        + "')";
                sendDBCommand(query);

                nameEntry.clear();
                awardBox.clear();
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
    
    public boolean existingAwardCheck(String name)
    {
        boolean exist = false;
        
        for(int i=0; i<DSIAward.allAwards.size(); i++)
        {
            if((DSIAward.allAwards.get(i).getAward_name().equals(name)))
                exist = true;
        }
        
        return exist;
    }
}
