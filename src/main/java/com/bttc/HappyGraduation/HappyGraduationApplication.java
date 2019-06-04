package com.bttc.HappyGraduation;

import com.bttc.HappyGraduation.utils.base.SimpleBaseRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages={"com.bttc.HappyGraduation"})
@EnableCaching
@EnableJpaRepositories(basePackages = {"com.bttc.HappyGraduation"},repositoryBaseClass = SimpleBaseRepository.class)
@EntityScan(basePackages={"com.bttc.HappyGraduation"})
public class HappyGraduationApplication {

	public static void main(String[] args) {
		SpringApplication.run(HappyGraduationApplication.class, args);
	}
}
