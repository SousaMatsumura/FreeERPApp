package main.java.interfaces.concrete;

import main.java.constants.Resource;
import main.java.interfaces.AParameters;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.function.Supplier;

public class CSuppliers {
   private CSuppliers(){}
   private static final Supplier<String> READ_USER_PATH_CHOICE = new Supplier<String>() {
      @Override
      public String get() {
         try {
            if (CPredicates.getExistAnd().test(new AParameters[]{Resource.WINDOWS.getParam(), Resource.INI.getParam()})) {
               BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(
                     Resource.WINDOWS.getValue()+ Resource.INI.getValue())));
               String line, preference = "";
               while ((line = input.readLine()) != null) {
                  if (!line.trim().equals("") && line.trim().equals(Resource.INSTALL_SETTINGS.getValue()))
                     preference = StringUtils.split(input.readLine(), '=')[1];
               }
               input.close();
               if(preference.equals(""))
                  throw new StringIndexOutOfBoundsException("The FreeERP.ini doesn't have the [InstallSettings] variable set.");
               return preference;
            } else {
               throw new Exception("File "+ Resource.WINDOWS.getValue()+ Resource.INI.getValue()+" not found.");
            }
         }catch (Exception err){
            err.printStackTrace();
            return null;
         }
      }
   };

   public static Supplier<String> getReadUserPathChoice() {
      return READ_USER_PATH_CHOICE;
   }

}
