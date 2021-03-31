/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clienteproy1;

import GUIUser.UserEditor;
import WS.NYCliente5;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.client.ClientConfig;

/**
 *
 * @author user-ubunto
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        String uri = "http://localhost:8080/HelloREST/rest/bonjour";
//        ClientConfig config = new ClientConfig();
//        Client client = ClientBuilder.newClient(config);
//        WebTarget target = client.target(uri);
//
//        String response = target.request().accept(MediaType.APPLICATION_JSON).get(String.class);
//
//        System.out.println(response);
        
        UserEditor newUserEditor = new UserEditor();
        newUserEditor.setVisible(true);


//      CODIGO QUE FUNCIONA
//        NYCliente5 nyClient = new NYCliente5();                  
//        System.out.println("GET PERSONA");
//        String response = nyClient.obtenerDB("asdf");
//        System.out.println(response);
//        String response2 = nyClient.obtenerDB("NUEVO USUARIO");
//        System.out.println(response2);
    }

}
