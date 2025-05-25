/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Controller.utils.Response;
import Controller.utils.Status;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import model.Plane;
import model.storage.PlaneStorage;
import view.AirportFrame;

/**
 *
 * @author Car
 */
// Controlador encargado de la lógica relacionada con la gestión de aviones
public class PlaneController {

    private static PlaneStorage as; // Almacén que contiene todos los aviones registrados
    private static AirportFrame airportFrame; // Referencia a la interfaz principal de la aplicación

    // Constructor utilizado cuando se cargan datos desde archivos JSON y se requiere vinculación con la interfaz
    public PlaneController(PlaneStorage as, AirportFrame af) {
        this.as = as;
        this.airportFrame = af;
    }

    // Constructor vacío para instanciación sin parámetros (ej. para pruebas o uso simple)
    public PlaneController() {
    }

    // Crea un nuevo avión con los datos recibidos y lo añade a la lista de almacenamiento
    public void createPlane(String id, String brand, String model, int maxCapacity, String airline) {
        Plane plane = new Plane(id, brand, model, maxCapacity, airline);
        as.getPlanes().add(plane);
    }

    // Devuelve la lista completa de aviones registrados
    public static ArrayList<Plane> getPlanes() {
        return as.getPlanes();
    }

    // Busca y retorna un avión según su identificador. Si no lo encuentra, retorna un avión vacío
    public Plane getPlaneByID(String id) {
        for (Plane plane : as.getPlanes()) {
            if (plane.getId().equals(id)) {
                return plane;
            }
        }
        return new Plane("NA", "NA", "NA", 0, "NA");
    }

    // Transforma la lista de aviones en un modelo de tabla para ser mostrado en la interfaz gráfica
    public DefaultTableModel toPlanesJList() {
        String[] columnas = {"ID", "Brand", "Model", "Max Capacity", "Airline", "Number Flights"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);

        for (Plane p : as.getPlanes()) {
            Object[] fila = {
                p.getId(),
                p.getBrand(),
                p.getModel(),
                p.getMaxCapacity(),
                p.getAirline(),
                p.getNumFlights()
            };
            model.addRow(fila);
        }

        return model;
    }

    // Valida los datos proporcionados para registrar un nuevo avión. Si son correctos, se procede a su creación.
    public Response registerAirplane(String id, String brand, String model,
            int maxCapacity, String airline) {
        try {
            // Validación de campos vacíos
            if (id == null || id.isEmpty()
                    || brand == null || brand.isEmpty()
                    || model == null || model.isEmpty()
                    || airline == null || airline.isEmpty()) {
                return new Response("No text field should be empty", Status.BAD_REQUEST).clone();
            }

            // Validación del formato del ID (2 letras mayúsculas seguidas de 5 dígitos)
            if (!id.matches("^[A-Z]{2}[0-9]{5}$")) {
                return new Response("ID must follow the format XXYYYYY (2 uppercase letters followed by 5 digits)", Status.BAD_REQUEST).clone();
            }

            // Validación para evitar duplicados
            for (Plane a : as.getPlanes()) {
                if (a.getId().equals(id)) {
                    return new Response("Airplane ID already exists", Status.BAD_REQUEST).clone();
                }
            }

            // Verifica que la capacidad máxima esté dentro de un rango razonable
            if (maxCapacity <= 0 || maxCapacity > 1000) {
                return new Response("Max capacity must be between 1 and 1000", Status.BAD_REQUEST).clone();
            }

            // Si todas las validaciones se cumplen, se retorna respuesta de éxito (la creación se ejecuta fuera de esta función)
            return new Response("Airplane created successfully", Status.CREATED).clone();

        } catch (Exception ex) {
            // Captura de errores inesperados
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR).clone();
        }
    }
}


    


