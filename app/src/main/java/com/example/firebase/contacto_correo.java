package com.example.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class contacto_correo extends AppCompatActivity {
   private String urlAdress = "http://192.168.1.70:3000/api/usuarios";
   private String [] arreglo;
   private String nom,email,mensajes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto_correo);

        Intent intent=getIntent();
        String mensaje=intent.getStringExtra("mensaje");
        Toast.makeText(this.getApplicationContext(),"Bienvenido: "+mensaje,Toast.LENGTH_LONG).show();
        arreglo =separar(mensaje);
        nom = arreglo[0];
        email = arreglo[1];
        mensajes = arreglo[2];
        sendPost(nom,email,mensajes);

    }


    public void sendPost(final String nom, final String email, final String mensaje) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlAdress);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("nombre",nom);
                    jsonParam.put("email",email) ;
                    jsonParam.put("mensaje", mensaje);


                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();

                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    public String[] separar (String cadena){
        String [] vect = cadena.split(" ;");
        return  vect;
    }

}
