package com.xht.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
public class UserInfo {
    private int id;
    private String username;
    private String password;
    private int type;
}
