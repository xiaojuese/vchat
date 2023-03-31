package com.yesnoabc.vchat.controller;

import com.yesnoabc.vchat.utils.OpenAIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: qupeng
 * @Date: 2023/3/29 17:44
 * @Description: null.java
 * @Version:
 * @Last Modified by: qupeng
 * @Last Modified Time: 2023/3/29 17:44
 */
@RestController
public class ChatController {
    @Autowired
    private OpenAIUtils openAIUtils;

    @GetMapping("/chat")
    public String index(Model model, @RequestParam("prompt") String prompt) {
        String generateText = openAIUtils.generateText(prompt);

        return generateText;
    }
}
