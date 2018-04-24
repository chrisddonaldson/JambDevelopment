/*
Welcome to the Jamb Development Manager.
Built by Chris Donaldson

The aim of this program is to streamline development work and to produce a historic account of development activities.

TO DO List:

	Reduce complexity and unify the SQL methods.
	
	Make an automatic outputer of the sum of the days waiting in development. This can be used to assess design coordination performance.
	
	Change the sorting method so it doesn't crash when you double click the sort by CAD option. Might have something to do with the query not being wrapped in a switch statement.
	
	Add method of completing/ archiving the Active projects.
	
	Add sort method of by person.
		Add method of assigning things to people (starcraft construction).
		
	Add sort to by shortest weight.
	
	Add data validation on the entry forms.

*/
package com.development;
 
import java.sql.Date;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
 
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Development Manager");
        
        Button SortByType = new Button("Sort by Type");
        SortByType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(PublicUI.SortType != "BYTYPE") {
            		PublicUI.SortType = "BYTYPE";
            		PublicUI.t.load();
            		PublicUI.t.display();
            	}
            	else if(PublicUI.SortType == "BYTYPE"){
            		PublicUI.SortType ="";
                    PublicUI.t.load();
                    PublicUI.t.display();
            	}

            }
        });
        Button CadJobs = new Button("Sort by Cad");
        CadJobs.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(PublicUI.SortType != "BYCAD") {
            		PublicUI.SortType = "BYCAD";
            		PublicUI.t.load();
            		PublicUI.t.display();
            	}
            	else{
            		PublicUI.SortType ="";
                    PublicUI.t.load();
                    PublicUI.t.display();
            	}

            }
        });
        
        Button Olddc = new Button("Oldest DC first");
        Olddc.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(PublicUI.SortType != "OLDESTDC") {
            		PublicUI.SortType = "OLDESTDC";
            		PublicUI.t.load();
            		PublicUI.t.display();
            	}
            	else if(PublicUI.SortType == "OLDESTDC"){
            		PublicUI.SortType ="";
                    PublicUI.t.load();
                    PublicUI.t.display();
            	}

            }
        });
        
        Button view4 = new Button("View 4");
        Button AddTicket = new Button("Add Ticket");
        

        PublicUI.t.load();
        PublicUI.t.display();
        AddTicket.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	PublicUI.t.addTicket();

            }
        });
        
        
        BorderPane root = new BorderPane();
        ScrollPane TicketScroller = new ScrollPane();
        PublicUI.TicketSpace.setPrefWrapLength(1500); // preferred width = 300
        TicketScroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        root.setPadding(new Insets(20,20,20,20));
        
        PublicUI.ButtonStack.setPadding(new Insets(30,30,30,30));
        PublicUI.ButtonStack.setAlignment(Pos.CENTER);
        PublicUI.ButtonStack.getChildren().addAll(SortByType, CadJobs, Olddc, view4, AddTicket);
        root.setLeft(PublicUI.ButtonStack);
        TicketScroller.setContent(PublicUI.TicketSpace);
        TicketScroller.setPrefSize(1200, 1200);
        root.setCenter(TicketScroller);
        primaryStage.setScene(new Scene(root, 1600, 900));
        primaryStage.show();
    }
}