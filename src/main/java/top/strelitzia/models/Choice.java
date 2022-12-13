package top.strelitzia.models;

import lombok.Data;

@Data
public class Choice {

    private String text;

    private Integer index;

    private String logprobs;

    private String finish_reason;
}
