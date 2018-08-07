package com.skytask.channel;

import com.skytask.common.Channels;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface ProductSource {

    @Input(Channels.CREATE_PRODUCT)
    SubscribableChannel createProduct();

    @Input(Channels.REQUEST_PRODUCT_LIST)
    SubscribableChannel getProductList();

    @Output(Channels.RESPONSE_PRODUCT_LIST)
    MessageChannel hearProductList();
}
