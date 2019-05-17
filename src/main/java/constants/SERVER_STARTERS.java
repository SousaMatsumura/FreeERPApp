package main.java.constants;

import main.java.interfaces.AParameters;
import main.java.interfaces.concrete.CParameters;

import java.util.*;

public final class SERVER_STARTERS {
   private SERVER_STARTERS(){}

   public static int getIndex(final AParameters param){
      if(param.equals(Constants.START_APACHE_PARAM)) return 1;
      else if(param.equals(Constants.START_POSTGRE_PARAM)) return 2;
      else if(param.equals(Constants.START_ARTISAN_PARAM)) return 3;
      else throw new NullPointerException(("Parameter "+param+" don't exists"));
   }

   public static AParameters getParameters(final int value){
      if(value == 1) return Constants.START_APACHE_PARAM;
      if(value == 2) return Constants.START_POSTGRE_PARAM;
      if(value == 3) return Constants.START_ARTISAN_PARAM;
      else throw new NullPointerException(("Value "+value+" don't exists"));
   }

   public static final Set<AParameters> GET_SET = getConstantsSet();

   private static class Constants {
      private static final AParameters START_APACHE_PARAM = CParameters.getInstanceOfParameter(
            "C:\\FreeERP\\EnterpriseDB-ApacheHTTPD\\apache\\bin\\httpd.exe -k start", null);
      private static final AParameters START_POSTGRE_PARAM = CParameters.getInstanceOfParameter(
            "C:\\FreeERP\\11\\bin\\pg_ctl -D \"C:\\FreeERP\\11\\data\" start", null);
      private static final AParameters START_ARTISAN_PARAM = CParameters.getInstanceOfParameter(
            "C:\\FreeERP\\PHP7\\php.exe artisan serve", "C:\\FreeERP\\laravel\\app-demo");
   }

   private static Set getConstantsSet(){
      final List<AParameters> temp = Arrays.asList(
            Constants.START_APACHE_PARAM, Constants.START_POSTGRE_PARAM, Constants.START_ARTISAN_PARAM);
      return new LinkedHashSet<>(Collections.unmodifiableSet(new LinkedHashSet<>(temp)));
   }
}
