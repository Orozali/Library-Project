package com.orozali.library.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    private int person_id;
    @NotEmpty(message = "Это полье не должен быть пустым")
    private String fullname;
    @NotNull
    @Min(value = 0, message = "Возраст должен быть больше нуля")
    private int birthdate;

}
