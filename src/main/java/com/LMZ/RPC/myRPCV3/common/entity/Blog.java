package com.LMZ.RPC.myRPCV3.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Blog implements Serializable {
    private Integer id;
    private Integer useId;
    private String title;
}