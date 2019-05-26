package main.java.constants;

import main.java.interfaces.AParameters;
import main.java.interfaces.concrete.CParameters;

import java.util.*;

public class VisualCppRedist {

   private VisualCppRedist(){}

   public static AParameters getParameters(final int value){
      if(value == 1) return Constants.X86_2013_PARAM;
      if(value == 2) return Constants.X86_2017_PARAM;
      if(value == 3) return Constants.X64_2017_PARAM;
      else throw new NullPointerException(("Value "+value+" don't exists"));
   }

   private static class Constants {
      private static final AParameters X86_2013_PARAM = CParameters.getInstanceOfParameter(
            Resource.getUserPathChoice()+"\\EnterpriseDB-ApacheHTTPD\\installer\\ApacheHTTPD\\vcredist_x86.exe", null);
      private static final AParameters X86_2017_PARAM = CParameters.getInstanceOfParameter(
            Resource.getUserPathChoice()+"\\11\\installer\\vcredist_x86.exe", null);
      private static final AParameters X64_2017_PARAM = CParameters.getInstanceOfParameter(
            Resource.getUserPathChoice()+"\\11\\installer\\vcredist_x64.exe", null);
   }

}