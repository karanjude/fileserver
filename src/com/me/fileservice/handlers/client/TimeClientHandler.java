package com.amazon.eisdocumentservice.handlers.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import com.amazon.eisdocumentservice.core.model.UnixTime;

public class TimeClientHandler extends ChannelInboundHandlerAdapter{
	
	@Override
    public void handlerAdded(ChannelHandlerContext ctx) {
    }
	
	@Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
    }
	
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
		UnixTime m = (UnixTime) msg;
	    System.out.println(m);
	    ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
