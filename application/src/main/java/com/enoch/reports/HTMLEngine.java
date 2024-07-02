package com.enoch.reports;

import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.google.common.base.Function;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@Component
public class HTMLEngine {
	
	public SpringTemplateEngine getTemplateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		ObjectClassLoaderResolver templateResolver = new ObjectClassLoaderResolver();
		templateResolver.setPrefix("templates/");
		templateEngine.addDialect(new LayoutDialect());
		templateResolver.setCacheable(false);
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");
		
		templateResolver.setCharacterEncoding("UTF-8");
		templateEngine.setTemplateResolver(templateResolver);
		return templateEngine;
	}
	
	
	public String process(Function<Context,String> handler) {
		Context ctx = new Context();
		String view = handler.apply(ctx);
		return getTemplateEngine().process(view, ctx);
	}
	
}
