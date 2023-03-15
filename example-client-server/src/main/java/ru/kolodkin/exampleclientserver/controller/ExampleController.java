package ru.kolodkin.exampleclientserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kolodkin.exampleclientserver.bean.Human;

import java.util.List;

@RestController
@RequestMapping("/example")
public class ExampleController {

    @GetMapping
    public ResponseEntity<List<Human>> getObjects() {
        return ResponseEntity.ok(List.of(
                new Human("Amigos"),
                new Human("Alpha"),
                new Human("Beta")));
    }
}
