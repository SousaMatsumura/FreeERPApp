package main;

import constants.DLL_CHECK_CONS;
import interfaces.concrete.CConsumer;
import constants.CommonCommands;
import constants.ServerStarters;
import constants.VCRedists;
import interfaces.concrete.CPredicates;
import interfaces.concrete.StoppableThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Main {
   public static void main(String[] args) throws InterruptedException {

      /*Abrindo cada uma das janelas de instalação dos vcredist, uma por vez*/

      CountDownLatch countDownLatch = new CountDownLatch(3);

      StoppableThread st;

      List<Thread> handleVCRedits = new ArrayList<>();
      for(int i = 1; i<4; i++){
         st = new StoppableThread.Builder().command(CConsumer.getSynchronizedConsumer())
                    .parameterCommand(VCRedists.getParameters(i))
                    .parameterPredicate(DLL_CHECK_CONS.getParameters(i))
                    .countDownLatch(countDownLatch).build();

         handleVCRedits.add(new Thread(st));
      }

      /*st = new StoppableThread.Builder().command(CConsumer.getSynchronizedConsumer())
                                 .parameterCommand(VCRedists.getParameters(1))
                                 .parameterPredicate(DLL_CHECK_CONS.getParameters(1))
                                 .countDownLatch(countDownLatch).build();

      final Thread handleX86_2013 = new Thread(st);

      st = new StoppableThread.Builder().command(CConsumer.getSynchronizedConsumer())
                 .parameterCommand(VCRedists.getParameters(2))
                 .parameterPredicate(DLL_CHECK_CONS.getParameters(2))
                 .countDownLatch(countDownLatch).build();

      final Thread handleX86_2017 = new Thread(st);

      st = new StoppableThread.Builder().command(CConsumer.getSynchronizedConsumer())
                 .parameterCommand(VCRedists.getParameters(3))
                 .parameterPredicate(DLL_CHECK_CONS.getParameters(3))
                 .countDownLatch(countDownLatch).build();

      final Thread handleX64_2017 = new Thread(st);

      handleX86_2013.start();
      handleX86_2017.start();
      if(CPredicates.EXIST.test(DLL_CHECK_CONS.getParameters(4))) handleX64_2017.start();
      else countDownLatch.countDown();*/

      for (int i = 0, s = handleVCRedits.size(); i<s;i++) {
         if(i==2) {
            if (CPredicates.EXIST.test(DLL_CHECK_CONS.getParameters(4))) handleVCRedits.get(i).start();
            else countDownLatch.countDown();
         }else{
            handleVCRedits.get(i).start();
         }
      }

      countDownLatch.await();


      /*Instalando HTTPD Apache Server*/
      CConsumer.getConsumer().accept(CommonCommands.getParameters(1));

      /*Realizando o start dos servidores*/
      ServerStarters.GET_SET.forEach(p -> {
         new Thread(() -> {
            CConsumer.getConsumer().accept(p);
         }).start();
      });

      /*Opening laravel project on chrome*/
      CConsumer.getConsumer().accept(CommonCommands.getParameters(2));
   }
}