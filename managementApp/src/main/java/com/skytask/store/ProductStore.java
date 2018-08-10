package com.skytask.store;

import com.skytask.common.Product;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public class ProductStore {

    private List<Product> products;
    private boolean flag = false;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public Future<List<Product>> getProducts() {
        return executor.submit(() -> {
            while (!flag) {
                Thread.sleep(1);
            }
            flag = false;
            return products;
        });
    }

    public void setProducts(List<Product> products) throws InterruptedException {
        while (flag) {
            Thread.sleep(1);
        }
        this.products = products;
        flag = true;
    }
}
