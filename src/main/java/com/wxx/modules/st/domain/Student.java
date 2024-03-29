package com.wxx.modules.st.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
    private Integer id;
    private String name;
    private Integer age;
    private String sex;
    private String phone;
    private String address;
    private Integer citationCount;
}



