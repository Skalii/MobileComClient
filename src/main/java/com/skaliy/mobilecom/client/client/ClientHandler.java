package com.skaliy.mobilecom.client.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class ClientHandler extends ChannelInboundMessageHandlerAdapter<String> {

    static int resultSize = 0;
    static ArrayList<String[]> queryResult = new ArrayList<String[]>();
    static boolean isFullResult = false;
    static boolean toggleB = false;
    static String toggleS = "";

    @Override
    public void messageReceived(ChannelHandlerContext channelHandlerContext, String message) throws Exception {
        if (message.endsWith("get_news")) {
            toggleB = true;
            toggleS = "news";
            return;
        } else if (message.endsWith("get_tariffs")) {
            toggleB = true;
            toggleS = "tariffs";
            return;
        }

        if (message.startsWith("[SERVER] - accepter the query: ")) {
            return;
        }

        if (message.startsWith("[SERVER] - query state: ")) {
            resultSize = 1;
            return;
        }

        if (message.startsWith("[SERVER] - result size: ")) {
            resultSize = Integer.parseInt(message
                    .substring(message.lastIndexOf(" ") + 1));
            return;
        }

        String[] record = message
                .substring(1, message.length() - 1)
                .split(", ");

        if (toggleB) {
            if (Objects.equals(toggleS, "news")) {
                for (int i = 3; i < record.length; i++) {
                    record[2] = record[2].concat(", ".concat(record[i]));
                }
            } else if (Objects.equals(toggleS, "tariffs")) {
                for (int i = 4; i < record.length; i++) {
                    if (i != record.length - 1)
                        record[3] = record[3].concat(", ".concat(record[i]));
                }
            }
        }

        queryResult.add(record);

        if (queryResult.size() == resultSize) {
            isFullResult = true;
        }
    }

}