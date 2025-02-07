package org.example.cmd;

import org.example.TCP_components.Response;
import org.example.managers.CommandManager;
import org.example.cmd.utils.Command;

import java.util.List;
import static java.lang.Math.max;

public class History extends Command {
    CommandManager commandManager;
    public History(CommandManager commandManager) {
        super("history", "вывести последние 8 команд");
        this.commandManager = commandManager;
    }
    @Override
    public Response apply(String userCommandArgument) {
        List<String>arr = commandManager.getCommandHistory();
        if (arr.isEmpty())return new Response(true,"История команд пуста","");

        StringBuilder stringBuilder = new StringBuilder();
        int start = max(arr.size() - 8, 0);
        for(int i = start; i < arr.size(); i++){
            stringBuilder.append(arr.get(i)).append("\n");
        }

        if (stringBuilder.isEmpty()){
            return new Response(true,"Список последних введённых команд пуст","");
        }
        return new Response(true,"Список последних введённых команд успешно получен",stringBuilder.toString());
    }
}
