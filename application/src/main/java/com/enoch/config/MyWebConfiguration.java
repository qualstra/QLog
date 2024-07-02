package com.enoch.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.enoch.reports.CASReport;
import com.enoch.reports.Report;
import com.enoch.reports.risq.RISQReport;

@Configuration
@EnableHypermediaSupport(type = HypermediaType.COLLECTION_JSON)
public class MyWebConfiguration implements WebMvcConfigurer {

	@Autowired
	ApplicationProperties aProp;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("file:///" + aProp.getResourceFolder());
	}

	@Bean
	public RepositoryRestConfigurer repositoryRestConfigurer() {

		return new RepositoryRestConfigurerAdapter() {

			@Override
			public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
				config.setBasePath("rs");// getMetadataConfiguration().setAlpsEnabled(true);
			}
		};
	}
	
	@Bean(name = "applicationEventMulticaster")
    public ApplicationEventMulticaster simpleApplicationEventMulticaster() {
        SimpleApplicationEventMulticaster eventMulticaster =
          new SimpleApplicationEventMulticaster();
        
        eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return eventMulticaster;
    }
	
	@Bean(name = "reportMap")
    public Map<String, ? super Report> processorMap(org.springframework.context.ApplicationContext context) {
	    Map<String, ? super Report > map = new HashMap<>();
	    map.put("CAS" ,context.getBean(CASReport.class));
	    map.put("VIR" ,context.getBean(RISQReport.class));
	    
	    return map;
	}
	
}

