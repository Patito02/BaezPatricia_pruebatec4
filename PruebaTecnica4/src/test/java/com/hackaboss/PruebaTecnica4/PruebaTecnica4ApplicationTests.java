package com.hackaboss.PruebaTecnica4;

import com.hackaboss.PruebaTecnica4.controller.FlightController;
import com.hackaboss.PruebaTecnica4.controller.HotelController;
import com.hackaboss.PruebaTecnica4.dto.FlightDTO;
import com.hackaboss.PruebaTecnica4.dto.HotelDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PruebaTecnica4ApplicationTests {

	@Autowired
	private HotelController hotelController;
	@Autowired
	private FlightController flightController;

	@Test
	public void testGetAllHotels() {
		List<HotelDTO> hotels = hotelController.getAllHotels();
		assertEquals(2, hotels.size()); // Verifica que la lista de hoteles tenga el tamaño esperado

	}

	@Test
	public void testGetAllFlights() {
		List<FlightDTO> hotels = flightController.getAllFlights();
		assertEquals(3, hotels.size()); // Verifica que la lista de vuelos tenga el tamaño esperado

	}

}
