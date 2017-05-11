package com.gedcom;

import com.gedcom.parser.GedComXMLGenerator;
import org.junit.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by ranjanr on 5/10/2017.
 */
public class TestGedComParser {

    private static String resourceRoot ="AconexCodingChallenge/resource/";

    @BeforeClass
    public static void BeforeClass() {
        System.out.println("In TestGedComParser::BeforeClass");
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("In TestGedComParser::setUp");
    }

    /**
     * This test method is the common method to execute the parser on the input file given and compare the output with
     * the golden output provided. All the 3 file names are provided as input parameter
     * @param strInputFile
     * @param strOutputFile
     * @param strResultFile
     * @throws IOException
     */
    public static void testInputParser(String strInputFile,
                                       String strOutputFile, String strResultFile ) throws IOException {
        System.out.println("In TestGedComParser::executeConvert");
        GedComXMLGenerator gedComXMLGenerator = new GedComXMLGenerator(strInputFile,strOutputFile);
        gedComXMLGenerator.gedComParser();
        Assert.assertTrue(comapreFile(strOutputFile,strResultFile));
        File fileOutput = new File(strOutputFile);
        fileOutput.delete();
    }

    /**
     * This is the utility class to do the file comparision
     *
     * @param strFileOne
     * @param strFileTwo
     * @return
     * @throws IOException
     */
    public static boolean comapreFile(String strFileOne,String strFileTwo)
            throws IOException {
        System.out.println("In TestGedComParser::comapreFile");
        BufferedReader brOne = new BufferedReader(new FileReader(strFileOne));
        BufferedReader brTwo = new BufferedReader(new FileReader(strFileTwo));

        String strLine;
        while((strLine = brOne.readLine()) != null)
        {
            if(!strLine.equals(brTwo.readLine()))
            {
                return false;
            }
        }
        if(brTwo.readLine() != null)
        {
            return false;
        }
        return true;
    }

    /**
     * This test case is to check the for the handling of the White spaces in the Input file.
     * The white spaces in input file can be multiple places as described below:
     *  1. White Space between Level
     *  2. White Space between Tag
     *
     */
    @Test
    public void testForWhiteSpaceInInputData() {
        System.out.println("In TestGedComParser::testForWhiteSpaceInInputData");
        try {
            String strInputFile = resourceRoot+"GEDCOMDataWithVariableWhiteSpacesBetweenLevelAndTag.txt";
            String strOutputFile = resourceRoot+"TestCaseGEDCOMDataWithVariableWhiteSpacesOutput.xml";
            String strResultFile = resourceRoot+"TestCaseGEDCOMDataWithVariableWhiteSpacesResult.xml";
            testInputParser(strInputFile,strOutputFile,strResultFile);
        } catch (IOException e) {
            Assert.fail(" Unexpected Exception.");
            e.printStackTrace();
        }
    }

    /**
     * This test case is test the handling of blank lines in the input file, which would be ignored during parsing
     */
    @Test
    public void testBlankLines(){
        System.out.println("In TestGedComParser::testBlankLines");
        try{
            String strInputFile = resourceRoot+"TestCaseGEDCOMDataWithBlankLines.txt";
            String strOutputFile = resourceRoot+"TestCaseGEDCOMDataWithBlankLinesOutput.xml";
            String strResultFile = resourceRoot+"TestCaseGEDCOMDataWithBlankLinesResult.xml";

            testInputParser(strInputFile,strOutputFile,strResultFile);
        } catch (IOException e) {
            Assert.fail(" Unexpected Exception.");
            e.printStackTrace();
        }
    }

    /**
     * This test case is cover different checks which were asked as the rquirment :
     * > "0 @I1@ INDI" get converted to     <indi id="@I1@"> [new subtree ]
     * > "1 NAME Jamis Gordon /Buck/" get converted to <name value="Jamis Gordon /Buck/">
     * > "2 SURN Buck" get converted to <surn>Buck</surn>  [Subelement]
     * > "1 SEX M" get converted to <sex>M</sex> [new subelement of INDI ]
     * @throws IOException
     * @throws Exception
     */
    @Test
    public void testValues() throws IOException, Exception {
        System.out.println("In TestGedComParser::testValues");
        String strInputFile = resourceRoot+"TestCaseGEDCOMDataValues.txt";
        String strOutputFile = resourceRoot+"TestCaseGEDCOMDataValuesOutput.xml";
        String strResultFile = resourceRoot+"TestCaseGEDCOMDataValuesResult.xml";
        testInputParser(strInputFile,strOutputFile,strResultFile);
    }

    /**
     * This test case is test the sample input given as part of coding challenge
     * @throws IOException
     * @throws Exception
     */
    @Test
    public void testConvertGEDCOM2XML() throws IOException, Exception {
        System.out.println("In TestGedComParser::testConvertGEDCOM2XML");
        String strInputFile = resourceRoot+"GEDCOM_Parser_Challenge_sample_data.txt";
        String strOutputFile = resourceRoot+"GEDCOM_Parser_Challenge_Output.xml";
        String strResultFile = resourceRoot+"GEDCOM_Parser_Challenge_result.xml";
        File fileOutput = new File(strOutputFile);
        if(fileOutput.exists()){
            fileOutput.delete();
        }
        testInputParser(strInputFile,strOutputFile,strResultFile);
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("In TestGedComParser::tearDown");
    }

    @AfterClass
    public static void AfterClass() {
        System.out.println("In TestGedComParser::AfterClass");
    }
}
