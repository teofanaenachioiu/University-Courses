package domain;


//-	Clasa Location(locationId:String, locationName: String, hotel: String, noRooms:int, pricePerNight:double)


import java.util.Objects;

public class Location implements HasID<String>{
    private String locationId;
    private String locationName;
    private String hotel;
    private int noRooms;
    private double pricePerNight;

    public Location(String locationId, String locationName, String hotel, int noRooms, double pricePerNight) {
        this.locationId = locationId;
        this.locationName = locationName;
        this.hotel = hotel;
        this.noRooms = noRooms;
        this.pricePerNight = pricePerNight;
    }

    @Override
    public String getID() {
        return this.locationId;
    }

    @Override
    public void setID(String s) {
        this.locationId=s;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public int getNoRooms() {
        return noRooms;
    }

    public void setNoRooms(int noRooms) {
        this.noRooms = noRooms;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    @Override
    public String toString() {
        return this.locationId+","+this.locationName+","+this.hotel+","+this.noRooms+","+this.pricePerNight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;
        Location location = (Location) o;
        return getNoRooms() == location.getNoRooms() &&
                Double.compare(location.getPricePerNight(), getPricePerNight()) == 0 &&
                Objects.equals(locationId, location.locationId) &&
                Objects.equals(getLocationName(), location.getLocationName()) &&
                Objects.equals(getHotel(), location.getHotel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationId, getLocationName(), getHotel(), getNoRooms(), getPricePerNight());
    }
}
