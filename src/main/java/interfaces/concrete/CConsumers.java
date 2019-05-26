package main.java.interfaces.concrete;

import main.java.constants.Resource;
import main.java.interfaces.AParameters;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public class CConsumers {
   private CConsumers(){}

   private static final Consumer<AParameters> COMMAND = new Consumer<AParameters>() {
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

   private static final Consumer<String> CONFIG_HTTPD_CONF = new Consumer<String>() {
      @Override
      public void accept(final String beginPath) {
         final String filePath = beginPath+ Resource.HTTPD_CONF_PATH.getValue()+ Resource.HTTPD_CONF_FILE.getValue();
         try {
            if (CPredicates.getExistAnd().test(new String[]{beginPath, filePath})){
               File httpdConf = new File(
                  beginPath+ Resource.HTTPD_CONF_PATH.getValue()+ Resource.HTTPD_CONF_FILE.getValue());
               String fileString =  FileUtils.readFileToString(httpdConf, StandardCharsets.UTF_8),
                     replace = CSuppliers.getReadUserPathChoice().get();
               fileString = StringUtils.replace(fileString, Resource.INSTALL_SETTINGS.getValue(), replace);
               FileUtils.writeStringToFile(httpdConf, fileString, StandardCharsets.UTF_8);
            } else {
               throw new Exception("File or Directory not found.");
            }
         }catch (Exception err){
            err.printStackTrace();
         }
      }
   };

   public static Consumer<AParameters> getCommand() {
      return COMMAND;
   }

   public static Consumer<String> getConfigHttpdConf() {
      return CONFIG_HTTPD_CONF;
   }
}
