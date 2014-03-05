html {
    head {
        title(title.toUpperCase())
    }
    body {
        p('Hello from Spring Boot!')
        p('This page has been rendered through MarkupTemplateEngine using a type checked model!')
        p("It is ${new Date()}")
        p("Groovy ${GroovySystem.version}")
        include template: 'views/includes/include.groovy'
    }
}
