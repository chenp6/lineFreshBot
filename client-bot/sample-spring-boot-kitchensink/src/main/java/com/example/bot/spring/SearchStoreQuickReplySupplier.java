package com.example.bot.spring;

import com.linecorp.bot.model.action.CameraAction;
import com.linecorp.bot.model.action.CameraRollAction;
import com.linecorp.bot.model.action.LocationAction;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.quickreply.QuickReply;
import com.linecorp.bot.model.message.quickreply.QuickReplyItem;
import com.linecorp.bot.model.message.sender.Sender;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;



public class SearchStoreQuickReplySupplier implements Supplier<Message> {


    @Override
    public Message get() {

        final List<QuickReplyItem> items = Arrays.<QuickReplyItem>asList(
                QuickReplyItem.builder()
                        .action(new MessageAction("找美食", "找美食"))
                        .build(),
                QuickReplyItem.builder()
                        .action(new MessageAction("找美食", "找美食"))
                        .build(),
                QuickReplyItem.builder()
                        .action(new MessageAction("找美食", "找美食"))
                        .build(),
                QuickReplyItem.builder()
                        .action(new MessageAction("找美食", "找美食"))
                        .build(),
                QuickReplyItem.builder()
                        .action(new MessageAction("找美食", "找美食"))
                        .build()
        );

        final QuickReply quickReply = QuickReply.items(items);

        return TextMessage
                .builder()
                .text("請選擇店家【類別】")
                .quickReply(quickReply)
                .sender(Sender.builder()
                        .name("鐵花小嚮導")
                        .build())
                .build();
    }


}
