package com.company;

public class Main {

    public static void main(String[] args) {
        Translator translator = new Translator();

        String formula = "a AND b OR (s OR NOT c AND (q OR s AND (a AND b))";

        System.out.println(translator.organizer(formula));
    }
}
