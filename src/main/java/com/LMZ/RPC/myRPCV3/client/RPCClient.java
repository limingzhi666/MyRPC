package com.LMZ.RPC.myRPCV3.client;

import com.LMZ.RPC.myRPCV3.common.RPCRequest;
import com.LMZ.RPC.myRPCV3.common.RPCResponse;

public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request);
}