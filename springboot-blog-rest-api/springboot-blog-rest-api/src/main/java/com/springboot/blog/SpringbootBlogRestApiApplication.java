package com.springboot.blog;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.springboot.blog.entity.Role;
import com.springboot.blog.repository.RoleRepository;

@SpringBootApplication
public class SpringbootBlogRestApiApplication /*implements CommandLineRunner*/{
    @Autowired
	private RoleRepository repository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
	}
	
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}


//	@Override
//	public void run(String... args) throws Exception {
//		Role adminRole=new Role();
//		adminRole.setName("ROLE_ADMIN");
//		repository.save(adminRole);
//		
//		Role userRole=new Role();
//		userRole.setName("ROLE_USER");
//		repository.save(userRole);
//	}
//	
//	FunctionalInterface instance = Consumer.class.getDeclaredAnnotation(FunctionalInterface.class);
//	Class<?> clazz = instance.getClass();

//	boolean isProxyClass = Proxy.isProxyClass(clazz);
//	assertTrue(isProxyClass);

}
