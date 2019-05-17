package main.java.constants;

import main.java.interfaces.AParameters;
import main.java.interfaces.concrete.CParameters;

import java.util.*;

public final class VISUAL_CPP_REDISTS {
   private VISUAL_CPP_REDISTS(){}

   public static int getIndex(final AParameters param){
      if(param.equals(Constants.X86_2013_PARAM)) return 1;
      else if(param.equals(Constants.X86_2017_PARAM)) return 2;
      else if(param.equals(Constants.X64_2017_PARAM)) return 3;
      else throw new NullPointerException(("Parameter "+param+" don't exists"));
   }

   public static AParameters getParameters(final int value){
      if(value == 1) return Constants.X86_2013_PARAM;
      if(value == 2) return Constants.X86_2017_PARAM;
      if(value == 3) return Constants.X64_2017_PARAM;
      else throw new NullPointerException(("Value "+value+" don't exists"));
   }

   public static final Set<AParameters> GET_SET = getConstantsSet();

   private static class Constants {
      private static final AParameters X86_2013_PARAM = CParameters.getInstanceOfParameter(
            "C:\\FreeERP\\EnterpriseDB-ApacheHTTPD\\installer\\ApacheHTTPD\\vcredist_x86.exe", null);
      private static final AParameters X86_2017_PARAM = CParameters.getInstanceOfParameter(
            "C:\\FreeERP\\11\\installer\\vcredist_x86.exe", null);
      private static final AParameters X64_2017_PARAM = CParameters.getInstanceOfParameter(
            "C:\\FreeERP\\11\\installer\\vcredist_x64.exe", null);
   }

   private static Set getConstantsSet(){
      final List<AParameters> temp = Arrays.asList(
            Constants.X86_2013_PARAM, Constants.X86_2017_PARAM, Constants.X64_2017_PARAM);
      return new LinkedHashSet<>(Collections.unmodifiableSet(new LinkedHashSet<>(temp)));
   }
}
