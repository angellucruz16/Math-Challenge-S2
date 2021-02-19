package com.example.ejercicios2;

import android.widget.Toast;

public class Pregunta {

    private int A;
    private int B;
    private int S;
    private String operador;
    private String[] operandos = {"+", "-", "*", "/"};


    //constructor
    public Pregunta() {
        this.A = (int) (Math.random() * 11);
        this.B = (int) (Math.random() * 11);
        int operadorRandom = (int) (Math.random() * 4);
        this.operador = operandos[operadorRandom];


    }

    //Métodos
    public String getPregunta() {
        return A +" " + operador +" "+ B;
    }

    public int getRespuesta () {
        int respuesta = 0;
        switch (operador) {
            case "+":
                respuesta = A + B;
                break;
            case "-":
                respuesta = A - B;
                break;
            case "*":
                respuesta = A * B;
                break;
            case "/":
                S = A * B;
                B = S / A;
                respuesta = B;
                break;

        }
        return respuesta;
    }
}
