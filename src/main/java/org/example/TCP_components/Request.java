package org.example.TCP_components;

import java.io.Serializable;

public class Request implements Serializable {
    private String commandName;
    private String serializedRequestBody;

    public Request(String commandName, String serializedRequestBody) {
        this.commandName = commandName;
        this.serializedRequestBody = serializedRequestBody;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getSerializedRequestBody() {
        return serializedRequestBody;
    }

    public void setSerializedRequestBody(String serializedRequestBody) {
        this.serializedRequestBody = serializedRequestBody;
    }

    @Override
    public String toString() {
        return "Request{" +
                "commandName='" + commandName + '\'' +
                ", serializedRequestBody='" + serializedRequestBody + '\'' +
                '}';
    }
}
