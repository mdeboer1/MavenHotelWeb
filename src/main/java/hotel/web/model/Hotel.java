package hotel.web;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mdeboer1
 */
public class Hotel {
    private int hotelId;
    private String hotelName, address, city, state, zip;
    
    public Hotel(){
        
    }

    public Hotel(int hotelId, String hotelName, String address, String city, String state, String zip) {
        setHotelId(hotelId);
        setName(hotelName);
        setAddress(address);
        setCity(city);
        setState(state);
        setZip(zip);
    }

    public final int getHotelId() {
        return hotelId;
    }

    private void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }
    
    public final String getHotelName(){
        return hotelName;
    }
    
    public final void setName(String hotelName){
        this.hotelName = hotelName;
    }

    public final String getAddress() {
        return address;
    }

    private void setAddress(String address) {
        this.address = address;
    }

    public final String getCity() {
        return city;
    }

    private void setCity(String city) {
        this.city = city;
    }

    public final String getState() {
        return state;
    }

    private void setState(String state) {
        this.state = state;
    }

    public final String getZip() {
        return zip;
    }

    private void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public final int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.hotelId;
        return hash;
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Hotel other = (Hotel) obj;
        if (this.hotelId != other.hotelId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Hotel{" + "hotelId=" + hotelId + ", name=" + hotelName + ", address="
                + address + ", city=" + city + ", state=" + state + ", zip=" 
                + zip + '}';
    }

    
    
    
}
