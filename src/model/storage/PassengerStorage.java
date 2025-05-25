/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.storage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import model.Passenger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Car
 */
public class PassengerStorage {
    
  private ArrayList<Passenger> passengers;

    public PassengerStorage() {
        this.passengers = new ArrayList<>();

        try {
            String json = Files.readString(Paths.get("C:\\Users\\Car\\Desktop\\AirportPOO\\json\\passengers.json"), StandardCharsets.UTF_8);
            this.passengers = readPassengers(json);
            this.passengers.sort(Comparator.comparing(Passenger::getId));
        } catch (Exception e) {
            System.err.println("❌ Error al cargar pasajeros: " + e.getMessage());
            this.passengers = new ArrayList<>();
        }
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    private ArrayList<Passenger> readPassengers(String json) {
        ArrayList<Passenger> list = new ArrayList<>();
        JSONArray array = new JSONArray(json);

        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);

            long id = obj.getLong("id");
            String firstname = obj.getString("firstname");
            String lastname = obj.getString("lastname");

            // ✅ Convertir String a LocalDate (formato ISO: yyyy-MM-dd)
            LocalDate birthDate = LocalDate.parse(obj.getString("birthDate"), DateTimeFormatter.ISO_DATE);

            int countryPhoneCode = obj.getInt("countryPhoneCode"); 
            long phone = obj.getLong("phone");
            String country = obj.getString("country");

            // Constructor con LocalDate
            Passenger p = new Passenger(id, firstname, lastname, birthDate, countryPhoneCode, phone, country);
            list.add(p);
        }

        return list;
    }
    
}
