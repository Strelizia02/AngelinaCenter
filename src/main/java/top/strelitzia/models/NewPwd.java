package top.strelitzia.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NewPwd {
    private String oldPwd;

    private String newPwd;
}
