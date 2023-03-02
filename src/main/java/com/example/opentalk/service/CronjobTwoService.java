package com.example.opentalk.service;

import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledFuture;


@Service
public class CronjobTwoService {
    @Autowired
    private TaskScheduler taskScheduler;


    private ScheduledFuture<?> scheduledFuture;


    public void startTask(String time, String content, MessageCreateEvent messageCreateEvent) {
        stopTask();
        messageCreateEvent.getChannel().sendMessage("Done to start cronjob 2 with time: " + time + " :)");
        scheduledFuture = taskScheduler.schedule(() -> {
            messageCreateEvent.getChannel().sendMessage("Now is time to " + content + " :)");
        }, new CronTrigger(time));
    }

    public void stopTask() {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }
    }

    public boolean status(){
        if(scheduledFuture == null){
            return false;
        }
        Boolean bool = scheduledFuture.isCancelled();
        return bool ? bool : !bool;
    }
}
