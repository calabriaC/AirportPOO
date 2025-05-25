/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Controller.utils.Response;
import Controller.utils.Status;
import java.io.IOException;
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
    private static PassengerStorage ps;
    private static AirportFrame airportFrame;
    public PassengerController(String a, AirportFrame af) throws IOException {
        ps = new PassengerStorage();
        airportFrame = af;
    }
    
    public PassengerController() {
        
    }
        //creando un passeger y agregandolo a la lista de passengers en storage
    public void createPassenger (long id, String firstname, String lastname, LocalDate birthDate, int countryPhoneCode, long phone, String country) {
        Passenger passenger = new Passenger(id, firstname, lastname, birthDate, countryPhoneCode, phone, country);
        ps.getPassengers().add(passenger);
    } 
    // envia passengers
    public static ArrayList<Passenger> sendPassengers() {
        return ps.getPassengers();
    }
    
    //metodo para modificar la información de un passenger si se encentra el id ingresado
    public void modifiePassengerInformation(long id, String firstname, String lastname, LocalDate birthDate, int countryPhoneCode, long phone, String country){
        ArrayList <Passenger> passengers = ps.getPassengers();
        for (Passenger passenger : passengers) {
            if (id == passenger.getId()) {
                passenger.setFirstname(firstname);
                passenger.setLastname(lastname);
                passenger.setBirthDate(birthDate);
                passenger.setCountryPhoneCode(countryPhoneCode);
                passenger.setPhone(phone);
                passenger.setCountry(country);
                break;
            } else {
                System.out.println("No se encontro un passenger con el id " +id);
            }  
        }
    }
    
    //funcion que devuelve un modelo para poner a la lista de pasajeron en la interfaz
    public DefaultTableModel toPassengersJList() {              
        String[] columnas = {"ID", "Name", "Birthdate", "Age", "Phone", "Country", "Num Flight"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0); //modelo para ser devuelto
        
        if (ps.getPassengers().isEmpty()) {
            System.out.println("Lista de pasajeros vacia");
        } else {
            for (Passenger p : ps.getPassengers()) {
                Object[] fila = new Object[] { //objeto para poner en el modelo
                  p.getId(), p.getFirstname(), String.valueOf(p.getBirthDate()), p.calculateAge(), p.getPhone(), p.getCountry(), p.getNumFlights()
                };
                model.addRow(fila);
            } 
        }
        return model;
    }
    // metodo que verifica si se puede registrar un passenger, si es el caso se usa una funcion llamada createPassenger que esta arriba
    public Response registerPassenger(long id, String firstname, String lastname,
                                  int day, int month, int year,
                                  int phoneCode, long phone, String country) {
    try {
        // Validación: campos de texto vacíos
        if (firstname == null || firstname.isEmpty() ||
            lastname == null || lastname.isEmpty() ||
            country == null || country.isEmpty()) {
            Response b= new Response("No text field should be empty", Status.BAD_REQUEST);
            return b.clone();
        }

        // Validación de ID
        if (id < 0 || String.valueOf(id).length() > 15) {
            Response b=new Response("ID must be at least and at most 15 digits", Status.BAD_REQUEST);
            return b.clone();
        }

        // Verificación de duplicado
        for (Passenger p : ps.getPassengers()) {
            if (p.getId() == id) {
                Response b = new Response("Passenger ID already exists", Status.BAD_REQUEST);
                return b.clone();
            }
        }

        // Validación del código de teléfono
        if (phoneCode < 0 || String.valueOf(phoneCode).length() > 3) {
            Response b= new Response("Phone code must be at least 0 and at most 3 digits", Status.BAD_REQUEST);
            return b.clone();
        }

        // Validación del número de teléfono
        if (phone < 0 || String.valueOf(phone).length() > 11) {
            Response b = new Response("Phone number must be at least 0 and less than 11 digits", Status.BAD_REQUEST);
            return b.clone();
        }

        // Validación de la fecha de nacimiento
        try {
            if (year < 1910 || year > 2025) {
                Response b=new Response("Invalid birth year", Status.BAD_REQUEST);
                return b.clone();
            }
            LocalDate birthDate = LocalDate.of(year, month, day); // Esto lanza excepción si la fecha es inválida
        } catch (DateTimeException e) {
            Response b =new Response("Invalid birthdate", Status.BAD_REQUEST);
            return b.clone();
        }

        
        Response b = new Response("Passenger created successfully", Status.CREATED);
        return b.clone();
    } catch (Exception ex) {
        Response b= new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        return b.clone();
    }
}
    
// metodo para modificar la información de un passenger si se encuentra el id ingresado
public Response modifyInfo(long id, String firstname, String lastname,
                                  int day, int month, int year,
                                  int phoneCode, long phone, String country){
   boolean founded = false;
         for (Passenger passenger : ps.getPassengers()) {
            
        if (id == passenger.getId()) {
                passenger.setFirstname(firstname);
                passenger.setLastname(lastname);
                LocalDate birthDate = LocalDate.of(year, month, day);
                passenger.setBirthDate(birthDate);
                passenger.setCountryPhoneCode(phoneCode);
                passenger.setPhone(phone);
                passenger.setCountry(country);
               founded=true;
                break;
            } 
               
    }if (founded) {
       Response b= new Response("Passenger updated successfully", Status.OK);
        return b.clone();
    }
  Response b=new Response ("Passenger id not found",Status.NOT_FOUND);
   return b.clone();
}
    
}
