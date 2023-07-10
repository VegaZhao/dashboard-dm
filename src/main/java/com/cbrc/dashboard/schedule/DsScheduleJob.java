package com.cbrc.dashboard.schedule;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.cbrc.dashboard.schedule
 * @author: Herry
 * @Date: 2020/10/30 11:08
 * @Description: TODO
 */
@Component
public class DsScheduleJob {

    private int count = 0;

    /**
     * @Author Smith
     * @Description 设置没6秒执行一次
     * @Date 14:23 2019/1/24
     * @Param
     * @return void
     **/
    //@Async
    //@Scheduled(cron = "*/6 * * * * ?")
    public  void process()  throws InterruptedException{
        System.out.println("第一个定时任务开始 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
        System.out.println("this is scheduler task1 running " + (count++));
        Thread.sleep(1000 * 10);
    }

    //@Async
   // @Scheduled(cron = "*/7 * * * * ?")
    public  void process2()  throws InterruptedException{
        System.out.println("第二个定时任务开始 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
        System.out.println("this is scheduler task2 running " + (count++));
        Thread.sleep(1000 * 10);
    }
}
