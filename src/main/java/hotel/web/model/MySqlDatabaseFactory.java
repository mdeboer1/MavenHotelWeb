/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author mdeboer1
 */
public final class MySqlDatabaseFactory {
    public static String filePath 
            = MySqlDatabaseFactory.class.getResource("config.properties").toString();
    public static String connectionClass = "db.connector";
    
    private MySqlDatabaseFactory(){
    }
    
    
    public static Connection getConnection() throws IOException, SQLException,
            ClassNotFoundException {
        Connection connection = null;
        File file = new File(filePath);

        Properties properties = new Properties();
        FileInputStream input;
        try{
            input = new FileInputStream(file);
            properties.load(input);
            input.close();
            
            String driverClass = properties.getProperty("driver.class");
            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");
            Class.forName (driverClass);
            connection = DriverManager.getConnection(url,username, password);
            
        }catch(IOException | ClassNotFoundException | SQLException ex){
            //add custom exception for file not provided
        }
        return connection;
    }
    
    public static DatabaseAccessorStrategy getAccessor()throws ClassNotFoundException, 
            InstantiationException, IllegalAccessException{
        
        DatabaseAccessorStrategy dBStrategy = null;
        File file = new File(filePath);
        Properties prop = new Properties();
        FileInputStream input;
        try{
            input = new FileInputStream(file);
            prop.load(input);
            input.close();
            
            String strategy = prop.getProperty("db.accessor");
            Class c = Class.forName(strategy);
            dBStrategy = (DatabaseAccessorStrategy)c.newInstance();
        } catch(IOException | ClassNotFoundException | InstantiationException 
                | IllegalAccessException ex){
            
        }
        return dBStrategy;
    }
    
    public static HotelDAOStrategy getDAO()throws ClassNotFoundException, 
            InstantiationException, IllegalAccessException, IOException{
        
        HotelDAOStrategy dAOStrategy = null;
        File file = new File(filePath);
        Properties prop = new Properties();
        FileInputStream input;
        try{
            input = new FileInputStream(file);
            prop.load(input);
            input.close();
            
            String strategy = prop.getProperty("db.dao");
            Class c = Class.forName(strategy);
            dAOStrategy = (HotelDAOStrategy)c.newInstance();
        }catch(IOException | ClassNotFoundException | InstantiationException 
                | IllegalAccessException ex){
            
        }
        return dAOStrategy;
    }
//    
    public static void main(String[] args) {
        Connection conn = null;
        try {
            conn = MySqlDatabaseFactory.getConnection();
        } catch (IOException | SQLException | ClassNotFoundException ex) {
            System.out.println("oops");
        }
        if (conn == null){
            System.out.println("Null");
        }
        else {
            System.out.println("Connected");
        }
//        DatabaseAccessorStrategy db = null;
//        try {
//            db = MySqlDatabaseFactory.getAccessor();
//            if (db == null){
//                System.out.println("oops");
//            }
//        } catch(ClassNotFoundException |
//            InstantiationException | IllegalAccessException e){
//            
//        }
        
//        HotelDAOStrategy dao = null;
//        try {
//            dao = MySqlDatabaseFactory.getDAO();
//        }catch (IOException | ClassNotFoundException | InstantiationException 
//                | IllegalAccessException ex) {
//            
//        }
//        if (dao == null){
//            System.out.println("null");
//        }
////        System.out.println(dao.toString());
        }
}
