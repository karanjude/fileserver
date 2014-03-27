package com.amazon.eisdocumentservice.core;

import com.amazon.eisdocumentservice.handlers.server.util.HttpFileServerInitializer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HttpFileServer {
	 private final int port;

	    public HttpFileServer(int port) {
	        this.port = port;
	    }

	    public void run() throws Exception {
	        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
	        EventLoopGroup workerGroup = new NioEventLoopGroup();
	        try {
	            ServerBootstrap b = new ServerBootstrap();
	            b.group(bossGroup, workerGroup)
	             .channel(NioServerSocketChannel.class)
	             .childHandler(new HttpFileServerInitializer());

	            b.bind(port).sync().channel().closeFuture().sync();
	        } finally {
	            bossGroup.shutdownGracefully();
	            workerGroup.shutdownGracefully();
	        }
	    }

	    public static void main(String[] args) throws Exception {
	        int port;
	        if (args.length > 0) {
	            port = Integer.parseInt(args[0]);
	        } else {
	            port = 8080;
	        }
	        new HttpFileServer(port).run();
	    }
}
