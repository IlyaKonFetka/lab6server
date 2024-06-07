package org.example.model;

import org.example.interfaces.Validatable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Person extends Element implements Validatable, Serializable, Comparable<Person> {
    private Integer id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным,
                    // Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null,
                                                  // Значение этого поля должно генерироваться автоматически
    private Long height; //Поле не может быть null, Значение поля должно быть больше 0
    private Long weight; //Поле не может быть null, Значение поля должно быть больше 0
    private String passportID; //Длина строки должна быть не меньше 4, Поле не может быть null
    private Color hairColor; //Поле может быть null
    private Location location; //Поле может быть null


    public static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Person(Integer id,
                  String name,
                  Coordinates coordinates,
                  Location location,
                  Long height,
                  Long weight,
                  String passportID,
                  Color hairColor) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDateTime.now();
        this.location = location;
        this.height = height;
        this.weight = weight;
        this.passportID = passportID;
        this.hairColor = hairColor;
    }

    public String getPassportID() {
        return passportID;
    }

    public Long getWeight() {
        return weight;
    }


    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String locationString = location==null?"null": location.toString();
        String hairColorString = hairColor==null?"null": hairColor.toString();
        return "Name: " + name + "\n" +
                    "\tid: " + id + ", passportID: " + passportID + ", creationDate: " + creationDate.format(timeFormatter) +
                ", height: " + height + ", weight: " + weight + "\n" +
                    "\thairColor: " + hairColorString + "\n" +
                    "\tCoordinates: " + coordinates.toString() + "\n" +
                    "\tLocation: " + locationString;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public int compareTo(Person o) {
        return Integer.compare(this.id, o.id);
    }

    public boolean validateWithoutID(){
        return validateName() &
               validateCoordinates() &
               validateCreationDate() &
               validateHeight() &
               validateWeight() &
               validatePassportID() &
               validateLocation();
    }

    @Override
    public boolean validate() {
        return validateID() &
                validateWithoutID();
    }
    public boolean validateID() {return id != null && id > 0;}
    public boolean validateName(){return name != null && !name.isEmpty();}
    public boolean validateCoordinates(){return coordinates != null && coordinates.validate();}
    public boolean validateCreationDate(){return creationDate != null;}
    public boolean validateHeight(){return height != null && height > 0;}
    public boolean validateWeight(){return weight != null && weight > 0;}
    public boolean validatePassportID(){return passportID != null && passportID.length() >= 4;}
    public boolean validateLocation(){return location == null || location.validate();}

}