package com.ckb.labs.e_supervisor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supervisor {
    private String firstName;
    private String lastName;
    private String surname;
    private String organisation;
    private String designation;
}
