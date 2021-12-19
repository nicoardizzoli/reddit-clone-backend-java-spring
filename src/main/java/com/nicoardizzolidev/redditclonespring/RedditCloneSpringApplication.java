package com.nicoardizzolidev.redditclonespring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication

//activamos Async ya que en el proceso de login/activacion del usuario, al mandarse un mail y todo, tarda aprox 11 segundos (mucho tiempo) asi que lo que hay que hacer
//es mandar a guardar el usuario, y el mail enviarlo de forma async asi queda ejecutandose.
@EnableAsync
public class RedditCloneSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedditCloneSpringApplication.class, args);
	}

}
