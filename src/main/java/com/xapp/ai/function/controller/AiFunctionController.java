package com.xapp.ai.function.controller;


import com.xapp.ai.function.service.WeatherService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class AiFunctionController {

  private final ChatModel chatModel;

  private final RestClient restClient;

  @GetMapping("/prompt")
  public String respondPromptsUsingFunction(@RequestParam String message) {

    /*UserMessage userMessage = UserMessage.builder()
        .text(message).build();
    OpenAiChatOptions openAiChatOptions = OpenAiChatOptions.builder()
        .withFunction("getWeather")
        .build();

    log.info("Received message: {}", userMessage.getText());

    ChatResponse chatResponse = chatModel.call(new Prompt(List.of(userMessage)));
    return chatResponse.getResult().getOutput().getText();
    */

    String response = ChatClient.create(chatModel)
        .prompt()
        .user(message)
        .toolCallbacks(FunctionToolCallback.builder("getCurrentWeather", new WeatherService(restClient))
            .description("Get the weather in location")
            .inputType(WeatherService.Request.class)
            .build())
        .call()
        .content();
    return response;
  }


}
