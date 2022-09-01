package calc;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException;

import java.util.HashMap;
import java.util.Scanner;


public class Application {

    public static void main(String[] args) {

        HashMap<String, Double> hmap = new HashMap<>();
        Scanner sc= new Scanner(System.in);

        while (true) {
            System.out.println("Insert your instructions (type \"exit\" for termination):");
            String exp = sc.nextLine().toLowerCase().trim();
            if (exp.equals("exit")) {
                break;
            }

            if (exp.contains("=")) {
                String[] split = exp.split("=");
                Double d = expBuild(split[1]);
                if (Character.isLetter(split[0].charAt(0))){

                    System.out.println(d==null ? "" : d);
                    hmap.put(split[0], d);
                }
                else{
                    System.out.println("Error!!!\nHint: Variables have to be on the left side of the term and have to start with a letter.");
                }


            } else {
                System.out.println(expBuild(hmap, exp));
            }
        }
    }

    public static Double expBuild(HashMap<String, Double> hmsd, String s) {
        Double ret = null;
        try {
            Expression eb = new ExpressionBuilder(s).variables(hmsd.keySet()).build();
            ret = eb.setVariables(hmsd).evaluate();

        } catch (UnknownFunctionOrVariableException e) {
            System.out.println("Error: First you have to assign variables before computing");
        }

        return ret;
    }

    public static Double expBuild(String s) {
        return new ExpressionBuilder(s).build().evaluate();
    }


}


