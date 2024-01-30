package tech.marechal;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;


public class ContextLogger implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger LOG = LoggerFactory.getLogger(ContextLogger.class);

	public void handleContextRefreshed(final ContextRefreshedEvent event) {
		event.getApplicationContext().getEnvironment();
		final ConfigurableEnvironment environment = (ConfigurableEnvironment) event.getApplicationContext().getEnvironment();
		final Iterator<PropertySource<?>> it = environment.getPropertySources().iterator();
		while (it.hasNext()) {
			final PropertySource<?> ps = it.next();
			LOG.info(StringFormatUtils.titleInFrame(ps.getName(), true));
			LOG.info("{}", ps.getSource().getClass());
			if (ps.getSource() instanceof Map) {
				final Map<Object, Object> map = (Map<Object, Object>) ps.getSource();
				LOG.info(StringFormatUtils.printMap(map, true));
			} else if (ps.getSource() instanceof Properties) {
				final Properties properties = (Properties) ps.getSource();
				LOG.info(StringFormatUtils.printProperties(properties, true));
			}
		}
	}

	@Override
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		this.handleContextRefreshed(event);
	}
}
