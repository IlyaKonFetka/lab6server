package org.example.managers;

import org.example.exceptions.ValidateException;
import org.example.model.Person;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class CollectionManager {
    private int currentId = 1;
    private TreeSet<Person> collection = new TreeSet<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private final CollectionSerializationManager collectionSerializationManager;

    public CollectionManager(CollectionSerializationManager collectionSerializationManager) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.collectionSerializationManager = collectionSerializationManager;
    }

    Comparator<Person>passportIDComparator = Comparator.comparing(Person::getName);

    public int getFreeId() {
        return collection.stream()
                .mapToInt(Person::getID)
                .max()
                .orElse(0) + 1;
    }
    public LocalDateTime getLastInitTime() throws NullPointerException{
        return lastInitTime;
    }

    public LocalDateTime getLastSaveTime() throws NullPointerException{
        return lastSaveTime;
    }

    public void setLastInitTime() {
        this.lastInitTime = LocalDateTime.now();
    }

    public void setLastSaveTime() {
        this.lastSaveTime = LocalDateTime.now();
    }

    public TreeSet<Person> getCollection() {
        return collection;
    }

    public Person byId(int id) {
        return collection.stream()
                .filter(person -> person.getID() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean isСontain(Person e) {
        return e != null && byId(e.getID()) != null;
    }

    public boolean isContain(Person e) {
        return e != null && collection.stream().anyMatch(person -> person.getID() == e.getID());
    }

    public boolean append(Person a) {
        boolean exists = collection.stream().anyMatch(person -> person.getID() == a.getID());
        if (exists) {
            return false;
        }
        collection.add(a);
        return true;
    }


    public boolean remove(Person a) {
        return collection.remove(a);
    }

    public boolean remove(int id) {
        return collection.removeIf(person -> person.getID() == id);
    }


    public boolean cleanCollection(){
        collection.clear();
        return true;
    }

    public String getFileName(){
        return this.collectionSerializationManager.getFileName();
    }

    public boolean saveCollection() {
        if(collectionSerializationManager.writeCollection(collection)){
            lastSaveTime = LocalDateTime.now();
            return true;
        }else {
            return false;
        }
    }

    public String getMaxName() {
        return collection.stream()
                .map(Person::getName)
                .max(String::compareTo)
                .orElse("");
    }

    @Override
    public String toString() {
        if (collection.isEmpty()) return "Коллекция пуста!";

        return collection.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n\n"))
                .trim();
    }

    public boolean loadCollection() throws ValidateException {
        collection = new TreeSet<>(collectionSerializationManager.readCollection());

        boolean allValid = collection.stream().allMatch(Person::validate);
        if (!allValid) {
            throw new ValidateException();
        }

        lastInitTime = LocalDateTime.now();
        return !collection.isEmpty();
    }

}
