/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.utils;

/**
 *
 * @author Car
 */
public class Response implements Cloneable {
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
    //
    @Override
    public Response clone() {
        try {
            return (Response) super.clone(); 
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); 
        }
    }
}
