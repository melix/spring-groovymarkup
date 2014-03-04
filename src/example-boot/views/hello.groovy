html {
   head {
	title('Hello, boot!')
   }
   body {
	p('Hello from Spring Boot!')
	p('This page has been rendered through MarkupTemplateEngine')
	p("It is ${new Date()}")
   }
}
