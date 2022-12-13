package top.strelitzia.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Bot {
    private String id;

    private List<Account> list = new ArrayList<>();
}
