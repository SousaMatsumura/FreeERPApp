package main.java;

import main.java.constants.*;
import main.java.interfaces.concrete.CConsumers;
import main.java.interfaces.concrete.CPredicates;
import main.java.interfaces.concrete.StoppableThread;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Main {
   public static void main(String[] args) throws InterruptedException {

      /*Lidando com o caminho de instalação escolhido pelo usuário*/
      handleUserPathChoice();

      /*Abrindo cada uma das janelas de instalação dos vcredist, uma por vez*/
      handleVCRedists();

      /*Instalando HTTPD Apache Server*/
      CConsumers.COMMAND.accept(CommonCommand.getParameters(1));

      /*Realizando o start dos servidores*/
      handleServerStart();
      /*ServerStarter.GET_SET.forEach(p -> {
         new Thread(() -> {
            CConsumers.COMMAND.accept(p);
         }).start();
      });*/

      /*Opening laravel project on chrome*/
      CConsumers.COMMAND.accept(CommonCommand.getParameters(2));
   }

   static void handleUserPathChoice() {
      try {
         Resource.setUserPathChoice();
         final File HTTPD_CONF = new File(Resource.getUserPathChoice() + Resource.HTTPD_CONF_PATH.getValue()
                                                + Resource.HTTPD_CONF_FILE.getValue());
         if (CPredicates.FILE_CONTAINS_INSTALL_SETTINGS.test(HTTPD_CONF)) {
            CConsumers.CONFIG_HTTPD_CONF.accept(Resource.getUserPathChoice());
         }
      } catch (Exception err) {
         err.printStackTrace();
      }
   }

   static void handleVCRedists()  throws InterruptedException{
      CountDownLatch countDownLatch = new CountDownLatch(3);

      StoppableThread st;

      List<Thread> handleInstallationsCheck = new ArrayList<>();
      Iterator<String> itr = CheckInstalled.GET_SET.iterator();
      int i = 1;
      while(i<4 && itr.hasNext()){
         st = new StoppableThread.Builder().command(CConsumers.COMMAND)
                    .parameterCommand(VisualCppRedist.getParameters(i))
                    .strPredicate(itr.next())
                    .countDownLatch(countDownLatch)
                    .predicate(CPredicates.IS_INSTALLED)
                    .build();
         handleInstallationsCheck.add(new Thread(st));
         i++;
      }
      i=0;
      for (int s = handleInstallationsCheck.size(); i<s;i++) {
         if(i==2) {
            if (CPredicates.EXIST.test(Resource.X64_CHECK.getValue()))
               handleInstallationsCheck.get(i).start();
            else countDownLatch.countDown();
         }else{
            handleInstallationsCheck.get(i).start();
         }
      }
      countDownLatch.await();
   }

   static void handleServerStart()  throws InterruptedException{
      Iterator<String> itr = ServerName.GET_SET.iterator();
      int i = 1;
      List<Thread> handleServerStarterCheck = new ArrayList<>();
      while(i<4 && itr.hasNext()){
         while(CPredicates.IS_STARTED.test(itr.next())){
            handleServerStarterCheck.add( new
            CConsumers.COMMAND.accept(ServerStarter.getParameters(i));
            i++;
            )
         }
      }

      /*ServerStarter.GET_SET.forEach(p -> {
         handleServerStarterCheck.add(new Thread(() -> {
            CConsumers.COMMAND.accept(p);
         }));
      });*/


      /*CountDownLatch countDownLatch = new CountDownLatch(3);

      StoppableThread st;

      List<Thread> handleServerStarterCheck = new ArrayList<>();
      Iterator<String> itr = ServerName.GET_SET.iterator();
      int i = 1;
      while(i<4 && itr.hasNext()){
         st = new StoppableThread.Builder().command(CConsumers.COMMAND)
                    .parameterCommand(ServerStarter.getParameters(i))
                    .strPredicate(itr.next())
                    .countDownLatch(countDownLatch)
                    .predicate(CPredicates.IS_STARTED)
                    .build();
         handleServerStarterCheck.add(new Thread(st));
         i++;
      }
      i=0;
      for (int s = handleServerStarterCheck.size(); i<s;i++) {
         handleServerStarterCheck.get(i).start();
      }
      countDownLatch.await();*/
   }
}
