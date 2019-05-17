package main.java;

import main.java.constants.*;
import main.java.interfaces.concrete.CConsumers;
import main.java.interfaces.concrete.CPredicates;
import main.java.interfaces.concrete.CSuppliers;
import main.java.interfaces.concrete.StoppableThread;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Main {
   public static void main(String[] args) throws InterruptedException {

      /*Lidando com o caminho de instalação escolhido pelo usuário*/
      handleUserPathChoice();

      /*Abrindo cada uma das janelas de instalação dos vcredist, uma por vez*/
      handleVCRedists();

      /*Instalando HTTPD Apache Server*/
      CConsumers.getConsumer().accept(COMMON_COMMANDS.getParameters(1));

      /*Realizando o start dos servidores*/
      SERVER_STARTERS.GET_SET.forEach(p -> {
         new Thread(() -> {
            CConsumers.getConsumer().accept(p);
         }).start();
      });

      /*Opening laravel project on chrome*/
      CConsumers.getConsumer().accept(COMMON_COMMANDS.getParameters(2));
   }

   static void handleUserPathChoice() {
      try {
         final File PERSISTENT_FILE = new File(RESOURCES.XML.getParam().command());
         if (CPredicates.getIsFileEmpty().test(PERSISTENT_FILE))
            CConsumers.getPersistUserPathChoice().accept(CSuppliers.getNonPersistentUserPathChoice().get());
         final String USER_PATH_CHOICE = CSuppliers.getPersistentUserPathChoice().get();
         final File HTTPD_CONF = new File(USER_PATH_CHOICE + RESOURCES.HTTPD_CONF_PATH.getValue()
                                                + RESOURCES.HTTPD_CONF_FILE.getValue());
         if (CPredicates.getFileContainsInstallSettings().test(HTTPD_CONF)) {
            CConsumers.getConfigHttpdConf().accept(USER_PATH_CHOICE);
         }
      } catch (Exception err) {
         err.printStackTrace();
      }
   }

   static void handleVCRedists()  throws InterruptedException{
      CountDownLatch countDownLatch = new CountDownLatch(3);

      StoppableThread st;

      List<Thread> handleVCRedits = new ArrayList<>();
      for(int i = 1; i<4; i++){
         st = new StoppableThread.Builder().command(CConsumers.getSynchronizedConsumer())
                    .parameterCommand(VISUAL_CPP_REDISTS.getParameters(i))
                    .parameterPredicate(DLL_CHECK_CONS.getParameters(i))
                    .countDownLatch(countDownLatch).build();

         handleVCRedits.add(new Thread(st));
      }

      for (int i = 0, s = handleVCRedits.size(); i<s;i++) {
         if(i==2) {
            if (CPredicates.getExist().test(DLL_CHECK_CONS.getParameters(4)))
               handleVCRedits.get(i).start();
            else countDownLatch.countDown();
         }else{
            handleVCRedits.get(i).start();
         }
      }

      countDownLatch.await();
   }
}
