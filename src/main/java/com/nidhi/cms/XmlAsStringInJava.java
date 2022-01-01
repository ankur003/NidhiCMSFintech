package com.nidhi.cms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;


public class XmlAsStringInJava {

    public static String getXmlAsString ()
          throws ParserConfigurationException, SAXException, IOException {
        
        // our XML file for this example
        File xmlFile = new File("/home/ankur/Music/formatxmlresponse.xml");
        
        // Let's get XML file as String using BufferedReader
        // FileReader uses platform's default character encoding
        // if you need to specify a different encoding, 
        // use InputStreamReader
        Reader fileReader = new FileReader(xmlFile);
        BufferedReader bufReader = new BufferedReader(fileReader);
        
        StringBuilder sb = new StringBuilder();
        String line = bufReader.readLine();
        while( line != null){
            sb.append(line).append("\n");
            line = bufReader.readLine();
        }
        String xml2String = sb.toString();
        System.out.println("XML to String using BufferedReader : ");
        System.out.println(xml2String);
        
        bufReader.close();
		return xml2String;
       
      }
}