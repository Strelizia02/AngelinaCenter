package top.strelitzia.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Bot的数据结构
 */
@Data
public class Bot {
    private String id;
    
    private String name;
    
    private Long ram;
    
    private Long cpu;
    
    private Long send;

    private Long receive;
    
    private Long qqCount;

    private Long groupCount;

    private List<QQ> list = new ArrayList<>();
}
