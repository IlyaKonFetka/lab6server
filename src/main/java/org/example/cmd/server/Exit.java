package org.example.cmd.server;

import org.example.TCP_components.Response;
import org.example.cmd.utils.Command;
import org.example.interfaces.Console;
import org.example.managers.CollectionManager;


public class Exit extends Command {
    private CollectionManager collectionManager;
    private Console console;
    public Exit(CollectionManager collectionManager, Console console) {
        super("exit","выйти из программы");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public Response apply(String userCommandArgument) {
        if (collectionManager.saveCollection()){
            collectionManager.setLastSaveTime();
            console.printSuccessful("Коллекция успешно сохранена в файл");
        } else {
            console.printError("Ошибка при сохранении коллекции");
        }
        console.printSuccessful("Завершение работы...");
        System.exit(1);
        return new Response(true,"","");
    }
}
