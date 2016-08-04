package com.bodani.rpc;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

/**
 *
 */
public class RPCServer implements SayHi {

    public String sayHi(String name) {
        return "Hi " + name;
    }
    
    public static void main(String[] args) throws IOException {
        RPC.Server server = new RPC.Builder(new Configuration()).setProtocol(SayHi.class)
                .setInstance(new RPCServer()).
                        setBindAddress("127.0.0.1").setPort(1234).build();
        server.start();
    }
}