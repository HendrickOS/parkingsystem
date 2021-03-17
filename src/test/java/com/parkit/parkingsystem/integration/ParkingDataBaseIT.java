package com.parkit.parkingsystem.integration;

import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {

	private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	@Mock
	private static ParkingSpotDAO parkingSpotDAO;
	@Mock
	private static TicketDAO ticketDAO;
	@Mock
	private static FareCalculatorService fareCalculatorService;
	@Mock
	private static Ticket ticket;

	private static DataBasePrepareService dataBasePrepareService;

	@Mock
	private static InputReaderUtil inputReaderUtil;

	@BeforeAll
	private static void setUp() throws Exception {
//		parkingSpotDAO = new ParkingSpotDAO();
//		parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
//		ticketDAO = new TicketDAO();
//		ticketDAO.dataBaseConfig = dataBaseTestConfig;
//		ticket = new Ticket();
//		fareCalculatorService = new FareCalculatorService();

		dataBasePrepareService = new DataBasePrepareService();
	}

	@BeforeEach
	private void setUpPerTest() throws Exception {
		when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
		dataBasePrepareService.clearDataBaseEntries();
	}

	@AfterAll
	private static void tearDown() {

	}

	@Test
	public void testParkingACar() {
		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		// ARRANGE
		when(inputReaderUtil.readSelection()).thenReturn(1);
		Mockito.when(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)).thenReturn(15);

		// ACT
		parkingService.processIncomingVehicle();

		// ASSERT
		Mockito.verify(ticketDAO).saveTicket(Mockito.any(Ticket.class));
		Mockito.verify(parkingSpotDAO).updateParking(Mockito.any(ParkingSpot.class));

		// TODO: check that a ticket is actually saved in DB and Parking table is
		// updated
		// with availability
	}

	@Test
	public void testParkingLotExit() {
		// GIVEN
//		testParkingACar();
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		Ticket ticket = new Ticket();
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);

		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		Mockito.when(ticketDAO.getTicket("ABCDEF")).thenReturn(ticket);
		Mockito.when(ticketDAO.updateTicket(ticket)).thenReturn(true);

		// WHEN
		parkingService.processExitingVehicle();

		// THEN
		Mockito.verify(parkingSpotDAO).updateParking(Mockito.any(ParkingSpot.class));
		Mockito.verify(ticketDAO).updateTicket(ticket);

		// TODO: check that the fare generated and out time are populated correctly in
		// the database
	}

}
