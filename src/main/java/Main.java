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

      /*Instalando Servers*/

      do
         CConsumers.COMMAND.accept(Resource.APACHE_PSQL_BAT.getParam());
      while ((!CPredicates.IS_STARTED.test(ServerName.HTTPD.toString()))
              && (!CPredicates.IS_STARTED.test(ServerName.POSTGRES.toString())));
      do
         CConsumers.COMMAND.accept(Resource.PHP_BAT.getParam());
      while (!CPredicates.IS_STARTED.test(ServerName.PHP.toString()));


      CConsumers.COMMAND.accept(Resource.START_BROWSER_BAT.getParam());



      /*Realizando o start dos servidores*/
      //handleServerStart();
      /*ServerStarter.GET_SET.forEach(p -> {
         new Thread(() -> {
            CConsumers.COMMAND.accept(p);
         }).start();
      });*/

      /*Opening laravel project on chrome*/

   }

   static void handleUserPathChoice() {
      try {
         Resource.setUserPathChoice();
         final File HTTPD_CONF = new File(Resource.USER_PATH_CHOICE + Resource.HTTPD_CONF_PATH.getValue()
                                                + Resource.HTTPD_CONF_FILE.getValue());
         if (CPredicates.FILE_CONTAINS_INSTALL_SETTINGS.test(HTTPD_CONF)) {
            CConsumers.CONFIG_FILE.accept(Resource.USER_PATH_CHOICE+Resource.HTTPD_CONF_PATH.getValue() + Resource.HTTPD_CONF_FILE.getValue());
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
}

