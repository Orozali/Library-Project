package com.orozali.library.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Books {
    private int book_id;
    @NotEmpty(message = "Это полье не должен быть пустым")
    private String name;
    @NotEmpty(message = "Это полье не должен быть пустым")
    private String author;
    private int year;
}
