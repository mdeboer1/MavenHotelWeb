package hotel.web.model;


import java.io.IOException;
import java.sql.BatchUpdateException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mdeboer1
 */
public class MySqlDatabaseAccessor implements DatabaseAccessorStrategy {
    private Connection connection;
    private Statement statement;
    private ResultSet result;
    //Eliminate these 4 properties
    private String driverClass;
    private String url;
    private String username;
    private String password;
    //Create a private DataSource property
    
    //Modify constructor to accept a DataSource object rather than Strings
    public MySqlDatabaseAccessor(String driverClass, String url, String username, 
            String password){
        this.driverClass = driverClass;
        this.url = url;
        this.username = username;
        this.password = password;
    }
    
    //This method is no longer needed/used
    @Override
    public final void setConnectionVariables(String driverClass, String url, String username, 
            String password) throws NullPointerException{
        this.driverClass = driverClass;
        this.url = url;
        this.username = username;
        this.password = password;
    }
    
    //Modify this method to us the DataSource object
    @Override
    public final void openConnection() throws IOException, SQLException, ClassNotFoundException{
        Class.forName(driverClass);
        connection = DriverManager.getConnection(url, username, password);
        
    }
    
    @Override
    public final void closeConnection() throws SQLException{
        try {
            statement.close();
        } catch (SQLException se){
            // throws to caller
        }
    }
    
    @Override
    public final List<Map<String, Object>> getHotelRecordsByColumnName(String 
            columnName, String recordToMatch) throws IOException, SQLException, ClassNotFoundException{
        openConnection();
        List<Map<String, Object>> hotelRecords = new ArrayList<>();
        PreparedStatement stmt;
        String sqlStatement = "select * from hotels where "
                + columnName + " = ?";

        connection.setAutoCommit(false);
        stmt = connection.prepareStatement(sqlStatement);
        stmt.setString(1, recordToMatch);
        result = stmt.executeQuery();

        ResultSetMetaData metaData = result.getMetaData();	
        final int fields = metaData.getColumnCount();
        
        while (result.next()){
            Map<String,Object> record = new LinkedHashMap<>();
                for( int i=1; i <= fields; i++ ) {
                    record.put( metaData.getColumnName(i), result.getObject(i) );
                } // end for
                hotelRecords.add(record);
        }
        connection.commit();
        return hotelRecords;
        }
    
    @Override
    public final List<Map<String, Object>> getAllHotelRecords(String tableName)
        throws SQLException, IOException, ClassNotFoundException {
        
        openConnection();
        List<Map<String, Object>> hotelRecords =
                new ArrayList<>();
        try {
            statement = connection.createStatement();
        } catch (SQLException ex) {

        }
        
        String sqlStatement = "SELECT * FROM " + tableName;
        result = statement.executeQuery(sqlStatement);
        
        ResultSetMetaData metaData = result.getMetaData();	
        final int fields = metaData.getColumnCount();
        
        while(result.next()) {
                Map<String,Object> record = new LinkedHashMap<>();
                for( int i=1; i <= fields; i++ ) {
                    record.put( metaData.getColumnName(i), result.getObject(i) );
                } // end for
                hotelRecords.add(record);
        }
        closeConnection();
        
        return hotelRecords;
    }
    
    @Override
    public final List<Map<String, Object>> getOneHotelRecordById(int hotelId, String
            tableName) throws IOException, SQLException, ClassNotFoundException{
        
        openConnection();
        List<Map<String, Object>> hotelRecord = new ArrayList<>();
        
        try{
            statement = connection.createStatement();
        } catch (SQLException ex){
            
        }
        String sqlStatement = "SELECT * FROM " + tableName + " WHERE hotel_id = "
                + hotelId;
        result = statement.executeQuery(sqlStatement);
        ResultSetMetaData metaData  = result.getMetaData();
        final int fields = metaData.getColumnCount();
        
        while (result.next()){
            Map<String,Object> record = new LinkedHashMap<>();
            for (int i =1; i <= fields; i++){
                record.put(metaData.getColumnName(i), result.getObject(i));
            }
            hotelRecord.add(record);
        }
        return hotelRecord;
        }
    
    @Override
    public final int getHotelRecordCount() throws IOException, SQLException, ClassNotFoundException{
        
        openConnection();
        int hotelCount = 0;
        
        try{
            statement = connection.createStatement();
        } catch (SQLException ex){
            
        }
        
        String sqlStatement = "select hotel_id from hotels order by hotel_id desc"
                + "limit 1";
        result = statement.executeQuery(sqlStatement);
        while(result.next()){
            hotelCount = result.getInt(1);
        }
        
        closeConnection();
        return hotelCount;
    }
    
    @Override
    public final void deleteHotelById(int hotelId) throws IOException, SQLException, ClassNotFoundException{
        openConnection();
        
        try {
            statement = connection.createStatement();
        } catch (SQLException ex){
            
        }
        
        String deleteStatement = "DELETE FROM hotels WHERE hotel_id = " + hotelId;
        statement.executeUpdate(deleteStatement);
        closeConnection();
    }
    
    @Override
    public final void updateOneHotelRecordColumnById(String tableName,  
            String newHotelName, String newHotelAddress, String newHotelCity,
            String newHotelState, String newHotelZip, int hotelId) throws 
            IOException, SQLException, ClassNotFoundException{
        openConnection();

        PreparedStatement updateRecord = null;

        String updateString = "update " + tableName + " set hotel_name = ?, "
                + "hotel_address = ?, hotel_city = ?, hotel_state = ?, hotel_zip"
                + " = ? where hotel_id = ?";
        try {
           
            connection.setAutoCommit(false);
            updateRecord = connection.prepareStatement(updateString);
            updateRecord.setString(1, newHotelName);
            updateRecord.setString(2, newHotelAddress);
            updateRecord.setString(3, newHotelCity);
            updateRecord.setString(4, newHotelState);
            updateRecord.setString(5, newHotelZip);
            updateRecord.setInt(6, hotelId);
            updateRecord.executeUpdate();
            connection.commit();
        } catch (SQLException e){
            if (connection != null){
                System.out.println(connection);
                try{
                    System.out.println("Rolling back");
                    connection.rollback();
                } catch (SQLException s){
                    
                }
            }
        } finally {
            if (updateRecord != null){
                updateRecord.close();
            }
            connection.setAutoCommit(true);
        }
    }
    
    @Override
    public final void insertNewHotel(Hotel hotel) throws IOException, 
            SQLException, ClassNotFoundException {
        openConnection();
        
        PreparedStatement addHotel = null;
        
        String sqlStatement = "INSERT INTO hotels (hotel_name, hotel_address, "
                    + "hotel_city, hotel_state, hotel_zip) values (?, ?, ?, ?, ?)";
        
        try {
            connection.setAutoCommit(false);
            addHotel = connection.prepareStatement(sqlStatement);
            addHotel.setString(1, hotel.getHotelName());
            addHotel.setString(2, hotel.getAddress());
            addHotel.setString(3, hotel.getCity());
            addHotel.setString(4, hotel.getState());
            addHotel.setString(5, hotel.getZip());
            addHotel.executeUpdate();
            connection.commit();
            
        } catch (SQLException e){
            if (connection != null){
                System.out.println(connection);
                try{
                    System.out.println("Rolling back");
                    connection.rollback();
                } catch (SQLException s){
                    
                }
            }
        } finally {
            if (statement != null) {
                statement.close();
                connection.setAutoCommit(true);
            }
        }
    }
    
    @Override
    public final void insertNewHotels(List<Map<String, Object>> hotelList) 
            throws IOException, SQLException, ClassNotFoundException,
            BatchUpdateException{
        openConnection();
        
        PreparedStatement addHotels = null;
        String sqlStatement = "INSERT INTO hotels (hotel_name, hotel_address, "
                + "hotel_city, hotel_state, hotel_zip) values (?, ?, ?, ?, ?)";

        try {
            connection.setAutoCommit(false);
            addHotels = connection.prepareStatement(sqlStatement);
            for (Map m : hotelList){
                
                addHotels.setObject(1, m.get("hotel_name").toString());
                addHotels.setObject(2, m.get("hotel_address").toString());
                addHotels.setObject(3, m.get("hotel_city").toString());
                System.out.println(m.get("hotel_state").toString());
                addHotels.setObject(4, m.get("hotel_state").toString());
                System.out.println(m.get("hotel_zip").toString());
                addHotels.setObject(5, m.get("hotel_zip").toString());
                
                addHotels.addBatch();

        }
            addHotels.executeBatch();
            connection.commit();
        }catch (BatchUpdateException b){
            
            } catch (SQLException e){
            
            } finally {
            
                if (statement != null) {
                    statement.close();
                    connection.setAutoCommit(true);
                }
            }
    }
    
//    public static void main(String[] args) {
//        DatabaseAccessorStrategy str = new MySqlDatabaseAccessor();
//        str.setConnectionVariables( "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/hotel", "root", "admin");
//        try {
//            str.openConnection();
//        } catch (IOException ex) {
//            Logger.getLogger(MySqlDatabaseAccessor.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//            Logger.getLogger(MySqlDatabaseAccessor.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(MySqlDatabaseAccessor.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        
//    }
}