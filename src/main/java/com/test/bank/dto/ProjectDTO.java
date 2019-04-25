package com.test.bank.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectDTO {

    private Long id;
    private Boolean deleted = null;
    private String description = null;
    private String name = null;
    private Boolean _private = null;
}
