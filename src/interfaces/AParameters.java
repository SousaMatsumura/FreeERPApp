package interfaces;

public @interface AParameters {
   String command();
   String path() default "";
}
