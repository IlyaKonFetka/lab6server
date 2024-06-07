package org.example.cmd.utils;


/**
 * Базовый класс объекта-команды
 */
public abstract class Command implements Describable, Executable {
    /**
     * Имя команды
     */
    private final String name;
    /**
     * Описание команды
     */
    private final String description;

    /**
     * Конструктор
     * @param name - имя
     * @param description - описание
     */
    public Command(String name, String description) {
        this.name = name;
        this.description = description;

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
