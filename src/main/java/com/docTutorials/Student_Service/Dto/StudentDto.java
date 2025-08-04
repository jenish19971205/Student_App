package com.docTutorials.Student_Service.Dto;

import jakarta.validation.constraints.NotBlank;


public class StudentDto {

    private int id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "City cannot be blank")
    private String city;

    public StudentDto(){
    }

    public StudentDto(int id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
}

}
