package org.sjimenez.chatapp;

import model.User;
import org.apache.ibatis.type.MappedTypes;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MappedTypes(User.class)
@MapperScan("org.sjimenez.chatapp.mappers")
@SpringBootApplication(scanBasePackages = { "org.sjimenez.chatapp" })
public class ChatappApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatappApplication.class, args);
	}
}
