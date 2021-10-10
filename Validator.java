package medicalinstitutionxml;

import java.io.*;
import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;

public class Validator {
       
    /**
     * Класс валидации
     * @param wayXML - путь к xml файлу
     * @param wayXSD - путь к xsd файлу
     * @throws java.io.IOException
     * @throws org.xml.sax.SAXException
     */
        public Validator (String wayXML, String wayXSD) throws IOException, SAXException {
        
        String filename = wayXML; //xml файл
        String logname = "log.txt"; //файл с логами
        String schemaname = wayXSD; //xsd файл 
        Schema schema = null; //оюнуляем схему
        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        SchemaFactory factory = SchemaFactory.newInstance(language);
      
        try {
           // установка проверки с использованием XSD
            schema = factory.newSchema(new File(schemaname));
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setSchema(schema);
            //создание объекта-парсера
            SAXParser parser = spf.newSAXParser();
            // установка обработчика ошибок и запуск
            parser.parse(filename, new PatientErrorHandler(logname));
            System.out.println(filename + " is valid");
            //запускаем класс вывода информации из xml файла 
            OutputInfoWithDOM startOutput = new OutputInfoWithDOM(wayXML);
            //обработчик ошибок 
        } catch (ParserConfigurationException e) {
            System.err.println(filename + " config error: " + e.getMessage());
        } catch (SAXException e) {
            System.err.println(filename + " SAX error: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        }

    }
        
}
