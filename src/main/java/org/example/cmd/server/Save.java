package org.example.cmd.server;


import org.example.TCP_components.Response;
import org.example.cmd.utils.Command;
import org.example.managers.CollectionManager;

public class Save extends Command {
    CollectionManager collectionManager;
    public Save(CollectionManager collectionManager) {
        super("save", "сохранить коллекцию в файл");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response apply(String userCommandArgument) {
        if (collectionManager.saveCollection()) {
            collectionManager.setLastSaveTime();
            return new Response(true,"Коллекция сохранена в файл","");
        }
        else {
            return new Response(false,"Ошибка при сохранении коллекции","");
        }
    }
}
