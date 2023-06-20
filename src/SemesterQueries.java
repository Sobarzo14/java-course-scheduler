/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author acv
 */
public class SemesterQueries {
    private static Connection connection;
    private static PreparedStatement addSemesterSQL;
    private static PreparedStatement getSemesterListSQL;
    private static ResultSet resultSet;
    
    public static void addSemester(String name)
    {
        connection = DBConnection.getConnection();
        try
        {
            addSemesterSQL = connection.prepareStatement("insert into app.semester (semester) values (?)");
            addSemesterSQL.setString(1, name);
            addSemesterSQL.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<String> getSemesterList()
    {
        connection = DBConnection.getConnection();
        ArrayList<String> semester = new ArrayList<String>();
        try
        {
            getSemesterListSQL = connection.prepareStatement("select semester from app.semester order by semester");
            resultSet = getSemesterListSQL.executeQuery();
            
            while(resultSet.next())
            {
                semester.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return semester;
        
    }
    
    
}
