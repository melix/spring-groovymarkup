package com.acme.boot

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class ApplicationController {

    @RequestMapping("/")
    String home() {
        'views/hello.groovy'
    }

    @RequestMapping("/typechecked")
    String typechecked(Model model) {
        model.addAttribute('title', 'A type checked template!')
        'views/typechecked.groovy'
    }


}
