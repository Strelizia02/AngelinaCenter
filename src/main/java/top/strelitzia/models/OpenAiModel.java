package top.strelitzia.models;

import lombok.Data;

/**
 * openAI的封装
 */
@Data
public class OpenAiModel {
    private String model;

    private String prompt;

    private Integer temperature;

    private Integer max_tokens;
}
