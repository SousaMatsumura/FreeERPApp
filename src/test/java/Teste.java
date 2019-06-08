import static org.junit.gen5.api.Assertions.*;

import main.java.constants.Resource;
import main.java.interfaces.AParameters;
import main.java.interfaces.concrete.CConsumers;
import main.java.interfaces.concrete.CParameters;
import main.java.interfaces.concrete.CPredicates;
import org.apache.commons.lang3.StringUtils;
import org.junit.gen5.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

class Teste {
    static final String httpd = "httpd.exe";
    static final String post = "postgres.exe";

   public static void main(String[] args) throws IOException, InterruptedException {

       Runtime.getRuntime().exec("cmd /c start C:\\Users\\spwnd\\Desktop\\FreeERP\\start-server\\startPHP.vbs");
       Runtime.getRuntime().exec("cmd /c start C:\\Users\\spwnd\\Desktop\\FreeERP\\start-server\\startBrowser.vbs");
       Runtime.getRuntime().exec("cmd /c start C:\\Users\\spwnd\\Desktop\\FreeERP\\start-server\\startApachePSQL.vbs");
      /* do CConsumers.CONFIG_FILE.accept("C:\\Users\\spwnd\\Desktop\\FreeERP\\start-server\\startApachePSQL.bat");
       while (CPredicates.FILE_CONTAINS_INSTALL_SETTINGS.test(new File("C:\\Users\\spwnd\\Desktop\\FreeERP\\start-server\\startApachePSQL.bat")));

       do CConsumers.CONFIG_FILE.accept("C:\\Users\\spwnd\\Desktop\\FreeERP\\start-server\\startPHP.bat");
       while (CPredicates.FILE_CONTAINS_INSTALL_SETTINGS.test(new File("C:\\Users\\spwnd\\Desktop\\FreeERP\\start-server\\startPHP.bat")));*/

      Resource.setUserPathChoice();
       System.out.println(Resource.USER_PATH_CHOICE);
   }

}