/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import com.formdev.flatlaf.FlatDarkLaf;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import model.loadData.DataInitializer;
import model.storage.FlightStorage;
import model.storage.LocationStorage;
import model.storage.PassengerStorage;
import model.storage.PlaneStorage;
import view.AirportFrame;

/**
 *
 * @author Car
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    // metodo main para que no se inicialice en la vista
    public static void main(String[] args) {
        // Establecer estilo visual (puedes cambiar FlatDarkLaf por otro si prefieres)
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("️ Failed to initialize LaF");
        }

        // Ejecutar interfaz gráfica en el hilo de eventos
        java.awt.EventQueue.invokeLater(() -> {
            try {
                //  Cargar datos del sistema
                DataInitializer initializer = new DataInitializer();
                initializer.initAll();

                //  Obtener instancias de almacenamiento
                PassengerStorage ps = initializer.getPassengerStorage();
                PlaneStorage as = initializer.getPlaneStorage();
                LocationStorage ls = initializer.getLocationStorage();
                FlightStorage fs = initializer.getFlightStorage();

                //  Crear la ventana principal con los datos cargados
                new AirportFrame(ps, as, ls, fs).setVisible(true);

            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
