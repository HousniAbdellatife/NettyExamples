package com.example.nettyexamples;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

public final class BasicNettyClient {

    private BasicNettyClient() {
    }

    public static void main(String[] args) throws InterruptedException {
        String host = args.length > 0 ? args[0] : "127.0.0.1";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 8080;
        String message = args.length > 2 ? args[2] : "hello netty";

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<ByteBuf>() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) {
                                    ctx.writeAndFlush(ctx.alloc().buffer().writeCharSequence(message + System.lineSeparator(), CharsetUtil.UTF_8));
                                }

                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {
                                    String response = msg.toString(CharsetUtil.UTF_8).trim();
                                    System.out.println("Client got response: " + response);
                                    ctx.close();
                                }
                            });
                        }
                    });

            ChannelFuture future = bootstrap.connect(host, port).sync();
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}
