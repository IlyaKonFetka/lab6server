package org.example.managers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.security.Key;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class TCPConnectingManager {
    private ServerSocketChannel serverSocket;
    private Selector selector;
    private Iterator<SelectionKey> keys;
    private SocketChannel clientChannel;
    private ByteBuffer buffer;

    public TCPConnectingManager(int port) throws IOException {
        this.serverSocket = ServerSocketChannel.open();
        serverSocket.configureBlocking(false);
        serverSocket.socket().bind(new InetSocketAddress(port));

        this.selector = Selector.open();
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
    }

    public Iterator<SelectionKey> getKeys() {
        return keys;
    }
    public void accept() throws IOException {
        clientChannel = serverSocket.accept();
        if (clientChannel != null) {
            clientChannel.configureBlocking(false);
            clientChannel.register(selector, SelectionKey.OP_READ);
        }
    }
    public void read(SelectionKey key){
        clientChannel = (SocketChannel) key.channel();
        buffer =  ByteBuffer.allocate(4096);
    }

    public SocketAddress getClientAddress() throws IOException {
        return clientChannel.getRemoteAddress();
    }
    public void waitForNewConnect() throws IOException {
        selector.select();
        keys =  selector.selectedKeys().iterator();
    }
    public void acceptConnection() throws IOException, NullPointerException {
        SocketChannel clientChannel = serverSocket.accept();
        clientChannel.configureBlocking(false);
        clientChannel.register(selector, SelectionKey.OP_READ);
    }


}
