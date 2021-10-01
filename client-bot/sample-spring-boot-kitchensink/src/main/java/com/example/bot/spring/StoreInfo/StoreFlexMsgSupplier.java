/*
 * Copyright 2018 LINE Corporation
 *
 * LINE Corporation licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.example.bot.spring.StoreInfo;

import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.flex.component.Box;
import com.linecorp.bot.model.message.flex.component.Button;
import com.linecorp.bot.model.message.flex.component.Button.ButtonHeight;
import com.linecorp.bot.model.message.flex.component.Button.ButtonStyle;
import com.linecorp.bot.model.message.flex.component.Icon;
import com.linecorp.bot.model.message.flex.component.Image;
import com.linecorp.bot.model.message.flex.component.Image.ImageAspectMode;
import com.linecorp.bot.model.message.flex.component.Image.ImageAspectRatio;
import com.linecorp.bot.model.message.flex.component.Image.ImageSize;
import com.linecorp.bot.model.message.flex.component.Separator;
import com.linecorp.bot.model.message.flex.component.Spacer;
import com.linecorp.bot.model.message.flex.component.Text;
import com.linecorp.bot.model.message.flex.component.Text.TextWeight;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.unit.FlexFontSize;
import com.linecorp.bot.model.message.flex.unit.FlexLayout;
import com.linecorp.bot.model.message.flex.unit.FlexMarginSize;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.function.Supplier;

import static java.util.Arrays.asList;

public class StoreFlexMsgSupplier implements Supplier<FlexMessage> {
    private Store store;

    public StoreFlexMsgSupplier(Store store){
        super();
        this.store = store;
    }

    @Override
    public FlexMessage get() {
        return new FlexMessage("storeBubble", getBubble());
    }

    public Bubble getBubble(){
        final Image heroBlock =
                Image.builder()
                        .url(createUri(store.photoURI))
                        .size(ImageSize.FULL_WIDTH)
                        .aspectRatio(ImageAspectRatio.R20TO13)
                        .aspectMode(ImageAspectMode.Cover)
                        .action(new URIAction("label", URI.create(store.hotspot), null))
                        .build();

        final Box bodyBlock = createBodyBlock();
        final Box footerBlock = createFooterBlock();
        final Bubble bubble =
                Bubble.builder()
                        .hero(heroBlock)
                        .body(bodyBlock)
                        .footer(footerBlock)
                        .build();
        return  bubble;
    }



    private Box createFooterBlock() {
        final Spacer spacer = Spacer.builder().size(FlexMarginSize.SM).build();
        final Button callAction = Button
                .builder()
                .style(ButtonStyle.LINK)
                .height(ButtonHeight.SMALL)
                .action(new URIAction("聯絡店家", URI.create("tel:"+store.phoneNumber), null))
                .build();
        final Separator separator = Separator.builder().build();
        final Button hotspotAction =
                Button.builder()
                        .style(ButtonStyle.LINK)
                        .height(ButtonHeight.SMALL)
                        .action(new URIAction("LINE熱點", URI.create(store.hotspot), null))
                        .build();

        final Button maskRationingAction =
                Button.builder()
                        .style(ButtonStyle.LINK)
                        .height(ButtonHeight.SMALL)
                        .action(new URIAction("1922傳送實名制簡訊", URI.create("https://liff.line.me/1656468166-drpQ2Obx?smsNo="+store.sms1922), null))
                        .build();
        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .spacing(FlexMarginSize.SM)
                .contents(asList(spacer, callAction, separator, hotspotAction,separator,maskRationingAction))
                .build();
    }

    private Box createBodyBlock() {
        final Text title =
                Text.builder()
                        .text(store.storeName)
                        .weight(TextWeight.BOLD)
                        .size(FlexFontSize.XL)
                        .build();

        final Box review = createReviewBox();

        final Box info = createInfoBox();

        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .contents(asList(title, review, info))
                .build();
    }

    private Box createInfoBox() {
        final Box place = Box
                .builder()
                .layout(FlexLayout.BASELINE)
                .spacing(FlexMarginSize.SM)
                .contents(asList(
                        Text.builder()
                                .text("地點")
                                .color("#aaaaaa")
                                .size(FlexFontSize.SM)
                                .flex(1)
                                .build(),
                        Text.builder()
                                .text(store.address)
                                .wrap(true)
                                .color("#666666")
                                .size(FlexFontSize.SM)
                                .flex(5)
                                .build()
                ))
                .build();
        final Box time =
                Box.builder()
                        .layout(FlexLayout.BASELINE)
                        .spacing(FlexMarginSize.SM)
                        .contents(asList(
                                Text.builder()
                                        .text("Time")
                                        .color("#aaaaaa")
                                        .size(FlexFontSize.SM)
                                        .flex(1)
                                        .build(),
                                Text.builder()
                                        .text(store.workTime)
                                        .wrap(true)
                                        .color("#666666")
                                        .size(FlexFontSize.SM)
                                        .flex(5)
                                        .build()
                        ))
                        .build();

        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .margin(FlexMarginSize.LG)
                .spacing(FlexMarginSize.SM)
                .contents(asList(place, time))
                .build();
    }

    private Box createReviewBox() {
        final Icon goldStar =
                Icon.builder().size(FlexFontSize.SM).url(URI.create("https://scdn.line-apps.com/n/channel_devcenter/img/fx/review_gold_star_28.png")).build();
        final Icon grayStar =
                Icon.builder().size(FlexFontSize.SM).url(URI.create("https://scdn.line-apps.com/n/channel_devcenter/img/fx/review_gray_star_28.png")).build();
        final Text point =
                Text.builder()
                        .text(store.star.toString())
                        .size(FlexFontSize.SM)
                        .color("#999999")
                        .margin(FlexMarginSize.MD)
                        .flex(0)
                        .build();

        Icon[] starPoint = {grayStar, grayStar, grayStar, grayStar, grayStar};
        for(int i=0;i < store.star;i++){
            starPoint[i]= goldStar;
        }
        return Box.builder()
                .layout(FlexLayout.BASELINE)
                .margin(FlexMarginSize.MD)
                .contents(asList(starPoint[0],starPoint[1],starPoint[2],starPoint[3],starPoint[4], point))
                .build();
    }
    private URI createUri(String path) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .scheme("https")
                .path(path).build()
                .toUri();
    }
}
