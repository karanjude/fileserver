package com.amazon.eisdocumentservice.handlers.server;

import java.net.InetAddress;
import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

public class EchoServerHandler extends SimpleChannelInboundHandler<String> {

	// @Override
	// public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
	// // Discard the received data silently.
	// ByteBuf in = (ByteBuf) msg;
	//
	// try{
	// //write the msg to output stream
	// //System.out.println(in.toString(io.netty.util.CharsetUtil.US_ASCII));
	// //ReferenceCountUtil.release(msg);
	//
	//
	// ctx.write(msg);
	// //ctx.flush();
	// }finally{
	// }
	// }

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
		// Close the connection when an exception is raised.
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// Send greeting for a new connection.
		ctx.write("Welcome to " + InetAddress.getLocalHost().getHostName()
				+ "!\r\n");
		ctx.write("It is " + new Date() + " now.\r\n");
		ctx.flush();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String request)
			throws Exception {
		// Generate and write a response.
		String response;
		boolean close = false;
		if (request.isEmpty()) {
			response = "Please type something.\r\n";
		} else if ("bye".equals(request.toLowerCase())) {
			response = "Have a good day!\r\n";
			close = true;
		} else {
			response = "Did you say '" + request + "'?\r\n";
		}

		// We do not need to write a ChannelBuffer here.
		// We know the encoder inserted at TelnetPipelineFactory will do the
		// conversion.
		ChannelFuture future = ctx.write(response);

		// Close the connection after sending 'Have a good day!'
		// if the client has sent 'bye'.
		if (close) {
			future.addListener(ChannelFutureListener.CLOSE);
		}

	}
}
