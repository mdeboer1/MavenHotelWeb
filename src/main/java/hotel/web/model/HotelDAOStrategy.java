/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author mdeboer1
 */
public interface HotelDAOStrategy {

    public abstract List<Hotel> getHotelRecords(String tableName) throws 
            SQLException, IOException, ClassNotFoundException;
    
    public abstract Hotel getOneHotelRecordById(int hotelId, String tableName)
        throws IOException, SQLException, ClassNotFoundException;
    
    public abstract void deleteHotelById(int hotelId) throws IOException, 
            SQLException, ClassNotFoundException;
    
    public abstract void addHotel(Hotel hotel) throws IOException, SQLException, 
            ClassNotFoundException;
    
    public abstract void addHotels(List<Hotel> list) throws IOException, SQLException,
            ClassNotFoundException;
    
    public abstract void updateOneHotelRecordColumnById(String tableName,  
            String newHotelName, String newHotelAddress, String newHotelCity,
            String newHotelState, String newHotelZip, int hotelId) throws 
            IOException, SQLException, ClassNotFoundException;
    
    public abstract int getHotelRecordCount()throws IOException, SQLException,
            ClassNotFoundException;
    
    public abstract void setDatabaseProperties(DatabaseAccessorStrategy database,
        String driverClass, String url, String username, String password);
}
