package com.xapp.ai.function.controller;


import com.xapp.ai.function.pojo.WeatherConfigProperties;
import com.xapp.ai.function.service.WeatherService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@RestController
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class AiFunctionController {

  private final WeatherConfigProperties weatherConfigProperties;
  private final ChatModel chatModel;

  @GetMapping("/prompt")
  public String respondPromptsUsingFunction(@RequestParam String message) {

    UserMessage userMessage = new UserMessage(message);

    ChatResponse response
        = chatModel.call(new Prompt(List.of(userMessage), OpenAiChatOptions.builder()
        .toolCallbacks(FunctionToolCallback.builder("getCurrentWeather",
                new WeatherService(weatherConfigProperties))
            .description("Get the weather in location")
            .inputType(WeatherService.Request.class)
            .build())
        .build()));

    return response.getResult().getOutput().getText();
  }

}
