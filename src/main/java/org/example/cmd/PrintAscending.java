package org.example.cmd;

import org.example.TCP_components.Response;
import org.example.cmd.utils.Command;
import org.example.interfaces.Console;
import org.example.managers.CollectionManager;
import org.example.model.Person;

public class PrintAscending extends Command {
    CollectionManager collectionManager;
    Console console;
    public PrintAscending(CollectionManager collectionManager, Console console) {
        super("print_ascending", "вывести элементы коллекции в порядке возрастания");
        this.collectionManager = collectionManager;
        this.console = console;
    }


    @Override
    public Response apply(String userCommandArgument) {
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
