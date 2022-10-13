package com.ckb.labs.e_supervisor.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recommendation {
    private String recommendation;
    private String recommenderName;
    private Date createdAt;
}
