package com.parkit.parkingsystem.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.constants.ParkingType;

public class ParkingSpotTest {

	private ParkingType CAR;
	private int number = 0;
	private boolean isAvailable = false;
	private ParkingSpot parkingSpot = new ParkingSpot(number, CAR, isAvailable);

	@Test
	public void isThereAvailableSpot() {

		parkingSpot.setAvailable(isAvailable);

		assertEquals(isAvailable, parkingSpot.isAvailable());
	}

	@Test
	public void getVehicleType() {

		parkingSpot.setParkingType(CAR);

		assertEquals(CAR, parkingSpot.getParkingType());
	}

	@Test
	public void getId() {
		int id = number;

		parkingSpot.setId(id);

		assertEquals(id, parkingSpot.getId());
	}

	@Test
	public void hashcode() {

		parkingSpot.hashCode();

		assertEquals(number, parkingSpot.hashCode());
	}

	@Test
	public void areEquals() {

		assertEquals(false, parkingSpot.equals(null));
		assertEquals(true, parkingSpot.equals(parkingSpot));

	}

}