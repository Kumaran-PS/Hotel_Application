package model;

import java.util.Objects;

public class Room implements IRoom{
    public String roomNumber;
    public Double price;
    public RoomType enumeration;
    public RoomType roomType;

    public Room(String roomNumber, Double price, RoomType enumeration) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return null;
    }

    @Override
    public RoomType getRoomType() {
        return null;
    }

    public Double getPrice() {
        return price;
    }

    public RoomType getEnumeration() {
        return enumeration;
    }

    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public String toString() {
        return "roomNumber=" + roomNumber +
                ", price=" + price +
                ", RoomType=" + enumeration;
    }

    // Checking (equals)only roomNumber because other variables
    // can be same but roomNumber cant , its unique.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room room)) return false;
        return roomNumber.equals(room.roomNumber);
    }

    // Checking (hashcode)only roomNumber because other variables
    // can be same but roomNumber cant , its unique.
    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
    }
}
