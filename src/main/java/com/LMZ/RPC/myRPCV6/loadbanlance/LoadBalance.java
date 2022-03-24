package com.LMZ.RPC.myRPCV6.loadbanlance;

import java.util.List;

public interface LoadBalance {
    String balance(List<String> addressList);
}