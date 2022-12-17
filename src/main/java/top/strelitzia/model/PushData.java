package top.strelitzia.model;

import lombok.Data;

import java.util.List;

@Data
public class PushData {
    List<MessageCount> messageCount;

    List<Function> functionCount;

    Long totalMemory;

    Long qqCount;

    Long groupCount;

    String botId;
}
