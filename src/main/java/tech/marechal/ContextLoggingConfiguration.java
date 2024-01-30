package tech.marechal;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class ContextLoggingConfiguration {

	@Bean
	public ContextLogger customListener() {
		return new ContextLogger();
	}
}
