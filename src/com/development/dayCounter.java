package com.development;

public class dayCounter {

	
	static double sumDates() {
		double totalDays = 0;
		for( int i =0; i<PublicUI.t.TicketList.size(); i++) {
			Ticket tempt = PublicUI.t.TicketList.get(i);
			if (tempt.dc.size()>0) {
				for(int j = 0; j<tempt.dc.size(); j++) {
					DesignCycle tempdc = tempt.dc.get(j);
					totalDays = totalDays + dateCalcs.getDayDiff(tempdc.dateCreated);
				}
			}
			else {}
		}
	return totalDays;
	}
}
