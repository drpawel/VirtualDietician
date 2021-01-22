package com.company.model;

import com.company.controller.ModelListener;

import java.sql.*;

public class AppModel {
    private ModelListener modelListener = null;
    private static final String DB_URL="jdbc:derby:Patients;create=true";
    private static final String DB_USER="";
    private static final String DB_PASSWORD="";

    public AppModel() {
        createDataBase();
//        insertPatientToDataBase("Robert Kubica","11111111111",172);
//        insertPatientToDataBase("Robert Kubica","21111111111",172);
//        insertMeasurementToDataBase(80,23,"11111111111");
//        deletePatientFromDataBase("11111111111");
//        show();
        shutdownDataBase();
    }

    public void addListener(ModelListener modelListener){
        this.modelListener = modelListener;
    }

    public void createDataBase(){
        try(Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
            try(Statement stmt = conn.createStatement()){
                try{
                	stmt.execute("DROP TABLE Measurements");
                	stmt.execute("DROP TABLE Patients");
                }catch(Exception e) {}
                if(!tableExists(conn,"Patients")){
                    stmt.execute("CREATE TABLE Patients (PatientId  INTEGER NOT NULL PRIMARY KEY" +
                            "               GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1)," +
                            "Name           VARCHAR(128) NOT NULL," +
                            "Pesel          VARCHAR(11) NOT NULL UNIQUE," +
                            "Height         REAL NOT NULL)");
                }
                if(!tableExists(conn,"Measurements")) {
                    stmt.execute("CREATE TABLE Measurements(MeasurementId	INTEGER NOT NULL PRIMARY KEY" +
                            "				GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1)," +
                            "Weight		    REAL NOT NULL," +
                            "BMI		    REAL NOT NULL," +
                            "Date		    TIMESTAMP NOT NULL," +
                            "PatientId		INTEGER NOT NULL REFERENCES Patients(PatientId))");
                }
            }catch(SQLException e){
                System.out.println("Statement execution fail!");
                e.printStackTrace();
            }
        }catch(SQLException e){
            System.out.println("Connection failed!\n");
            e.printStackTrace();
        }
    }

    public void insertPatientToDataBase(String name, String pesel, float height){
        try(Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
            try(PreparedStatement stmt = conn.prepareStatement("INSERT INTO Patients (Name,Pesel,Height) VALUES (?,?,?)")) {
                stmt.setString(1,name);
                stmt.setString(2,pesel);
                stmt.setFloat(3,height);
                stmt.executeUpdate();
            }catch(SQLException e){
                com.company.DialogLibrary.showNoValidDataDialog();
                System.out.println("Statement execution fail!\n");
                e.printStackTrace();
            }
        }catch(SQLException e){
            System.out.println("Connection failed!\n");
            e.printStackTrace();
        }
        //this.modelListener.modelChanged(this);
    }

    public void insertMeasurementToDataBase(float weight, float BMI, String pesel){
        try(Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
            int patientId = -1;
            patientId = getPatientId(pesel, conn);

            if(patientId!=-1){
                try(PreparedStatement stmt = conn.prepareStatement("INSERT INTO Measurements (Weight,BMI,Date,PatientId) VALUES (?,?,CURRENT_TIMESTAMP,?)")){
                    stmt.setFloat(1,weight);
                    stmt.setFloat(2,BMI);
                    stmt.setInt(3,patientId);
                    stmt.executeUpdate();
                }catch(SQLException e){
                    System.out.println("Statement execution fail!\n");
                    e.printStackTrace();
                }
            }
        }catch(SQLException e){
            System.out.println("Connection failed!\n");
            e.printStackTrace();
        }
        //this.modelListener.modelChanged(this);
    }

    public void deletePatientFromDataBase(String pesel){
        try(Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
            int patientId = -1;
            patientId = getPatientId(pesel, conn);

            if(patientId!=-1){
                try(PreparedStatement stmt = conn.prepareStatement("DELETE FROM Measurements WHERE PatientId=?")){
                    stmt.setInt(1,patientId);
                    stmt.executeUpdate();
                }catch(SQLException e){
                    System.out.println("Statement execution fail!\n");
                    e.printStackTrace();
                }
                try(PreparedStatement stmt = conn.prepareStatement("DELETE FROM Patients WHERE PatientId=?")){
                    stmt.setInt(1,patientId);
                    stmt.executeUpdate();
                }catch(SQLException e){
                    System.out.println("Statement execution fail!\n");
                    e.printStackTrace();
                }
            }
        }catch(SQLException e){
            System.out.println("Connection failed!\n");
            e.printStackTrace();
        }
        //this.modelListener.modelChanged(this);
    }

    private int getPatientId(String pesel, Connection conn) {
        int patientId = -1;
        try(PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Patients WHERE Pesel=?")) {
            stmt.setString(1, pesel);
            try(ResultSet rs=stmt.executeQuery()){
                if(rs.next()) {
                    patientId = rs.getInt("PatientId");
                }
            }
        }catch(SQLException e) {
            System.out.println("Statement execution fail!\n");
            com.company.DialogLibrary.showNoPatientDialog();
            e.printStackTrace();
        }
        return patientId;
    }

    public void shutdownDataBase(){
        try {
            DriverManager.getConnection("jdbc:derby:Patients;shutdown=true");
        }
        catch (SQLException se) {
            // SQL State XJO15 and SQLCode 50000 mean an OK shutdown.
            if (!(se.getErrorCode() == 50000) && (se.getSQLState().equals("XJ015"))){
                System.err.println(se);
            }
        }
    }

    public void show(){
        try (Connection conn=DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs=stmt.executeQuery("SELECT * FROM Patients ORDER BY Name")) {
                    while (rs.next())
                        System.out.println(rs.getString("PatientId") + "\t" + rs.getString("Name") + "\t" + rs.getString("Pesel") + "\t" + rs.getString("Height"));
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
