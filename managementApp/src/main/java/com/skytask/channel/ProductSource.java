package com.skytask.channel;

import com.skytask.common.Channels;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface ProductSource {

    @Output(Channels.CREATE_PRODUCT)
    MessageChannel createProduct();

    @Output(Channels.REQUEST_PRODUCT_LIST)
    MessageChannel getProductList();

    @Input(Channels.RESPONSE_PRODUCT_LIST)
    SubscribableChannel hearProductList();
}
