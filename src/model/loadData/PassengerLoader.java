/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.loadData;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import model.Passenger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Car
 */
public class PassengerLoader {

    public ArrayList<Passenger> loadFromJsonFile(String path) throws IOException {
        String json = Files.readString(Paths.get(path), StandardCharsets.UTF_8);
        return parsePassengers(json);
    }

    private ArrayList<Passenger> parsePassengers(String json) {
        ArrayList<Passenger> list = new ArrayList<>();
        JSONArray array = new JSONArray(json);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);

            long id = obj.getLong("id");
            String firstname = obj.getString("firstname");
            String lastname = obj.getString("lastname");
            long phoneNumber = obj.getLong("phone");
            LocalDate birthdate = LocalDate.parse(obj.getString("birthDate"), formatter);
            int countryPhoneCode = obj.getInt("countryPhoneCode");
            String country = obj.getString("country");

            list.add(new Passenger(id, firstname, lastname, birthdate, countryPhoneCode, phoneNumber, country));
        }

        return list;
    }
}
    

