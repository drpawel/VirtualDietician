package com.company.model;

import com.company.controller.ModelListener;
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;

import java.sql.*;
import java.util.ArrayList;

/**
 * AppModel Class
 */
public class AppModel {
    private ModelListener modelListener = null;
    private static final String DB_URL="jdbc:derby:Patients;create=true";
    private static final String DB_USER="";
    private static final String DB_PASSWORD="";

    /*
        From Derby documentation:
        "If your application runs on JDK 1.6 or higher, then you do not need to explicitly load the EmbeddedDriver. In that environment, the driver loads automatically."
     */

    /**
     * AppModel constructor
     */
    public AppModel() {
        createDataBase();
    }

    /**
     * Add modelListener function
     * @param modelListener Interface overridden in AppController
     */
    public void addListener(ModelListener modelListener){
        this.modelListener = modelListener;
        this.modelListener.modelChanged(this);
    }

    /**
     * Create database function
     */
    public void createDataBase(){
        try(Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
            try(Statement stmt = conn.createStatement()){
//                try{
//                	stmt.execute("DROP TABLE Measurements");
//                	stmt.execute("DROP TABLE Patients");
//                }catch(Exception e) {}
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

    /**
     * Insert new Patient to database
     * @param name Name of patient
     * @param pesel Pesel of patient
     * @param height Height of patient
     */
    public void insertPatientToDataBase(String name, String pesel, float height){
        try(Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
            try(PreparedStatement stmt = conn.prepareStatement("INSERT INTO Patients (Name,Pesel,Height) VALUES (?,?,?)")) {
                stmt.setString(1,name);
                stmt.setString(2,pesel);
                stmt.setFloat(3,height);
                stmt.executeUpdate();
            }catch(DerbySQLIntegrityConstraintViolationException e){
                com.company.DialogLibrary.showNoValidDataDialog();
            }catch(SQLException e){
                System.out.println("Statement execution fail!\n");
                e.printStackTrace();
            }
        }catch(SQLException e){
            System.out.println("Connection failed!\n");
            e.printStackTrace();
        }
        this.modelListener.modelChanged(this);
    }

    /**
     * Insert new Patient's Measurement to database function
     * @param weight Weight of Patient
     * @param BMI Calculated BMI of Patient
     * @param pesel Pesel of Patient
     */
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

    /**
     * Get all Patients from database function
     * @return patientList List of all Patients from database
     */
    public ArrayList<Patient> getPatientsList(){
        ArrayList<Patient> patientsList = new ArrayList<>();
        try(Connection conn=DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
            try(Statement stmt = conn.createStatement()){
                try(ResultSet rs=stmt.executeQuery("SELECT * FROM Patients ORDER BY Name")){
                    while(rs.next()){
                        patientsList.add(new Patient(rs.getString("Name"),rs.getString("Pesel"),rs.getFloat("Height")));
                    }
                }
            }catch(SQLException e){
                System.out.println("Statement execution fail!\n");
                e.printStackTrace();
            }
        }catch(SQLException e){
            System.out.println("Connection failed!\n");
            e.printStackTrace();
        }
        return patientsList;
    }

    /**
     * Get all Measurements of Patient from database function
     * @return MeasurementsList List of all Patient's Measurements from database
     */
    public ArrayList<Measurement> getMeasurementsList(String pesel){
        ArrayList<Measurement> measurementsList = new ArrayList<>();
        try(Connection conn=DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
            int patientId = -1;
            patientId = getPatientId(pesel, conn);

            if(patientId!=-1){
                try(Statement stmt = conn.createStatement()){
                    String query = "SELECT * FROM Measurements WHERE PatientId="+patientId+" ORDER BY Date ASC";
                    try(ResultSet rs=stmt.executeQuery(query)){
                        while(rs.next()){
                            measurementsList.add(new Measurement(rs.getFloat("Weight"),rs.getFloat("BMI"),rs.getString("Date")));
                        }
                    }
                }catch(SQLException e){
                    System.out.println("Statement execution fail!\n");
                    e.printStackTrace();
                }
            }
        }catch(SQLException e){
            System.out.println("Connection failed!\n");
            e.printStackTrace();
        }
        return measurementsList;
    }

    /**
     * Delete Patient from database function
     * @param pesel Patient's pesel
     */
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
        this.modelListener.modelChanged(this);
    }

    /**
     * Shutdown database function
     */
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

    /**
     * Get PatientId by pesel function
     * @param pesel Patient's pesel
     * @param conn Connection to database
     * @return patientId
     */
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

    /**
     * Get Patient's height by pesel function
     * @param pesel Patient's pesel
     * @return height Patient's height
     */
    public float getPatientHeight(String pesel){
        float height = -1f;
        try(Connection conn=DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
            try(PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Patients WHERE Pesel=?")) {
                stmt.setString(1, pesel);
                try(ResultSet rs=stmt.executeQuery()){
                    if(rs.next()) {
                        height = rs.getFloat("Height");
                    }
                }
            }catch(SQLException e) {
                System.out.println("Statement execution fail!\n");
                com.company.DialogLibrary.showNoPatientDialog();
                e.printStackTrace();
            }
        }catch(SQLException e){
            System.out.println("Connection failed!\n");
            e.printStackTrace();
        }
        return height;
    }

    /**
     * Check if table exist function
     * @param conn Connection to database
     * @param tableName Name of table
     * @return exist Boolean of table existence
     * @throws SQLException
     */
    public static boolean tableExists(Connection conn, String tableName) throws SQLException {
        boolean exists=false;
        DatabaseMetaData dbmd=conn.getMetaData();
        try (ResultSet rs=dbmd.getTables(null,null,tableName.toUpperCase(),null)) {
            exists=rs.next();
        }
        return(exists);
    }
}
