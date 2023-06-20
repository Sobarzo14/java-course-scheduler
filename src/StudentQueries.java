/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 *
 * @author abela
 */
public class StudentQueries {
    private static Connection connection;
    private static PreparedStatement addStudentSQL;
    private static PreparedStatement getAllStudentsSQL; 
    private static ResultSet resultSet;
    
    public static void addStudent(StudentEntry entry){
        connection = DBConnection.getConnection();
        try{
            addStudentSQL = connection.prepareStatement("insert into app.student (studentid, firstname, lastname) values (?, ?, ?)");
            addStudentSQL.setString(1, entry.getStudentID());
            addStudentSQL.setString(2, entry.getFirstName());
            addStudentSQL.setString(3, entry.getLastName());
            addStudentSQL.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<StudentEntry> getAllStudents(){
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList<StudentEntry>();
        try{
            getAllStudentsSQL = connection.prepareStatement("select * from app.student order by studentid");
            resultSet = getAllStudentsSQL.executeQuery();
            while(resultSet.next()){
                students.add(new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return students;   
    }
    
    public static StudentEntry getStudent(String student) {
        connection = DBConnection.getConnection();
        try{
            getAllStudentsSQL = connection.prepareStatement("select studentid, firstname, lastname from app.student where studentid = ?");
            getAllStudentsSQL.setString(1, student);
            resultSet = getAllStudentsSQL.executeQuery();
            
            resultSet.next();
            return new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }
    }
    public static void dropStudent(String student){
        connection = DBConnection.getConnection();
        try{
            getAllStudentsSQL = connection.prepareStatement("delete from app.student where studentid = ?");
            getAllStudentsSQL.setString(1, student);
            getAllStudentsSQL.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
}
