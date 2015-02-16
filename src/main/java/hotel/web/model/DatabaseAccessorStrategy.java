/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.web;

import java.io.IOException;
import java.sql.BatchUpdateException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mdeboer1
 */
public interface DatabaseAccessorStrategy {

    public abstract void closeConnection() throws SQLException;

    public abstract List<Map<String, Object>> getAllHotelRecords(String tableName) 
            throws SQLException, IOException, ClassNotFoundException;

    public abstract void openConnection() throws IOException, 
            SQLException, ClassNotFoundException;
    
    public abstract void updateOneHotelRecordColumnById(String tableName,  
            String newHotelName, String newHotelAddress, String newHotelCity,
            String newHotelState, String newHotelZip, int hotelId) throws 
            IOException, SQLException, ClassNotFoundException;
    
    public abstract List<Map<String, Object>> getOneHotelRecordById(int hotelId, String
            tableName) throws IOException, SQLException, ClassNotFoundException;
    
    public abstract void deleteHotelById(int hotelId) throws IOException, 
            SQLException, ClassNotFoundException;
    
    public abstract void insertNewHotel(Hotel hotel) throws IOException, 
            SQLException, ClassNotFoundException, BatchUpdateException;
    
    public abstract void insertNewHotels(List<Map<String, Object>> hotelList) 
            throws IOException, SQLException, ClassNotFoundException,
            BatchUpdateException;
    
    public abstract int getHotelRecordCount() throws IOException, 
            SQLException, ClassNotFoundException;
    
    public abstract void setConnectionVariables(String driverClass, String url, String username, 
            String password) throws NullPointerException;
}
