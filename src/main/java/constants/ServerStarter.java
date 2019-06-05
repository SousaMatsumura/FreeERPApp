package main.java.constants;

import main.java.interfaces.AParameters;
import main.java.interfaces.concrete.CParameters;

import java.util.*;

public enum ServerStarter {
   START_APACHE(1), START_POSTGRE_BEGIN(2), START_POSTGRE_END(3), START_ARTISAN_COMMAND(4), START_ARTISAN_PATH(5);

   private int id;
   ServerStarter(int id){ this.id = id; }

   public String getValue(){
      if(id == 1) return "\\EnterpriseDB-ApacheHTTPD\\apache\\bin\\httpd.exe -k start";
      else if(id == 2) return "\\11\\bin\\pg_ctl -D \"";
      else if(id == 3) return "\\11\\data\" start";
      else if(id == 4) return "\\PHP7\\php.exe artisan serve";
      else if(id == 5) return "\\laravel\\app-demo";
      else throw new NullPointerException(("Id "+id+" don't exists"));
   }

   public static final Set<AParameters> GET_SET = getConstantsSet();

   private static class Constants {
      private static final AParameters START_APACHE_PARAM = CParameters.getInstanceOfParameter(
            Resource.getUserPathChoice()+START_APACHE.getValue(), null);
      private static final AParameters START_POSTGRE_PARAM = CParameters.getInstanceOfParameter(
         Resource.getUserPathChoice()+START_POSTGRE_BEGIN.getValue()+ Resource.getUserPathChoice()
            +START_POSTGRE_END.getValue(), null);
      private static final AParameters START_ARTISAN_PARAM = CParameters.getInstanceOfParameter(
            Resource.getUserPathChoice()+START_ARTISAN_COMMAND.getValue(),
            Resource.getUserPathChoice()+START_ARTISAN_PATH.getValue());
   }

   private static Set getConstantsSet(){
      final List<AParameters> temp = Arrays.asList(
            Constants.START_APACHE_PARAM, Constants.START_POSTGRE_PARAM, Constants.START_ARTISAN_PARAM);
      return new LinkedHashSet<>(Collections.unmodifiableSet(new LinkedHashSet<>(temp)));
   }
   public static AParameters getParameters(final int value){
      if(value == 1) return Constants.START_APACHE_PARAM;
      if(value == 2) return Constants.START_POSTGRE_PARAM;
      if(value == 3) return Constants.START_ARTISAN_PARAM;
      else throw new NullPointerException(("Value "+value+" don't exists"));
   }
}
