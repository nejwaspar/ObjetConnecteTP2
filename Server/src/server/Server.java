/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author Nejwa
 */
import spark.Request;
import spark.Response;
import static spark.Spark.*;
public class Server {

static int temp_courante = 20;
static int temp_chauffage = 20;
static boolean etat_Radiateur= false;
    public static void main(String[] args) {
        post("/thermometre", (Request request, Response response) -> {
            // Recupere la temperature
            String val=request.queryParams("temperature");
            temp_courante=Integer.parseInt(val);
            // Fait fonctionner le radiateur suivant la temperature
            etat_Radiateur= temp_courante<temp_chauffage;
            System.out.println("Temperature recue: "+val);
            return "OK";
            });
      
        get("/radiateur", (request, response) -> {
           
            if (etat_Radiateur)
                return "{\"radiateurON\": \"true\"}";
            else
                return "{\"radiateurOFF\": \"false\"}";
            });
  
        get("/thermometre", (request, response) -> {
          return "{\"temperatureCourante\":"+temp_courante+"}";
            });
   
        post("/chauffage", (Request request, Response response) -> {
        
            String val=request.queryParams("temperature");
            temp_chauffage=Integer.parseInt(val);
         
            etat_Radiateur= temp_courante<temp_chauffage;
            System.out.println("Nouvelle temperature de chauffage: "+temp_chauffage);
            return "OK";
            });
    }    
    }

