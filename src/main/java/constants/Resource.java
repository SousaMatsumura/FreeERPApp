package main.java.constants;

import main.java.interfaces.AParameters;
import main.java.interfaces.concrete.CParameters;
import main.java.interfaces.concrete.CSuppliers;

public enum Resource {
   WINDOWS(1), INI(2), HTTPD_CONF_PATH(3), HTTPD_CONF_FILE(4), INSTALL_SETTINGS(5), X64_CHECK(6);

   private int id;
   private static String userPathChoice;

   Resource(int id){
      this.id = id;
   }

   public String getValue(){
      if(id == 1) return "C:\\Windows\\";
      else if(id == 2) return "FreeERP.ini";
      else if(id == 3) return "\\EnterpriseDB-ApacheHTTPD\\apache\\conf\\";
      else if(id == 4) return "httpd.conf";
      else if(id == 5) return "[InstallSettings]";
      else if(id == 6) return "C:\\Windows\\SysWOW64";
      else throw new NullPointerException(("Id "+id+" don't exists"));
   }

   public AParameters getParam(){
      if(id == 1) return Constants.WINDOWS_PARAM;
      if(id == 2) return Constants.INI_PARAM;
      if(id == 6) return Constants.X64_CHECK_PARAM;
      else throw new NullPointerException(("Parameter with 'id = "+id+"' don't exists"));
   }

   private static class Constants {
      private static final AParameters WINDOWS_PARAM = CParameters.getInstanceOfParameter(Resource.WINDOWS.getValue(), null);
      private static final AParameters INI_PARAM = CParameters.getInstanceOfParameter(
            Resource.WINDOWS.getValue()+ Resource.INI.getValue(), null);
      private static final AParameters X64_CHECK_PARAM = CParameters.getInstanceOfParameter(
            Resource.X64_CHECK.getValue(), null);
   }

   public static String getUserPathChoice() {
      return userPathChoice;
   }

   public static void setUserPathChoice() {
      Resource.userPathChoice = CSuppliers.getReadUserPathChoice().get();
   }
}