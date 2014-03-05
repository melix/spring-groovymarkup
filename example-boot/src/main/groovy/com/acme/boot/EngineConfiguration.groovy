package com.acme.boot

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.ViewResolver
import org.springframework.web.servlet.groovy.LocaleAwareEngineFactory
import org.springframework.web.servlet.groovy.MarkupTemplateViewResolver
import groovy.text.markup.TemplateConfiguration

@Configuration
class EngineConfiguration {

    @Bean
    public TemplateConfiguration templateConfiguration() {
        new TemplateConfiguration(locale:Locale.ENGLISH)
    }

    @Bean
    public LocaleAwareEngineFactory templateEngineFactory() {
	    new LocaleAwareEngineFactory(parent: this.class.classLoader, configuration: templateConfiguration())
    }

    @Bean
    public ViewResolver myViewResolver(){
        def viewResolver = new MarkupTemplateViewResolver()
        viewResolver.factory = templateEngineFactory()
        viewResolver.order = 0
        viewResolver.viewModels['views/typechecked.groovy'] = [title:'String']

        viewResolver
    }
}
