package org.springcat.demo.helloworld;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by 32983 on 2017/6/6.
 */
public class HelloWorldCommandTest  {

    @Test
    public void helloWorldCommand() {
        HelloWorldCommand helloWorldCommand = new HelloWorldCommand("klov");
       String result = helloWorldCommand.execute();
       System.out.println("【HelloWorldCommand】 result = "+result);
    }
}
