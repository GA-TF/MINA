package client;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MyClient {
	public static void main(String args[]) {
		// create tcp/ip connector
		IoConnector connector = new NioSocketConnector();
		// �����������ݵĹ�����
		DefaultIoFilterChainBuilder chain = connector.getFilterChain();
		// �趨���������һ��һ��(/r/n)�Ķ�ȡ����
		chain.addLast("myChin", new ProtocolCodecFilter(
				new TextLineCodecFactory()));
		// ��������
		// connector.getFilterChain().addLast("codec", new
		// ProtocolCodecFilter(new
		// TextLineCodecFactory(Charset.forName("UTF-8"),LineDelimiter.WINDOWS.getValue(),LineDelimiter.WINDOWS.getValue())));
		// �趨�������˵���Ϣ��������new һ��clinethandler����
		connector.setHandler(new SamplMinaClientHandler());
		// set connect timeout
		connector.setConnectTimeoutMillis(30000);
		// ����connector.setConnectTimeout(30);
		// ���ӵ�������
		ConnectFuture cf = connector.connect(new InetSocketAddress("localhost",
				9988));
		// wait for the connection attem to be finished
		cf.awaitUninterruptibly();
		cf.getSession().getCloseFuture().awaitUninterruptibly();
		connector.dispose();
	}
}
