package com.amazon.eisdocumentservice.handlers.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class EchoClientHandler extends SimpleChannelInboundHandler<String> {

	@Override
	protected void channelRead0(ChannelHandlerContext arg0, String msg)
			throws Exception {
		System.err.println(msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
	          ctx.close();
	}
	
}
