package main.java;

import main.java.interfaces.AParameters;
import main.java.interfaces.concrete.CConsumers;
import main.java.interfaces.concrete.CParameters;
import main.java.interfaces.concrete.CPredicates;
import main.java.interfaces.concrete.CSuppliers;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Test {
   public static void main(String[] args) throws Exception{

      Main.handleUserPathChoice();

   }
}
