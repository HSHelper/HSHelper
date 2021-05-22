package ru.hsHelper.api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hsHelper.api.services.impl.util.InitService;

@RestController
@RequestMapping("/init")
public class InitController {
    private final InitService initService;

    public InitController(InitService initService) {
        this.initService = initService;
    }

    @PostMapping("/")
    public void initialize() {
        initService.initialize();
    }
}
