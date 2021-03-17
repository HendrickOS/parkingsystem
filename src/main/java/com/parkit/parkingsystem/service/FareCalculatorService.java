package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

	private TicketDAO ticketDAO;

	public FareCalculatorService(TicketDAO ticketDAO) {
		super();
		this.ticketDAO = ticketDAO;
	}

	public FareCalculatorService() {

		this.ticketDAO = new TicketDAO();

	}

	public void calculateFare(Ticket ticket) {
		if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
			throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
		}

		// Temps en millisecondes
		long timeIn = ticket.getInTime().getTime();
		long timeOut = ticket.getOutTime().getTime();

		double durationInMinutes = (timeOut - timeIn) / 1000 / 60;
		double durationInHours = durationInMinutes / 60;

		double priceCar = durationInHours * Fare.CAR_RATE_PER_HOUR;
		double priceBike = durationInHours * Fare.BIKE_RATE_PER_HOUR;

		if (durationInMinutes <= 30) {
			priceCar = 0;
			priceBike = 0;
		}

		/* new TicketDAO().isReccurent(ticket); */
		if (ticketDAO.isReccurent(ticket)) {
			priceBike = priceBike - (priceBike * 0.05);
			priceCar = priceCar - (priceCar * 0.05);
		}

		switch (ticket.getParkingSpot().getParkingType()) {
		case CAR: {
			ticket.setPrice(priceCar);
			break;
		}
		case BIKE: {
			ticket.setPrice(priceBike);
			break;
		}
		default:
			throw new IllegalArgumentException("Unkown Parking Type");
		}
	}
}