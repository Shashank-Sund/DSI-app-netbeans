/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsi;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.pool.*;
import javafx.geometry.Pos;
import java.lang.*;
import javafx.scene.control.Alert;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author sunda
 */
public class FXMLDocumentController implements Initializable {
    
    //used for database
    ResultSet dbResults;
    Connection dbConn;
    Statement commStmt;
    PreparedStatement pst;
    
    private Label label;
    @FXML
    private AnchorPane loginPane;
    @FXML
    private Button loginBtn;
    @FXML
    private Label signInLbl;
    @FXML
    private PasswordField passwordEntry;
    @FXML
    private TextField emailEntry;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    //event handler for login button
    @FXML
    private void handleLoginBtn(ActionEvent event) throws IOException {
        
        if (emailEntry.getText() == null) 
            displayAlertWarning("Invalid Credentials", "Please enter an email and password.", "");
        else if (passwordEntry.getText() == null) 
            displayAlertWarning("Invalid Credentials", "Please enter an email and password.", "");
        else 
        {
            String userEntry = emailEntry.getText();
            String passEntry = passwordEntry.getText();

            int accesskey = passwordCheckDB(userEntry, passEntry);

            if (accesskey == 0) 
                displayAlertWarning("Invalid Credentials", "The username or password is invalid.", "");
            else 
            {
                Parent ille = FXMLLoader.load(getClass().getResource("menuFXML.fxml"));
                Scene mainScene = new Scene(ille);
        
                Stage wind = (Stage)((Node)event.getSource()).getScene().getWindow();
        
                wind.setScene(mainScene);
                wind.show();
                
            }

        }
    }
    
    public void sendDBCommand(String query)
    {
        String URL = "jdbc:oracle:thin:@localhost:1521:XE";
        String userID = "system";
        String userPassword = "password";
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
    
    //does password check within credentials table
    public int passwordCheckDB(String userEntry, String passEntry){
        
        String sqlQuery = "SELECT * FROM SYSTEM.CREDENTIALS";
        sendDBCommand(sqlQuery);
        
        int systemuserID = 0;
        
        try
        {
            while(dbResults.next())
            {                
               int credID = (dbResults.getInt(1));
               String username = dbResults.getString(2);
               String password = dbResults.getString(3);
               
               if ((userEntry.equals(username)) && (passEntry.equals(password)))
                   systemuserID = credID;
            }     
        }
        catch (SQLException sqle)
        {
            //txtOutput.setText(sqle.toString());
        }
        return systemuserID;
    }
    
    public void displayAlertWarning(String title, String header, String context) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
    }
    
    /*public void prePop()
    {
        //insert statements for testing
        String inCredentials1 = "INSERT INTO JAVAUSER.CREDENTIALS (systemuser_ID,username,passsword) "
               + "VALUES (1,'shanksund@gmail.com','password')";
        sendDBCommand(inCredentials1);
    }*/
    
}
