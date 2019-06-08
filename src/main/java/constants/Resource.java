package main.java.constants;

import main.java.interfaces.AParameters;
import main.java.interfaces.concrete.CParameters;
import main.java.interfaces.concrete.CSuppliers;

public enum Resource {
   WINDOWS(1), INI(2), HTTPD_CONF_PATH(3), HTTPD_CONF_FILE(4), INSTALL_SETTINGS(5), X64_CHECK(6), APACHE_PSQL_BAT(7), PHP_BAT(8), START_BROWSER_BAT(9);

   private int id;
   public static String USER_PATH_CHOICE;

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
      else if(id == 7) return "\\start-server\\startApachePSQL.vbs";
      else if(id == 8) return "\\start-server\\startPHP.vbs";
      else if(id == 9) return "\\start-server\\startBrowser.vbs";
      else throw new NullPointerException(("Id "+id+" don't exists"));
   }

   public AParameters getParam(){
      if(id == 1) return Constants.WINDOWS_PARAM;
      if(id == 2) return Constants.INI_PARAM;
      if(id == 6) return Constants.X64_CHECK_PARAM;
      if(id == 7) return Constants.APACHE_PSQL_BAT_PARAM;
      if(id == 8) return Constants.PHP_BAT_PARAM;
      if(id == 9) return Constants.START_BROWSER_BAT_PARAM;
      else throw new NullPointerException(("Parameter with 'id = "+id+"' don't exists"));
   }

   private static class Constants {
      private static final AParameters WINDOWS_PARAM = CParameters.getInstanceOfParameter(Resource.WINDOWS.getValue(), null);
      private static final AParameters INI_PARAM = CParameters.getInstanceOfParameter(
            Resource.WINDOWS.getValue()+ Resource.INI.getValue(), null);
      private static final AParameters APACHE_PSQL_BAT_PARAM = CParameters.getInstanceOfParameter(
            "cmd /c start "+USER_PATH_CHOICE+Resource.APACHE_PSQL_BAT.getValue(), null);
      private static final AParameters PHP_BAT_PARAM = CParameters.getInstanceOfParameter(
            "cmd /c start "+USER_PATH_CHOICE+Resource.PHP_BAT.getValue(), null);
      private static final AParameters X64_CHECK_PARAM = CParameters.getInstanceOfParameter(
            Resource.X64_CHECK.getValue(), null);
      private static final AParameters START_BROWSER_BAT_PARAM = CParameters.getInstanceOfParameter(
            "cmd /c start "+USER_PATH_CHOICE+Resource.START_BROWSER_BAT.getValue(), null);
   }

   public static void setUserPathChoice() {
      Resource.USER_PATH_CHOICE = CSuppliers.getReadUserPathChoice().get();
   }
}