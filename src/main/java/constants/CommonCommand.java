package main.java.constants;

import main.java.interfaces.AParameters;
import main.java.interfaces.concrete.CParameters;

public enum CommonCommand {
   INSTALL_APACHE(1), OPEN_DEFAULT_BROWSER(2);

   private int id;

   CommonCommand(int id){
      this.id = id;
   }

   public String getValue(){
      if(id == 1) return "\\EnterpriseDB-ApacheHTTPD\\apache\\bin\\httpd.exe -k install";
      else if(id == 2) return
         "http://localhost:8000";
      else throw new NullPointerException(("Id "+id+" don't exists"));
   }

   public static AParameters getParameters(final int value){
      if(value == 1) return Constants.INSTALL_APACHE_PARAM;
      if(value == 2) return Constants.OPEN_DEFAULT_BROWSER_PARAM;
      else throw new NullPointerException(("Value "+value+" doesn't exists"));
   }

   private static class Constants {
      private static final AParameters INSTALL_APACHE_PARAM = CParameters.getInstanceOfParameter(
            Resource.getUserPathChoice()+INSTALL_APACHE.getValue(), null);
      private static final AParameters OPEN_DEFAULT_BROWSER_PARAM = CParameters.getInstanceOfParameter(
            "cmd /c start "+OPEN_DEFAULT_BROWSER.getValue(), null);
   }
}
