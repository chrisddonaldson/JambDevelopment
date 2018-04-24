package com.development;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TicketHandler{
ArrayList<Ticket> TicketList = new ArrayList<Ticket>();
	TicketHandler() {}

	void addTicket() { //////////////////////////////////////////////////////////////TICKET ADDER//////////////////////////////////////////////////////////////////////////////////////
		Stage stage = new Stage();
		VBox root = new VBox(30);
		root.setPadding(new Insets(30, 30, 30, 30));

		Label TitleLabel = new Label("Title");
		Label TypeLabel = new Label("Type");
		Label ObjectiveLabel = new Label("Objective");
		Label ValueLabel = new Label("Value");

		TextField TitleTextField = new TextField();
		TextField TypeTextField = new TextField();
		TextField ObjectiveTextField = new TextField();
		TextField ValueTextField = new TextField();

		Button Submit = new Button("Submit");

		Submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				String newTitle = TitleTextField.getText();
				String newType = TypeTextField.getText();
				String newObjective = ObjectiveTextField.getText();
				String newValue = ValueTextField.getText();
				Calendar currenttime = Calendar.getInstance();
				Date sqldate = new Date((currenttime.getTime()).getTime());
				int newValueInt = Integer.parseInt(newValue);
				
				try{  
					Class.forName("com.mysql.jdbc.Driver");  
					Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/development","root","xxxx");  

					  String query = " insert into active_project (Title, Type, Objective, Value,DateCreated)"
						        + " values (?, ?, ?, ?,?)";

						      // create the mysql insert preparedstatement
						      PreparedStatement preparedStmt = con.prepareStatement(query);
						      preparedStmt.setString (1, newTitle);
						      preparedStmt.setString (2, newType);
						      preparedStmt.setString (3, newObjective);
						      preparedStmt.setInt  (4, newValueInt);
						      preparedStmt.setDate  (5, sqldate);
						      

						      // execute the preparedstatement
						      preparedStmt.execute();
 
					con.close();  
					
					}catch(Exception e){ System.out.println(e); }
				
				
				stage.close();
            	load();
            	display();
			}  
			
		});

		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(TitleLabel, TitleTextField, TypeLabel, TypeTextField, ObjectiveLabel,
				ObjectiveTextField, ValueLabel, ValueTextField, Submit);

	
		stage.setTitle("Add new Ticket");
		stage.setScene(new Scene(root, 450, 600));
		stage.show();

	}
	
	void load() {
		//get all the tickets
		TicketList.clear(); 
		ResultSet rs = null;
		Connection conn = null;
		Statement st = null;
		try
	    {
	      // create our mysql database connection
	      String myDriver = "org.gjt.mm.mysql.Driver";
	      //String myUrl = "jdbc:mysql://localhost/development";
	      String myUrl = "jdbc:mysql://localhost:3306/development?autoReconnect=true&useSSL=false";
	      
	      Class.forName(myDriver);
	      conn = DriverManager.getConnection(myUrl, "root", "xxxx");
	      
	      // our SQL SELECT query. 
	      // if you only need a few columns, specify them by name instead of using "*"
	      String query;
	      if (PublicUI.SortType == "BYTYPE"){
	      query = "SELECT * FROM active_project order by Type";
	      }
	      
	      else if (PublicUI.SortType == "BYCAD"){
	      query = "SELECT * FROM development.active_project join development.design_cycles on development.design_cycles.Parent_ID = development.active_project.idActive_Project \r\n" + 
	      		"where development.design_cycles.CAD_TOF = 1 AND development.design_cycles.Date_Completed IS NULL";
	      }
	      
	      else if (PublicUI.SortType == "OLDESTDC"){
	      query = "SELECT * FROM development.active_project join development.design_cycles on development.design_cycles.Parent_ID = development.active_project.idActive_Project where Date_Completed IS NULL ORDER BY Date_created" ;
	      }
	      
	      else {
	    	       query = "SELECT * FROM active_project";  
	      }
	      // create the java statement
	      st = conn.createStatement();
	      
	      // execute the query, and get a java resultset
	       rs = st.executeQuery(query);
	      
	      // iterate through the java resultset
	      while (rs.next())
	      {
	        int id = rs.getInt("idActive_Project");
	        String Title= rs.getString("Title");
	        String Type = rs.getString("Type");
	        String Obejective = rs.getString("Objective");
	        int Value= rs.getInt("Value");
	        Date dateCreated = rs.getDate("DateCreated");
	        
	        
	        // print the results
	        //System.out.format("%s, %s, %s, %s, %s\n", id, Title, Type, Obejective, Value);
	        // Build ticket objects using this information
	        Ticket TempTicket = new Ticket(id, Title, Type, Obejective, Value, dateCreated);
	        TicketList.add(TempTicket);
	      }
	      st.close();
	    }
	    catch (Exception e)
	    {
	      System.err.println("Got an exception! ");
	      System.err.println(e.getMessage());
	    } finally {
	        try { rs.close(); } catch (Exception e) { /* ignored */ }
	        try { st.close(); } catch (Exception e) { /* ignored */ }
	        try { conn.close(); } catch (Exception e) { /* ignored */ }
	    }
	  }
	
	void display() {
		PublicUI.TicketSpace.getChildren().clear();
		for(int i =0; i<TicketList.size(); i++) {
			Ticket temp = new Ticket();
			temp = TicketList.get(i);
			PublicUI.TicketSpace.getChildren().add(temp.getTicket());
		}
		
		
		
		if(!PublicUI.launch) {
			PublicUI.DaysWaitingSum.setText("Total wait days = " + dayCounter.sumDates());
			PublicUI.ButtonStack.getChildren().addAll(PublicUI.DaysWaitingSum);
			PublicUI.launch=true;
		}
		else {}
	}

		
	

}
