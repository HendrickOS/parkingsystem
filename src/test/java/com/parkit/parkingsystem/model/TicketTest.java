package com.parkit.parkingsystem.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TicketTest {

	private Ticket ticket = new Ticket();

	@Test
	public void getPrice() {
		double price = 500;

		ticket.setPrice(price);

		assertEquals(price, ticket.getPrice());
	}

	@Test
	public void getId() {
		int id = 0;

		ticket.setId(id);

		assertEquals(id, ticket.getId());
	}

}
