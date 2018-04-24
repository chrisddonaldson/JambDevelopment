package com.development;

import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class PublicUI{
	  static FlowPane TicketSpace = new FlowPane(30,30);
	  
      static TicketHandler t = new TicketHandler();
      
      static String SortType = "";
      
      static Label DaysWaitingSum = new Label();
      
      static VBox ButtonStack = new VBox(30);
      
      static boolean launch = false;
}
