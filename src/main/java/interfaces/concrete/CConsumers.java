package main.java.interfaces.concrete;

import main.java.constants.Resource;
import main.java.interfaces.AParameters;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public class CConsumers {
   public CConsumers() {}


   public static final Consumer<AParameters> COMMAND = new Consumer<AParameters>() {
      @Override
      public synchronized void accept(AParameters param) {
         try {
            Process p;
            if (param.path().equals("")) {
               p = Runtime.getRuntime().exec(param.command());
            } else {
               p = Runtime.getRuntime().exec(param.command(), null, new File(param.path()));
            }
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while (input.readLine() != null) ;
            input.close();
         } catch (Exception err) {
            err.printStackTrace();
         }
      }
   };

   public static final Consumer<String> CONFIG_FILE = new Consumer<String>() {
      @Override
      public void accept(final String filePath) {
         try {
            if (CPredicates.EXIST_AND.test(new String[]{filePath, filePath})) {
               File httpdConf = new File(filePath);
               String fileString = FileUtils.readFileToString(httpdConf, StandardCharsets.UTF_8),
                       replace = CSuppliers.getReadUserPathChoice().get();
               fileString = StringUtils.replace(fileString, Resource.INSTALL_SETTINGS.getValue(), replace);
               FileUtils.writeStringToFile(httpdConf, fileString, StandardCharsets.UTF_8);
            } else {
               throw new Exception("File or Directory not found.");
            }
         } catch (Exception err) {
            err.printStackTrace();
         }
      }
   };
}
