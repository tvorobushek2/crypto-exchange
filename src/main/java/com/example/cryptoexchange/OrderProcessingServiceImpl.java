package com.example.cryptoexchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OrderProcessingServiceImpl implements OrderProcessingService {

    private static final Logger logger = LoggerFactory.getLogger(OrderProcessingServiceImpl.class);
    private final ExecutorService executorService;

    public OrderProcessingServiceImpl() {
        AtomicInteger threadNumber = new AtomicInteger(1);
        ThreadFactory threadFactory = runnable -> {
            Thread thread = new Thread(runnable);
            thread.setName("Crypto-Processor-Thread-" + threadNumber.getAndIncrement());
            thread.setDaemon(false);
            return thread;
        };
        this.executorService = Executors.newFixedThreadPool(3, threadFactory);
    }

    @Override
    public void processOrders(List<Order> orders) {
        for (Order order : orders) {
            executorService.submit(() -> {
                String threadName = Thread.currentThread().getName();
                logger.info("Поток {} начал обработку заявки ID: {}", threadName, order.getId());
                
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    logger.error("Поток {} был прерван", threadName);
                }
                
                order.setStatus(OrderStatus.FILLED);
                logger.info("Поток {} завершил обработку заявки ID: {}, статус: {}", threadName, order.getId(), order.getStatus());
            });
        }
        
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}