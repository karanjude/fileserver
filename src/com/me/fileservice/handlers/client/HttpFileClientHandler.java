package com.amazon.eisdocumentservice.handlers.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.CharsetUtil;

public class HttpFileClientHandler extends SimpleChannelInboundHandler<HttpObject> {

	OutputStream writer = null;
	
    @Override
    public void exceptionCaught(
            ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg)
			throws Exception {
        if (msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) msg;

            System.out.println("STATUS: " + response.getStatus());
            System.out.println("VERSION: " + response.getProtocolVersion());
            System.out.println();

            if (!response.headers().isEmpty()) {
                for (String name: response.headers().names()) {
                    for (String value: response.headers().getAll(name)) {
                        System.out.println("HEADER: " + name + " = " + value);
                    }
                }
                System.out.println();
            }

            if (HttpHeaders.isTransferEncodingChunked(response)) {
                System.out.println("CHUNKED CONTENT {");
            } else {
                System.out.println("CONTENT {");
            }
        }
        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            	
            if(null != writer){
            	ByteBuf b = content.content();
            	b.readBytes(writer, b.readableBytes());
            }
//            System.out.print(content2.toString(CharsetUtil.UTF_8));
//            System.out.flush();

            if (content instanceof LastHttpContent) {
                System.out.println("} END OF CONTENT");
            	writer.flush();
            	writer.close();
            	writer = null;
            }
        }
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		
		if(null == writer){
			writer = new FileOutputStream(new File("x"));
		}
	}
}
