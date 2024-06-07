package org.example.cmd.utils;

import org.example.TCP_components.Request;
import org.example.TCP_components.Response;

interface Executable {
    public Response apply(String commandArgument);
}
