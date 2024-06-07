package org.example.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.example.TCP_components.Request;
import org.example.TCP_components.Response;
import org.example.model.Person;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TCPSerializationManager {
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(LocalDateTime.class, new CollectionSerializationManager.LocalDateTimeAdapter())
            .create();

    public String serialize(Serializable a){
        return gson.toJson(a);
    }

    public Response response(String stringResponse) throws JsonSyntaxException {
        return gson.fromJson(stringResponse,Response.class);
    }
    public Request request(String stringRequest) throws JsonSyntaxException {
        return gson.fromJson(stringRequest,Request.class);
    }
    public Person person(String stringPerson) throws JsonSyntaxException {
        return gson.fromJson(stringPerson,Person.class);
    }
}
