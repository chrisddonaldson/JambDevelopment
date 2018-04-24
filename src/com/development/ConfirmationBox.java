package com.development;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConfirmationBox {
	
	static boolean alert(String message) {
	Alert alert = new Alert(AlertType.CONFIRMATION);
	alert.setTitle("Confirm action");
	alert.setHeaderText(message);
	alert.setContentText("Please select an answer or close this dialogue.");

	Optional<ButtonType> result = alert.showAndWait();
	if (result.get() == ButtonType.OK){
	    return true;
	} else {
		return false;
	}
	}
}
