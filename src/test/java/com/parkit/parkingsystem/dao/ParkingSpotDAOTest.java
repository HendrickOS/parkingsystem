package com.parkit.parkingsystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;

public class ParkingSpotDAOTest {

	ParkingSpotDAO parkingSpotDAO = new ParkingSpotDAO();

	@Test
	public void getNextAvailableSlotTest() {
		// ARRANGE
		ParkingType parkingType = ParkingType.CAR;
		String vehicleRegNumber = "VEG" + UUID.randomUUID().toString().substring(0, 5);
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, parkingType, false);
		Ticket ticket = new Ticket();
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		ticket.setVehicleRegNumber(vehicleRegNumber);

		// ACT
		parkingSpotDAO.getNextAvailableSlot(parkingType);

		// ASSERT
		assertEquals(1, parkingSpot.getId());
	}

	@Test
	public void updateParkingTest() {
		// ARRANGE
		ParkingType parkingType = ParkingType.CAR;
		String vehicleRegNumber = "VEG" + UUID.randomUUID().toString().substring(0, 5);
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, parkingType, false);
		Ticket ticket = new Ticket();
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		ticket.setVehicleRegNumber(vehicleRegNumber);

		// ACT
		parkingSpotDAO.updateParking(parkingSpot);

		// ASSERT
		assertEquals(1, parkingSpot.getId());
		assertEquals(false, parkingSpot.isAvailable());
	}

}
