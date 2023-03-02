package com.example.opentalk;

import com.example.opentalk.listeners.PingListener;
import lombok.AllArgsConstructor;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.javacord.api.DiscordApi;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@AllArgsConstructor
public class OpenTalkApplication {
    private final Environment env;
    private final PingListener pingListener;

    public static void main(String[] args) {

        SpringApplication.run(OpenTalkApplication.class, args);
    }

    @Bean
    @ConfigurationProperties("discord-api")
    public DiscordApi discordApi() {
        String token = env.getProperty("token");
        DiscordApi discordApi = new DiscordApiBuilder().setToken(token)
                .setAllNonPrivilegedIntents().login().join();
        discordApi.addMessageCreateListener(pingListener);
        return discordApi;
    }
}
