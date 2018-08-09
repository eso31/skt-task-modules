package com.skytask.listener;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.skytask.store.ProductStore;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Collections;

@RunWith(JUnit4ClassRunner.class)
public class ListenerTest {

    private ProductStore productStore;

    private Listener listener;

    @Before
    public void setUp() {
        productStore = EasyMock.createMock(ProductStore.class);
        listener = new Listener(productStore);
    }

    @Test
    public void testupdateProductListValid() throws IOException {
        productStore.setProducts(Collections.emptyList());
        EasyMock.expectLastCall();
        EasyMock.replay(productStore);

        listener.updateProductList("[]");

        EasyMock.verify(productStore);
    }

    @Test(expected = JsonMappingException.class)
    public void testupdateProductListInvalid() throws IOException {
        productStore.setProducts(Collections.emptyList());
        EasyMock.expectLastCall();
        EasyMock.replay(productStore);

        listener.updateProductList("");

        EasyMock.verify(productStore);
    }
}