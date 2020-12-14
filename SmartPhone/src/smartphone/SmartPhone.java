/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartphone;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import org.json.JSONObject;

/**
 *
 * @author Nejwa
 */
public class SmartPhone {

    /**
     * @param args the command line arguments
     */
        private static void changerTemperatureChauffage(int temperatureChauffage) throws MalformedURLException, IOException
    {
        String charset = java.nio.charset.StandardCharsets.UTF_8.name();
        String url = "http://localhost:4567/chauffage";
        String query = String.format("temperature=%s", temperatureChauffage);
        URLConnection connection = new URL(url).openConnection();
        connection.setDoOutput(true); // Triggers POST.
        connection.setRequestProperty("Accept-Charset", charset);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);

        try (OutputStream output = connection.getOutputStream()) {
            output.write(query.getBytes(charset));
        }

        InputStream response = connection.getInputStream();

        try (Scanner scanner = new Scanner(response)) {
            String responseBody = scanner.useDelimiter("\\A").next();
        }
    }
       private static void afficherTemperatureCourante() throws IOException 
    {
        String charset = java.nio.charset.StandardCharsets.UTF_8.name();
        String url = "http://localhost:4567/thermometre";
        URLConnection connection = new URL(url).openConnection();
        connection.setDoOutput(false); // Triggers POST.
        connection.setRequestProperty("Accept-Charset", charset);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);

        InputStream response = connection.getInputStream();
        // Obtenir le resultat de la requete
        try (Scanner scanner = new Scanner(response)) {
            String responseBody = scanner.useDelimiter("\\A").next();
            JSONObject obj = new JSONObject(responseBody);
            int temperatureCourante = obj.getInt("temperatureCourante");
            System.out.println("Temperature courante:" + temperatureCourante);
        }
    }
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        Scanner in = new Scanner(System.in);
        int reponse=0;
        while(reponse != 3)
        {
            System.out.println("Choisir une option:");
            System.out.printf("1. Recevoir et afficher la température courante \n");
            System.out.printf("2. Entrer au clavier et envoyer la température de chauffage au serveur \n");
            System.out.printf("3. Arrêter l’application \n");
            reponse=in.nextInt();
            if(reponse == 1 )
            {
             afficherTemperatureCourante();   
            }else{
                if( reponse == 2)
                {
                    System.out.println("Entrer la nouvelle temperature de chauffage");
                    int temperatureChauffage=in.nextInt();
                    changerTemperatureChauffage(temperatureChauffage);
                }
            }
        }
    }
}
