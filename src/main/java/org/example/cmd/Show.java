package org.example.cmd;

import org.example.TCP_components.Response;
import org.example.managers.CollectionManager;
import org.example.interfaces.Console;
import org.example.model.Person;
import org.example.cmd.utils.Command;

/**
 * Класс команды "show".
 * Выводит все элементы коллекции в порядке их id
 */
public class Show extends Command {
    CollectionManager collectionManager;
    Console console;
    public Show(CollectionManager collectionManager, Console console) {
        super("show", "вывести элементы коллекции");
        this.collectionManager = collectionManager;
        this.console = console;
    }


    @Override
    public Response apply(String __) {
        if(collectionManager.getCollection().isEmpty()) {
            return new Response(true,"Коллекция пуста","");
        }
        StringBuilder stringBuilder = new StringBuilder();
        for(Person p: collectionManager.getCollection()) {
            stringBuilder.append(p.toString()).append("\n");
        }
        return new Response(true, "Коллекция успешно выведена", stringBuilder.toString());
    }
}
