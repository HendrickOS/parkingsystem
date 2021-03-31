package com.parkit.parkingsystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;

public class TicketDAOTest {

	TicketDAO ticketDAO = new TicketDAO();

	@Test
	public void saveTicket() {
		// ARRANGE
		String vehicleRegNumber = "VEG" + UUID.randomUUID().toString().substring(0, 5);
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		Ticket ticket = new Ticket();
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		ticket.setVehicleRegNumber(vehicleRegNumber);

		// ACT
		ticketDAO.saveTicket(ticket);

		// ASSERT
		Ticket ticketResult = ticketDAO.getTicket(vehicleRegNumber);
		assertNotNull(ticketResult);
		assertEquals(inTime.getDate(), ticketResult.getInTime().getDate());
		assertEquals(outTime.getDate(), ticketResult.getOutTime().getDate());
		assertEquals(ticket.getParkingSpot(), ticketResult.getParkingSpot());
		assertEquals(ticket.getVehicleRegNumber(), ticketResult.getVehicleRegNumber());
	}

	@Test
	public void updateTicket() {
		// ARRANGE
		Date newInTime = new Date();
		newInTime.setTime(System.currentTimeMillis() - (180 * 60 * 1000));
		Date newOutTime = new Date();
		newOutTime.setTime(System.currentTimeMillis() - (120 * 60 * 1000));
		String vehicleRegNumber = "VEG" + UUID.randomUUID().toString().substring(0, 5);
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		Ticket ticket = new Ticket();
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		ticket.setVehicleRegNumber(vehicleRegNumber);
		ticketDAO.saveTicket(ticket);
		ticket.setInTime(newInTime);
		ticket.setOutTime(newOutTime);

		// ACT
		ticketDAO.updateTicket(ticket);

		// ASSERT
		Ticket ticketResult = ticketDAO.getTicket(vehicleRegNumber);
		assertNotNull(ticketResult);
		assertEquals(newInTime.getDate(), ticketResult.getInTime().getDate());
		assertEquals(newOutTime.getDate(), ticketResult.getOutTime().getDate());
	}

}
