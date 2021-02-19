package com.example.ejercicios2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView pregunta;
    private EditText respuesta;
    private Button ok;
    private Button nuevaPregunta;
    private TextView puntajeContador;
    private TextView cronometro;
    private Button intentarDeNuevo;
    private Pregunta preguntaActual; //Declarando la clase pregunta
    private int tiempo = 30;
    private int puntaje = 0;
    private boolean isPressed = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Referencias
        pregunta = findViewById(R.id.pregunta);
        respuesta = findViewById(R.id.respuesta);
        ok = findViewById(R.id.ok);
        nuevaPregunta = findViewById(R.id.nuevaPregunta);
        puntajeContador = findViewById(R.id.puntajeContador);
        cronometro = findViewById(R.id.cronometro);
        intentarDeNuevo = findViewById(R.id.intentarDeNuevo);

        puntajeText();
        mostrarNuevaPregunta();
        tiempoContador();


        //toast
        Toast.makeText(this, "Answer" + " " + preguntaActual.getRespuesta(), Toast.LENGTH_SHORT).show();
        //Botones
        ok.setOnClickListener(
                (v) -> {
                    responder();
                }
        );
      //  nuevaPregunta.setOnClickListener(
        //        (v) -> {
        //            mostrarNuevaPregunta();
        //        }
       // );
        intentarDeNuevo.setOnClickListener(
        (v) -> {
            intentarDeNuevoBoton();
        }
        );

       nuevaPregunta.setOnTouchListener(
               (view,event) -> {

                   switch (event.getAction()) {
                       case MotionEvent.ACTION_DOWN:
                           isPressed = true;
                           new Thread(
                                   () -> { //Contar los segundo 1.5 hacemos un for para verificar que siga siendo presionadi
                                       for (int i = 0; i < 20; i++) {
                                           try {
                                               Thread.sleep(75);
                                               if(isPressed == false){
                                                   return; // Con return matamos el hilo. no tengo que hacer nada
                                               } else {

                                               }
                                           } catch (InterruptedException e) {
                                               e.printStackTrace();
                                           }
                                       } //for
                                       isPressed = false;
                                       runOnUiThread(
                                               ()-> {
                                                mostrarNuevaPregunta();
                                                   tiempo=30;
                                                   puntajeText();
                                                   tiempoContador();

                                               }
                                       );
                                   } //lambda

                           ).start();

                       break;
                       case MotionEvent.ACTION_UP:
                           isPressed = false;
                           break;
                   }
                   return true;
               }
       );

    }

    public void responder() {
        String respuestaUsuario = respuesta.getText().toString(); // respuesta.GetText().toString(); es coger la respuesta del usuario y guardarla en el string respuestaUsuario
        int respuestaUsuarioEnInt = Integer.parseInt(respuestaUsuario); // Estoy pasando respuestaUsuario a un entero respuestaUsuarioInt.
        int respuestaCorrecta = preguntaActual.getRespuesta(); // Aqui llamamos a la respuesta con get Respuesta y la guardamos en int respuestaCorrecta


        //Haremos la comparación de los dos int de respuestaCorrecta y la del usuario con un IF

        if (respuestaUsuarioEnInt == respuestaCorrecta) {
            Toast.makeText(this, "CORRECTO", Toast.LENGTH_SHORT).show();
            puntaje = +5;
            puntajeText();
            mostrarNuevaPregunta();
        } else {
            Toast.makeText(this, "INCORRECTO", Toast.LENGTH_SHORT).show();
            puntaje = -4;
            puntajeText();
            mostrarNuevaPregunta();
        }

    }

    public void mostrarNuevaPregunta() {
        preguntaActual = new Pregunta(); //Aquí estoy instanciando la clase
        pregunta.setText(preguntaActual.getPregunta()); // Aquí le digo que cambie el texto del textView Pregunta
    }

    public void puntajeText() {
        puntajeContador.setText("" + puntaje);
    }

    public void tiempoContador() {
        tiempo = 30;

        new Thread (
                () -> {
                    while (tiempo > 0) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        runOnUiThread( // debe usarse porque en el hilo creado para el tiempo no se puede poner nada gráfico, este metodo dice que lo corra en el principal
                                ()-> {
                                    cronometro.setText("" + tiempo);
                                }
                        );
                        tiempo--;
                        if (tiempo==0){
                            runOnUiThread(
                                    () -> {
                                        intentarDeNuevo.setVisibility(View.VISIBLE);
                                        ok.setVisibility(View.GONE);
                                        nuevaPregunta.setVisibility(View.GONE);
                                    }
                            );
                        }
                    }
                }
        ).start();
    }
    public void intentarDeNuevoBoton (){
        ok.setVisibility(View.VISIBLE);
        intentarDeNuevo.setVisibility(View.GONE);
        nuevaPregunta.setVisibility(View.VISIBLE);
        tiempo=30;
        puntaje = 0;
        puntajeText();  //
        tiempoContador();
    }

} //Final