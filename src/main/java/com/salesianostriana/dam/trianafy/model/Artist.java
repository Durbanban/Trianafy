package com.salesianostriana.dam.trianafy.model;


import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.trianafy.view.View;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
@Builder
public class Artist {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

}
