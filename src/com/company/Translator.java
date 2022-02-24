package com.company;

import java.util.ArrayList;
import java.util.regex.*;

public class Translator {

    private final String AND = "AND";
    private final String OR = "OR";
    private final String NOT = "NOT";
    private final String IMP = "IMP";
    private int organizerFormulaIndex;

    private ArrayList<String> separator (String formula){

        //Array that receiveis formula organized/divided.
        ArrayList<String> organizedFormula = new ArrayList<String>();

        //Formula without blank spaces.
        StringBuilder processedFormula = new StringBuilder(formula);

        //This for removes string`s blank spaces for better manipulation.
        for(int i = 0; i < processedFormula.length(); i++) {

            //Truth if an operator is being read.
            //System.out.println(i);
            //Remove blank spaces from formula.
            if (processedFormula.charAt(i) == ' ') {
                processedFormula = processedFormula.deleteCharAt(i);
                i = 0;
            }

        }

        String carrier = new String();

        for(int i = 0; i < processedFormula.length(); i++){

            boolean onOperator;
            String operator = new String();

            //Carries a possible operator.
            if(i < processedFormula.length() - 2) {
                 carrier = Character.toString(processedFormula.charAt(i)) + Character.toString(processedFormula.charAt(i + 1)) + Character.toString(processedFormula.charAt(i + 2));
            }


            if(carrier.charAt(1) == 'O' && carrier.charAt(2) == 'R'){

                organizedFormula.add(Character.toString(processedFormula.charAt(i)));
                carrier = Character.toString(processedFormula.charAt(i + 1)) + Character.toString(processedFormula.charAt(i + 2));

                operator = carrier;
                onOperator = true;
            }
            else if(carrier.equals(AND) || carrier.equals(NOT) || carrier.equals(IMP) ){
                operator = carrier;
                onOperator = true;

            } else {
                onOperator = false;
            }

            if(onOperator){
                organizedFormula.add(operator);

            } else if(Pattern.matches("[a-z]", Character.toString(processedFormula.charAt(i))) || processedFormula.charAt(i) == '(' || processedFormula.charAt(i) == ')'){

                organizedFormula.add(Character.toString(processedFormula.charAt(i)));
            }
        }


        return organizedFormula;
    }

    //Metodo para validar o input do usuario.
    public Boolean validation(String formula){

        ArrayList<String> processedFormula = separator(formula);
        int variables = 0;

        //Esse for tira os espacos entre operadores e variaveis para melhor manipulacao da string.
        for(int i = 0; i < processedFormula.size(); i++){

            //Verdade se um operator estiver sendo lido.
            boolean onOperator;
            String operator = new String();
            String carrierNext = processedFormula.get(i + 1);

            if( processedFormula.get(i).equals(AND) || processedFormula.get(i).equals(OR) || processedFormula.get(i).equals(NOT) || processedFormula.get(i).equals(IMP) ){
                onOperator = true;
            } else {
                onOperator = false;
            }

            //Checa o numero de variaveis somando a variavel "variables" se detectar uma variavel.
            if(processedFormula.get(i).equals("a") || processedFormula.get(i).equals("b") || processedFormula.get(i).equals("c")){
                variables++;

                //Checa se uma variavel esta do lado da outro (ausencia de operador).
                if(processedFormula.get(i + 1).equals("a") || processedFormula.get(i + 1).equals("b") || processedFormula.get(i + 1).equals("c")
                        || processedFormula.get(i - 1).equals("a") || processedFormula.get(i - 1).equals("b") || processedFormula.get(i - 1).equals("c")){

                    return false;
                }
            }

            if(onOperator && !(operator.equals(NOT)) && carrierNext.equals(AND) || carrierNext.equals(OR) || carrierNext.equals(NOT) || carrierNext.equals(IMP)){

                return false;
            }

        }

        return true;
    }

    private int findLastParenthesis(ArrayList<String> processedFormula){
        int i = 0;
        int lastParenthesisIndex = 0;

        for(String element : processedFormula) {
            if (element.equals("(")) {
                lastParenthesisIndex = i;
            }
            i++;
        }
        //System.out.println(i);
        System.out.println(processedFormula);
        return lastParenthesisIndex;
    }

    //Metodo para ler a formula do usuario e retornala traduzida para outro metodo que vai precessala.
    private ArrayList<String> organizerSubFormula(ArrayList<String> processedSubformula){

        ArrayList<String> processedFormula = processedSubformula;

        //Armazena as operações em ordem de precedencia para serem processadas.
        ArrayList<String> orderedFormula = new ArrayList<>();

        //Verdade se um operator estiver sendo lido.
        boolean onOperator;
        String operator = new String();

        //Contador para o while.
        int i = 0;

        //Numero de operações na formula.
        int numberOfOperations = 0;

        //Le quantos operadores tem na formula.
        for (String element : processedFormula) {

            if (element.equals(AND) || element.equals(OR) || element.equals(NOT) || element.equals(IMP) ) {
                numberOfOperations++;
            }
        }

        while(processedFormula.contains(NOT) || processedFormula.contains(AND) || processedFormula.contains(OR) || processedFormula.contains(IMP)) {

            System.out.println(i);

            if (processedFormula.get(i).equals(AND) || processedFormula.get(i).equals(OR) || processedFormula.get(i).equals(NOT) || processedFormula.get(i).equals(IMP)) {
                onOperator = true;
                operator = processedFormula.get(i);
            } else {
                onOperator = false;
            }


            //Filtro de precedencias, tira o operador de maior precedencia e coloca ele primeira no Array "orderedFormula" com as suas variaveis em sequencia.
            if(onOperator){
                if(operator.equals(NOT)){

                    orderedFormula.add(operator);
                    orderedFormula.add(processedFormula.get(i + 1));

                    processedFormula.remove(i);

                    //É igualado i á 0 para a leitura começar do começo de novo, para não ouver gaps de leitura e operadores
                    //de mesma precedencia não serem ordenados um antes do outro.
                    i = 0;
                    //É negado se a "processedFormula" contain o operador "NOT", porque se "contains(NOT)" retornar "false"
                    //quer dizer que não tem mais operadores "NOT" (e negada retorna "true") podendo assim ser possivel
                    // adicionar o operador "AND" e obedecer a precedencia. (o mesmo segue para as outras condições)
                } else if (operator.equals(AND) && !processedFormula.contains(NOT)){

                    //Adiciona o operador AND ao array "OrderedFormula" e suas variaveis.
                    orderedFormula.add(operator);

                    //Se o "i" for 0, quer dizer que o operador esta comparando o valor de outra operacao
                    //entao é adicionado a primeira variavel uma string sinalizando isso e logo depois a segunda.
                    //se "i" não igualar a 0
                    if(i == 0){

                        orderedFormula.add(processedFormula.get(i + 1));
                        orderedFormula.add("CurrentValue");

                        processedFormula.remove(i + 1);

                        i++;
                    } else {

                        orderedFormula.add(processedFormula.get(i - 1));
                        orderedFormula.add(processedFormula.get(i + 1));

                        processedFormula.remove(i + 1);
                        processedFormula.remove(i - 1);
                    }

                    processedFormula.remove(i - 1);

                    i = 0;

                } else if ( operator.equals(OR) && !processedFormula.contains(AND)){

                    orderedFormula.add(operator);

                    if(i == 0){

                        orderedFormula.add(processedFormula.get(i + 1));
                        orderedFormula.add("CurrentValue");

                        processedFormula.remove(i + 1);

                        i++;
                    } else {

                        orderedFormula.add(processedFormula.get(i - 1));
                        orderedFormula.add(processedFormula.get(i + 1));

                        processedFormula.remove(i + 1);
                        processedFormula.remove(i - 1);
                    }

                    processedFormula.remove(i - 1);

                    i = 0;

                } else if ( operator.equals(IMP) && !processedFormula.contains(OR)){

                    if(i == 0){

                        orderedFormula.add(processedFormula.get(i + 1));
                        orderedFormula.add("CurrentValue");

                        processedFormula.remove(i + 1);

                        i++;
                    } else {

                        orderedFormula.add(processedFormula.get(i - 1));
                        orderedFormula.add(processedFormula.get(i + 1));

                        processedFormula.remove(i + 1);
                        processedFormula.remove(i - 1);
                    }

                    processedFormula.remove(i - 1);

                    i = 0;
                }
            } else {
                i++;
            }

            System.out.println(operator);
            System.out.println("processedFormula: " + processedFormula);
            System.out.println(orderedFormula);
        }

        return orderedFormula;
    }

    public ArrayList<String> organizer(String formula){

        ArrayList<String> organizedFormula = new ArrayList<String>();

        ArrayList<String> processedFormula = separator(formula);

        ArrayList<String> subFormula = new ArrayList<String>();

        ArrayList<String> organizedSubFormula = new ArrayList<String>();

        int numberOfSubFormulas = 0;

        for(String element : processedFormula){
            if(element.equals("(")){
                numberOfSubFormulas++;
            }
        }

        System.out.println(numberOfSubFormulas);

        for(int i = 0; i < numberOfSubFormulas; i++){
            int lastParenthesis = findLastParenthesis(processedFormula);

            for(int q = lastParenthesis + 1; !processedFormula.get(q).equals(")"); q++){
                subFormula.add(processedFormula.get(q));
            }

            System.out.println("Subformula: " + subFormula);

            organizedSubFormula = organizerSubFormula(subFormula);

            for(String element : organizedSubFormula){
                organizedFormula.add(element);
            }
        }



        return organizedFormula;
    }
}
