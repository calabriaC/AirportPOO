/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Controller.utils.Response;
import Controller.utils.Status;
import javax.swing.table.DefaultTableModel;
import model.Location;
import model.storage.LocationStorage;
import view.AirportFrame;

/**
 *
 * @author Car
 */
public class LocationController {

    // Controlador encargado de manejar las operaciones relacionadas con ubicaciones (aeropuertos)
    private static LocationStorage ls; // Almacén de todas las ubicaciones
    private static AirportFrame airportFrame; // Referencia a la interfaz gráfica principal

    // Constructor para cargar ubicaciones desde archivo JSON
    public LocationController(LocationStorage ls, AirportFrame af) {
        this.ls = ls;
        this.airportFrame = af;
    }

    // Constructor vacío para inicializaciones sin parámetros
    public LocationController() {
    }

    // Crea una nueva ubicación y la agrega al almacenamiento
    public Location createLocation(String airportID, String airportName, String city, String country, double latitude, double longitude) {
        Location location = new Location(airportID, airportName, city, country, latitude, longitude);
        ls.getLocations().add(location);
        return location;
    }

    // Busca y retorna una ubicación según su ID. Si no se encuentra, retorna una ubicación genérica "NA"
    public Location getLocationByID(String id) {
        for (Location location : ls.getLocations()) {
            if (location.getAirportId().equals(id)) {
                return location;
            }
        }
        return new Location("NA", "NA", "NA", "NA", 0, 0);
    }

    // Genera el modelo de datos para visualizar todas las ubicaciones en una tabla de la interfaz
    public DefaultTableModel toLocationsJList() {
        String[] columnas = {"Airport ID", "Airport Name", "City", "Contry"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);

        for (Location l : ls.getLocations()) {
            Object[] fila = {
                l.getAirportId(),
                l.getAirportName(),
                l.getAirportCity(),
                l.getAirportCountry()
            };
            model.addRow(fila);
        }

        return model;
    }

    // Verifica si un valor decimal tiene máximo cuatro cifras decimales
    private boolean hasMaxFourDecimals(double value) {
        String[] parts = String.valueOf(value).split("\\.");
        return parts.length < 2 || parts[1].length() <= 4;
    }

    // Valida todos los datos necesarios para registrar una nueva ubicación
    public Response registerLocation(String airportID, String airportName, String city, String country,
            double latitude, double longitude) {
        try {
            // Verifica que no haya campos de texto vacíos
            if (airportID == null || airportID.isEmpty()
                    || airportName == null || airportName.isEmpty()
                    || city == null || city.isEmpty()
                    || country == null || country.isEmpty()) {
                return new Response("No text field should be empty", Status.BAD_REQUEST).clone();
            }

            // Validación del formato del ID del aeropuerto: exactamente 3 letras mayúsculas
            if (!airportID.matches("^[A-Z]{3}$")) {
                return new Response("Airport ID must be exactly 3 uppercase letters", Status.BAD_REQUEST).clone();
            }

            // Evita duplicados comprobando si ya existe un ID igual
            for (Location loc : ls.getLocations()) {
                if (loc.getAirportId().equalsIgnoreCase(airportID)) {
                    return new Response("Airport ID already exists", Status.BAD_REQUEST).clone();
                }
            }

            // Validación del rango permitido para la latitud
            if (latitude < -90 || latitude > 90) {
                return new Response("Latitude must be in range [-90, 90]", Status.BAD_REQUEST).clone();
            }

            // Validación de precisión decimal de la latitud
            if (!hasMaxFourDecimals(latitude)) {
                return new Response("Latitude must have at most 4 decimal places", Status.BAD_REQUEST).clone();
            }

            // Validación del rango permitido para la longitud
            if (longitude < -180 || longitude > 180) {
                return new Response("Longitude must be in range [-180, 180]", Status.BAD_REQUEST).clone();
            }

            // Validación de precisión decimal de la longitud
            if (!hasMaxFourDecimals(longitude)) {
                return new Response("Longitude must have at most 4 decimal places", Status.BAD_REQUEST).clone();
            }

            // Si todo es válido, devuelve una respuesta de éxito
            return new Response("Location created successfully", Status.CREATED).clone();

        } catch (Exception ex) {
            // Captura de cualquier error inesperado
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR).clone();
        }
    }
}



    

