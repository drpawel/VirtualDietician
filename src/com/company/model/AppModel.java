package com.company.model;

import com.company.controller.ModelListener;

import java.sql.*;

public class AppModel {
    private ModelListener modelListener;
    private static final String DB_URL="jdbc:derby:Patients;create=true";
    private static final String DB_USER="";
    private static final String DB_PASSWORD="";

    public AppModel() {
        createDataBase();
        insertPatientToDataBase(new Patient("Robert Kubica",172));
        insertPatientToDataBase(new Patient("Robert Kubica",172));
        insertPatientToDataBase(new Patient("Robert Kubica",172));
        insertPatientToDataBase(new Patient("Robert Kubica",172));
        show();
    }

    public void addListener(ModelListener modelListener){
        this.modelListener = modelListener;
    }

    public void createDataBase(){
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
            try(Statement stmt = conn.createStatement()){
                try {
                	stmt.execute("DROP TABLE Measurements");
                	stmt.execute("DROP TABLE Patients");
                } catch (Exception e) {}
                if(!tableExists(conn,"Patients")){
                    stmt.execute("CREATE TABLE Patients (PatientId  INTEGER NOT NULL PRIMARY KEY" +
                            "               GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1)," +
                            "Name           VARCHAR(128) NOT NULL," +
                            "Height         REAL NOT NULL)");
                }
                if (!tableExists(conn,"Measurements")) {
                    stmt.execute("CREATE TABLE Measurements(MeasurementId	INTEGER NOT NULL PRIMARY KEY" +
                            "				GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1)," +
                            "Weight		    REAL NOT NULL," +
                            "BMI		    REAL NOT NULL," +
                            "Date		    TIMESTAMP NOT NULL," +
                            "PatientId		INTEGER NOT NULL REFERENCES Patients(PatientId))");
                }
            }catch (SQLException e){
                System.out.println("Statement execution fail!");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Connection failed!\n");
            e.printStackTrace();
        }
    }

    public void insertPatientToDataBase(Patient patient){
        try(Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
            try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO Patients (Name,Height) VALUES (?,?)")) {
                stmt.setString(1,patient.getName());
                stmt.setFloat(2,patient.getHeight());
                stmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Statement execution fail!\n");
                e.printStackTrace();
            }
        }catch (SQLException e){
            System.out.println("Connection failed!\n");
            e.printStackTrace();
        }
    }

    public void show(){
        try (Connection conn=DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs=stmt.executeQuery("SELECT * FROM Patients ORDER BY Name")) {
                    while (rs.next())
                        System.out.println(rs.getString("PatientId") + "\t" + rs.getString("Name") + "\t" + rs.getString("Height"));
                }
            } catch (SQLException e) {
                System.out.println("Blad przy wykonywaniu polecania\n");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Blad przy tworzeniu polaczenia\n");
            e.printStackTrace();
        }
    }

    public static boolean tableExists(Connection conn, String tableName) throws SQLException {
        boolean exists=false;
        DatabaseMetaData dbmd=conn.getMetaData();
        try (ResultSet rs=dbmd.getTables(null,null,tableName.toUpperCase(),null)) {
            exists=rs.next();
        }
        return(exists);
    }
}
