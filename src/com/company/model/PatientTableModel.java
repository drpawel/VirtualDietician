package com.company.model;

import com.company.model.Patient;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * PatientTableModel class
 */
public class PatientTableModel extends AbstractTableModel {
    private String[] columns = {"NAME","HEIGHT","PESEL"};
    private ArrayList<Patient> patients;

    /**
     * PatientTableModel constuctor
     */
    public PatientTableModel() {
    }

    /**
     * PatientList setter
     * @param patients Patients' list
     */
    public void setPatients(ArrayList<Patient> patients) {
        this.patients = patients;
    }

    /**
     * RowCount getter
     * @return rowCount
     */
    @Override
    public int getRowCount() {
        if(patients!=null){
            return patients.size();
        }else{
            return 0;
        }
    }

    /**
     * ColumnCount getter
     * @return ColumnCount
     */
    @Override
    public int getColumnCount() {
        return columns.length;
    }

    /**
     * ColumnName getter
     * @param columnIndex Column's index
     * @return columnName
     */
    @Override
    public String getColumnName(int columnIndex){
        return columns[columnIndex];
    }

    /**
     * Value at row and column getter
     * @param rowIndex
     * @param columnIndex
     * @return value
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Patient patient = patients.get(rowIndex);
        switch (columnIndex){
            case 0:
                return patient.getName();
            case 1:
                return patient.getHeight();
            case 2:
                return patient.getPesel();
        }
        return null;
    }
}
