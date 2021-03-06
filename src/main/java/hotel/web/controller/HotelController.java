/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.web.controller;

import hotel.web.model.Hotel;
import hotel.web.model.HotelDbService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mdeboer1
 */
@WebServlet(name = "controller", urlPatterns = {"/control"})
public class HotelController extends HttpServlet {
    private static final String RESULT_PAGE = "/hotelmanagement.jsp"; 
    private static String hotelTableName = "hotels";
    private static boolean isNewServlet = true;
    
    int hotelCount;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        //Retrieve strategy and database connection information from web.xml 
        ServletContext ctx = request.getServletContext();
        String driverClass = (String)ctx.getAttribute("driver.class");
        String url = (String)ctx.getAttribute("db.url");
        String username = (String)ctx.getAttribute("db.username");
        String password = (String)ctx.getAttribute("db.password");
        String hotelDao = (String)ctx.getAttribute("hotel.dao.strategy");
        String dbAccessor = (String)ctx.getAttribute("db.accessor.strategy");
        int id;
        //Create the database service class
        HotelDbService service = null;
        try {
            service = new HotelDbService(driverClass, url, username,
                    password, hotelDao, dbAccessor);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            
        }
        
        //This is used only on start up of web page to get the count of hotels in the db
        if (hotelCount == 0){
            try {
                if (service != null){
                    hotelCount = service.retrieveHotelRecordCount() + 1;
                }
            } catch (SQLException | ClassNotFoundException | NullPointerException ex) {
                Logger.getLogger(HotelController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        //Crud operations
        String edit = request.getParameter("editHotel");
        String delete = request.getParameter("deleteHotel");
        List<Hotel> listOfNewHotels = new ArrayList<>();
        String addHotel = request.getParameter("addToList");
        String submitList = request.getParameter("submitToDb");
        String newHotelName = request.getParameter("editName");
        String newHotelAddress = request.getParameter("editAddress");
        String newHotelCity = request.getParameter("editCity");
        String newHotelState = request.getParameter("editState");
        String newHotelZip = request.getParameter("editZip");
        String hotelId = request.getParameter("hotelId");
        HttpSession session = request.getSession();
        String filter = request.getParameter("filter");
        List <Hotel> hotelList = null;
        String hId = request.getParameter("byId");
        String hotelName = request.getParameter("byName");
        String hotelAddress = request.getParameter("byAddress");
        String hotelCity = request.getParameter("byCity");
        String hotelState = request.getParameter("byState");
        String hotelZip = request.getParameter("byZip");
        String allHotels = request.getParameter("allHotels");
        HttpSession session3 = request.getSession();
        
        if (filter != null){
            try{
                String columnName = null;
                String columnFilter = null;
                if (allHotels != null){
                    if (service != null){
                        hotelList = service.retrieveHotels(hotelTableName);
                    }
                }
                if (!hId.isEmpty()){
                    columnName = "hotel_id";
                    columnFilter = hId;
                }
                else if (!hotelName.isEmpty()){
                    columnName = "hotel_name";
                    columnFilter = hotelName;
                }
                else if (!hotelAddress.isEmpty()){
                    columnName = "hotel_address";
                    columnFilter = hotelAddress;
                }
                else if (!hotelCity.isEmpty()){
                    columnName = "hotel_city";
                    columnFilter = hotelCity;
                }
                else if (!hotelState.isEmpty()){
                    columnName = "hotel_state";
                    columnFilter = hotelState;
                }
                else if (!hotelZip.isEmpty()){
                    columnName = "hotel_zip";
                    columnFilter = hotelZip;
                }
                if (service != null && allHotels == null){
                        hotelList = service.retrieveHotelsByColumnName(columnName, columnFilter);
                    }
            }catch (SQLException | ClassNotFoundException ex) {
                        //Logger.getLogger(HotelController.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.setAttribute("hotelNameList", hotelList);
            session3.setAttribute("hotelNameList", hotelList);
        }    

        
        if (edit != null){
            try {
                // change parameters
                int updateId = Integer.parseInt(hotelId);
                if (service !=null){
                    service.updateOneHotelRecordColumnById(hotelTableName, newHotelName
                            , newHotelAddress, newHotelCity, newHotelState, newHotelZip,
                            updateId);
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(HotelController.class.getName()).log(Level.SEVERE, null, ex);
            }
            hotelList = new ArrayList<>();
        }
        else if (delete != null){
            try {
                id = Integer.parseInt(hotelId);
                //change hotelId by retrieving it from form
                if (service != null){
                    service.deleteHotelById(id);
                }
            } catch (NumberFormatException | SQLException | ClassNotFoundException ex) {
                
            }
            hotelList = new ArrayList<>();
        }
        else if (addHotel != null){
            
            Hotel h = new Hotel(hotelCount, request.getParameter("addName"),
                request.getParameter("addAddress"), request.getParameter("addCity"),
                request.getParameter("addState"), request.getParameter("addZip"));
            hotelCount++;
            
            if(session.getAttribute("list") == null){
                listOfNewHotels.add(h);
                session.setAttribute("list", listOfNewHotels);
            }
            else{
                listOfNewHotels = (List)session.getAttribute("list");
//            if (service != null){
//                try {
//                    service.addHotel(h);
//                } catch (SQLException | ClassNotFoundException ex) {
//                    
//                }
            listOfNewHotels.add(h);
            session.setAttribute("list", listOfNewHotels);
            }
        }
        
        else if (submitList != null){
            
            List<Hotel> newHotels = (List)session.getAttribute("list");
            try {
                if (service != null){
                    service.addHotels(newHotels);
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(HotelController.class.getName()).log(Level.SEVERE, null, ex);
            }
            session.invalidate();
        }
        //This section is used to get a List of all hotels and display them in the index.jsp
        if (filter == null){
            try {
                if (service != null){    
                    hotelList = service.retrieveHotels(hotelTableName);
                }
            } catch (SQLException | ClassNotFoundException | NullPointerException ex) {

            }
            request.setAttribute("hotelNameList", hotelList);
        }
        
        //This section retrieves the query string from the hotel name hyperlinks
        String[] query = request.getParameterValues("id");
        Hotel hotel = null;
        
        if (query != null){
            try {
                id = Integer.parseInt(query[0]);
                for(Hotel h : hotelList){
                    if (id == h.getHotelId()){
                        hotel = h;
                    }
                }
                request.setAttribute("hotelToEdit", hotel);
            } catch (NumberFormatException e){

            }
            }
        RequestDispatcher view =
            request.getRequestDispatcher(response.encodeURL(RESULT_PAGE));
        view.forward(request, response);
        
    }

    public final String getHotelTableName() {
        return hotelTableName;
    }

    public final void setHotelTableName(String hotelTableName) throws
            NullPointerException {
        
        if (!hotelTableName.isEmpty()){
            try{
                this.hotelTableName = hotelTableName;
            } catch (NullPointerException e){    
            }
            
        }
    }    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
