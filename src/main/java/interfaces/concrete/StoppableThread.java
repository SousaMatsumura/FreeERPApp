package main.java.interfaces.concrete;

import main.java.interfaces.AParameters;

import java.security.InvalidParameterException;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class StoppableThread implements Runnable{
   private volatile boolean flag;
   private CountDownLatch countDownLatch;
   private Consumer command;
   private AParameters parameterCommand;
   private String strPredicate;
   private Predicate<String> predicate;

   private StoppableThread(Consumer command, AParameters parameterCommand, Predicate<String> predicate, String strPredicate, CountDownLatch countDownLatch){
      if(command == null || parameterCommand == null || strPredicate == null || countDownLatch == null || predicate == null)
         throw new InvalidParameterException();
      this.flag = predicate.test(strPredicate);
      this.strPredicate = strPredicate;
      this.predicate = predicate;
      this.command = command;
      this.parameterCommand = parameterCommand;
      this.countDownLatch = countDownLatch;
   }

   public static class Builder{
      private CountDownLatch countDownLatch;
      private Consumer command;
      private AParameters parameterCommand;
      private String strPredicate;
      private Predicate<String> predicate;

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
      public Builder predicate(Predicate<String> predicate){
         this.predicate = predicate;
         return this;
      }
      public StoppableThread build(){
         return new StoppableThread(command, parameterCommand, predicate, strPredicate, countDownLatch);
      }
   }

   @Override
   public void run() {
      while(!flag){
         try {
            command.accept(parameterCommand);
            flag = predicate.test(strPredicate);
         }catch (Exception err){
            err.printStackTrace();
         }
      }
      countDownLatch.countDown();
   }

}