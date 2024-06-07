package org.example.cmd;


import com.google.gson.JsonSyntaxException;
import org.example.TCP_components.Response;
import org.example.managers.CollectionManager;
import org.example.interfaces.Console;
import org.example.managers.TCPSerializationManager;
import org.example.model.Person;
import org.example.cmd.utils.Command;

public class Add extends Command {
    private Console console;
    private CollectionManager collectionManager;
    private TCPSerializationManager serializator;


    public Add(Console console, CollectionManager collectionManager, TCPSerializationManager serializator) {
        super("add", "добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
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
        person.setId(collectionManager.getFreeId());
        collectionManager.append(person);
        collectionManager.setLastInitTime();
        return new Response(true,"Новый Person успешно добавлен","");
    }
}
