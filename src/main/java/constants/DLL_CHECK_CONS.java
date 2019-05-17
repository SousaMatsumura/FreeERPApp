package main.java.constants;

import main.java.interfaces.AParameters;
import main.java.interfaces.concrete.CParameters;

import java.util.*;

public final class DLL_CHECK_CONS {

   private DLL_CHECK_CONS(){}

   public static int getIndex(final AParameters param){
      if(param.equals(Constants.X86_MSVCR120_PARAM)) return 1;
      else if(param.equals(Constants.X86_MSVCP140_PARAM)) return 2;
      else throw new NullPointerException(("Parameter "+param+" don't exists"));
   }

   public static AParameters getParameters(final int value){
      if(value == 1) return Constants.X86_MSVCR120_PARAM;
      if(value == 2) return Constants.X86_MSVCP140_PARAM;
      if(value == 3) return Constants.X64_MSVCP140_PARAM;
      if(value == 4) return Constants.X64_CHECK_PARAM;
      else throw new NullPointerException(("Value "+value+" don't exists"));
   }

   public static final Set<AParameters> GET_SET = getConstantsSet();

   private static class Constants {
      private static final AParameters X86_MSVCR120_PARAM = CParameters.getInstanceOfParameter(
            "C:\\Windows\\System32\\msvcr120.dll", null);
      private static final AParameters X86_MSVCP140_PARAM = CParameters.getInstanceOfParameter(
            "C:\\Windows\\System32\\msvcp140.dll", null);
      private static final AParameters X64_CHECK_PARAM = CParameters.getInstanceOfParameter(
            "C:\\Windows\\SysWOW64", null);
      private static final AParameters X64_MSVCP140_PARAM = CParameters.getInstanceOfParameter(
            "C:\\Windows\\SysWOW64\\msvcp140.dll", null);
   }

   private static Set getConstantsSet(){
      final List<AParameters> temp = Arrays.asList(
            Constants.X86_MSVCR120_PARAM, Constants.X86_MSVCP140_PARAM, Constants.X64_MSVCP140_PARAM, Constants.X64_CHECK_PARAM);
      return new LinkedHashSet<>(Collections.unmodifiableSet(new LinkedHashSet<>(temp)));
   }

}
