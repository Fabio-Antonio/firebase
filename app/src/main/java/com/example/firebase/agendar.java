package com.example.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class agendar extends AppCompatActivity {
EditText nombre,email,celular,direccion,referencia,pedido,fecha;
    final Calendar myCalendar = Calendar.getInstance();
    private Button enviar;
    private String urlAdress = "http://192.168.1.70:3000/api/usuarios";
    private String [] arreglo;
    DatabaseReference mref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar);

        Intent intent=getIntent();
        String mensaje=intent.getStringExtra("mensaje");

        nombre=(EditText)findViewById(R.id.nombre);
        email=(EditText)findViewById(R.id.email);
        celular=(EditText)findViewById(R.id.celular);
        direccion=(EditText)findViewById(R.id.direccion);
        referencia=(EditText)findViewById(R.id.referencia);
        pedido=(EditText)findViewById(R.id.pedido);
        arreglo =separar(mensaje);
        nombre.setText( arreglo[0]);
        celular.setText(arreglo[1]);
        email.setText(arreglo[2]);
        direccion.setText( arreglo[3]);
        pedido.setText(arreglo[4]);
        referencia.setText(arreglo[5]);


         fecha= (EditText) findViewById(R.id.fecha);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        fecha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                new DatePickerDialog(agendar.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        mref = FirebaseDatabase.getInstance().getReference().child("agenda-list");

    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        fecha.setText(sdf.format(myCalendar.getTime()));
    }

    public String[] separar (String cadena){
        String [] vect = cadena.split(" ;");
        return  vect;
    }

    public void sendPost() {
        final String nom = nombre.getText().toString();
        final String emails = email.getText().toString();
        final String mensaje = "Su pedido con numero de referencia: "

                +referencia.getText().toString()+" se ha agendado la fecha de entrega para el dia: "+fecha.getText().toString();
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
    public void onClick2(View v) {
         mref.child(referencia.getText().toString()).child("nombre").setValue(nombre.getText().toString());
        mref.child(referencia.getText().toString()).child("celular").setValue(celular.getText().toString());
        mref.child(referencia.getText().toString()).child("email").setValue(email.getText().toString());
        mref.child(referencia.getText().toString()).child("direccion").setValue(direccion.getText().toString());
        mref.child(referencia.getText().toString()).child("pedido").setValue(pedido.getText().toString());
        mref.child(referencia.getText().toString()).child("referencia").setValue(referencia.getText().toString());
        mref.child(referencia.getText().toString()).child("fecha").setValue(fecha.getText().toString());
      sendPost();
    }
}
