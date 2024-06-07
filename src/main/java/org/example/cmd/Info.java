package org.example.cmd;

import org.example.TCP_components.Response;
import org.example.interfaces.Console;
import org.example.managers.CollectionManager;
import org.example.model.Person;
import org.example.cmd.utils.Command;

public class Info extends Command {
    CollectionManager collectionManager;
    Console console;
    public Info(CollectionManager collectionManager, Console console) {
        super("info", "вывести текущую информацию о коллекции");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public Response apply(String userCommandArgument) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("информация о коллекции").append("\n")
                .append("_____________________________________________________________").append("\n")
                .append("тип коллекции: ").append(collectionManager.getCollection().getClass()).append("\n")
                .append("количество элементов: ").append(collectionManager.getCollection().size()).append("\n");
        try {
            stringBuilder.append("время последней инициализации: ")
                    .append(collectionManager.getLastInitTime().format(Person.timeFormatter)).append("\n");
        }catch (NullPointerException e){
            stringBuilder.append("инициализация не происходила").append("\n");
        }
        try {
            stringBuilder.append("время последнего сохранения: ")
                    .append(collectionManager.getLastSaveTime().format(Person.timeFormatter)).append("\n");
        }catch(NullPointerException e){
            stringBuilder.append("сохранения не происходило").append("\n");
        }
        stringBuilder.append("адрес файла-хранилища: ").append(collectionManager.getFileName()).append("\n");
        return new Response(true,"Информация о коллекции получена", stringBuilder.toString());
    }
}
