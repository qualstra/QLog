package com.enoch.reports;

import java.util.Map;

import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresource.ClassLoaderTemplateResource;
import org.thymeleaf.templateresource.ITemplateResource;

import com.enoch.ApplicationContext;

public class ObjectClassLoaderResolver extends ClassLoaderTemplateResolver {
	private final ClassLoader classLoader;

	public ObjectClassLoaderResolver() {
		this(null);
	}

	public ObjectClassLoaderResolver(final ClassLoader classLoader) {
		super();
		// Class Loader might be null if we want to apply the default one
		this.classLoader = classLoader;
	}

	@Override
	protected ITemplateResource computeTemplateResource(final IEngineConfiguration configuration,
			final String ownerTemplate, final String template, final String resourceName,
			final String characterEncoding, final Map<String, Object> templateResolutionAttributes) {
		if (template.indexOf('.') > 0) {

			String companyTemplate = "templates/"+template.replace("deflt",
					"test");//ApplicationContext.getContext().getCompany().getUUID().toString());

			ClassLoaderTemplateResource resource = new ClassLoaderTemplateResource(this.classLoader,
					companyTemplate + ".html", characterEncoding);
			if (resource.exists()) {
				return resource;
			}
			resource = new ClassLoaderTemplateResource(this.classLoader,
					companyTemplate.substring(0, companyTemplate.lastIndexOf('.')) + ".html", characterEncoding);
			if (resource.exists()) {
				return resource;
			}

			resource = new ClassLoaderTemplateResource(this.classLoader, "templates/" + template + ".html", characterEncoding);
			if (resource.exists()) {
				return resource;
			}
			resource = new ClassLoaderTemplateResource(this.classLoader,
					"templates/"+template.substring(0, template.lastIndexOf('.')) + ".html", characterEncoding);
			if (resource.exists()) {
				return resource;
			}
		}
		return new ClassLoaderTemplateResource(this.classLoader, resourceName, characterEncoding);
	}
}
