package com.example.phoneducks;

import com.example.phoneducks.model.Channel;
import com.example.phoneducks.repository.ChannelRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PhoneDucksApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhoneDucksApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(ChannelRepository repository) {
        return args -> {
            repository.save(new Channel(1l, "test Channel"));
        };
    }

}
