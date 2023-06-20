/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;

/**
 *
 * @author abela
 */
public class ScheduleQueries {
    private static Connection connection;
    private static PreparedStatement addScheduleSQL;
    private static PreparedStatement getScheduleSQL;
    private static PreparedStatement getStudentCountSQL;
    private static PreparedStatement seatsTakenSQL;
    private static PreparedStatement waitlistedSQL;
    private static PreparedStatement dropStudentSQL;
    private static PreparedStatement dropScheduleSQL;
    private static PreparedStatement updateSQL;

    private static ResultSet resultSet;

    public static void addScheduleEntry(ScheduleEntry entry) {
        connection = DBConnection.getConnection();
        try {
            //Add the schedule into the database
            addScheduleSQL = connection.prepareStatement("insert into app.schedule (semester, studentid, status, coursecode, timestamp) values (?, ?, ?, ?, ?)");
            addScheduleSQL.setString(1, entry.getSemester());
            addScheduleSQL.setString(2, entry.getStudentID());
            addScheduleSQL.setString(3, entry.getStatus());
            addScheduleSQL.setString(4, entry.getCourseCode());
            addScheduleSQL.setTimestamp(5, entry.getTimestamp());
            addScheduleSQL.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> scheduleArray = new ArrayList<ScheduleEntry>();
        try {
            getScheduleSQL = connection.prepareStatement("select * from app.schedule where semester = ? and studentid = ?");
            getScheduleSQL.setString(1, semester);
            getScheduleSQL.setString(2, studentID);
            resultSet = getScheduleSQL.executeQuery();

            while (resultSet.next()) {
                ScheduleEntry newEntry = new ScheduleEntry(semester, resultSet.getString(2), studentID, resultSet.getString(4), resultSet.getTimestamp(5));
                scheduleArray.add(newEntry);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return scheduleArray;

    }

    public static int getScheduledStudentCount(String semester, String course) {
        connection = DBConnection.getConnection();
        int count = 0;
        try {
            getStudentCountSQL = connection.prepareStatement("select studentid from app.schedule where semester = (?) and coursecode = (?)");
            getStudentCountSQL.setString(1, semester);
            getStudentCountSQL.setString(2, course);

            resultSet = getStudentCountSQL.executeQuery();
            //Checks how many students there are 
            while (resultSet.next()) {
                count++;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return count;
    }

    public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String semester, String course) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> scheduleList = new ArrayList<ScheduleEntry>();
        try {
            seatsTakenSQL = connection.prepareStatement("select studentid, timestamp from app.schedule where semester = ? and courseCode = ? and status = ?");
            seatsTakenSQL.setString(1, semester);
            seatsTakenSQL.setString(2, course);
            seatsTakenSQL.setString(3, "S");
            resultSet = seatsTakenSQL.executeQuery();

            while (resultSet.next()) {
                scheduleList.add(new ScheduleEntry(semester, course, resultSet.getString(1), "S", resultSet.getTimestamp(2)));
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return scheduleList;
    }

    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByCourse(String semester, String course) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> scheduleList = new ArrayList<ScheduleEntry>();
        try {
            waitlistedSQL = connection.prepareStatement("select studentid, timestamp from app.schedule where semester = ? and courseCode = ? and status = ?");
            waitlistedSQL.setString(1, semester);
            waitlistedSQL.setString(2, course);
            waitlistedSQL.setString(3, "W");
            resultSet = waitlistedSQL.executeQuery();

            while (resultSet.next()) {
                scheduleList.add(new ScheduleEntry(semester, course, resultSet.getString(1), "W", resultSet.getTimestamp(2)));
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return scheduleList;
    }
    
    public static void dropStudentScheduleByCourse(String semester, String student, String course){
        connection = DBConnection.getConnection();
        try{
            dropStudentSQL = connection.prepareStatement("delete from app.schedule where semester = ? and studentid = ? and coursecode = ?");
            dropStudentSQL.setString(1, semester);
            dropStudentSQL.setString(2, student);
            dropStudentSQL.setString(3, course);
            dropStudentSQL.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public static void dropScheduleByCourse(String semester, String course){
        connection = DBConnection.getConnection();
        try{
            dropScheduleSQL = connection.prepareStatement("delete from app.schedule where semester = ? and coursecode = ?");
            dropScheduleSQL.setString(1, semester);
            dropScheduleSQL.setString(2, course);
            dropScheduleSQL.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public static void updateScheduleEntry(String semester, ScheduleEntry entry){
        connection = DBConnection.getConnection();
        try{
            updateSQL = connection.prepareStatement("update app.schedule set status = 'S' where semester = ? and studentid = ? and coursecode = ?");
            updateSQL.setString(1, semester);
            updateSQL.setString(2, entry.getStudentID());
            updateSQL.setString(3, entry.getCourseCode());
            updateSQL.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
}
