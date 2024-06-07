package org.example.managers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.example.interfaces.Console;
import org.example.model.Person;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class CollectionSerializationManager {
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    private final String fileName;
    private final Console console;

    public CollectionSerializationManager(String fileName, Console console) {
        this.fileName = fileName;
        this.console = console;
    }

    public boolean writeCollection(Collection<Person> collection) {
        File file = new File(fileName);

        if (!file.exists()) {
            console.printWarning("Файл " + fileName + " не существует!");
            return false;
        }

        try (PrintWriter collectionPrintWriter = new PrintWriter(fileName)) {
            collectionPrintWriter.println(gson.toJson(collection));
            return true;
        } catch (IOException exception) {
            console.printWarning("Загрузочный файл не может быть открыт!");
            return false;
        }
    }

    public String getFileName() {
        return fileName;
    }

    public Collection<Person> readCollection() {
        if (fileName != null && !fileName.isEmpty()) {
            try (var fileReader = new FileReader(fileName)) {
                var collectionType = new TypeToken<Collection<Person>>() {}.getType();
                var reader = new BufferedReader(fileReader);

                var jsonString = new StringBuilder();

                String line;
                while((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        jsonString.append(line);
                    }
                }
                if (jsonString.isEmpty()) {
                    jsonString = new StringBuilder("[]");
                }

                return gson.fromJson(jsonString.toString(), collectionType);

            } catch (FileNotFoundException exception) {
                console.printError("Загрузочный файл не найден! Сохранение в файл невозможно");
            } catch (NoSuchElementException exception) {
                console.printWarning("Загрузочный файл пуст!");
            } catch (JsonParseException exception) {
                console.printWarning("В загрузочном файле не обнаружена необходимая коллекция!");
            } catch (IllegalStateException | IOException exception) {
                console.printWarning("Непредвиденная ошибка!");
                System.exit(0);
            }
        } else {
            console.printWarning("Аргумент командной строки с загрузочным файлом не найден!");
        }
        return new PriorityQueue<>();
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    static class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
        @Override
        public JsonElement serialize(LocalDateTime date, Type typeOfSrc,
                                     JsonSerializationContext context) {
            return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)); // "yyyy-mm-dd"
        }

        @Override
        public LocalDateTime deserialize(JsonElement json, Type type,
                                         JsonDeserializationContext context) throws JsonParseException {
            return LocalDateTime.parse(json.getAsJsonPrimitive().getAsString());
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
