package com.amazon.eisdocumentservice.handlers.client.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import com.amazon.eisdocumentservice.core.model.UnixTime;

public class TimeDecoder extends ByteToMessageDecoder{
	
	@Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) { // (2)
        if (in.readableBytes() < 4) {
            return; // (3)
        }

        out.add(new UnixTime(in.readInt())); // (4)
    }
}
