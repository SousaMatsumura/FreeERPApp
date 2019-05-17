package main.java.interfaces;

public @interface AParameters {
   String command();
   String path() default "";
}
