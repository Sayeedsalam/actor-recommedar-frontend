package com.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String Arg1,String Arg2,String Arg3, AsyncCallback<String> callback)
			throws IllegalArgumentException;
	void greetServer(String Arg1,String Arg2,String Arg3,String Arg4, AsyncCallback<String> callback)
			throws IllegalArgumentException;
}
