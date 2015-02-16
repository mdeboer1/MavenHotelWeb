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
public class HotelDbService {
    
    private HotelDAOStrategy dao;
    
    public HotelDbService(String driverClass, String url, String username, 
            String password, String hotelDao, String dbAccessor)
            throws ClassNotFoundException, InstantiationException, 
            IllegalAccessException {
        try{
            Class clazz = Class.forName(hotelDao);
            dao = (HotelDAOStrategy)clazz.newInstance();
            clazz = Class.forName(dbAccessor);
            DatabaseAccessorStrategy database = (DatabaseAccessorStrategy)clazz.newInstance();
            dao.setDatabaseProperties(database, driverClass, url, username, password);
        } catch(ClassNotFoundException | InstantiationException 
                | IllegalAccessException ex){
            
        }
        
    }
    
    public final List<Hotel> retrieveHotels(String tableName)throws 
            SQLException, IOException, ClassNotFoundException, NullPointerException {
        
        List<Hotel> records = null;
        
        try {
            records = dao.getHotelRecords(tableName);
        } catch (ClassNotFoundException | SQLException | IOException | 
                NullPointerException ex){
            
        }
        return records;
    }
    
    public final Hotel retrieveHotelById(int hotelId, String tableName)
        throws IOException, SQLException, ClassNotFoundException,
            NullPointerException {
        Hotel hotel = null;
        
        try {
            hotel = dao.getOneHotelRecordById(hotelId, tableName);
        }catch(IOException | SQLException | NullPointerException |
                ClassNotFoundException e){
            
        }
        return hotel;
    }
    
    public final void deleteHotelById(int hotelId)throws IOException, 
            SQLException, ClassNotFoundException{
        try {
            dao.deleteHotelById(hotelId);
        } catch (IOException | SQLException | ClassNotFoundException ex) {
           
        }
    }
    
    public final void addHotel(Hotel hotel)throws IOException, SQLException,
            ClassNotFoundException{
        try {
            dao.addHotel(hotel);
        } catch (IOException | SQLException | ClassNotFoundException ex) {
            
        }
    }
    public final void addHotels(List<Hotel> list) throws IOException, 
            SQLException, ClassNotFoundException{
        try {
            dao.addHotels(list);
        } catch (IOException | SQLException | ClassNotFoundException e){
            
        }
    }
    
    public final void updateOneHotelRecordColumnById(String tableName,  
            String newHotelName, String newHotelAddress, String newHotelCity,
            String newHotelState, String newHotelZip, int hotelId) throws 
            IOException, SQLException, ClassNotFoundException{
        try{
            dao.updateOneHotelRecordColumnById(tableName, newHotelName, 
                    newHotelAddress, newHotelCity, newHotelState, newHotelZip,
                    hotelId);
        } catch (IOException | SQLException | ClassNotFoundException e)  {
            
        }  
    }
    
    public final int retrieveHotelRecordCount()throws 
            IOException, SQLException, ClassNotFoundException{
        int recordCount = 0;
        
        try {
            recordCount = dao.getHotelRecordCount();
        } catch (IOException | SQLException | ClassNotFoundException ex) {
            
        }
        
        return recordCount;
    }
    
//    public static void main(String[] args) {
//        HotelDbService service = new HotelDbService();
//        List<Hotel> list = null;
//        try {
//            list = service.retrieveHotels("hotels");
//        } catch (SQLException | IOException | ClassNotFoundException | NullPointerException ex) {
//            Logger.getLogger(HotelDbService.class.getName()).log(Level.SEVERE, null, ex);
//        }
////        System.out.println(list.size());
//        for (Hotel h : list){
//            System.out.println(h.toString());
//        }
//    }
}
