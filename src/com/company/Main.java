package com.company;

public class Main {

    public static void main(String[] args) {
        Translator translator = new Translator();

        String formula = "a AND b OR (NOT c AND q OR s)";

        translator.organizer(formula);
    }
}
