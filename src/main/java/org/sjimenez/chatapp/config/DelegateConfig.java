package org.sjimenez.chatapp.config;

import org.sjimenez.chatapp.delegate.GroupDelegate;
import org.sjimenez.chatapp.delegate.MessageDelegate;
import org.sjimenez.chatapp.delegate.UserDelegate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class DelegateConfig {

	@Bean
	@Scope("prototype")
	public GroupDelegate groupDelegate()
	{
		return new GroupDelegate();
	}
	
	@Bean
	@Scope("prototype")
	public UserDelegate userDelegate()
	{
		return new UserDelegate();
	}
	
	@Bean
	@Scope("prototype")
	public MessageDelegate messageDelegate()
	{
		return new MessageDelegate();
	}
}
