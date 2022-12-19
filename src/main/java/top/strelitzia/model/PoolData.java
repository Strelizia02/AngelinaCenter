package top.strelitzia.model;

import lombok.Data;

@Data
public class PoolData {
    private String name;
    private Integer star;
    private String pool;
    /**
     * 0->非限定
     * 1->周年限定
     * 2->联动限定
     * 3->五倍权值
     * 4->新年限定
     */
    private Integer limit;
  
    private Integer version;
}
