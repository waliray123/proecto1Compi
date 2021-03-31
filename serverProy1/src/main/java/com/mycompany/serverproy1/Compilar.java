/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serverproy1;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author user-ubunto
 */
@Path("/compilar")
public class Compilar {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String direBonjour() {
        return "Bonjour, tout le monde!";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String sayJsonHello() {
        NewServlet serv = new NewServlet();
        return "{\"name\":\"greeting\", \"message\":\"Bonjour tout le monde!\"}";
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String sayHTMLHello() {
        return "<html><title>Hello</title><body><h1>Bonjour, tout le monde!</h1><body></html>";
    }
}
