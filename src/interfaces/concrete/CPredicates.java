package interfaces.concrete;

import interfaces.AParameters;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.Predicate;

public class CPredicates {

    public static final Predicate<AParameters> EXIST = new Predicate<AParameters>() {
        @Override
        public boolean test(AParameters param) {
            try {
                String line;
                Process p = Runtime.getRuntime().exec("cmd /C if exist "+param.command()+" (echo t) else (echo f)");
                BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                line = input.readLine();
                do{
                    if (!line.trim().equals("")) {
                        if (line.equals("t")) {
                            return true;
                        } else if(line.equals("f"));
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

    public static final Predicate<AParameters[]> EXIST_AND = new Predicate<AParameters[]>() {
        @Override
        public boolean test(AParameters[] params) {
            boolean result = true;
            for (AParameters p : params) {
                result &= EXIST.test(p);
            }
            return result;
        }
    };
}
