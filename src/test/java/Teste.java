
import java.io.IOException;
import java.sql.*;


class Teste {
    static final String httpd = "httpd.exe";
    static final String post = "postgres.exe";

   public static void main(String[] args) throws IOException, InterruptedException {

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
         //System.out.println(rs.toString());
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
      }catch (SQLException e){
         e.printStackTrace();
      }

   }

}