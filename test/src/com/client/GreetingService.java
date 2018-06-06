package com.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String greetServer(String Arg1,String Arg2,String Arg3) throws IllegalArgumentException;
	String greetServer(String Arg1,String Arg2,String Arg3,String Arg4) throws IllegalArgumentException;
}
