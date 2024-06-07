package org.example.cmd;

import org.example.TCP_components.Response;
import org.example.managers.CollectionManager;
import org.example.cmd.utils.Command;

public class Clear extends Command {
    CollectionManager collectionManager;
    public Clear(CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response apply(String commandArgument) {
        if (collectionManager.getCollection().isEmpty()){
            return new Response(true, "Коллекция уже пуста", "");
        }
        collectionManager.cleanCollection();
        return new Response(true, "Коллекция успешно очищена", "");
    }
}
