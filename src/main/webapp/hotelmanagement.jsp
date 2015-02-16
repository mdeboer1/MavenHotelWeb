<%-- 
    Document   : Hotel Management System
    Created on : Feb 10, 2015, 1:30:08 PM
    Author     : mdeboer1
--%>

<%@page import="hotel.web.Hotel"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" 
           uri="http://java.sun.com/jsp/jstl/xml" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
        <link rel="stylesheet" href="css/management.css">
        <title>Hotel Management System></title>
    </head>
    <body>
        <header id="header" name="header"><br>
                <h1>Welcome to the hotel management system</h1>
        </header>
        <div id="container"><br>
            <div class="row">
                <div id="fullHotelList">
                    <form id="hotels" name="hotels" method="POST" action='<%= 
                    request.getContextPath()%>/control'>
                        <div class="col-md-4">
                            <fieldset>
                                <legend>Hotel List</legend>
                                    <ul id="hList" class="list-group" height="600px">
                                        <c:forEach var="hotel" items="${hotelNameList}" >
                                            <li class="list-group-item">
                                                <a href="control?id=${hotel.hotelId}">${hotel.hotelName}</a></li>
                                        </c:forEach>
                                    </ul>
                            </fieldset>
                        </div>
                    </form>
                </div>    
                <div id="editDiv">        
                    <form id="userInteractionPanel" name="userInteractionPanel" 
                          method="POST" action='<%= request.getContextPath()%>/control'>
                        <div id="editField" class="col-md-8"><br>
                            <fieldset>
                                <legend>Edit hotel information below</legend>
                                <input type="hidden" id="hotelId" name="hotelId" value="<c:out value="${hotelToEdit.hotelId}"/>">
                                    <input id="editName" name="editName" type="text" class="form-control" placeholder="Edit hotel name" value="${hotelToEdit.hotelName}"><br>
                                    <input id="editAddress" name="editAddress" type="text" class="form-control" placeholder="Edit hotel address" value="${hotelToEdit.address}"><br>
                                    <input id="editCity"  name="editCity" type="text" class="form-control" placeholder="Edit hotel city" value="${hotelToEdit.city}"><br>
                                    <input id="editState" name="editState" type="text" class="form-control" placeholder="Edit hotel state" value="${hotelToEdit.state}"><br>
                                    <input id="editZip" name="editZip"  type="text" class="form-control" placeholder="Edit hotel zip code" value="${hotelToEdit.zip}"><br>
                                    <button id="editHotel" name="editHotel" class="btn btn-default" type="submit">Edit hotel</button>
                                    <button id="deleteHotel" name="deleteHotel" class="btn btn-default" type="submit">Delete Hotel</button>
                            </fieldset>
                        </div>
                    </form>
                </div>
                <div id="addHotel"class="col-md-8">
                    <form id="userInteractionPanel" name="userInteractionPanel" 
                          method="POST" action='<%= request.getContextPath()%>/control'>
                        <fieldset>
                            <legend>Enter the information to add hotels</legend>
                                <input id="addName" name="addName" type="text" class="form-control" placeholder="Enter hotel name"><br>
                                <input id="addAddress" name="addAddress" type="text" class="form-control" placeholder="Enter hotel address"><br>
                                <input id="addtCity"  name="addCity" type="text" class="form-control" placeholder="Enter hotel city"><br>
                                <input id="addState" name="addState" type="text" class="form-control" placeholder="Enter hotel state"><br>
                                <input id="addZip" name="addZip"  type="text" class="form-control" placeholder="Enter hotel zip code"><br>
                                <button id="addToList" name="addToList" class="btn btn-default" type="submit">Add hotel</button>
                                <!--button id="submitToDb" name="submitToDb" class="btn btn-default" type="submit">Submit entries</button-->
                        </fieldset>
                    </form>
                </div>    
            </div>
        </div>
         <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>            
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    </body>
</html>
