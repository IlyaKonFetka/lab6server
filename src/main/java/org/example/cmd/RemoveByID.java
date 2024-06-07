package org.example.cmd;

import org.example.TCP_components.Response;
import org.example.cmd.utils.Command;
import org.example.managers.CollectionManager;
import org.example.model.Person;

public class RemoveByID extends Command {
    CollectionManager collectionManager;
    public RemoveByID(CollectionManager collectionManager) {
        super("remove_by_id", "удалить элемент коллекции по id");
        this.collectionManager = collectionManager;

    }
    @Override
    public Response apply(String userCommandArgument) {
        if(collectionManager.getCollection().isEmpty()) {
            return new Response(false,"Коллекция пуста","");
        }
        int id = Integer.parseInt(userCommandArgument);
        Person p = collectionManager.byId(id);
        if (p == null) {
            return new Response(false, "Элемента с таким id нет в коллекции", "");
        }
        if (collectionManager.remove(id))
            return new Response(true, "Объект успешно удалён", "");
        else
            return new Response(false, "Непредвиденная ошибка, объект не удалён", "");
    }
}
