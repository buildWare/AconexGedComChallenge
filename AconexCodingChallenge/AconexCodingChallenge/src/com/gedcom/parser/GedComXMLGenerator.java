package com.gedcom.parser;

import com.gedcom.model.Node;
import com.gedcom.model.NodeStack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

/**
 * Created by
 * ranjanr
 * 5/10/2017
 * GedcomParser
 *
 */
public class GedComXMLGenerator {
    private BufferedReader inputFileReader;		// input file reader
    private XMLWriter xmlOutputWriter;			// output xml file writer
    private NodeStack nodeStack;
    private final String defaultRootNodeInput = "-1 GEDCOM";			// Final variable to protect from any modification. -1 node level assigned


    /**
     * No output file constructor will write the output to the stdout
     *
     * @param strInputFilename
     * @throws IOException
     */
    public GedComXMLGenerator(String strInputFilename ) throws IOException
    {
        inputFileReader = new BufferedReader(new FileReader(strInputFilename));
        xmlOutputWriter = new XMLWriter();
        nodeStack = new NodeStack();
    }

    /**
     * Constructor with input and out file to initialize the GEDCom file reader and XML Output writer
     *
     * @param strInputFilename
     * @param strOutputFilename
     * @throws IOException
     */
    public GedComXMLGenerator(String strInputFilename , String strOutputFilename) throws IOException
    {
        inputFileReader = new BufferedReader(new FileReader(strInputFilename));
        xmlOutputWriter = new XMLWriter(strOutputFilename);
        nodeStack = new NodeStack();
    }

    public BufferedReader getInput()
    {
        return inputFileReader;
    }

    public NodeStack getNodeStack()
    {
        return nodeStack;
    }

    public String getRootNode()
    {
        return defaultRootNodeInput;
    }

    /**
     * Prev Node holds the last read line value. For the first look it will hold the default root node.
     * Curr Node hold the current line read from the input file
     * Node is the data model which parses each input line and extracts the different value like
     * Level,tag,id & value
     *
     * 1. Read the current line from the input file
     * 2. Check for blank line. If true loop to read the next line else process further
     * 3. Check for EOF or Null input line. This marks the end of loop and stop parsing
     * 4. If all the above conditions are negative, it means it's a valid input line, so perform the check for
     * following cases:
     *      > If the Node Stack is empty or the Curr Node level > Prev Node level: Write the start element tag and push
     *        the Node to the empty Node Stack
     *      > If Curr Node level == Prev Node Level : Write the complete xml element (<>childnode</>) for the Prev node
     *      > If Prev Node level > Curr Node level : Write the complete xml element (<>childnode</>) for the Prev node and
     *      pop the Node stack to write the end element till Current Level is <= Level of node in stack
     *      > once the complete file is read pop the last element from the stack to write the end element for the root node
     *
     * @throws IOException
     */
    public void gedComParser() throws IOException
    {
        String strLine = null;
        String strPrevLine = getRootNode();
        Node prev = new  Node(strPrevLine);

        Node curr; // new  Node(strPrevLine);
        while (true)
        {
            strLine = getInput().readLine() ;

            if ( strLine !=null && strLine.trim().compareTo("") == 0 ){
                continue ;
            }
            curr = new  Node(strLine);
            // EOF reached
            if ( strLine  == null ){
                xmlOutputWriter.childNode(prev);
                break;
            }

            if ( getNodeStack().isEmpy() || prev.getIntLevel() < curr.getIntLevel() ){
                xmlOutputWriter.createNode(prev);
                getNodeStack().push(prev);
            }
            else if ( prev.getIntLevel() == curr.getIntLevel() ){
                xmlOutputWriter.childNode(prev);
            }
            else if ( prev.getIntLevel() > curr.getIntLevel() ){
                xmlOutputWriter.childNode(prev);
                while ( (!getNodeStack().isEmpy() && (curr.getIntLevel() <= getNodeStack().peek().getIntLevel())) ){
                    xmlOutputWriter.endNode(getNodeStack().pop());
                }
            }
            prev = curr ;
        }// EOF

        // Pop the root node and write the end element
        while(!getNodeStack().isEmpy())
        {
            xmlOutputWriter.endNode(getNodeStack().pop());
        }
    }
}
