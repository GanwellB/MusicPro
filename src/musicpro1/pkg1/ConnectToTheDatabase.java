/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicpro1.pkg1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Ganwell
 */
public class ConnectToTheDatabase 
{
    static String host = "jdbc:mysql://localhost:3306/musicpro";
    static String username = "root";
    static String password = "12345";
    static int countSong = 1, direc = 0;;
    static String songName;
    static Connection connection;
    static ResultSet resultSet;
    static Statement statement;
    public ConnectToTheDatabase()
    {
        try 
        {
            getSongNameInDatabase();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToTheDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void connectToDatabase()
    {
        try 
        {
            doConnect();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(ConnectToTheDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void updateSongCounter(String song) throws SQLException
    {
        doConnect();
        //statement = (com.mysql.jdbc.Statement)connection.createStatement();
        String updateSong = "update favasongs set counter = '"+incrementSongCounter(song)+"' where song_name = '"+song+"'";
        PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(updateSong);
        int rows = preparedStatement.executeUpdate();
    }
    public static void addSongToDataBase(String songToStore) throws SQLException
    {
        doConnect();
        String addTrack = "insert into favasongs(song_name,counter) values('"+songToStore+"','"+howManyTimePlayed()+"')";
        PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(addTrack);
        int rows = preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    public static int incrementSongCounter(String songSelected) throws SQLException
    {
        doConnect();
        Statement statement = null;
        statement = connection.createStatement();
        String getCounter = "select counter from favasongs where song_name = '"+songSelected+"'";
        ResultSet resultSet = null;
        resultSet = statement.executeQuery(getCounter);
        try 
        {
            int  me = resultSet.getInt("counter");
            countSong = me;
        } 
        catch (Exception e) 
        {
        }
        return countSong+1;
    }
    public static int howManyTimePlayed()
    {
        return countSong;
    }
    public static void doConnect() throws SQLException
    {
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            connection = (java.sql.Connection)DriverManager.getConnection(host, username, password);
            //statement = (com.mysql.jdbc.Statement)connection.createStatement();
            System.out.println("Successfull!!");
        }
        catch (ClassNotFoundException ex)
        {
            Logger.getLogger(ConnectToTheDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void getSongNameInDatabase() throws SQLException
    {
        doConnect();
        statement = (com.mysql.jdbc.Statement)connection.createStatement();
        String getSong = "select song_name from favasongs";
        resultSet = statement.executeQuery(getSong);
        while (resultSet.next()) 
        {            
            songName = resultSet.getString("song_name");
            String songLoc = MusicProComponents.listModel2.getElementAt(direc).toString();
            String songNameOnList = MusicProComponents.listModel.getElementAt(direc).toString();
            if(songName.equalsIgnoreCase(songNameOnList) || songName == null || songName.isEmpty())
            {
                updateSongCounter(songNameOnList);
                System.out.println("Updated");
            }
            else
            {
                addSongToDataBase(songNameOnList);
                System.out.println("Song Added");
            }
            direc++;
            System.out.println("Songs "+songName);
        }
        if(resultSet.last() == false)
        {
            for(int i = 0; i < MusicProComponents.listModel2.getSize(); i++)
            {
                String songNameOnList = MusicProComponents.listModel.getElementAt(i).toString();
                addSongToDataBase(songNameOnList);
                System.out.println("Added song: "+songNameOnList);
            }
        }
        else
        {
        }
    }
    public static void readDataFromDatabase() throws SQLException
    {
    }
}
