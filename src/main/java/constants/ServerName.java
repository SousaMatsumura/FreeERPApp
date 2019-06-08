package main.java.constants;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

public enum ServerName {
    HTTPD(1), POSTGRES(2), PHP(3);
    private int id;
    public static final LinkedHashSet<String> GET_SET = getSet();
    ServerName(int id){
        this.id = id;
    }

    @Override
    public String toString() {
        switch (id){
            case 1: return "httpd.exe";
            case 2: return "postgres.exe";
            case 3: return "php.exe";
            default: throw new RuntimeException("Id "+id+" don't exists");
        }
    }

    private static LinkedHashSet getSet(){
        final List<String> temp = Arrays.asList(
                ServerName.HTTPD.toString(),
                ServerName.POSTGRES.toString(),
                ServerName.PHP.toString());
        return new LinkedHashSet<>(Collections.unmodifiableSet(new LinkedHashSet<>(temp)));
    }

}