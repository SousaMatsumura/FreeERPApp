package constants;

import interfaces.AParameters;
import interfaces.concrete.CParameters;

public enum CommonCommands {
   INSTALL_APACHE(1), OPEN_CHROME(2);
   private int value;

   CommonCommands(int value){this.value = value;}

   public static int getIndex(final AParameters param){
      if(param.equals(Constants.INSTALL_APACHE_PARAM)) return 1;
      else if(param.equals(Constants.OPEN_CHROME_PARAM)) return 2;
      else throw new NullPointerException(("Parameter "+param+" don't exists"));
   }

   public static AParameters getParameters(final int value){
      if(value == 1) return Constants.INSTALL_APACHE_PARAM;
      if(value == 2) return Constants.OPEN_CHROME_PARAM;
      else throw new NullPointerException(("Value "+value+" don't exists"));
   }

   public static class Constants {
      public static final AParameters INSTALL_APACHE_PARAM = CParameters.getInstanceOfParameter(
            "C:\\FreeERP\\EnterpriseDB-ApacheHTTPD\\apache\\bin\\httpd.exe -k install", null);
      public static final AParameters OPEN_CHROME_PARAM = CParameters.getInstanceOfParameter(
            "c:\\program files (x86)\\google\\chrome\\application\\chrome.exe"
                  +" --new-window \"http://localhost:8000\"", null);
   }

}
