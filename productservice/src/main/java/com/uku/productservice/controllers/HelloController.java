package com.uku.productservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {
    @GetMapping("/say/{name}/{times}")
    public String sayHello(@PathVariable ("name") String name, @PathVariable ("times") int times){
        StringBuilder answer = new StringBuilder();
        for(int i = 1; i <= times; i++){
            answer.append("Hello ").append(name).append("<br>");
        }
        return answer.toString();
    }
}
