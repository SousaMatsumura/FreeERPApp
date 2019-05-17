/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


/*Recontruindo essa classe em Main.java*/

public class FreeERP {
   static final int[] PORT_DEPENDENCIES = new int[]{
         /*Apache HTTPD Server*/ 8080,
         /*Apache HTTPD Server*/ 3160,
         ///*Artisan Serve*/ 8000,
         /*PostgreSQL Server*/ 5432
   };

   static final String[] NAME_DEPENDENCIES = new String[]{
         /*PHP*/ "php.exe",
         /*Apache HTTPD Server*/ "httpd.exe",
         /*PostgreSQL Server*/ "pg_ctl.exe",
         /*PostgreSQL Server*/ "postgres.exe"
   };

   static final char ASPAS = '"', ESPACO = ' ';
   static final String VIRGULA = ",";

   public static void main(String[] args) {
      // "http://localhost:8080"
      //openPage("http://localhost:8000");
      //kill(9912);

      /*for(int i = 0; i< PORT_DEPENDENCIES.length; i++) while(kill(PORT_DEPENDENCIES[i]));

      for(String s : NAME_DEPENDENCIES) while(kill(s));*/

      /*while (kill("php.exe"));
      while (kill("httpd.exe"));
      while (kill("pg_ctl.exe"));
      while (kill("postgres.exe"));*/

      new Thread(new Runnable() {
           @Override
         public void run() {
            command("C:\\FreeERP\\EnterpriseDB-ApacheHTTPD\\installer\\ApacheHTTPD\\vcredist_x86.exe");
         }
      }).start();
      new Thread(new Runnable() {
           @Override
         public void run() {
            command("C:\\FreeERP\\11\\installer\\vcredist_x86.exe");
         }
      }).start();
      new Thread(new Runnable() {
           @Override
         public void run() {
            command("C:\\FreeERP\\11\\installer\\vcredist_x64.exe");
         }
      }).start();
      
      installApache();
      new Thread(new Runnable() {
         @Override
         public void run() {
            startApache();
         }
      }).start();
      new Thread(new Runnable() {
         @Override
         public void run() {
            startPostgre();
         }
      }).start();
      new Thread(new Runnable() {
         @Override
         public void run() {
            startArtisan();
         }
      }).start();
      openPage("http://localhost:8000");


      /*installApache();
      startApache();
      startPostgre();
      startArtisan();
      openPage("http://localhost:8000");*/

   }

   public static void openPage(String url){
      try {
         /*start "" "c:\program files (x86)\google\chrome\application\chrome.exe" --new-window "http://localhost:8080"*/
         String comando = "c:\\program files (x86)\\google\\chrome\\application\\chrome.exe"
                                +" --new-window "+ASPAS+url+ASPAS;
         Runtime.getRuntime().exec(comando);


         //Runtime.getRuntime().exec("iexplore.exe "+url);
         //Runtime.getRuntime().exec("MicrosoftEdge.exe");
         //Runtime.getRuntime().exec("microsoft-edge://");
      }catch(IOException ioex) {
         ioex.printStackTrace();

         /*try {
            *//*start "" "c:\program files (x86)\google\chrome\application\chrome.exe" --new-window "http://localhost:8080"*//*
            String comando = "c:\\program files (x86)\\google\\chrome\\application\\chrome.exe"
                                   +" --new-window "+ASPAS+url+ASPAS;
            Runtime.getRuntime().exec(comando);
         }catch(IOException ioeg) {
            ioeg.printStackTrace();
         }*/
      }
   }


   public static void installApache() {
      try {
         String line;
         Process p = Runtime.getRuntime().exec("C:\\FreeERP\\EnterpriseDB-ApacheHTTPD\\apache\\bin\\httpd.exe -k install");
         BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
         while ((line = input.readLine()) != null) {
            if (!line.trim().equals("")) {
               System.out.println(line);
            }
         }
         input.close();
      } catch (Exception err) {
         err.printStackTrace();
      }
   }

   public static synchronized void command(String s) {
      try {
         String line;
         Process p = Runtime.getRuntime().exec(s);

      } catch (Exception err) {
         err.printStackTrace();
      }
   }
   
   public static void startApache() {
      try {
         String line;
         Process p = Runtime.getRuntime().exec("C:\\FreeERP\\EnterpriseDB-ApacheHTTPD\\apache\\bin\\httpd.exe -k start");
         BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
         while ((line = input.readLine()) != null) {
            if (!line.trim().equals("")) {
               System.out.println(line);
            }
         }
         input.close();
      } catch (Exception err) {
         err.printStackTrace();
      }
   }

 public static void startPostgre() {
      try {
         String line;
         Process p = Runtime.getRuntime().exec("C:\\FreeERP\\11\\bin\\pg_ctl -D \"C:\\FreeERP\\11\\data\" start");
         BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
         while ((line = input.readLine()) != null) {
            if (!line.trim().equals("")) {
               System.out.println(line);
            }
         }
         input.close();
      } catch (Exception err) {
         err.printStackTrace();
      }
   }

 public static void startArtisan() {
      try {
         String line;
         Process p=Runtime.getRuntime().exec("C:\\FreeERP\\PHP7\\php.exe artisan serve",
               null, new File("C:\\FreeERP\\laravel\\app-demo"));
         /*Process p = Runtime.getRuntime().exec("cd C:\\Users\\GabrieldeSousaMatsum\\Desktop\\FreeERP\\laravel\\app-demo\n" +
            "C:\\Users\\GabrieldeSousaMatsum\\Desktop\\FreeERP\\PHP7\\php.exe artisan serve");*/
         BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
         while ((line = input.readLine()) != null) {
            if (!line.trim().equals("")) {
               System.out.println(line);
            }
         }
         input.close();
      } catch (Exception err) {
         err.printStackTrace();
      }
   }

  /* public static boolean kill(String processo) {
      try {
         String line;
         Process p = Runtime.getRuntime().exec("tasklist.exe /fo csv /nh");
         BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
         while ((line = input.readLine()) != null) {
            if (!line.trim().equals("")) {
               if (line.substring(1, line.indexOf("\"", 1)).equalsIgnoreCase(processo)) {
                  Runtime.getRuntime().exec("taskkill /F /IM " + line.substring(1, line.indexOf("\"", 1)));
                  return true;
               }
            }
         }
         input.close();
      } catch (Exception err) {
         err.printStackTrace();
      }
      return false;
   }*/


/*   public static boolean kill(int port) {
      try {
         String line;
         Process p = Runtime.getRuntime().exec("tasklist.exe /fo csv /nh");
         BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
         while ((line = input.readLine()) != null) {
            if (!line.trim().equals("")) {
               int po = Integer.parseInt(line.split(VIRGULA)[1].replaceAll("\"", ""));
               //System.out.println(po);
               if (po == port) {
                  //System.out.println("HERE: "+(line.substring(1, line.indexOf("\"", 1))));
                  Runtime.getRuntime().exec("taskkill /F /IM " + line.substring(1, line.indexOf("\"", 1)));
                  return true;
               }
            }
         }
         input.close();
      } catch (Exception err) {
         err.printStackTrace();
      }
      return false;
   }*/
}