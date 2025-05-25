/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.loadData;

import java.util.ArrayList;
import model.Flight;
import model.Location;
import model.Passenger;
import model.Plane;
import model.storage.FlightStorage;
import model.storage.LocationStorage;
import model.storage.PassengerStorage;
import model.storage.PlaneStorage;

/**
 *
 * @author Car
 */
public class DataInitializer {

    private PassengerStorage passengerStorage;
    private PlaneStorage planeStorage;
    private LocationStorage locationStorage;
    private FlightStorage flightStorage;

    public void initAll() {
        try {
            // 1. Cargar pasajeros
            PassengerLoader passengerLoader = new PassengerLoader();
            ArrayList<Passenger> passengers = passengerLoader.loadFromJsonFile("json/passengers.json");
            passengerStorage = new PassengerStorage(passengers);

            // 2. Cargar aviones
            PlaneLoader planeLoader = new PlaneLoader();
            ArrayList<Plane> planes = planeLoader.loadFromJsonFile("json/planes.json");
            planeStorage = new PlaneStorage(planes);

            // 3. Cargar localizaciones
            LocationLoader locationLoader = new LocationLoader();
            ArrayList<Location> locations = locationLoader.loadFromJsonFile("json/locations.json");
            locationStorage = new LocationStorage(locations);

            // 4. Cargar vuelos
            FlightLoader flightLoader = new FlightLoader();
            ArrayList<Flight> flights = flightLoader.loadFromJsonFile("json/flights.json", planes, locations);
            flightStorage = new FlightStorage(flights);

        } catch (Exception e) {
            System.err.println(" Error durante la carga de datos: " + e.getMessage());
            e.printStackTrace();

            passengerStorage = new PassengerStorage(new ArrayList<>());
            planeStorage = new PlaneStorage(new ArrayList<>());
            locationStorage = new LocationStorage(new ArrayList<>());
            flightStorage = new FlightStorage(new ArrayList<>());
        }
    }

    public PassengerStorage getPassengerStorage() {
        return passengerStorage;
    }

    public PlaneStorage getPlaneStorage() {
        return planeStorage;
    }

    public LocationStorage getLocationStorage() {
        return locationStorage;
    }

    public FlightStorage getFlightStorage() {
        return flightStorage;
    }
}
