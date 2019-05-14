package interfaces.concrete;

import interfaces.AParameters;

import java.lang.annotation.Annotation;

public class CParameters {
   public static AParameters getInstanceOfParameter(final String command, final String path){
      return new AParameters(){

         @Override
         public Class<? extends Annotation> annotationType() {
            return AParameters.class;
         }

         @Override
         public String command() {
            return command;
         }

         @Override
         public String path() {
            if(path!=null) return path;
            return "";
         }

         @Override
         public boolean equals(Object obj) {
            if(obj == null) return false;
            if(obj == this) return true;
            if(obj.getClass() != this.getClass()) return false;
            AParameters par = (AParameters) obj;
            return command() != null && command().equals(par.command());
         }

         @Override
         public int hashCode() {
            return command()!=null ? command().hashCode() : 1;
         }
      };
   }
}
