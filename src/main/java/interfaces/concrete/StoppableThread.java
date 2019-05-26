package main.java.interfaces.concrete;

import main.java.interfaces.AParameters;

import java.security.InvalidParameterException;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

public class StoppableThread implements Runnable{
   private volatile boolean flag;
   private CountDownLatch countDownLatch;
   private Consumer command;
   private AParameters parameterCommand;
   private String strPredicate;

   private StoppableThread(Consumer command, AParameters parameterCommand, String strPredicate, CountDownLatch countDownLatch){
      if(command == null || parameterCommand == null || strPredicate == null || countDownLatch == null)
         throw new InvalidParameterException();
      this.flag = CPredicates.getIsInstalled().test(strPredicate);
      this.strPredicate = strPredicate;
      this.command = command;
      this.parameterCommand = parameterCommand;
      this.countDownLatch = countDownLatch;
   }

   public static class Builder{
      private CountDownLatch countDownLatch;
      private Consumer command;
      private AParameters parameterCommand;
      private String strPredicate;

      public Builder(){}

      public Builder countDownLatch(CountDownLatch countDownLatch){
         this.countDownLatch = countDownLatch;
         return this;
      }
      public Builder command(Consumer command){
         this.command = command;
         return this;
      }
      public Builder parameterCommand(AParameters parameterCommand){
         this.parameterCommand = parameterCommand;
         return this;
      }
      public Builder strPredicate(String strPredicate){
         this.strPredicate = strPredicate;
         return this;
      }
      public StoppableThread build(){
         return new StoppableThread(command, parameterCommand, strPredicate, countDownLatch);
      }
   }

   @Override
   public void run() {
      while(!flag){
         try {
            command.accept(parameterCommand);
            flag = CPredicates.getIsInstalled().test(strPredicate);
         }catch (Exception err){
            err.printStackTrace();
         }
      }
      countDownLatch.countDown();
   }

}