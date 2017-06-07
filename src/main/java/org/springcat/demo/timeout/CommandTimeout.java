package org.springcat.demo.timeout;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class CommandTimeout extends HystrixCommand<String> {

    private final String name;

    public CommandTimeout(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"),1000);
        this.name = name;
    }

    @Override
    protected String run()  {
        System.out.println("aaaa");
        try {
            //sleep100秒强制超时，默认超时时间是1s
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("run end");
        return "";
    }
    @Override
    protected String getFallback() {
        return "Hello Failure " + name + "!";
    }
}