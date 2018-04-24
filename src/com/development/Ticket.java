package com.development;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Ticket {
	int id;
	String title;
	String type;
	String objective;
	int value;
	GridPane gp;
	VBox ticket = new VBox(10);
	ArrayList<DesignCycle> dc = new ArrayList<DesignCycle>();
	Date dateCreated;

	public Ticket(int id, String title, String type, String objective, int value, Date dateCreated) {
		this.id = id;
		this.title = title;
		this.type = type;
		this.objective = objective;
		this.value = value;
		this.dateCreated = dateCreated;
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String dateCreatedText = df.format(dateCreated);
		ticket.setPadding(new Insets(10, 10, 10, 10));
		
		
		
		ticket.setStyle("-fx-background-color: #dce2e2;");
		
		
		switch (this.type) {
        case "LTN":  ticket.setStyle("-fx-background-color: #f9f9c0;");
                 break;
        case "KEN":  ticket.setStyle("-fx-background-color: #ccf9c0;");
        break;
        case "FG":  ticket.setStyle("-fx-background-color: #f9c0c0;");
        break;
        case "CUSTOM":  ticket.setStyle("-fx-background-color: #f0c0f9;");
        break;
        case "CP":  ticket.setStyle("-fx-background-color: #d2c0f9;");
        break;
		}
		Label TitleLabel = new Label("Title: " + this.title);
		Label TypeLabel = new Label("Type: " + this.type);
		Label ObjectiveLabel = new Label("Objective: " + this.objective);
		Label dateCreatedLabel = new Label("Date Created: "+dateCreatedText);
		ObjectiveLabel.setWrapText(true);
		ScrollPane ObjectiveScroll = new ScrollPane();
		ObjectiveScroll.setContent(ObjectiveLabel);
		Button RemoveTicket = new Button("DLT");
		Button EditTicket = new Button("Edit");
		VBox ButtonBox = new VBox(5);
		ButtonBox.setAlignment(Pos.CENTER);
		ButtonBox.getChildren().addAll(RemoveTicket, EditTicket);
		String valueStr = "" + this.value;
		Label ValueLabel = new Label("Value: " + valueStr);
		Button newDesignCycle = new Button("Add Cycle");

		GridPane gp = new GridPane();
		//gp.setGridLinesVisible(true);///////////////////////////////////////////////////////////////////////////////GRID LINES
		gp.getRowConstraints().add(new RowConstraints(16)); // Row 0 is 100 wide
		gp.getRowConstraints().add(new RowConstraints(80)); // Row 1 is 100 wide
		gp.getRowConstraints().add(new RowConstraints(16)); // Row 2 is 100 wide
		gp.getRowConstraints().add(new RowConstraints(32)); // Row 3 is 100 wide
		gp.getRowConstraints().add(new RowConstraints(150)); // Row 4 is 100 wide
		gp.getColumnConstraints().add(new ColumnConstraints(70)); // Row 0 is 100 wide
		gp.getColumnConstraints().add(new ColumnConstraints(70)); // Row 1 is 100 wide
		gp.getColumnConstraints().add(new ColumnConstraints(70)); // Row 2 is 100 wide
		gp.getColumnConstraints().add(new ColumnConstraints(70)); // Row 3 is 100 wide
		
		// Build and get display for Design Cycles
		designCycleGetter();
		VBox designCycleHolder = new VBox(5);
		//designCycleHolder.getChildren().add(new Label("Designholder"));
		if (dc.size() > 0) {
			for (int i = 0; i < dc.size(); i++) {
				DesignCycle tempDC;
				tempDC = dc.get(i);
				//System.out.println("In the DC array position: "+i+" the ID is: "+tempDC.ID);
				//System.out.println("In the DC array position: "+i+" the Description is: "+tempDC.description);
				designCycleHolder.getChildren().add(tempDC.getHBox());}
			
			GridPane.setConstraints(designCycleHolder, 0, 4, 4, 1);
		}


			GridPane.setConstraints(TitleLabel, 0, 0, 3, 1);
			GridPane.setConstraints(TypeLabel, 3, 0, 2, 1);
			GridPane.setConstraints(ObjectiveLabel, 0, 1, 3, 1);
			GridPane.setConstraints(ButtonBox, 3, 1);
			GridPane.setConstraints(ValueLabel, 3, 2);
			GridPane.setConstraints(dateCreatedLabel, 0, 2, 4, 1);
			GridPane.setConstraints(newDesignCycle, 0, 3, 4, 1);
			
			

			newDesignCycle.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					addCycle();
			        PublicUI.t.load();
			        PublicUI.t.display();
;
				}
			});

			gp.getChildren().addAll(TitleLabel, TypeLabel, ObjectiveLabel, ButtonBox, ValueLabel, newDesignCycle,dateCreatedLabel);
			
			if (dc.size() > 0) {
				gp.getChildren().add(designCycleHolder);
			} else {
			}

			ticket.getChildren().add(gp);
			//System.out.println("Object constructed: "+this.title);

			RemoveTicket.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if(ConfirmationBox.alert("Delete this ticket?")){
					try {
						String myDriver = "org.gjt.mm.mysql.Driver";
						String myUrl = "jdbc:mysql://localhost/development";
						Class.forName(myDriver);
						Connection conn = DriverManager.getConnection(myUrl, "root", "xxxx");

						PreparedStatement st = conn
								.prepareStatement("DELETE FROM active_project WHERE idActive_Project = " + id + ";");
						st.executeUpdate();
					} catch (Exception e) {
						System.out.println(e);
					}
					System.out.println("Deleted item numer: " + id);
					PublicUI.t.load();
					PublicUI.t.display();
				}else {
					return;
				}

			}});

			EditTicket.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					Stage stage = new Stage();
					VBox root = new VBox(30);
					root.setPadding(new Insets(30, 30, 30, 30));

					Label TitleLabel = new Label("Title");
					Label TypeLabel = new Label("Type");
					Label ObjectiveLabel = new Label("Objective");
					Label ValueLabel = new Label("Value");

					TextField TitleTextField = new TextField();
					TitleTextField.setText(title);
					TextField TypeTextField = new TextField();
					TypeTextField.setText(type);
					TextField ObjectiveTextField = new TextField();
					ObjectiveTextField.setText(objective);
					TextField ValueTextField = new TextField();
					String vs = "" + value;
					ValueTextField.setText(vs);

					Button Submit = new Button("Submit");

					Submit.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {

							String newTitle = TitleTextField.getText();
							String newType = TypeTextField.getText();
							String newObjective = ObjectiveTextField.getText();
							String newValue = ValueTextField.getText();
							int newValueInt = Integer.parseInt(newValue);
							try {
								Class.forName("com.mysql.jdbc.Driver");
								Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/development?autoReconnect=true&useSSL=false",
										"root", "xxxx");

								// create the java mysql update preparedstatement
								String query = "update active_project set Title = ?, Type = ?, Objective = ?, value = ? where idActive_Project = ?";
								PreparedStatement preparedStmt = con.prepareStatement(query);

								preparedStmt.setString(1, newTitle);
								preparedStmt.setString(2, newType);
								preparedStmt.setString(3, newObjective);
								preparedStmt.setInt(4, newValueInt);
								preparedStmt.setInt(5, id);
								// execute the java preparedstatement
								preparedStmt.executeUpdate();

								con.close();

							} catch (Exception e) {
								System.out.println(e);
							}
							stage.close();
							PublicUI.t.load();
							PublicUI.t.display();
						}

					});

					root.setAlignment(Pos.CENTER);
					root.getChildren().addAll(TitleLabel, TitleTextField, TypeLabel, TypeTextField, ObjectiveLabel,
							ObjectiveTextField, ValueLabel, ValueTextField, Submit);

					stage.setTitle("Edit Ticket");
					stage.setScene(new Scene(root, 450, 600));
					stage.show();

				}
			});
		}



	public Ticket() {
	}

	VBox getTicket() {
		return ticket;
	}

	GridPane getGP() {
		return gp;
	}

	void addCycle() {
		Stage stage = new Stage();
		VBox CycleRoot = new VBox(30);
		CycleRoot.setPadding(new Insets(30, 30, 30, 30));
		Label DescriptionLabel = new Label("Desciption");
		TextField DescriptionTextField = new TextField();
		Button Submit = new Button("Submit");
		Calendar currenttime = Calendar.getInstance();
		Date sqldate = new Date((currenttime.getTime()).getTime());
		CheckBox CadCheckBox = new CheckBox("Is this a CAD job?");
		Submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String newDescription = DescriptionTextField.getText();
				
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/development?autoReconnect=true&useSSL=false", "root","xxxx");
					String query = "insert into design_cycles (Description, Parent_ID,Date_Created,CAD_TOF)" + " values (?, ?,?,?);";
					// create the mysql insert preparedstatement
					PreparedStatement preparedStmt = con.prepareStatement(query);
					preparedStmt.setString(1, newDescription);
					preparedStmt.setInt(2, id);
					preparedStmt.setDate(3, sqldate);
					preparedStmt.setBoolean(4, CadCheckBox.isSelected());
					// execute the preparedstatement
					preparedStmt.execute();
					con.close();
					//System.out.println();
				} catch (Exception e) {
					System.out.println(e);}
				stage.close();
				PublicUI.t.load();
				PublicUI.t.display();
				//designCycleGetter();
				System.out.println("Cycle Added");
			}
		});
		CycleRoot.setAlignment(Pos.CENTER);
		CycleRoot.getChildren().addAll(DescriptionLabel, DescriptionTextField,CadCheckBox, Submit);
		stage.setTitle("Add new Design Cycle");
		stage.setScene(new Scene(CycleRoot, 450, 600));
		stage.show();
	}

	void designCycleGetter() {
		this.dc.clear();
		try {
			// create our mysql database connection
			String myDriver = "org.gjt.mm.mysql.Driver";
			String myUrl = "jdbc:mysql://localhost:3306/development?autoReconnect=true&useSSL=false";
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, "root", "xxxx");
			// our SQL SELECT query.
			// if you only need a few columns, specify them by name instead of using "*"
			String query = "SELECT idDesign_Cycle, Description, Date_Created,CAD_TOF FROM design_cycles where Parent_ID=" + id +" AND Date_Completed is NULL";
			// create the java statement
			Statement st = conn.createStatement();
			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery(query);
			if (rs.isBeforeFirst()) {
				// iterate through the java resultset
				while (rs.next()) {
					String Description = rs.getString("Description");
					int dcID = rs.getInt("idDesign_Cycle");
					Date newDateCreated = rs.getDate("Date_Created");
					boolean newCadJob = rs.getBoolean("CAD_TOF");
					// print the results
					//System.out.format("%s, %s\n", dcID, Description);
					// Build ticket objects using this information
					DesignCycle TempDC = new DesignCycle(dcID, Description,newDateCreated,newCadJob);
					
					dc.add(TempDC);
				}
				st.close();
			} else {
				st.close();
			}
		} catch (Exception e) {
			System.err.println("Got an exception! In design cycle getter");
			System.err.println(e.getMessage());
		}

	}
	


}
