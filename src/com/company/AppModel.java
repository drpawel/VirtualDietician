package com.company;

import java.sql.*;

public class AppModel {
    private ModelListener modelListener;
    //public static final String JDBC_DRIVER="org.apache.derby.jdbc.EmbeddedDriver";
    private static final String DB_URL="jdbc:derby:Patients;create=true";
    private static final String DB_USER="";
    private static final String DB_PASSWORD="";

    public AppModel() {
        createConnection();
    }

    public void addListener(ModelListener modelListener){
        this.modelListener = modelListener;
    }

    public void createConnection(){

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
