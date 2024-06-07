package org.example.cmd;

import org.example.TCP_components.Response;
import org.example.interfaces.Console;
import org.example.managers.CollectionManager;
import org.example.model.Person;
import org.example.cmd.utils.Command;


public class FilterContainsPassportID extends Command {
    CollectionManager collectionManager;
    Console console;
    public FilterContainsPassportID(CollectionManager collectionManager, Console console) {
        super("filter_contains_passport_i_d", "вывести элементы, значение passportID которых содержит подстроку");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public Response apply(String subStr) {
        if (subStr.isEmpty()) {
            return new Response(false,"Подстрока пуста или передана некорректно","");
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Person p: collectionManager.getCollection()){
            if(p.getPassportID().contains(subStr)){
                console.println(p.toString());
                stringBuilder.append(p).append("\n");
            }
        }
        if (stringBuilder.isEmpty()){
            return new Response(true, "Объектов с данной подстрокой в паспорте не найдено","");
        }
        return new Response(true,"Список объектов с данной подстрокой в паспорте успешно получен",stringBuilder.toString());
    }
}
