package com.amazon.eisdocumentservice.handlers.client.util;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import com.amazon.eisdocumentservice.handlers.client.EchoClientHandler;

public class EchoClientInitializer extends ChannelInitializer<SocketChannel> {
	private final StringDecoder DECODER = new StringDecoder();
	private final StringEncoder ENCODER = new StringEncoder();
	private final EchoClientHandler echoClientHandler = new EchoClientHandler();

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		
		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
		pipeline.addLast("decoder",DECODER);
		pipeline.addLast("encoder",ENCODER);
		pipeline.addLast("handler",echoClientHandler);
		
	}

}
