package com.yesnoabc.vchat.controller;

import com.unfbx.chatgpt.OpenAiApi;
import com.unfbx.chatgpt.OpenAiClient;
import com.unfbx.chatgpt.OpenAiStreamClient;
import com.unfbx.chatgpt.entity.chat.ChatCompletion;
import com.unfbx.chatgpt.entity.chat.ChatCompletionResponse;
import com.unfbx.chatgpt.entity.chat.Message;
import com.unfbx.chatgpt.sse.ConsoleEventSourceListener;
import com.yesnoabc.vchat.utils.OpenAIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

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
    private static final Logger LOGGER = Logger.getLogger(ChatController.class.getName());

    @Autowired
    private OpenAIUtils openAIUtils;
    @Value("${promptTemplate}")
    private String promptTemplate;
    @GetMapping("/chat")
    public String index(Model model, @RequestParam("theme") String theme, @RequestParam("purpose") String purpose, @RequestParam("keywords") String keywords, @RequestParam("wordCount") String wordCount) {
        String formattedDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String prompt = String.format(promptTemplate,formattedDateTime, theme, purpose, keywords, wordCount);
        LOGGER.info(prompt);
        String generateText = "";//openAIUtils.generateText(prompt);


        OpenAiClient openAiClient = OpenAiClient.builder()
                .apiKey(Arrays.asList("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"))
                //自定义key的获取策略：默认KeyRandomStrategy
                //.keyStrategy(new KeyRandomStrategy())
                //.keyStrategy(new FirstKeyStrategy())
                //自己做了代理就传代理地址，没有可不不传
                .apiHost("https://openai.yesnoabc.com/")
                .build();
        //聊天模型：gpt-3.5
        Message message = Message.builder().role(Message.Role.USER).content(prompt).build();
        ChatCompletion chatCompletion = ChatCompletion.builder().messages(Arrays.asList(message)).build();
        chatCompletion.setMaxTokens(3500);
        ChatCompletionResponse chatCompletionResponse = openAiClient.chatCompletion(chatCompletion);
        chatCompletionResponse.getChoices().forEach(e -> {
            System.out.println(e.getMessage());
        });



        return generateText;
    }
}
