package com.example.incomingphone.accessibility.uploadhandler;

import com.walhalla.ui.DLog;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class CustomTimer {

    private final ScheduledExecutorService scheduler;
    private ScheduledFuture<?> tickFuture;
    private ScheduledFuture<?> timeoutFuture;
    private boolean isRunning;
    private final long tickInterval = 2; // Тик каждую секунду
    private final long timeoutMinutes = 10; // Время жизни таймера в минутах

    public CustomTimer() {
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.isRunning = false;
    }

    // Запуск таймера
    public void start(Runnable bbb) {
        if (isRunning) {
            DLog.d("Timer is already running.");
            return;
        }
        isRunning = true;
        DLog.d("Timer started.");

        // Запланировать тики каждую секунду
        tickFuture = scheduler.scheduleAtFixedRate(bbb, 0, tickInterval, TimeUnit.SECONDS);

        // Запланировать автоматическую остановку таймера через заданное время
        timeoutFuture = scheduler.schedule(() -> {
            stop();
        }, timeoutMinutes, TimeUnit.MINUTES);
    }

    // Остановка таймера
    public void stop() {
        if (!isRunning) {
            DLog.d("Timer is not running.");
            return;
        }
        isRunning = false;
        if (tickFuture != null) {
            tickFuture.cancel(true);
        }
        if (timeoutFuture != null) {
            timeoutFuture.cancel(true);
        }
        scheduler.shutdown();
        DLog.d("Timer stopped.");
    }

    // Проверка статуса таймера
    public boolean isRunning() {
        return isRunning;
    }

//    public static void main(String[] args) throws InterruptedException {
//        CustomTimer customTimer = new CustomTimer();
//
//        // Запуск таймера
//        customTimer.start();
//        // Ждем 5 минут, чтобы увидеть работу таймера
//        Thread.sleep(5 * 60 * 1000);
//        // Остановка таймера
//        customTimer.stop();
//    }
}
