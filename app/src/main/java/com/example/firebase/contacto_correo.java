package com.example.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class contacto_correo extends AppCompatActivity {
    private EditText email,nombre,mensajes,contestar;
    private Button enviar;
   private String urlAdress = "http://192.168.1.70:3000/api/usuarios";
   private String [] arreglo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto_correo);

        Intent intent=getIntent();
        String mensaje=intent.getStringExtra("mensaje");
        email=(EditText)findViewById(R.id.email);
        nombre=(EditText)findViewById(R.id.nombre);
        mensajes=(EditText)findViewById(R.id.mensaje);
        contestar=(EditText)findViewById(R.id.contestar);
        enviar=(Button)findViewById(R.id.enviar);
        arreglo =separar(mensaje);
        nombre.setText( arreglo[0]);
        email.setText(arreglo[1]);
        mensajes.setText(arreglo[2]);

      enviar.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              sendPost();
          }
      });
    }


    public void sendPost() {
        final String nom = nombre.getText().toString();
        final String emails = email.getText().toString();
        final String mensaje = contestar.getText().toString();
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
                    jsonParam.put("email",emails) ;
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
