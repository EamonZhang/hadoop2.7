package com.bodani.rpc;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 *
 */
public class RPCClient {

    public static void main(String[] args) throws IOException {
    	SayHi proxy = RPC.getProxy(SayHi.class, 10000L,
                new InetSocketAddress("127.0.0.1", 1234),
                new Configuration());
        System.out.printf(proxy.sayHi("eamon"));
        RPC.stopProxy(proxy);
    }
}