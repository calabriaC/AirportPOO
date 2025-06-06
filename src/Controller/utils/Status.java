/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.utils;

/**
 *
 * @author Car
 */
public abstract class Status {
    // respuesta exitosa

    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int NO_CONTENT = 204;

    // respuesta error del cliente
    public static final int BAD_REQUEST = 400;
    public static final int NOT_FOUND = 404;

    // respuesta error del servidor
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int NOT_IMPLEMENTED = 501;

}
