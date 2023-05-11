package com.elastic.example;

import com.elastic.example.util.LocalDateTimeSerializer;
import com.elastic.example.util.LocalDatetimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDatetimeDeserializer.class)
    private LocalDateTime createDate;
    private int id;
    private String name;
    private String email;
    private int age;
}
