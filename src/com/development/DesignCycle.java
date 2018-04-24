package com.development;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DesignCycle {
	int ID;
	int parentID;
	String description;
	Date dateCreated;
	Date dateCompleted;
	boolean finished;
	HBox InfoRow;
	Label DescriptionLabel;
	Label IDLabel;
	Label AgeLabel;
	boolean cadJob;
	

	public DesignCycle(int iD, int parentID, String description, Date dateCreated, Date dateCompleted,
			boolean finished) {
		super();
		ID = iD;
		this.parentID = parentID;
		this.description = description;
		this.dateCreated = dateCreated;
		this.dateCompleted = dateCompleted;
		this.finished = finished;

		Label DescriptionLabel = new Label(description);
		Label IDLabel = new Label(ID + "");
		InfoRow.getChildren().addAll(IDLabel, DescriptionLabel);
	}

	public DesignCycle(int dcID, String description2, Date newDate, boolean newCadJob) {
		// TODO Auto-generated constructor stub
		this.ID = dcID;
		this.description = description2;
		this.dateCreated = newDate;
		DescriptionLabel = new Label();
		DescriptionLabel.setWrapText(true);
		this.cadJob=newCadJob;

		AgeLabel = new Label();
		IDLabel= new Label();
		InfoRow =  new HBox(10);


	}

	HBox getHBox() {
		String IDStr = "" + this.ID;
		IDLabel.setText(IDStr);
		DescriptionLabel.setText(this.description);
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String dateCreatedText = df.format(dateCreated);
		long age = dateCalcs.getDayDiff(dateCreated);
		
		AgeLabel.setText(""+age);
		Button deleteCycle = new Button("D");
		Button editCycle = new Button("Edit");
		Button completeCycle = new Button("C");
		
		GridPane layout = new GridPane();
		layout.setGridLinesVisible(true);///////////////////////////////////////////////////////////////////GRID LINES
		layout.getColumnConstraints().add(new ColumnConstraints(25)); 
		layout.getColumnConstraints().add(new ColumnConstraints(140)); 
		layout.getColumnConstraints().add(new ColumnConstraints(25));
		layout.getColumnConstraints().add(new ColumnConstraints(25));
		layout.getColumnConstraints().add(new ColumnConstraints(25));
		layout.getColumnConstraints().add(new ColumnConstraints(40));
		layout.add(IDLabel, 0, 0);
		
		layout.add(DescriptionLabel, 1, 0);
		layout.add(AgeLabel, 2, 0);
		layout.add(deleteCycle, 3, 0);
		layout.add(completeCycle, 4, 0);
		layout.add(editCycle, 5, 0);
	
		
        deleteCycle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(ConfirmationBox.alert("Delete this Design Cycle?")) {
            	try {
					String myDriver = "org.gjt.mm.mysql.Driver";
					String myUrl = "jdbc:mysql://localhost:3306/development?autoReconnect=true&useSSL=false";
					Class.forName(myDriver);
					Connection conn = DriverManager.getConnection(myUrl, "root", "xxxx");

					PreparedStatement st = conn
							.prepareStatement("DELETE FROM design_cycles WHERE idDesign_Cycle = " + ID + ";");
					st.executeUpdate();
				} catch (Exception e) {
					System.out.println(e);
				}
				System.out.println("Deleted item numer: " + ID);
				PublicUI.t.load();
				PublicUI.t.display();
			}else {return;}}

            
        });
        
        completeCycle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
			public void handle(ActionEvent event) {
            	if(ConfirmationBox.alert("Mark this Design Cycle as complete?")) {
				Date today = new java.sql.Date(Calendar.getInstance().getTime().getTime());
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/development?autoReconnect=true&useSSL=false",
							"root", "xxxx");

					// create the java mysql update preparedstatement
					String query = "update design_cycles set Date_Completed = ? where iddesign_cycle = ?";
					PreparedStatement preparedStmt = con.prepareStatement(query);

					preparedStmt.setDate(1, today);
					preparedStmt.setInt(2, ID);
					// execute the java preparedstatement
					preparedStmt.executeUpdate();

					con.close();

				} catch (Exception e) {
					System.out.println(e);
				}
				PublicUI.t.load();
				PublicUI.t.display();
            	}
            	else{return;}
				
			}

            
        });
        
        editCycle.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage stage = new Stage();
				VBox root = new VBox(30);
				root.setPadding(new Insets(30, 30, 30, 30));

				Label DescriptionLabel = new Label("description");
				
				TextField DescriptionTextField = new TextField();
				DescriptionTextField.setText(description);
				
				CheckBox CadCheckBox = new CheckBox("Is this a CAD job?");
				CadCheckBox.setSelected(cadJob);
				Button Submit = new Button("Submit");

				Submit.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {

						String newDescription = DescriptionTextField.getText();
						try {
							Class.forName("com.mysql.jdbc.Driver");
							Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/development?autoReconnect=true&useSSL=false",
									"root", "xxxx");

							// create the java mysql update preparedstatement
							String query = "update design_cycles set Description = ?, CAD_TOF = ? where iddesign_cycle = ?";
							PreparedStatement preparedStmt = con.prepareStatement(query);

							preparedStmt.setString(1, newDescription);
							preparedStmt.setBoolean(2,CadCheckBox.isSelected());
							preparedStmt.setInt(3, ID);
							// execute the java preparedstatement
							preparedStmt.executeUpdate();

							con.close();

						} catch (Exception e) {
							System.out.println(e);
						}
						PublicUI.t.load();
						PublicUI.t.display();
						stage.close();
						
					}

				});

				root.setAlignment(Pos.CENTER);
				root.getChildren().addAll(DescriptionLabel, DescriptionTextField,CadCheckBox, Submit);
				stage.setTitle("Edit Design Cycle");
				stage.setScene(new Scene(root, 450, 600));
				stage.show();

			}
		});
		
        InfoRow.getChildren().add(layout);
		return InfoRow;
	}

}
