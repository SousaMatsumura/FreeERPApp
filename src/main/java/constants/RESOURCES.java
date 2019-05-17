package main.java.constants;

import main.java.interfaces.AParameters;
import main.java.interfaces.concrete.CParameters;

public enum RESOURCES {
   PATH(1), XML(2), WINDOWS(3), INI(4), HTTPD_CONF_PATH(5), HTTPD_CONF_FILE(6), INSTALL_SETTINGS(7);

   private int id;

   RESOURCES(int id){
      this.id = id;
   }

   public String getValue(){
      if(id == 1) return "src\\main\\resources\\";
      else if(id == 2) return "userPreference.xml";
      else if(id == 3) return "C:\\Windows\\";
      else if(id == 4) return "FreeERP.ini";
      else if(id == 5) return "\\EnterpriseDB-ApacheHTTPD\\apache\\conf\\";
      else if(id == 6) return "httpd.conf";
      else if(id == 7) return "[InstallSettings]";
      else throw new NullPointerException(("Id "+id+" don't exists"));
   }

   public AParameters getParam(){
      if(id == 1) return Constants.PATH_PARAM;
      if(id == 2) return Constants.XML_PARAM;
      if(id == 3) return Constants.WINDOWS_PARAM;
      if(id == 4) return Constants.INI_PARAM;
      else throw new NullPointerException(("Parameter with 'id = "+id+"' don't exists"));
   }

   private static class Constants {
      private static final AParameters PATH_PARAM = CParameters.getInstanceOfParameter(RESOURCES.PATH.getValue(), null);
      private static final AParameters XML_PARAM = CParameters.getInstanceOfParameter(
            RESOURCES.PATH.getValue()+RESOURCES.XML.getValue(), null);
      private static final AParameters WINDOWS_PARAM = CParameters.getInstanceOfParameter(RESOURCES.WINDOWS.getValue(), null);
      private static final AParameters INI_PARAM = CParameters.getInstanceOfParameter(
            RESOURCES.WINDOWS.getValue()+RESOURCES.INI.getValue(), null);
      private static final AParameters HTTPD_CONF_PATH_PARAM = CParameters.getInstanceOfParameter(
            RESOURCES.HTTPD_CONF_PATH.getValue(), null);
      private static final AParameters HTTPD_CONF_FILE_PARAM = CParameters.getInstanceOfParameter(
            RESOURCES.HTTPD_CONF_PATH.getValue()+RESOURCES.HTTPD_CONF_FILE.getValue(), null);
   }
}