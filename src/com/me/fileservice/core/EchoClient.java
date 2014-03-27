package com.amazon.eisdocumentservice.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.amazon.eisdocumentservice.handlers.client.TimeClientHandler;
import com.amazon.eisdocumentservice.handlers.client.util.EchoClientInitializer;
import com.amazon.eisdocumentservice.handlers.client.util.TimeDecoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoClient {
	private String host = "localhost";
	private int port = 8080;
	
	public EchoClient(String host, int port){
		this.host = host;
		this.port = port;
	}
	
	public void run() throws Exception{
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.handler(new EchoClientInitializer());

            // Start the client.
            Channel f = b.connect(host, port).sync().channel(); // (5)

            acceptStringsFromStdin(f);
        } finally {
            workerGroup.shutdownGracefully();
        }
		
	}
	
	private void acceptStringsFromStdin(Channel f) throws Exception {
		ChannelFuture lastWriteFuture = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		for(;;){
			String line = in.readLine();
			if(null == line)
				break;
			
			lastWriteFuture = f.writeAndFlush(line + "\r\n");
			if("bye".equalsIgnoreCase(line)){
				f.closeFuture().sync();
				break;
			}
		}
		
		if(null != lastWriteFuture){
			lastWriteFuture.sync();
		}
	}

	public static void main(String[] args) throws Exception {
		int port = Integer.parseInt("8080");
		new EchoClient("localhost",port).run();
	}
}
