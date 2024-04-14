package com.streams.kafkaproject.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class StudentDTO {

    private String name;
    private String rollNo;
    private List<Address> addressList;
}
