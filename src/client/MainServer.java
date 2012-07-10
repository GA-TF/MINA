package client;   
  
import java.io.IOException;   
import java.net.InetSocketAddress;   
  
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;   
import org.apache.mina.filter.codec.ProtocolCodecFilter;   
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;   
import org.apache.mina.transport.socket.SocketAcceptor;   
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;   

  
public class MainServer {   
    public static void main(String args[]) throws IOException{   
        //����һ����������Server��socket ��NIO   
        SocketAcceptor acceptor = new NioSocketAcceptor();   
        
        //�����������ݵĹ�����   
        DefaultIoFilterChainBuilder chain= acceptor.getFilterChain();   
        //�趨���������һ��һ�е�(/r/n)�Ķ�ȡ����   
        chain.addLast("myChin", new ProtocolCodecFilter(new TextLineCodecFactory()));   
        //�趨�������˵���Ϣ������ new һ������   
        acceptor.setHandler(new SamplMinaServerHandler());   
        //�������󶨵Ķ˿�   
        int bindport=9988;   
        //�󶨶˿ڣ�����������   
        acceptor.bind(new InetSocketAddress(bindport));   
        System.out.println("start ok,listen on:="+bindport);   
           
    }   
  
}  
