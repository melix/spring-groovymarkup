html {
    head {
        title('Bonjour, Boot!')
    }
    body {
        p('Salut Spring Boot!')
        p('Cette page a été générée à partir du MarkupTemplateEngine !')
        p("Date du jour ${new Date()}")
        p("Groovy ${GroovySystem.version}")
        include template: 'views/includes/include.groovy'
    }
}
