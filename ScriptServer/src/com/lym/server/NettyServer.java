package com.lym.server;

import java.nio.charset.Charset;

import com.google.gson.Gson;
import com.lym.dao.DeviceDao;
import com.lym.model.Device;
import com.lym.model.Message;
import com.lym.model.ScriptInfo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;

public class NettyServer {
	public ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	public class ServerHandler extends SimpleChannelInboundHandler<String> {
		AttributeKey<String> deviceAttributeKey = AttributeKey.valueOf("device");
		DeviceDao mDeviceDao = new DeviceDao();
		Gson mGson = new Gson();

		// �յ���Ϣ
		@Override
		protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
			Channel client = ctx.channel();
			Message message = mGson.fromJson(msg, Message.class);
			switch (message.getType()) {
			case Message.CONNECT: {
				// �豸����
				String deviceId = message.getContent();
				Device device = new Device();
				device.setOnline(true);
				device.setDeviceId(deviceId);
				// ����豸
				mDeviceDao.addOrUpdate(device);
				// ���豸id���浽ͨ��������
				client.attr(deviceAttributeKey).set(deviceId);
				break;
			}
			case Message.REBOOT: {
				// ��������
				break;
			}
			case Message.START_SCRIPT: {
				// �����ű�
				String deviceId = message.getTo();
				ScriptInfo info = mGson.fromJson(message.getContent(), ScriptInfo.class);
				Device device = mDeviceDao.getDevice(deviceId);
				device.setScriptInfo(info);
				device.setRunning(true);
				mDeviceDao.addOrUpdate(device);
				break;
			}
			case Message.STOP_SCRIPT: {
				// ֹͣ�ű�
				String deviceId = message.getTo();
				Device device = mDeviceDao.getDevice(deviceId);
				device.setRunning(false);
				mDeviceDao.addOrUpdate(device);
				break;
			}
			}
			String to = message.getTo();
			if (to != null) {
				for (Channel channel : channels) {
					if (to.equals(channel.attr(deviceAttributeKey).get())) {
						channel.writeAndFlush(mGson.toJson(message) + "\n");
						break;
					}
				}
			}
		}

		// IO�쳣
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			// �쳣���־͹ر�����
			ctx.close();
		}

		// �½�����
		@Override
		public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
			channels.add(ctx.channel());
		}

		// ���ӶϿ�
		@Override
		public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
			channels.remove(ctx.channel());
			Channel client = ctx.channel();
			String deviceId = client.attr(deviceAttributeKey).get();
			Device device = mDeviceDao.getDevice(deviceId);
			if (device == null) {
				return;
			}
			device.setOnline(false);
			mDeviceDao.addOrUpdate(device);
		}

	}

	private static NettyServer instance;

	public static synchronized NettyServer getInstance() {
		if (instance == null) {
			instance = new NettyServer();
		}
		return instance;
	}

	boolean start;
	ServerBootstrap mServerBootstrap;
	EventLoopGroup bossGroup;
	EventLoopGroup workerGroup;
	ChannelFutureListener mChannelFutureListener;

	private NettyServer() {

	}

	public synchronized void startServer() {
		if (start) {
			return;
		}
		start = true;
		bossGroup = new NioEventLoopGroup();
		// ���������Ѿ����յ�����
		workerGroup = new NioEventLoopGroup();
		// ��һ������nio����ĸ���������
		mServerBootstrap = new ServerBootstrap();
		mServerBootstrap.group(bossGroup, workerGroup);
		mServerBootstrap.channel(NioServerSocketChannel.class);
		// 1024 ��ʾ������������ʱ��ȴ����û������������
		mServerBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
		// SO_KEEPALIVE �Ƿ���Ҫ���������������
		mServerBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
		mServerBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				// ��8192���ַ�����û�յ�"\n"�ַ����쳣
				pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
				pipeline.addLast("decoder", new StringDecoder(Charset.forName("UTF-8")));
				pipeline.addLast("encoder", new StringEncoder(Charset.forName("UTF-8")));
				pipeline.addLast("handler", new ServerHandler());
				pipeline.addLast("ping", new IdleStateHandler(10, 5, 10) {
					@Override
					protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
						super.channelIdle(ctx, evt);
						if (((IdleStateEvent) evt).state().equals(IdleState.READER_IDLE)) {
							ctx.close();
						} else if (((IdleStateEvent) evt).state().equals(IdleState.WRITER_IDLE)) {
							// ��������
							ctx.writeAndFlush("{}\n");
						}
					}
				});
				System.out.println(ch.remoteAddress() + "�����Ϸ�����");
			}
		});
		mChannelFutureListener = new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture f) throws Exception {
				if (!f.isSuccess()) {
					start = false;
				}
			}

		};
		mServerBootstrap.bind(7777);
	}

	public synchronized void stopServer() {
		if (start == false) {
			return;
		}
		start = false;
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
	}
}
