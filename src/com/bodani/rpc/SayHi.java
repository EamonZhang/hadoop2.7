package com.bodani.rpc;

public interface SayHi {
	// hadoop 2.0 之后需要在接口中定义 版本号
	public static final long versionID = 100L;

	public String sayHi(String name);
}
