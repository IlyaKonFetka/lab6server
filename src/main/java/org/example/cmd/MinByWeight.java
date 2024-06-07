package org.example.cmd;

import org.example.TCP_components.Response;
import org.example.cmd.utils.Command;
import org.example.interfaces.Console;
import org.example.managers.CollectionManager;
import org.example.model.Person;

public class MinByWeight extends Command {
    CollectionManager collectionManager;
    Console console;
    public MinByWeight(CollectionManager collectionManager, Console console) {
        super("min_by_weight", "вывести элемент с минимальным weight");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public Response apply(String userCommandArgument) {
        if(collectionManager.getCollection().isEmpty()) {
            return new Response(true,"Коллекция пуста","");
        }

        Long minWeight = Long.MAX_VALUE;
        Person minP = null;
        for(Person p: collectionManager.getCollection()){
            if (p.getWeight() <= minWeight){
                minP = p;
                minWeight = p.getWeight();
            }
        }
        assert minP != null;
        return new Response(true, "Person с минимальным weight успешно получен", minP.toString());
    }
}
