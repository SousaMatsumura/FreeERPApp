package main.java.interfaces.concrete;

import main.java.constants.Resource;
import main.java.interfaces.AParameters;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.function.Predicate;

public class CPredicates {
    private CPredicates(){}
    private static final Predicate<String> EXIST = new Predicate<String>() {
        @Override
        public boolean test(String path) {
            try {
                String line;
                Process p = Runtime.getRuntime().exec("cmd /C if exist "+path+" (echo t) else (echo f)");
                BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                line = input.readLine();
                do{
                    if (!line.trim().equals("")) {
                        if (line.equals("t")) return true;
                    }
                }while (((line = input.readLine()) != null) && (line.equals("t") || line.equals("f")));
                input.close();
                return false;
            } catch (Exception err) {
                err.printStackTrace();
                return false;
            }finally {
                Runtime.getRuntime().runFinalization();
            }
        }
    };

    private static final Predicate<String> IS_INSTALLED = new Predicate<String>() {
        @Override
        public synchronized boolean test(String command) {
            try {
                String line;
                Process p = Runtime.getRuntime().exec(command);
                BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                line = input.readLine();
                do{
                    if (!line.trim().equals("")) {
                        if (line.equals("True")) return true;
                    }
                }while (((line = input.readLine()) != null) && (line.equals("True") || line.equals("False")));
                input.close();
                return false;
            } catch (Exception err) {
                err.printStackTrace();
                return false;
            }finally {
                Runtime.getRuntime().runFinalization();
            }
        }
    };

    private static final Predicate<String[]> EXIST_AND = new Predicate<String[]>() {
        @Override
        public boolean test(String[] params) {
            boolean result = true;
            for (String p : params) {
                result &= EXIST.test(p);
            }
            return result;
        }
    };

    private static final Predicate<File> IS_FILE_EMPTY = new Predicate<File>() {
        @Override
        public boolean test(final File file) {
            try{
                final int LIMITE = 4096;
                return (FILE_EXISTS.test(file) && file.length() < LIMITE &&
                    FileUtils.readFileToString(file, StandardCharsets.UTF_8).trim().isEmpty());

            }catch (Exception err){
                err.printStackTrace();
                return false;
            }
        }
    };

    private static final Predicate<File> FILE_EXISTS = new Predicate<File>() {
        @Override
        public boolean test(final File file) {
            try{
                return (file.exists() && file.isFile());
            }catch (Exception err){
                err.printStackTrace();
                return false;
            }
        }
    };

    private static final Predicate<File> FILE_CONTAINS_INSTALL_SETTINGS = new Predicate<File>() {
        @Override
        public boolean test(File file) {
            try{
                return (!IS_FILE_EMPTY.test(file) &&
                   StringUtils.contains(
                      FileUtils.readFileToString(file, StandardCharsets.UTF_8), Resource.INSTALL_SETTINGS.getValue()));
            }catch (Exception err){
                err.printStackTrace();
                return false;
            }
        }
    };

    public static Predicate<String> getExist() {
        return EXIST;
    }

    public static Predicate<String[]> getExistAnd() {
        return EXIST_AND;
    }

    public static Predicate<File> getFileContainsInstallSettings() {
        return FILE_CONTAINS_INSTALL_SETTINGS;
    }

    public static Predicate<String> getIsInstalled() {
        return IS_INSTALLED;
    }
}
