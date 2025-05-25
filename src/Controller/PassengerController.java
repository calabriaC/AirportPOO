/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Controller.utils.Response;
import Controller.utils.Status;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import model.Passenger;
import model.storage.PassengerStorage;
import view.AirportFrame;

/**
 *
 * @author Car
 */
public class PassengerController {
    //dos contructores, uno para la carga del .json y el otro para agregar datos con la interfaz
// Controlador encargado de gestionar la lógica relacionada con los pasajeros


    private static PassengerStorage ps; // Almacén de pasajeros del sistema
    private static AirportFrame airportFrame; // Referencia a la interfaz gráfica principal

    // Constructor utilizado cuando se inyectan dependencias desde otra parte del sistema
    public PassengerController(PassengerStorage ps, AirportFrame af) {
        this.ps = ps;
        this.airportFrame = af;
    }

    // Constructor vacío para uso en contextos donde no se pasan parámetros
    public PassengerController() {
    }

    // Crea un nuevo pasajero y lo añade a la lista almacenada
    public void createPassenger(long id, String firstname, String lastname, LocalDate birthDate, int countryPhoneCode, long phone, String country) {
        Passenger passenger = new Passenger(id, firstname, lastname, birthDate, countryPhoneCode, phone, country);
        ps.getPassengers().add(passenger);
    }

    // Retorna la lista completa de pasajeros almacenados
    public static ArrayList<Passenger> sendPassengers() {
        return ps.getPassengers();
    }

    // Modifica los datos de un pasajero si se encuentra su ID en la lista
    public void modifiePassengerInformation(long id, String firstname, String lastname, LocalDate birthDate, int countryPhoneCode, long phone, String country) {
        for (Passenger passenger : ps.getPassengers()) {
            if (id == passenger.getId()) {
                passenger.setFirstname(firstname);
                passenger.setLastname(lastname);
                passenger.setBirthDate(birthDate);
                passenger.setCountryPhoneCode(countryPhoneCode);
                passenger.setPhone(phone);
                passenger.setCountry(country);
                break;
            } else {
                System.out.println("No se encontró un passenger con el id " + id);
            }
        }
    }

    // Construye un modelo de tabla con los datos de todos los pasajeros, para mostrar en la interfaz
    public DefaultTableModel toPassengersJList() {
        String[] columnas = {"ID", "Name", "Birthdate", "Age", "Phone", "Country", "Num Flight"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);

        for (Passenger p : ps.getPassengers()) {
            Object[] fila = {
                p.getId(),
                p.getFirstname(),
                String.valueOf(p.getBirthDate()),
                p.calculateAge(),
                p.getPhone(),
                p.getCountry(),
                p.getNumFlights()
            };
            model.addRow(fila);
        }

        return model;
    }

    // Valida los datos de entrada antes de registrar un nuevo pasajero
    public Response registerPassenger(long id, String firstname, String lastname,
                                      int day, int month, int year,
                                      int phoneCode, long phone, String country) {
        try {
            // Validación de campos obligatorios
            if (firstname == null || firstname.isEmpty()
                    || lastname == null || lastname.isEmpty()
                    || country == null || country.isEmpty()) {
                return new Response("No text field should be empty", Status.BAD_REQUEST).clone();
            }

            // Verifica que el ID sea válido y no tenga más de 15 dígitos
            if (id < 0 || String.valueOf(id).length() > 15) {
                return new Response("ID must be at least and at most 15 digits", Status.BAD_REQUEST).clone();
            }

            // Verificación de ID duplicado
            for (Passenger p : ps.getPassengers()) {
                if (p.getId() == id) {
                    return new Response("Passenger ID already exists", Status.BAD_REQUEST).clone();
                }
            }

            // Validación del código telefónico del país
            if (phoneCode < 0 || String.valueOf(phoneCode).length() > 3) {
                return new Response("Phone code must be at least 0 and at most 3 digits", Status.BAD_REQUEST).clone();
            }

            // Validación del número de teléfono
            if (phone < 0 || String.valueOf(phone).length() > 11) {
                return new Response("Phone number must be at least 0 and less than 11 digits", Status.BAD_REQUEST).clone();
            }

            // Verificación de fecha de nacimiento válida y en un rango aceptable
            try {
                if (year < 1910 || year > 2025) {
                    return new Response("Invalid birth year", Status.BAD_REQUEST).clone();
                }
                LocalDate birthDate = LocalDate.of(year, month, day); // Puede lanzar excepción si la fecha no existe
            } catch (DateTimeException e) {
                return new Response("Invalid birthdate", Status.BAD_REQUEST).clone();
            }

            return new Response("Passenger created successfully", Status.CREATED).clone();
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR).clone();
        }
    }

    // Actualiza los datos de un pasajero existente con los nuevos valores
    public Response modifyInfo(long id, String firstname, String lastname,
                               int day, int month, int year,
                               int phoneCode, long phone, String country) {
        boolean founded = false;

        for (Passenger passenger : ps.getPassengers()) {
            if (id == passenger.getId()) {
                passenger.setFirstname(firstname);
                passenger.setLastname(lastname);
                passenger.setBirthDate(LocalDate.of(year, month, day));
                passenger.setCountryPhoneCode(phoneCode);
                passenger.setPhone(phone);
                passenger.setCountry(country);
                founded = true;
                break;
            }
        }

        if (founded) {
            return new Response("Passenger updated successfully", Status.OK).clone();
        }
        return new Response("Passenger id not found", Status.NOT_FOUND).clone();
    }
}



