/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Controller.utils.Response;
import Controller.utils.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import model.Flight;
import model.Passenger;
import model.storage.FlightStorage;
import model.storage.PassengerStorage;
import view.AirportFrame;

/**
 *
 * @author Car
 */
public class FlightController {

// Controlador responsable de gestionar la lógica relacionada con vuelos (Flight)
    private static FlightStorage fs; // Repositorio estático de vuelos
    private static PassengerStorage ps; // Repositorio estático de pasajeros
    private static AirportFrame airportFrame; // Referencia a la interfaz gráfica principal

    // Controladores auxiliares para obtener aviones y ubicaciones
    PlaneController ac = new PlaneController();
    LocationController lc = new LocationController();

    // Constructor que se utiliza cuando se cargan datos desde archivos JSON
    public FlightController(FlightStorage fs, PassengerStorage ps, AirportFrame af) {
        this.fs = fs;
        this.ps = ps;
        this.airportFrame = af;
    }

    // Constructor vacío para casos donde se inicializa sin cargar datos
    public FlightController() {
    }

    // Crea una instancia de vuelo usando los datos proporcionados por la interfaz
    public Flight createFlight(String flightID, String planeID, String departureLocationId, String arrivalLocationId, LocalDateTime departureDate, int hoursDurationArrival, int minutesDurationArrival) {
        Flight flight = new Flight(flightID, ac.getPlaneByID(flightID), lc.getLocationByID(departureLocationId), lc.getLocationByID(arrivalLocationId), departureDate, hoursDurationArrival, minutesDurationArrival);
        fs.getFlights().add(flight); // Agrega el vuelo a la lista de vuelos almacenados
        return flight;
    }

    // Devuelve todos los vuelos registrados
    public static ArrayList<Flight> sendFlights() {
        return fs.getFlights();
    }

    // Transforma los vuelos a un modelo de tabla para ser visualizados en la interfaz
    public DefaultTableModel toFlightsJList() {
        String[] columnas = {"ID", "Departure Airport ID", "Arrival Airport ID", "Scale airport", "Departure Date", "Arrival Date", "Plane ID", "Number Passengers"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);

        for (Flight f : fs.getFlights()) {
            Object[] fila = {
                f.getId(),
                f.getDepartureLocation().getAirportId(),
                f.getArrivalLocation().getAirportId(),
                (f.getScaleLocation() != null) ? f.getScaleLocation().getAirportCity() : "NA",
                f.getDepartureDate(),
                f.calculateArrivalDate(),
                f.getPlane().getId(),
                f.getNumPassengers()
            };
            model.addRow(fila);
        }

        return model;
    }

    // Asocia un pasajero a un vuelo específico según sus identificadores
    public Response addPassengerToFlight(String flightId, int passengerIdSearch) {
        for (Passenger p : ps.getPassengers()) {
            if (p.getId() == passengerIdSearch) {
                for (Flight f : fs.getFlights()) {
                    if (f.getId().equals(flightId)) {
                        f.addPassenger(p);
                        p.addFlight(f);
                        return new Response("Passenger added to flight", Status.OK).clone();
                    }
                }
            }
        }
        return new Response("Passenger id not found", Status.NOT_FOUND).clone();
    }

    // Verifica si un pasajero tiene vuelos registrados y devuelve un mensaje correspondiente
    public Response getPassengerFlightsResponse(String idSearch) {
        for (Passenger p : ps.getPassengers()) {
            if (String.valueOf(p.getId()).equals(idSearch)) {
                if (p.getFlights().isEmpty()) {
                    return new Response("Passenger has no flights", Status.NOT_FOUND).clone();
                }
            }
        }
        return new Response("Showing flights", Status.OK).clone();
    }

    // Crea un modelo de tabla con los vuelos asociados a un pasajero dado
    public DefaultTableModel getPassengerFlights(String idSearch) {
        String[] columnas = {"ID", "Departure Date", "Arrival Date"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);

        for (Passenger p : ps.getPassengers()) {
            if (String.valueOf(p.getId()).equals(idSearch)) {
                for (Flight f : p.getFlights()) {
                    Object[] fila = {f.getId(), f.getDepartureDate(), f.calculateArrivalDate()};
                    model.addRow(fila);
                }
                break;
            }
        }

        return model;
    }

    // Crea un modelo para llenar el comboBox de vuelos disponibles
    public DefaultComboBoxModel<String> getFlightModel() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (Flight f : fs.getFlights()) {
            model.addElement(f.getId());
        }
        return model;
    }

    // Crea un modelo con los IDs de los pasajeros para mostrar en un comboBox
    public DefaultComboBoxModel<String> getUserModel() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (Passenger p : ps.getPassengers()) {
            model.addElement(String.valueOf(p.getId()));
        }
        return model;
    }

    // Aplica un retraso en horas y minutos a un vuelo específico
    public Response delayFlight(String flightsId, int hours, int minutes) {
        for (Flight f : fs.getFlights()) {
            if (flightsId.equals(f.getId())) {
                f.delay(hours, minutes);
                return new Response("Flight delayed successfully", Status.OK).clone();
            }
        }
        return new Response("Flight not found", Status.BAD_REQUEST).clone();
    }

    // Registra un nuevo vuelo con validaciones de entrada
    public Response registerFlight(String id, String planeId, String departureLocationId, String scaleLocationId,
            String arrivalLocationId, LocalDateTime departureDate,
            int hoursDurationArrival, int minutesDurationArrival,
            int hoursDurationScale, int minutesDurationScale) {
        try {
            // Verifica que los campos obligatorios no estén vacíos
            if (id == null || id.isEmpty() || planeId == null || departureLocationId == null || arrivalLocationId == null || departureDate == null) {
                return new Response("No field should be null or empty", Status.BAD_REQUEST).clone();
            }

            // Evita duplicidad en IDs de vuelos
            for (Flight f : fs.getFlights()) {
                if (f.getId().equals(id)) {
                    return new Response("Flight ID already exists", Status.BAD_REQUEST).clone();
                }
            }

            // Asegura que la fecha de salida no sea pasada
            if (departureDate.isBefore(LocalDateTime.now())) {
                return new Response("Departure date must be in the future", Status.BAD_REQUEST).clone();
            }

            // Controla que la duración del vuelo sea válida
            if (hoursDurationArrival < 0 || minutesDurationArrival < 0 || (hoursDurationArrival == 0 && minutesDurationArrival == 0)) {
                return new Response("Flight duration must be greater than 00:00", Status.BAD_REQUEST).clone();
            }

            // Valida la escala solo si se proporciona una ubicación de escala
            if ((hoursDurationScale < 0 || minutesDurationScale < 0)
                    || (hoursDurationScale == 0 && minutesDurationScale == 0 && scaleLocationId != null)) {
                return new Response("Scale duration must be greater than 00:00 if scale location is provided", Status.BAD_REQUEST).clone();
            }

            // Construcción del nuevo vuelo incluyendo escala (si aplica)
            Flight newFlight = new Flight(id, ac.getPlaneByID(planeId), lc.getLocationByID(departureLocationId), lc.getLocationByID(scaleLocationId), lc.getLocationByID(arrivalLocationId),
                    departureDate, hoursDurationArrival, minutesDurationArrival,
                    hoursDurationScale, minutesDurationScale);

            fs.getFlights().add(newFlight); // Se guarda el vuelo en el sistema

            return new Response("Flight created successfully", Status.CREATED).clone();
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR).clone();
        }
    }
}


