package com.gedcom.parser;

import com.gedcom.model.Node;

import java.io.*;

/**
 * Created by ranjanr on 5/10/2017.
 */
public class XMLWriter {
    private Writer out;

    public XMLWriter() throws IOException
    {
        // If nothing is specified an support to
        // output the result onto standard output
        out = new BufferedWriter(new OutputStreamWriter(System.out));
    }

    public XMLWriter(String strFilename) throws IOException
    {
        // XML output file where the result will be written
        if ( strFilename != null )
        {
            File newOutputFile = new File(strFilename);
            if(!newOutputFile.exists()){
                newOutputFile.createNewFile();
            }
            out = new BufferedWriter(new FileWriter(strFilename));
        }
    }

    public void createNode(Node obj) throws IOException
    {
        // Used to write Nodes with Value normaly parent node ex:
        //        <name value="Jamis Gordon /Buck/">

        indentation(obj);
        out.write("<"+obj.getStrTag());
        if( obj.getStrId() != null )
        {
            out.write(" id=\""+obj.getStrId()+"\"");
        }
        if( obj.getStrValue() != null )
        {
            out.write(" value=\""+obj.getStrValue()+"\"");
        }
        out.write(">\n");
    }

    public void childNode(Node obj) throws IOException
    {
        // Used to write Nodes with Data normaly singular or child node ex:
        //            <surn>Buck</surn>

        indentation(obj);
        out.write("<"+obj.getStrTag()+">");
        if ( obj.getStrValue() != null )
        {
            out.write(obj.getStrValue());
        }
        out.write("</"+obj.getStrTag()+">\n");
    }

    public void endNode(Node obj) throws IOException
    {
        // Used to write the closing node with proper indentation ex:
        //    </indi>

        indentation(obj);
        out.write("</"+obj.getStrTag()+">\n");
        out.flush();
    }

    protected void indentation(Node obj) throws IOException
    {
        // Used to provide indentation nodes as per ther level ex:
        //            <surn>Buck</surn>

        String separator = "    ";
        for ( int i = 0 ; i<=obj.getIntLevel();i++ )
        {
            out.write (separator);
        }
    }
}
