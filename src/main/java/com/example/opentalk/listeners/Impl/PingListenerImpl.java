package com.example.opentalk.listeners.Impl;


import com.example.opentalk.listeners.PingListener;
import com.example.opentalk.service.CronjobService;
import com.example.opentalk.service.CronjobThreeService;
import com.example.opentalk.service.CronjobTwoService;
import lombok.AllArgsConstructor;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;



@Service
@AllArgsConstructor
public class PingListenerImpl implements PingListener {

    private final CronjobService cronjob;
    private final CronjobTwoService cronjob2;
    private final CronjobThreeService cronjob3;

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {
        if(messageCreateEvent.getMessageContent().equals("!Hello")){
            messageCreateEvent.getChannel().sendMessage("Hi bro! How can I assist you today :)");
        }else if(messageCreateEvent.getMessageContent().startsWith("!Schedule")){
            String[] content = messageCreateEvent.getMessageContent().split("-");
            try{
                if(content[1].equals("stop")){
                    if(content[2].equals("1")){
                        cronjob.stopTask();
                        messageCreateEvent.getChannel().sendMessage("Stoped schedule 1 :)");
                    }else if(content[2].equals("2")){
                        cronjob2.stopTask();
                        messageCreateEvent.getChannel().sendMessage("Stoped schedule 2 :)");
                    }else if(content[2].equals("3")){
                        cronjob3.stopTask();
                        messageCreateEvent.getChannel().sendMessage("Stoped schedule 3 :)");
                    } else {
                        messageCreateEvent.getChannel().sendMessage("Chưa có loại schedule này :)");
                    }
                }else if(content[1].equals("status")){
                    messageCreateEvent.getChannel()
                            .sendMessage("Schedule 1 running: " + cronjob.status() +
                                    "\nSchedule 2 running: " + cronjob2.status() +
                                    "\nSchedule 3 running: " + cronjob3.status());
                }else if(content[1].equals("help")) {

                }else{
                    List<Boolean> bool = Arrays.asList(cronjob.status(), cronjob2.status(), cronjob3.status());
                    Boolean bool2 =  bool.stream().filter(b ->  !b).findFirst().get();
                    int index = bool.indexOf(bool2);
                    if(index == 0){
                        cronjob.startTask(content[1], content[2], messageCreateEvent);
                        bool.set(index, true);
                    }else if(index == 1){
                        cronjob2.startTask(content[1], content[2], messageCreateEvent);
                        bool.set(index, true);
                    }else{
                        cronjob3.startTask(content[1], content[2], messageCreateEvent);
                        bool.set(index, true);
                    }
                }
            }catch (Exception e){
                System.out.println(e);
                messageCreateEvent.getChannel().sendMessage("Something wrong :(");
            }
        }
    }
}
