package org.example.cmd;

import org.example.TCP_components.Response;
import org.example.managers.CommandManager;
import org.example.cmd.utils.Command;

import java.util.Map;

public class Help extends Command {
    private CommandManager commandManager;
    public Help(CommandManager commandManager) {
        super("help", "вывести информацию о доступных командах");
        this.commandManager = commandManager;
    }

    @Override
    public Response apply(String userCommandArgument) {
        if (commandManager.getCommands().isEmpty()) {
            return new Response(true, "Список доступных команд пуст", "");
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Command> entry : commandManager.getCommands().entrySet() ){
            stringBuilder.append(entry.getValue().getName()).append(" - ").append(entry.getValue().getDescription())
                    .append("\n");
        }
        return new Response(true, "Список доступных команд успешно выведен", stringBuilder.toString());
    }
}
