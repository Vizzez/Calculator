package calc;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException;

import java.util.HashMap;
import java.util.Scanner;


public class Application {
    static final String LEFTREGEX = "^([a-z]|[A-Z]|_)([a-z]|[A-Z]|\\d|_)*";

    public static void main (String[] args) {

        HashMap<String, Double> hmap = new HashMap<> ();
        Scanner sc = new Scanner (System.in);

        while (true) {
            System.out.println ("Insert your instructions (type \"exit\" for termination):");
            System.out.print (">");
            String exp = sc.nextLine ().toLowerCase ().trim ();
            if (extracted (hmap, exp)) break;
        }
    }

    private static boolean extracted (HashMap<String, Double> hmap, String exp) {

        if (exp.equals ("exit")) {
            return true;
        }
        if (exp.contains ("=")) {
            String[] split = exp.split ("=");
            Double d = expBuild (split[1]);

            if (split[0].matches (LEFTREGEX)) {
                System.out.println (d == null ? "" : d);
                hmap.put (split[0], d);
            } else {
                System.out.println ("Error!!!\nHint: Variables have to be on the left side of the term and have to start with a letter or a underscrore.");
            }


        } else {
            System.out.println (expBuild (hmap, exp));
        }
        return false;
    }

    public static Double expBuild (HashMap<String, Double> hmsd, String s) {
        Double ret = null;
        try {
            Expression eb = new ExpressionBuilder (s).variables (hmsd.keySet ()).build ();
            ret = eb.setVariables (hmsd).evaluate ();

        } catch (UnknownFunctionOrVariableException e) {
            System.out.println ("Error: First you have to assign variables before computing");
        }

        return ret;
    }

    public static Double expBuild (String s) {
        return new ExpressionBuilder (s).build ().evaluate ();
    }


}


