package com.LMZ.RPC.myRPCV6.loadbanlance;

import java.util.List;

public class RoundLoadBalance implements LoadBalance {
    private int choose = -1;

    @Override
    public String balance(List<String> addressList) {
        choose++;
        choose = choose % addressList.size();
        return addressList.get(choose);
    }
}