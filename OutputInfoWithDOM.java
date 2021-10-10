package medicalinstitutionxml;

import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class OutputInfoWithDOM {

    private DocumentBuilder docBuilder;
    private String wayXML;

    /**
     * Класс для запуска вывода информации из xml файла
     *
     * @param wayXML - путь к xml файлу
     * @throws java.io.IOException
     * @throws org.xml.sax.SAXException
     */
    public OutputInfoWithDOM(String wayXML) throws SAXException, IOException {
        this.wayXML = wayXML;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            // создание DOM-анализатора
            docBuilder = factory.newDocumentBuilder();
            //запускаем метод для для вывода информации
            GetInformation();
            //обработчик ошибок
        } catch (ParserConfigurationException e) {
            System.err.println("Ошибка конфигурации парсера: " + e);
        }
    }

    /**
     * Метод для вывода информации из xml файла
     *
     * @throws java.io.IOException
     * @throws org.xml.sax.SAXException
     */
    public void GetInformation() throws SAXException, IOException {
        try {

            File Fxml = new File(wayXML);
            // parsing XML-документа и нормализация 
            Document doc = docBuilder.parse(Fxml);
            doc.getDocumentElement().normalize();
            //выводим root элемент 
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            // получение списка дочерних элементов <patient>
            NodeList NLpatient = doc.getElementsByTagName("patient");
            //выводим результаты 
            System.out.println("\nResults:");
            //получаем длину списка и выводим все эти элементы в цикле
            for (int i = 0; i < NLpatient.getLength(); i++) {
                //берем i-го пациента списка 
                Node elNodePatient = NLpatient.item(i);
                // если его дочерние узлы типа ELEMENT_NODE
                if (elNodePatient.getNodeType() == elNodePatient.ELEMENT_NODE) {

                    Element elemPatient = (Element) elNodePatient;
                    //Получаем ФИО пациента и отправляем в NodeList
                    NodeList NLname = elemPatient.getElementsByTagName("name");
                    Element elName = (Element) NLname.item(0);
                    NodeList ResultNodeName = elName.getChildNodes();
                    //Получаем день рождления пациента и отправляем в NodeList
                    NodeList NLbirth = elemPatient.getElementsByTagName("birth");
                    Element elBirth = (Element) NLbirth.item(0);
                    NodeList ResultNodeBirth = elBirth.getChildNodes();
                    //Получаем номер полиса пациента и отправляем в NodeList
                    NodeList NLpolicy = elemPatient.getElementsByTagName("policy");
                    Element elpolicy = (Element) NLpolicy.item(0);
                    NodeList ResultNodePolicy = elpolicy.getChildNodes();

                    //Выводим значения нулевых элементов всех NodeList 
                    System.out.println("Patient #" + (i + 1)
                            + "\nFull name: "
                            + ((Node) ResultNodeName.item(0)).getNodeValue()
                            + "\nDate of Birth: "
                            + ((Node) ResultNodeBirth.item(0)).getNodeValue()
                            + "\nOMS policy number: "
                            + ((Node) ResultNodePolicy.item(0)).getNodeValue());

                    //получаем все тесты, пройденные i-м пациентом 
                    NodeList NLTest = elemPatient.getElementsByTagName("test");
                    //Тесты выводим в отдельном цикле , так как их может быть несколько 
                    for (int j = 0; j < NLTest.getLength(); j++) {
                        //берем j-й тест i-го пациента списка 
                        Node elNodeTest = NLTest.item(j);
                        // если его дочерние узлы типа ELEMENT_NODE
                        if (elNodePatient.getNodeType() == elNodePatient.ELEMENT_NODE) {
                            Element elemTest = (Element) elNodeTest;
                            //Получаем дату j-го теста i-го пациента и отправляем в NodeList
                            NodeList NLdate = elemTest.getElementsByTagName("date");
                            Element eldate = (Element) NLdate.item(0);
                            NodeList ResultNodeDate = eldate.getChildNodes();
                            //Получаем тип j-го теста i-го пациента и отправляем в NodeList
                            NodeList NLtype = elemTest.getElementsByTagName("type");
                            Element eltype = (Element) NLtype.item(0);
                            NodeList ResultNodeType = eltype.getChildNodes();
                            /*Получаем id лаборатории, в которой проводился  
                           j-й теста i-го пациента и отправляем в NodeList*/
                            NodeList NLlabID = elemTest.getElementsByTagName("labID");
                            Element ellabID = (Element) NLlabID.item(0);
                            NodeList ResultNodeLabID = ellabID.getChildNodes();

                            //Выводим значения нулевых элементов всех NodeList j-го теста
                            System.out.println("\nTest #" + (j + 1)
                                    + "\nTest date: "
                                    + ((Node) ResultNodeDate.item(0)).getNodeValue()
                                    + "\nTest type: "
                                    + ((Node) ResultNodeType.item(0)).getNodeValue()
                                    + "\nLaboratory id: "
                                    + ((Node) ResultNodeLabID.item(0)).getNodeValue());

                        }
                    }
                    //отделяем данные пациентов 
                    System.out.println("-----------------------------------------");

                }
            }
            //обработчик ошибок
        } catch (IOException e) {
            System.err.println("File error or I/O error: " + e);
        } catch (SAXException e) {
            System.err.println("Parsing failure: " + e);
        }
    }

}
