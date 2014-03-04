@Grab('org.springframework:spring-webmvc:4.0.2.RELEASE')
@Grab('javax.servlet:servlet-api:2.5')
import org.springframework.web.servlet.ViewResolver
import org.springframework.web.servlet.groovy.MarkupTemplateViewResolver
import groovy.text.markup.MarkupTemplateEngine
import groovy.text.markup.TemplateConfiguration

@Configuration
class EngineConfig {

    @Bean
    public TemplateConfiguration templateConfiguration() {
        new TemplateConfiguration()
    }

    @Bean
    public MarkupTemplateEngine templateEngine() {
	new MarkupTemplateEngine(this.class.classLoader, new File('.'), templateConfiguration())
    }

    @Bean
    public ViewResolver viewResolver(){
        def viewResolver = new MarkupTemplateViewResolver()
        viewResolver.engine = templateEngine()
        viewResolver.order = 1
 
        viewResolver
    }
}
