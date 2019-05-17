package main.java.interfaces.concrete;

import main.java.constants.RESOURCES;
import main.java.interfaces.AParameters;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.function.Supplier;

public class CSuppliers {
   private CSuppliers(){}
   private static final Supplier<String> NON_PERSISTENT_USER_PATH_CHOICE = new Supplier<String>() {
      @Override
      public String get() {
         try {
            if (CPredicates.getExistAnd().test(new AParameters[]{RESOURCES.WINDOWS.getParam(), RESOURCES.INI.getParam()})) {
               BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(
                     RESOURCES.WINDOWS.getValue()+RESOURCES.INI.getValue())));
               String line, preference = "";
               while ((line = input.readLine()) != null) {
                  if (!line.trim().equals("") && line.trim().equals(RESOURCES.INSTALL_SETTINGS.getValue()))
                     preference = StringUtils.split(input.readLine(), '=')[1];
               }
               input.close();
               if(preference.equals(""))
                  throw new StringIndexOutOfBoundsException("The FreeERP.ini doesn't have the [InstallSettings] variable set.");
               return preference;
            } else {
               throw new Exception("File or Directory not found.");
            }
         }catch (Exception err){
            err.printStackTrace();
            return null;
         }
      }
   };

   private static final Supplier<String> PERSISTENT_USER_PATH_CHOICE = new Supplier<String>(){
      @Override
      public String get() {
         try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new FileInputStream(new File(RESOURCES.PATH.getValue()+RESOURCES.XML.getValue())));

            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();
            XPathExpression expr = xPath.compile("/preferences/root/node/map/entry[@key='PATH_CHOICE']/@value");
            NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            if(nl.getLength() > 0) return nl.item(0).getNodeValue();
            else throw new NullPointerException("Not found the 'value' variable set in tag entry[@key='PATH_CHOICE'] in file (src\\main\\resources\\userPreference.xml).");
         }catch (Exception err){
            err.printStackTrace();
         }
         return null;
      }
   };

   public static Supplier<String> getNonPersistentUserPathChoice() {
      return NON_PERSISTENT_USER_PATH_CHOICE;
   }

   public static Supplier<String> getPersistentUserPathChoice() {
      return PERSISTENT_USER_PATH_CHOICE;
   }
}
