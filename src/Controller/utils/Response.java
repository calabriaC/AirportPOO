/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.utils;

/**
 *
 * @author Car
 */
public class Response {
     private String message;
    private int status;
    private Object object;

    public Response(String message, int status) {
        this.message = message;
        this.status = status;
    }
    
    public Response(String message, int status, Object object) {
        this.message = message;
        this.status = status;
        this.object = object;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public Object getObject() {
        return object;
    }
    //haciendo uso del patrón de diseño Prototype 
    @Override
    public Response clone() {
        try {
            return (Response) super.clone(); // shallow copy
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // no debería pasar
        }
    }
}
