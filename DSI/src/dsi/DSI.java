/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*

1) connect to sql locally on laptop through sql command line (save username and password)
2) change user info using the above username and password in sendDBcommand
3) connect to driver in Services tab and attach jar file
4) use same above username and password to test connection

#1ecbe1 - light blue
#effbfd - white

*/

public class DSI extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            
            Scene scene = new Scene(root);
        
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    
}
