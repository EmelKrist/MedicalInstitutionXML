package medicalinstitutionxml;

import java.io.*;
import org.xml.sax.SAXException;

public class MedicalInstitutionXML {

    public static void main(String[] args) throws IOException, SAXException {
        try {
            //запускаем процесс валидации и проверяем xml файл на целостность 
            Validator valid = new Validator("MedicalInstitution.xml",
                    "MedicalInstitutionSchema.xsd");
            //обработчик ошибок
        } catch (SAXException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
