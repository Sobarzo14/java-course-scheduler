/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author abela
 */
public class CourseQueries {
    private static Connection connection;
    private static PreparedStatement getAllCoursesSQL;
    private static PreparedStatement addCourseSQL;
    private static PreparedStatement getAllCourseCodesSQL;
    private static PreparedStatement getCourseSeatsSQL;  
    private static PreparedStatement dropCourseSQL;


    private static ResultSet resultSet;
    
    public static ArrayList<CourseEntry> getAllCourses(String semester)
    {
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> courses = new ArrayList<CourseEntry>();
        try
        {
            //Selects all courses in the database
            getAllCoursesSQL = connection.prepareStatement("select * from app.course where semester = (?) order by coursecode");
            getAllCoursesSQL.setString(1, semester);
            resultSet = getAllCoursesSQL.executeQuery();
            
            //Adds all courses to the ArrayList
            while(resultSet.next())
            {
                courses.add(new CourseEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courses;
        
    }
    
    public static void addCourse(CourseEntry course)
    {
        connection = DBConnection.getConnection();
        try
        {
            //Adds course into the database
            addCourseSQL = connection.prepareStatement("insert into app.course (semester, coursecode, description, seats) values (?, ?, ?, ?)");
            addCourseSQL.setString(1, course.getSemester());
            addCourseSQL.setString(2, course.getCourseCode());
            addCourseSQL.setString(3, course.getCourseDescription());
            addCourseSQL.setInt(4, course.getSeats());            
            addCourseSQL.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<String> getAllCourseCodes(String semester)
    {
        connection = DBConnection.getConnection();
        ArrayList<String> courseCodes = new ArrayList<String>();
        try
        {
            //Gets all coursecodes in a semester
            getAllCourseCodesSQL = connection.prepareStatement("select coursecode from app.course where semester = (?) order by coursecode");
            getAllCourseCodesSQL.setString(1, semester);
            resultSet = getAllCourseCodesSQL.executeQuery();
            
            while(resultSet.next())
            {
                courseCodes.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courseCodes;
        
    }
    
    public static int getCourseSeats(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        int courseSeats = 0;
        try
        {
            //Gets all course seats by semester and course
            getCourseSeatsSQL = connection.prepareStatement("select seats from app.course where semester = ? and coursecode = ?");
            getCourseSeatsSQL.setString(1, semester);
            getCourseSeatsSQL.setString(2, courseCode);            
            resultSet = getCourseSeatsSQL.executeQuery();
            if (resultSet.next()){
                courseSeats = resultSet.getInt(1);                
            }
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courseSeats;
        
    }
    
    public static void dropCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        try
        {
            dropCourseSQL = connection.prepareStatement("delete from app.course where semester = (?) and coursecode = (?)");
            dropCourseSQL.setString(1, semester);
            dropCourseSQL.setString(2, courseCode);
                  
            dropCourseSQL.executeUpdate();
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
}
