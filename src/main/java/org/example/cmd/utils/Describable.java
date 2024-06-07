package org.example.cmd.utils;

/**
 * Интерфейс для объектов, содержащих описание
 */
interface Describable {
    /**
     * Получить имя команды
     *
     * @return имя команды
     */
    public String getName();

    /**
     * Получить текст описание команды
     *
     * @return строка-описание
     */
    public String getDescription();
}
