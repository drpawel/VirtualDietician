package com.company;
import java.sql.*;

public class VirtualDietician {
    /*
     * System bazodanowy Apache Derby (tryb embedded, jezeli baza danych nie istnieje, to zostanie utworzona)
     */
    public static final String JDBC_DRIVER="org.apache.derby.jdbc.EmbeddedDriver";
    public static final String DB_URL="jdbc:derby:Patients;create=true";
    public static final String DB_USER="";
    public static final String DB_PASSWORD="";

    public static void main(String[] args)
    {
        /*
         * Tworzenie polaczenia z baza danych
         */
        try (Connection conn=DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD))
        {
            System.out.println("Polaczenie z baza danych: " + conn.getMetaData().getURL());
            System.out.println("Klasa obiektu polaczenia: " + conn.getClass());

        }
        catch (SQLException e)
        {
            System.out.println("Blad przy tworzeniu polaczenia\n");
            e.printStackTrace();
        }
    }
}
