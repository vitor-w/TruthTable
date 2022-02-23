package com.company;

public class Formulas {

    //Um metodo representado o operador AND.
    static boolean AND (boolean a, boolean b){

        return a && b;
    }

    //Um metodo representando o operador OR.
    static boolean OR (boolean a, boolean b){

        return a || b;
    }

    //Um metodo representado o operador NOT.
    static boolean NOT (boolean a){

        return !a;
    }

    //Um metodo representado o operador de implicacao.
    static boolean IMPLICATION (boolean a, boolean b){

        return (!a || b);
    }


}
