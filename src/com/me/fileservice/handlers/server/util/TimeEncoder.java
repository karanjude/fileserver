package com.amazon.eisdocumentservice.handlers.server.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import com.amazon.eisdocumentservice.core.model.UnixTime;

public class TimeEncoder extends MessageToByteEncoder<UnixTime> {
    
//	@Override
//    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
//        UnixTime m = (UnixTime) msg;
//        ByteBuf encoded = ctx.alloc().buffer(4);
//        encoded.writeInt(m.value());
//        ctx.write(encoded, promise); // (1)
//    }
    
    @Override
    protected void encode(ChannelHandlerContext ctx, UnixTime msg, ByteBuf out) {
        out.writeInt(msg.value());
    }
}