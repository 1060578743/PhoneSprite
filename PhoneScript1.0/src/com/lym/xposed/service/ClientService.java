package com.lym.xposed.service;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.google.gson.Gson;
import com.lym.xposed.Const;
import com.lym.xposed.model.Message;
import com.lym.xposed.utils.LogUtil;
import com.lym.xposed.utils.UUIDS;

public class ClientService extends Service {
	public class ClientHandler extends SimpleChannelInboundHandler<String> {
		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			super.channelActive(ctx);
			mChannel = ctx.channel();
			Message message = new Message();
			message.setType(Message.CONNECT);
			message.setContent(UUIDS.getUUID());
			Gson gson = new Gson();
			String sender = gson.toJson(message) + "\n";
			ctx.channel().writeAndFlush(sender);
			LogUtil.log(sender);
		}

		@Override
		public void channelInactive(ChannelHandlerContext ctx) throws Exception {
			// 服务端接收到客户端掉线通知
			LogUtil.log("inactive");
			if (mEventLoopGroup == null) {
				return;
			}
			LogUtil.log("重连");
			// 3秒后重新连接
			mEventLoopGroup.schedule(new Runnable() {
				@Override
				public void run() {
					doConnect();
				}
			}, 3, TimeUnit.SECONDS);
		}

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, String msg)
				throws Exception {
			Intent intent = new Intent();
			intent.setAction(ACTION_RECEIVE_MESSAGE);
			intent.putExtra("msg", msg);
			sendBroadcast(intent);
			LogUtil.log("receive:" + msg);
		}

	}

	public static final String ACTION_RECEIVE_MESSAGE = "com.lym.xpose.service.ClientService.receive";
	public static final String ACTION_SEND_MESSAGE = "com.lym.xpose.service.ClientService.send";

	Bootstrap mBootstrap;
	BroadcastReceiver mBroadcastReceiver;
	Channel mChannel;
	ChannelFutureListener mChannelFutureListener;
	EventLoopGroup mEventLoopGroup;

	public void doConnect() {
		ChannelFuture future = mBootstrap.connect(Const.TCP_HOST,
				Const.TCP_PORT);
		future.addListener(mChannelFutureListener);
	}

	public void initClient() {
		mEventLoopGroup = new NioEventLoopGroup();
		mBootstrap = new Bootstrap();
		mBootstrap.group(mEventLoopGroup);
		mBootstrap.channel(NioSocketChannel.class);
		mBootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000);
		mBootstrap.option(ChannelOption.SO_KEEPALIVE, true);
		mBootstrap.option(ChannelOption.TCP_NODELAY, true);
		mBootstrap.option(ChannelOption.SO_TIMEOUT, 5000);
		mBootstrap.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192,
						Delimiters.lineDelimiter()));
				pipeline.addLast("decoder", new StringDecoder());
				pipeline.addLast("encoder", new StringEncoder());
				pipeline.addLast("handler", new ClientHandler());
				pipeline.addLast("ping", new IdleStateHandler(10, 5, 10) {
					@Override
					protected void channelIdle(ChannelHandlerContext ctx,
							IdleStateEvent evt) throws Exception {
						super.channelIdle(ctx, evt);
						if (((IdleStateEvent) evt).state().equals(
								IdleState.READER_IDLE)) {
							ctx.close();
						} else if (((IdleStateEvent) evt).state().equals(
								IdleState.WRITER_IDLE)) {
							// 发送心跳
							LogUtil.log("发送心跳");
							ctx.writeAndFlush("{}\n");
						}
					}
				});
			}
		});
		mChannelFutureListener = new ChannelFutureListener() {
			public void operationComplete(ChannelFuture f) throws Exception {
				if (f.isSuccess()) {
					// 连接成功
					return;
				}
				if (mEventLoopGroup == null) {
					return;
				}
				LogUtil.log("重连");
				// 3秒后重新连接
				mEventLoopGroup.schedule(new Runnable() {
					@Override
					public void run() {
						doConnect();
					}
				}, 3, TimeUnit.SECONDS);
			}
		};
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		LogUtil.log("client onCreate");
		// 注册广播接收器
		mBroadcastReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				String msg = intent.getStringExtra("msg");
				// 发送消息
				sendMessage(msg);
			}
		};
		registerReceiver(mBroadcastReceiver, new IntentFilter(
				ACTION_SEND_MESSAGE));
		// 启动服务器
		this.run();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		LogUtil.log("client destroy");
		unregisterReceiver(mBroadcastReceiver);
		mChannel.close();
		mEventLoopGroup.shutdownGracefully();
		mEventLoopGroup = null;
	}

	public void run() {
		initClient();
		doConnect();
	}

	public void sendMessage(String msg) {
		mChannel.writeAndFlush(msg + "\n");
	}

}
