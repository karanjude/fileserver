package com.amazon.eisdocumentservice.handlers.server.util;

import com.amazon.eisdocumentservice.handlers.server.HttpFileServerHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class HttpFileServerInitializer extends ChannelInitializer<SocketChannel>  {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// Create a default pipeline implementation.
        ChannelPipeline pipeline = ch.pipeline();

        // Uncomment the following line if you want HTTPS
        //SSLEngine engine = SecureChatSslContextFactory.getServerContext().createSSLEngine();
        //engine.setUseClientMode(false);
        //pipeline.addLast("ssl", new SslHandler(engine));

        pipeline.addLast("encoder", new HttpResponseEncoder());
        pipeline.addLast("decoder", new HttpRequestDecoder());
        pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
        pipeline.addLast("chunkedWriter", new ChunkedWriteHandler());

        pipeline.addLast("handler", new HttpFileServerHandler(false)); // Specify false if SSL.
    }
}
