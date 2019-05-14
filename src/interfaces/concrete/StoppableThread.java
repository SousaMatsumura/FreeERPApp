package interfaces.concrete;

import interfaces.AParameters;

import java.security.InvalidParameterException;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

public class StoppableThread implements Runnable{
   private volatile boolean flag;
   private CountDownLatch countDownLatch;
   private Consumer command;
   private AParameters parameterCommand;
   private AParameters parameterPredicate;


   private StoppableThread(Consumer command, AParameters parameterCommand, AParameters parameterPredicate, CountDownLatch countDownLatch){
      if(command == null || parameterCommand == null || parameterPredicate == null || countDownLatch == null)
         throw new InvalidParameterException();
      this.flag = CPredicates.EXIST.test(parameterPredicate);
      this.parameterPredicate = parameterPredicate;
      this.command = command;
      this.parameterCommand = parameterCommand;
      this.countDownLatch = countDownLatch;
   }

   public static class Builder{
      private CountDownLatch countDownLatch;
      private Consumer command;
      private AParameters parameterCommand;
      private AParameters parameterPredicate;

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
      public Builder parameterPredicate(AParameters parameterPredicate){
         this.parameterPredicate = parameterPredicate;
         return this;
      }
      public StoppableThread build(){
         return new StoppableThread(command, parameterCommand, parameterPredicate, countDownLatch);
      }
   }

   @Override
   public void run() {
      while(!flag){
         try {
            command.accept(parameterCommand);
            flag = CPredicates.EXIST.test(parameterPredicate);
         }catch (Exception err){
            err.printStackTrace();
         }
      }
      countDownLatch.countDown();
   }

   public void stop(){
      flag = true;
   }
}