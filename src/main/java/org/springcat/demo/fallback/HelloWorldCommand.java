package org.springcat.demo.fallback;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Created by 32983 on 2017/6/6.
 */
public class HelloWorldCommand extends HystrixCommand<String> {

  private String name;

  public HelloWorldCommand(String name) {
    super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(name)));
    this.name = name;
  }

  @Override
  protected String run() throws Exception {
    System.out.println("run success " + Thread.currentThread().getName());
//    return "run success " + Thread.currentThread().getName();
    throw new Exception("11111");
  }

  @Override
  protected String getFallback() {
    return "Failure " + name + "!";
  }
}