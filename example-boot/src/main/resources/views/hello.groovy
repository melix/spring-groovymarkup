html {
    head {
        title('Hello, boot!')
    }
    body {
        p('Hello from Spring Boot!')
        p('This page has been rendered through MarkupTemplateEngine')
        p("It is ${new Date()}")
        p("Groovy ${GroovySystem.version}")
        include template: 'views/includes/include.groovy'
    }
}
