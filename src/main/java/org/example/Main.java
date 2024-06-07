package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.cmd.*;
import org.example.cmd.server.Exit;
import org.example.cmd.server.Save;
import org.example.exceptions.ValidateException;
import org.example.managers.*;
import java.io.*;


public class Main {

    public static final Logger logger = LogManager.getLogger("Server");

    public static void main(String[] args) {
        //check
        var console = new ConsoleManager();

        if (args.length == 0) {
            console.println("Введите имя загружаемого файла, как аргумент командной строки");
            System.exit(1);
        }
        var serializationManager = new CollectionSerializationManager(args[0],console);
        var collectionManager = new CollectionManager(serializationManager);

        try {
            if (!collectionManager.loadCollection())
                console.printWarning("Коллекция пуста");
            else{
                console.printSuccessful("Коллекция успешно загружена из файла");
            }
            collectionManager.setLastInitTime();
        } catch (ValidateException e) {
            console.printError("Объекты коллекции не валидны");
            System.exit(1);
        }

        try {
            TCPConnectingManager connector = new TCPConnectingManager(0);
            TCPSerializationManager serializator = new TCPSerializationManager();

            var commandManager = new CommandManager();
            var serverCommandManager = new CommandManager();

            commandManager.register(new Add(console, collectionManager, serializator));
            commandManager.register(new AddIfMax(collectionManager, console, serializator));
            commandManager.register(new Clear(collectionManager));
            commandManager.register(new ExecuteScript(console,commandManager));
            commandManager.register(new FilterContainsPassportID(collectionManager,console));
            commandManager.register(new Help(commandManager));
            commandManager.register(new History(commandManager));
            commandManager.register(new Info(collectionManager,console));
            commandManager.register(new MinByWeight(collectionManager,console));
            commandManager.register(new PrintAscending(collectionManager,console));
            commandManager.register(new RemoveByID(collectionManager));
            commandManager.register(new RemoveLower(collectionManager,console));
            commandManager.register(new Show(collectionManager, console));
            commandManager.register(new Update(collectionManager,console,serializator));

            serverCommandManager.register(new Exit(collectionManager,console));
            serverCommandManager.register(new Save(collectionManager));
            serverCommandManager.register(new Help(serverCommandManager));
            serverCommandManager.register(new Show(collectionManager,console));

            new Runner(console,commandManager, serverCommandManager, connector,serializator, 8095)
                    .interactiveMode();

        } catch (IOException e) {
            console.printError("Ошибка создания канала или селектора");
            System.exit(1);
        }
    }
}