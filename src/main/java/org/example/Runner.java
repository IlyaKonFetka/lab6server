package org.example;


import com.google.gson.JsonSyntaxException;
import org.example.TCP_components.Request;
import org.example.TCP_components.Response;

import org.example.interfaces.Console;
import org.example.managers.CommandManager;
import org.example.managers.TCPConnectingManager;
import org.example.managers.TCPSerializationManager;
import org.example.cmd.utils.Command;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;
public class Runner {
    private Console console;
    private CommandManager commandManager;
    private CommandManager serverCommandManager;

    private TCPConnectingManager connector;
    private TCPSerializationManager serializator;
    private int port;

    public Runner(Console console,
                  CommandManager commandManager,
                  CommandManager servarCommandManager,
                  TCPConnectingManager connector,
                  TCPSerializationManager serializator,
                  int port) {
        this.console = console;
        this.commandManager = commandManager;
        this.serverCommandManager = servarCommandManager;
        this.connector = connector;
        this.serializator = serializator;
        this.port = port;
    }

    public void interactiveMode() {
        try {
            ServerSocketChannel serverSocket = ServerSocketChannel.open();
            serverSocket.configureBlocking(false);
            serverSocket.socket().bind(new InetSocketAddress(port));

            Selector selector = Selector.open();
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);

            new Thread(this::handleConsoleInput).start();

            ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
            ByteBuffer[] messageBufferHolder = new ByteBuffer[1];

            while (true) {
                selector.select();
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();

                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    keyIterator.remove();

                    if (!key.isValid()) {
                        continue;
                    }

                    try {
                        if (key.isAcceptable()) {
                            SocketChannel clientChannel = serverSocket.accept();
                            if (clientChannel != null) {
                                clientChannel.configureBlocking(false);
                                clientChannel.register(selector, SelectionKey.OP_READ);
                                Main.logger.info("One more connected: " + clientChannel.getRemoteAddress());
                            }
                        } else if (key.isReadable()) {
                            SocketChannel clientChannel = (SocketChannel) key.channel();
                            readMessage(clientChannel, lengthBuffer, messageBufferHolder);
                        }
                    } catch (IOException e) {
                        key.cancel();
                        key.channel().close();
                        console.printWarning("Клиент отключился или произошла ошибка");
                    } catch (JsonSyntaxException e){
                        key.cancel();
                        key.channel().close();
                        console.printWarning("Ошибка чтения запроса от клиента. Клиент отключён");
                    }
                }
            }
        } catch (NoSuchElementException exception) {
            console.printError("Пользовательский ввод не обнаружен!");
        } catch (IllegalStateException exception) {
            console.printError("Непредвиденная ошибка!");
        } catch (ClosedChannelException e) {
            console.printError("ClosedChannelException");
        } catch (IOException e) {
            console.printError("IOException");
        }
    }

    private void readMessage(SocketChannel clientChannel, ByteBuffer lengthBuffer, ByteBuffer[] messageBufferHolder) throws IOException {
        if (clientChannel.read(lengthBuffer) > 0) {
            if (!lengthBuffer.hasRemaining()) {
                lengthBuffer.flip();
                int messageLength = lengthBuffer.getInt();
                lengthBuffer.clear();

                messageBufferHolder[0] = ByteBuffer.allocate(messageLength);
            }
        }

        ByteBuffer messageBuffer = messageBufferHolder[0];
        if (messageBuffer != null && clientChannel.read(messageBuffer) > 0) {
            if (!messageBuffer.hasRemaining()) {
                messageBuffer.flip();
                String message = new String(messageBuffer.array(), 0, messageBuffer.limit(), "UTF-8");
                messageBuffer.clear();

                // Обработка запроса
                Request request = serializator.request(message);
                Main.logger.info(clientChannel.getRemoteAddress() + ": " + request.getCommandName());

                // Штатное отключение клиента
                if (request.getCommandName().equals("exit")) {
                    clientChannel.close();
                    console.printWarning("Клиент отключился");
                    return;
                }

                // Создание и отправка ответа
                Response response = launchCommand(request, commandManager);
                String stringResponse = serializator.serialize(response);
                sendMessage(clientChannel, stringResponse);
            }
        }
    }


    private void sendMessage(SocketChannel clientChannel, String message) throws IOException {
        byte[] responseBytes = message.getBytes("UTF-8");
        ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
        lengthBuffer.putInt(responseBytes.length);
        lengthBuffer.flip();
        clientChannel.write(lengthBuffer);

        ByteBuffer messageBuffer = ByteBuffer.wrap(responseBytes);
        while (messageBuffer.hasRemaining()) {
            clientChannel.write(messageBuffer);
        }
    }


    private void handleConsoleInput() {

        try {
            Response response;
            String responseBody;

            String userCommandName;
            String userCommandArgument;

            while (true) {
                console.prompt();
                userCommandName = console.read().trim();
                userCommandArgument = console.readln().trim();

                response = launchCommand(new Request(userCommandName,userCommandArgument), serverCommandManager);
                responseBody = response.getResponseBody();

                if (response.isStatus()){
                    if (responseBody != null && !responseBody.isEmpty()) {
                        Main.logger.info(response.getResponseBody());
                    }
                    console.printSuccessful(response.getTextStatus());
                }else {
                    if (responseBody == null || responseBody.isEmpty()) {
                        Main.logger.info(response.getResponseBody());
                    }
                    console.printError(response.getTextStatus());
                }
            }
        } catch (NoSuchElementException exception) {
            console.printError("Пользовательский ввод не обнаружен!");
        } catch (IllegalStateException exception) {
            console.printError("Непредвиденная ошибка!");
        }
    }
    private Response launchCommand(Request request, CommandManager currentCommandManager) {
        String commandName = request.getCommandName();
        String stringRequestBody = request.getSerializedRequestBody();

        if (commandName.isEmpty()) return new Response(false,"Пустой запрос","");
        Command command = currentCommandManager.getCommands().get(commandName);

        if (command == null) return new Response(false, "Команда '" + commandName
                + "' не найдена на сервере. Наберите 'help' для справки", "");
        currentCommandManager.addToHistory(commandName);
        return command.apply(stringRequestBody);
    }
}