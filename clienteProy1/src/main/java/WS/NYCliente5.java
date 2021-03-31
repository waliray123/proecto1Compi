/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WS;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

/**
 * Jersey REST client generated for REST resource:HelloResource [/bonjour]<br>
 * USAGE:
 * <pre>
 *        NYCliente5 client = new NYCliente5();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author user-ubunto
 */
public class NYCliente5 {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/HelloREST/rest";

    public NYCliente5() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("bonjour");
    }

    public String obtenerDB(String db) throws ClientErrorException {
        WebTarget resource = webTarget;
        if (db != null) {
            resource = resource.queryParam("db", db);
        }
        resource = resource.path("prueba");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }
    
    public String obtenerDBForms(String db) throws ClientErrorException {
        WebTarget resource = webTarget;
        if (db != null) {
            resource = resource.queryParam("db", db);
        }
        resource = resource.path("dbForms");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }
    
    public String obtenerFormulariosPorIdUsuario(String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        if (id != null) {
            resource = resource.queryParam("id", id);
        }
        resource = resource.path("forms");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }
    
    public String crearFormulario(String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        if (id != null) {
            resource = resource.queryParam("id", id);
        }
        resource = resource.path("createForm");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    public String addPerson(String entrada,String usuarioLog) throws ClientErrorException {
        Form form = new Form();
        form.param("entr", entrada);
        form.param("userLog", usuarioLog);
        String pruebaRetorno = webTarget.path("prueba").request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED_TYPE), String.class );
        return pruebaRetorno;
        //return webTarget.path("prueba").request(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED).post(Entity.form(form), String.class);
    }
    
    public String getFormPorId(String id) throws ClientErrorException {
        Form form = new Form();
        form.param("idF", id);        
        String pruebaRetorno = webTarget.path("getForm").request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED_TYPE), String.class );
        return pruebaRetorno;
        //return webTarget.path("prueba").request(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED).post(Entity.form(form), String.class);
    }
    
    public String cargarForm(String strF) throws ClientErrorException {
        Form form = new Form();
        form.param("strForm", strF);        
        String pruebaRetorno = webTarget.path("cargarForm").request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED_TYPE), String.class );
        return pruebaRetorno;
        //return webTarget.path("prueba").request(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED).post(Entity.form(form), String.class);
    }

    public void close() {
        client.close();
    }
    
}
