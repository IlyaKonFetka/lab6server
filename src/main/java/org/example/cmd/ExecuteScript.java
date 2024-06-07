package org.example.cmd;

import org.example.TCP_components.Response;
import org.example.cmd.utils.Command;
import org.example.interfaces.Console;
import org.example.managers.CommandManager;

public class ExecuteScript extends Command {
    public ExecuteScript(Console console, CommandManager commandManager) {
        super("execute_script", "выполнить ряд команд из файла");
    }

    @Override
    public Response apply(String userCommandArgument) {
        return new Response(true,"Скрипт успешно выполнен","");
    }
}
