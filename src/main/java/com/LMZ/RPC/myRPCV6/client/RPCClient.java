package com.LMZ.RPC.myRPCV6.client;

import com.LMZ.RPC.myRPCV6.common.RPCRequest;
import com.LMZ.RPC.myRPCV6.common.RPCResponse;

public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request);
}