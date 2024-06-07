package org.example.cmd;

import org.example.TCP_components.Response;
import org.example.cmd.utils.Command;
import org.example.interfaces.Console;
import org.example.managers.CollectionManager;
import org.example.model.Person;

public class RemoveLower extends Command {
    CollectionManager collectionManager;
    Console console;
    public RemoveLower(CollectionManager collectionManager, Console console) {
        super("remove_lover", "удалить все элементы меньше данного");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public Response apply(String userCommandArgument) {
        if(collectionManager.getCollection().isEmpty()) {
            return new Response(true,"Коллекция пуста","");
        }

        if (userCommandArgument.isEmpty()) {
            return new Response(false,"Имя пусто","");
        }

        for(Person p: collectionManager.getCollection()){
            if(p.getName().compareTo(userCommandArgument) < 0 )
                collectionManager.remove(p);
        }
        return new Response(true,"Все элементы успешно удалены","");
    }
}
