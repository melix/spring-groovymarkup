package com.acme.boot

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.ViewResolver
import org.springframework.web.servlet.groovy.MarkupTemplateViewResolver
import groovy.text.markup.MarkupTemplateEngine
import groovy.text.markup.TemplateConfiguration

@Configuration
class EngineConfiguration {

    @Bean
    public TemplateConfiguration templateConfiguration() {
        new TemplateConfiguration()
    }

    @Bean
    public MarkupTemplateEngine templateEngine() {
	    new MarkupTemplateEngine(this.class.classLoader, templateConfiguration())
    }

    @Bean
    public ViewResolver myViewResolver(){
        def viewResolver = new MarkupTemplateViewResolver()
        viewResolver.engine = templateEngine()
        viewResolver.order = 0
        viewResolver.viewModels['typechecked.groovy'] = [title:'String']

        viewResolver
    }
}
