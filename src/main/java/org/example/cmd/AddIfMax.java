package org.example.cmd;

import com.google.gson.JsonSyntaxException;
import org.example.TCP_components.Response;
import org.example.managers.CollectionManager;
import org.example.interfaces.Console;
import org.example.managers.TCPSerializationManager;
import org.example.model.Person;
import org.example.cmd.utils.Command;

public class AddIfMax extends Command {
    private CollectionManager collectionManager;
    private Console console;
    private TCPSerializationManager serializator;

    public AddIfMax(CollectionManager collectionManager , Console console, TCPSerializationManager serializator) {
        super("add_if_max", "добавить элемент если он превышает максимальный элемент в коллекции");
        this.collectionManager = collectionManager;
        this.console = console;
        this.serializator = serializator;
    }

    @Override
    public Response apply(String argument) {
        Person person;
        try {
            person = serializator.person(argument);
        }catch (JsonSyntaxException e){
            console.printWarning("Ошибка чтения. Некорректная сериализация Person");
            return new Response(false, "Невозможно десериализовать Person","");
        }

        if (person == null){
            console.printWarning("Person пуст");
            return new Response(false, "Person пуст","");
        }

        if (!person.validateWithoutID()){
            console.printWarning("Поля принятого Person не валидны");
            return new Response(false, "Поля Person не валидны","");
        }
        if (person.getName().compareTo(collectionManager.getMaxName()) > 0){
            person.setId(collectionManager.getFreeId());
            collectionManager.append(person);
            collectionManager.setLastInitTime();
            return new Response(true,"Новый Person успешно добавлен","");
        }else {
            return new Response(true,"Переданный Person не превосходит наибольший в коллекции. Объект не добавлен","");
        }
    }
}
