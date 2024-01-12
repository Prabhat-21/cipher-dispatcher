package com.example.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.project.dto.Cipher;
import com.example.project.service.CipherDispatherService;

@Controller
@RequestMapping (value = "/project/v1")
public class CipherDispatcherController {
    @Autowired
    private CipherDispatherService cipherDispatherService;

    @PostMapping ("/dispatch")
    public void dispatch(@RequestBody Cipher cipher) {
        cipherDispatherService.dispatch(cipher);
    }

    @PostMapping ("/close")
    public void closeLoop(@RequestHeader ("secret") String secret) {
        cipherDispatherService.closeLoop(secret);
    }
}
