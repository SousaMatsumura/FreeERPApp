package main.java;

import main.java.constants.*;
import main.java.interfaces.concrete.CConsumers;
import main.java.interfaces.concrete.CPredicates;
import main.java.interfaces.concrete.StoppableThread;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Main {
   public static void main(String[] args) throws InterruptedException {

      /*Lidando com o caminho de instalação escolhido pelo usuário*/
      handleUserPathChoice();


      /*Abrindo cada uma das janelas de instalação dos vcredist, uma por vez*/
      handleVCRedists();


      /*Instalando Servers*/
      do CConsumers.COMMAND.accept(Resource.APACHE_PSQL_BAT.getParam());
      while ((!CPredicates.IS_STARTED.test(ServerName.HTTPD.toString()))
              && (!CPredicates.IS_STARTED.test(ServerName.POSTGRES.toString())));

      TimeUnit.SECONDS.sleep(15);
      handleDataBase();

      do CConsumers.COMMAND.accept(Resource.PHP_BAT.getParam());
      while (!CPredicates.IS_STARTED.test(ServerName.PHP.toString()));

      /*Opening laravel project on default browser*/
      CConsumers.COMMAND.accept(Resource.START_BROWSER_BAT.getParam());

   }

   private static void handleUserPathChoice() {
      try {
         Resource.setUserPathChoice();
         /*final File HTTPD_CONF = new File(Resource.USER_PATH_CHOICE + Resource.HTTPD_CONF_PATH.getValue()
                                                + Resource.HTTPD_CONF_FILE.getValue());*/
         File temp = new File(Resource.USER_PATH_CHOICE + Resource.HTTPD_CONF_PATH.getValue()
                                                + Resource.HTTPD_CONF_FILE.getValue());
         if (CPredicates.FILE_CONTAINS_INSTALL_SETTINGS.test(temp)) {
            CConsumers.CONFIG_FILE.accept(Resource.USER_PATH_CHOICE+Resource.HTTPD_CONF_PATH.getValue() + Resource.HTTPD_CONF_FILE.getValue());
         }

         temp = new File(Resource.USER_PATH_CHOICE+ StringUtils.replace(Resource.APACHE_PSQL_BAT.getValue(), ".vbs", ".bat"));
         if (CPredicates.FILE_CONTAINS_INSTALL_SETTINGS.test(temp)) {
            CConsumers.CONFIG_FILE.accept(Resource.USER_PATH_CHOICE+ StringUtils.replace(Resource.APACHE_PSQL_BAT.getValue(), ".vbs", ".bat"));
         }

         temp = new File(Resource.USER_PATH_CHOICE+Resource.APACHE_PSQL_BAT.getValue());
         if (CPredicates.FILE_CONTAINS_INSTALL_SETTINGS.test(temp)) {
            CConsumers.CONFIG_FILE.accept(Resource.USER_PATH_CHOICE+Resource.APACHE_PSQL_BAT.getValue());
         }

         temp = new File(Resource.USER_PATH_CHOICE+ StringUtils.replace(Resource.PHP_BAT.getValue(), ".vbs", ".bat"));
         if (CPredicates.FILE_CONTAINS_INSTALL_SETTINGS.test(temp)) {
            CConsumers.CONFIG_FILE.accept(Resource.USER_PATH_CHOICE+ StringUtils.replace(Resource.PHP_BAT.getValue(), ".vbs", ".bat"));
         }

         temp = new File(Resource.USER_PATH_CHOICE+Resource.PHP_BAT.getValue());
         if (CPredicates.FILE_CONTAINS_INSTALL_SETTINGS.test(temp)) {
            CConsumers.CONFIG_FILE.accept(Resource.USER_PATH_CHOICE+Resource.PHP_BAT.getValue());
         }

         temp = new File(Resource.USER_PATH_CHOICE+Resource.START_BROWSER_BAT.getValue());
         if (CPredicates.FILE_CONTAINS_INSTALL_SETTINGS.test(temp)) {
            CConsumers.CONFIG_FILE.accept(Resource.USER_PATH_CHOICE+Resource.START_BROWSER_BAT.getValue());
         }

         temp = new File(Resource.USER_PATH_CHOICE+"\\PHP7\\php.ini");
         if (CPredicates.FILE_CONTAINS_INSTALL_SETTINGS.test(temp)) {
            CConsumers.CONFIG_FILE.accept(Resource.USER_PATH_CHOICE+"\\PHP7\\php.ini");
         }

         temp = new File(Resource.USER_PATH_CHOICE+"\\PHP7\\pear.ini");
         if (CPredicates.FILE_CONTAINS_INSTALL_SETTINGS.test(temp)) {
            CConsumers.CONFIG_FILE.accept(Resource.USER_PATH_CHOICE+"\\PHP7\\pear.ini");
         }

         temp = new File(Resource.USER_PATH_CHOICE+"\\PHP7\\pear.bat");
         if (CPredicates.FILE_CONTAINS_INSTALL_SETTINGS.test(temp)) {
            CConsumers.CONFIG_FILE.accept(Resource.USER_PATH_CHOICE+"\\PHP7\\pear.bat");
         }
      } catch (Exception err) {
         err.printStackTrace();
      }
   }

   private static void handleDataBase(){
      try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432", "postgres", "postgre")) {
         Statement std = connection.createStatement();
         std.execute("CREATE EXTENSION IF NOT EXISTS dblink;");
         std.execute("DO\n" +
                           "$do$\n" +
                           "BEGIN\n" +
                           "   IF NOT EXISTS (\n" +
                           "      SELECT\n" +
                           "      FROM   pg_catalog.pg_roles\n" +
                           "      WHERE  rolname = 'root') THEN\n" +
                           "      CREATE ROLE root WITH LOGIN;\n" +
                           "   END IF;\n" +
                           "END\n" +
                           "$do$;");
         std.execute("DO\n" +
                           "$do$\n" +
                           "BEGIN\n" +
                           "   IF EXISTS (SELECT 1 FROM pg_database WHERE datname = 'freeerp') THEN\n" +
                           "      RAISE NOTICE 'Database already exists'; \n" +
                           "   ELSE\n" +
                           "      PERFORM dblink_exec('host=127.0.0.1 dbname=postgres user=postgres port=5432 password=postgre'," +
                           "'CREATE DATABASE freeerp OWNER = root');\n" +
                           "   END IF;" +
                           "END\n" +
                           "$do$ LANGUAGE plpgsql;");
         std.execute("ALTER ROLE root WITH SUPERUSER CREATEDB CREATEROLE;");
      }catch (SQLException e){
         e.printStackTrace();
      }
   }

   private static void handleVCRedists()  throws InterruptedException{
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

