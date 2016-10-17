package pt.ist.rc.paragraph.repl;

import pt.ist.rc.paragraph.model.GraphData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Pedro Joaquim on 11-10-2016
 */
public class ParaGraphREPL {

    private static final String EXIT_CMD = "exit";
    private static final String LOAD_CMD = "load";


    public static void main(String[] args) {
        BufferedReader br = null;



        try {

            br = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.print("Paragraph > ");
                execCMD(br.readLine());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static void execCMD(String cmd){

        if(cmd == null || cmd.isEmpty()){
            System.out.println("Invalid Command");
            return;
        }

        String[] splitedCMD = cmd.split(" ");

        switch (splitedCMD[0]) {
            case EXIT_CMD: System.out.println("Bye!"); System.exit(0);
            case LOAD_CMD: break;
            default: System.out.print("Invalid Command");
        }

    }

}
