package com.LMZ.RPC.myRPCV4.client;

import com.LMZ.RPC.myRPCV4.common.RPCRequest;
import com.LMZ.RPC.myRPCV4.common.RPCResponse;

public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request);
}