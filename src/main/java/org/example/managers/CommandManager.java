package org.example.managers;

import org.example.cmd.utils.Command;

import java.util.*;

public class CommandManager {
    private final Map<String, Command> commands;
    private final List<String> commandHistory;

    private final HashSet<String> scriptFileNames;

    public boolean isScript(){
        return !scriptFileNames.isEmpty();
    }
    public CommandManager() {
        this.commands = new LinkedHashMap<>();
        this.commandHistory = new ArrayList<>();
        this.scriptFileNames = new HashSet<>();
    }
    public void cleanScriptStack(){
        this.scriptFileNames.clear();
    }
    public boolean addScriptFileName(String fileName){
        if (!scriptFileNames.contains(fileName)){
            scriptFileNames.add(fileName);
            return true;
        }return false;
    }

    public boolean deleteLastScriptFileName(String fileName){
        if (scriptFileNames.contains(fileName)){
            scriptFileNames.remove(fileName);
            return true;
        }return false;
    }

    public void register(Command command) {
        commands.put(command.getName(), command);
    }

    public Map<String, Command> getCommands() {
        return commands;
    }

    public List<String> getCommandHistory() {
        return commandHistory;
    }

    public void addToHistory(String command) {
        commandHistory.add(command);
    }
}
