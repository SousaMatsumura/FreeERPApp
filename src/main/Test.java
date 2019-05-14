package main;

import constants.DLL_CHECK_CONS;
import interfaces.AParameters;
import interfaces.concrete.CPredicates;

public class Test {
   public static void main(String[] args) {
      System.out.println(CPredicates.EXIST_AND.test(new AParameters[]{DLL_CHECK_CONS.Constants.X64_CHECK_PARAM,
            DLL_CHECK_CONS.Constants.X64_MSVCP140_PARAM}));

      //DLL_CHECK_CONS.getConstantsSet().forEach(p -> CPredicates.EXIST.test((AParameters) p));

   }
}
