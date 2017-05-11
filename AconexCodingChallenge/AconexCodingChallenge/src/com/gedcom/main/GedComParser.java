package com.gedcom.main;

import com.gedcom.model.Node;
import com.gedcom.parser.GedComXMLGenerator;

import java.io.IOException;
import java.util.Queue;

/**
 * Created by ranjanr on 5/10/2017.
 */
public class GedComParser {

    public static void main (String []args){
        try {
            System.out.print("***********************************************************************");
            System.out.print("\n\t\t\t\t\"GEDCOM Parser Coding Challange\"\t\t\t\t");
            System.out.print("\n***********************************************************************");
            System.out.print("\nUsage: \n");
            System.out.print("$java gedcomparser inputfilenameandpath outputfilenameandpath");
            if(args == null){
                System.out.print("\nNo input File to parse, exiting");
                return;
            }
            if (!(args instanceof String[])){
                System.out.print("\nIncorrect command executed, see usage help \"$java gedcomparser inputfilenameandpath outputfilenameandpath\"");
                return;
            }

            if (args.length == 0 || args.length > 2){
                System.out.print("\nIncorrect input executed, see usage help \"$java gedcomparser inputfilenameandpath outputfilenameandpath\"");
                return;
            }

            if (args.length >0 && args.length <= 2){
                System.out.print("\ninput file name : "+args[0]);
                System.out.print("\noutput file name : "+args[1]);
                GedComXMLGenerator obj = new GedComXMLGenerator(args[0],args[1]);
                obj.gedComParser();
            }

            if (args.length >0 && args.length < 2){
                System.out.print("\ninput file name : "+args[0]);
                System.out.print("\nNo Output file given, the result XML would be printed on the stdout");
                GedComXMLGenerator obj = new GedComXMLGenerator(args[0]);
                obj.gedComParser();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
