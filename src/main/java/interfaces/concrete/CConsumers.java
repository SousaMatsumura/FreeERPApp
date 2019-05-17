package main.java.interfaces.concrete;

import main.java.constants.RESOURCES;
import main.java.interfaces.AParameters;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;
import java.util.prefs.Preferences;

public class CConsumers {
   private CConsumers(){}

   private static final Consumer<AParameters> SYNCHRONIZED_CONSUMER = new Consumer<AParameters>() {
      @Override
      public synchronized void accept(AParameters param) {
         try{
            Process p;
            if (param.path().equals("")) {
               p = Runtime.getRuntime().exec(param.command());

            } else {
               p = Runtime.getRuntime().exec(param.command(), null, new File(param.path()));
            }
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while (input.readLine() != null);
            input.close();
         }catch (Exception err){
            err.printStackTrace();
         }
      }
   };

   private static final Consumer<AParameters> CONSUMER = new Consumer<AParameters>() {
      @Override
      public void accept(AParameters param) {
         try{
            Process p;
            if (param.path().equals("")) {
               p = Runtime.getRuntime().exec(param.command());
            } else {
               p = Runtime.getRuntime().exec(param.command(), null, new File(param.path()));
            }
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while (input.readLine() != null);
            input.close();
         }catch (Exception err){
            err.printStackTrace();
         }
      }
   };

   private static final Consumer<String> PERSIST_USER_PATH_CHOICE = new Consumer<String>() {
      @Override
      public void accept(final String userString) {
         Preferences pref = Preferences.userRoot().node("src\\main\\resources");
         pref.put("PATH_CHOICE", userString);
         try {
            pref.exportNode(new FileOutputStream(new File("src\\main\\resources\\userPreference.xml")));
         }catch (Exception err){
            err.printStackTrace();
         }
      }
   };

   private static final Consumer<String> CONFIG_HTTPD_CONF = new Consumer<String>() {
      @Override
      public void accept(final String beginPath) {
         final AParameters PATH_PARAM = CParameters.getInstanceOfParameter(beginPath, null),
               FILE_PARAM = CParameters.getInstanceOfParameter(
                     beginPath+RESOURCES.HTTPD_CONF_PATH.getValue()+RESOURCES.HTTPD_CONF_FILE.getValue(), null);
         try {
            if (CPredicates.getExistAnd().test(new AParameters[]{PATH_PARAM, FILE_PARAM})){
               File httpdConf = new File(
                  beginPath+RESOURCES.HTTPD_CONF_PATH.getValue()+RESOURCES.HTTPD_CONF_FILE.getValue());
               String fileString =  FileUtils.readFileToString(httpdConf, StandardCharsets.UTF_8),
                     replace = CSuppliers.getPersistentUserPathChoice().get();
               fileString = StringUtils.replace(fileString, RESOURCES.INSTALL_SETTINGS.getValue(), replace);
               FileUtils.writeStringToFile(httpdConf, fileString, StandardCharsets.UTF_8);
            } else {
               throw new Exception("File or Directory not found.");
            }
         }catch (Exception err){
            err.printStackTrace();
         }
      }
   };

   public static Consumer<AParameters> getSynchronizedConsumer() {
      return SYNCHRONIZED_CONSUMER;
   }

   public static Consumer<AParameters> getConsumer() {
      return CONSUMER;
   }

   public static Consumer<String> getPersistUserPathChoice() {
      return PERSIST_USER_PATH_CHOICE;
   }

   public static Consumer<String> getConfigHttpdConf() {
      return CONFIG_HTTPD_CONF;
   }
}
