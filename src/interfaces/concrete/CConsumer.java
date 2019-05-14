package interfaces.concrete;

import interfaces.AParameters;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public class CConsumer {
   private static final Consumer<AParameters> SYNCHRONIZED_CONSUMER = new Consumer<AParameters>() {
      @Override
      public synchronized void accept(AParameters param) {
         try{

            Process p;
            if (param.path().equals("")) {
               p = Runtime.getRuntime().exec(param.command());

            } else {
               p = Runtime.getRuntime().exec(param.command(), null, new File(param.path()));
            }
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while (input.readLine() != null);
            input.close();
         }catch (Exception err){
            err.printStackTrace();
         }
      }
   };

   private static final Consumer<AParameters> CONSUMER = new Consumer<AParameters>() {
      @Override
      public void accept(AParameters param) {
         try{
            Process p;
            if (param.path().equals("")) {
               p = Runtime.getRuntime().exec(param.command());
            } else {
               p = Runtime.getRuntime().exec(param.command(), null, new File(param.path()));
            }
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while (input.readLine() != null);
            input.close();
         }catch (Exception err){
            err.printStackTrace();
         }
      }
   };

   public static Consumer<AParameters> getSynchronizedConsumer() {
      return SYNCHRONIZED_CONSUMER;
   }

   public static Consumer<AParameters> getConsumer() {
      return CONSUMER;
   }
}
