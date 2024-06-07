package org.example.cmd;

import org.example.TCP_components.Response;

import org.example.cmd.utils.Command;
import org.example.interfaces.Console;
import org.example.managers.CollectionManager;
import org.example.managers.TCPSerializationManager;
import org.example.model.Person;

public class Update extends Command {
    private Console console;
    private CollectionManager collectionManager;
    private TCPSerializationManager serializator;
    public Update(CollectionManager collectionManager, Console console, TCPSerializationManager serializator) {
        super("update", "обновить элемент коллекции по id");
        this.collectionManager = collectionManager;
        this.serializator = serializator;
        this.console = console;
    }

    @Override
    public Response apply(String userCommandArgument) {
        Person person = serializator.person(userCommandArgument);
        int id = person.getID();
        if (collectionManager.remove(id)){
            collectionManager.append(person);
            return new Response(true, "Элемент успешно обновлён","");
        } else {
            return new Response(false, "Элемента с таким id нет в коллекции","");
        }
    }
}
