package com.LMZ.RPC.myRPCV5.client;

import com.LMZ.RPC.myRPCV5.common.RPCRequest;
import com.LMZ.RPC.myRPCV5.common.RPCResponse;

public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request);
}