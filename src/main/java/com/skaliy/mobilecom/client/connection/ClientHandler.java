package com.skaliy.mobilecom.client.connection;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;

import java.util.ArrayList;

public class ClientHandler extends ChannelInboundMessageHandlerAdapter<String> {

    static int resultSize = 0;
    static ArrayList<String[]> queryResult = new ArrayList<String[]>();
    static boolean isFullResult = false;

    @Override
    public void messageReceived(ChannelHandlerContext channelHandlerContext, String message) throws Exception {
        message = message.replaceAll("_p_", "\n");

        if (message.startsWith("[SERVER] - accepted the query: ")) {
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

        for (int i = 0; i < record.length; i++) {
            record[i] = record[i].replaceAll("_c_", ", ");

            if (record[i].contains("_bo_")) {
                record[i] = record[i]
                        .replaceAll("_bo_", "\uD83D\uDF84 ")
                        .replaceAll("_bc_", "")
                        .replaceAll(",", "\n\uD83D\uDF84 ");
            }
        }

        queryResult.add(record);

        if (queryResult.size() == resultSize) {
            isFullResult = true;
        }
    }

}