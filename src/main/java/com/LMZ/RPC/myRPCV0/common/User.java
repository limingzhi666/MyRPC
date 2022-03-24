package com.LMZ.RPC.myRPCV0.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.net.ServerSocket;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    //客户端和服务端共有的
    private Integer id;
    private String userName;
    private Boolean sex;
}