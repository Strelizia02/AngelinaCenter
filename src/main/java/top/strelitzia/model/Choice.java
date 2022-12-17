package top.strelitzia.model;

import lombok.Data;

@Data
public class Choice {

    private String text;

    private Integer index;

    private String logprobs;

    private String finish_reason;
}
