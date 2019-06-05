import static org.junit.gen5.api.Assertions.*;

import main.java.interfaces.AParameters;
import main.java.interfaces.concrete.CConsumers;
import main.java.interfaces.concrete.CParameters;
import main.java.interfaces.concrete.CPredicates;
import org.apache.commons.lang3.StringUtils;
import org.junit.gen5.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

class Teste {
    static final String httpd = "httpd.exe";
    static final String post = "postgres.exe";

   public static void main(String[] args) {
      System.out.println(CPredicates.IS_STARTED.test(httpd));
      //test("c:\\Windows\\system32\\taskkill.exe psql");
   }
   public static synchronized void test(String s) {
      try{
         Process p;
         p = Runtime.getRuntime().exec(s);
         BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
         String line;
         while ((line = input.readLine()) != null){
            System.out.println(line);
            if(StringUtils.contains(line, post)){
               System.out.println("HERE "+line);
            }
         }
         input.close();
      }catch (Exception err){
         err.printStackTrace();
      }
   }

   public static synchronized boolean test() {
      try{
         Process p;
         p = Runtime.getRuntime().exec("tasklist");
         BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
         String line;
         while ((line = input.readLine()) != null){
            System.out.println(line);
            if(StringUtils.contains(line, post)){
               return true;
            }
         }
         input.close();
      }catch (Exception err){
         err.printStackTrace();
      }
      return false;
   }
}