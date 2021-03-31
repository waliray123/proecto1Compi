/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user-ubunto
 */
public class GuardarDatos {

    private UsuariosFormularios userForms;
    private List<FormularioHTML> formulariosHTML;

    public GuardarDatos() {
        userForms = new UsuariosFormularios();
        formulariosHTML = new ArrayList<>();
        //crearArchivo();
    }

    public UsuariosFormularios getUserForms() {
        return userForms;
    }

    public void setUserForms(UsuariosFormularios userForms) {
        this.userForms = userForms;
    }

    private void crearArchivo() {
        try {
            File file = new File("Datos.dat");
            FileOutputStream fos;

            fos = new FileOutputStream(file);

            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(userForms);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void crearArchivoFormularios() {
        try {
            File file = new File("Formularios.dat");
            FileOutputStream fos;

            fos = new FileOutputStream(file);

            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(formulariosHTML);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public List<FormularioHTML> getFormulariosHTML() {                
        try {
            File file = new File("Formularios.dat");
            if (!file.exists()) {
                crearArchivoFormularios();
            } else {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                formulariosHTML = (List<FormularioHTML>) ois.readObject();                
                ois.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }        
        return formulariosHTML;
    }                

    public String getAllUsers() {
        String dbUser = "";
        UsuariosFormularios userForms1;
        try {
            File file = new File("Datos.dat");
            if (!file.exists()) {
                crearArchivo();
            } else {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                userForms1 = (UsuariosFormularios) ois.readObject();                
                ois.close();
                dbUser = userForms1.getUsuarios();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }        
        return dbUser;
    }
    
    public String getAllForms() {
        String dbUser = "";
        UsuariosFormularios userForms1;
        try {
            File file = new File("Datos.dat");
            if (!file.exists()) {
                crearArchivo();
            } else {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                userForms1 = (UsuariosFormularios) ois.readObject();                
                ois.close();
                dbUser = userForms1.getFormularios();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }        
        return dbUser;
    }
    
    public void guardarUsuariosyFormularios(String strUsua,String strForm){
        this.userForms.setUsuarios(strUsua);
        this.userForms.setFormularios(strForm);
        crearArchivo();
    }
    
    public UsuariosFormularios getUsuariosFormularios(){
        UsuariosFormularios userForms1 = this.userForms;
        try {
            File file = new File("Datos.dat");
            if (!file.exists()) {
                crearArchivo();
            } else {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                userForms1 = (UsuariosFormularios) ois.readObject();                
                ois.close();                
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }   
        return userForms1;
    }
    
    
}
