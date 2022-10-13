package com.ckb.labs.e_supervisor.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Finding {
    private String finding;
    private Date createdAt;
}
