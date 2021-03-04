package com.zjk.item.WebSocket;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

/**
 * @author : 张江坤
 * @description:websocket服务端
 * @date :2020/4/15 9:55
 */
@ServerEndpoint(value = "webSocketTest")
@Component
public class WebSocket {
    private static int onlineCount = 0;

    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<WebSocket>();

    private Session session;

    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSockets.add(this);
        addOnlineCount();
        System.out.println("有新链接加入，当前在线人数：");
        try {
            sendMessage("给客户端返回消息");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    private void onMessage(String message) {
        System.out.println("给客户端发消息:"+message);
        for(WebSocket socket : webSockets){
            try {
                socket.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //发消息给客户端
    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    @OnClose
    public void onClose(Session session){
        webSockets.remove(this);
        rmvOnlineCount();
        System.out.println("有一个客户端断开链接");
    }


    private void rmvOnlineCount() {
        onlineCount--;
    }


    private void addOnlineCount() {
        onlineCount++;
    }

    public int getOnlineCount(){
        return onlineCount;
    }

    public static void  main(String args[]){
        int size = 1000000;

        BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size, 0.01);
        for (int i = 0; i < size; i++) {
            bloomFilter.put(i);
        }
        List<Integer> list = new ArrayList<Integer>(1000);
        // 故意取10000个不在过滤器里的值，看看有多少个会被认为在过滤器里
        for (int i = size + 10000; i < size + 20000; i++) {
            if (bloomFilter.mightContain(i)) {
                list.add(i);
            }
        }
        System.out.println("误判的数量：" + list.size());
    }


    }


