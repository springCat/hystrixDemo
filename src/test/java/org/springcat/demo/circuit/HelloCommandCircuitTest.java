package org.springcat.demo.circuit;

import org.junit.Test;
import org.junit.experimental.ParallelComputer;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.springcat.demo.timeout.CommandTimeout;

import static org.junit.Assert.*;

/**
 * Created by 32983 on 2017/6/7.
 */
public class HelloCommandCircuitTest {

  @Test
  public void testSynchronous1() throws InterruptedException {
          String world = new HelloCommandCircuit("World1",10).execute();
          System.out.println(world);
  }

  @Test
  public void testSynchronous2() throws InterruptedException {
    String world = new HelloCommandCircuit("World2",10).execute();
    System.out.println(world);
  }

  @Test
  public void testSynchronous3() throws InterruptedException {
    String world = new HelloCommandCircuit("World3",10).execute();
    System.out.println(world);
  }

  @Test
  public void testSynchronous4() throws InterruptedException {
    String world = new HelloCommandCircuit("World4",10).execute();
    System.out.println(world);
  }

  @Test
  public void testSynchronous5() throws InterruptedException {
    String world = new HelloCommandCircuit("World5",10).execute();
    System.out.println(world);
  }

  public static void main(String[] args) {
    Class[] cls = {HelloCommandCircuitTest.class,HelloCommandCircuitTest.class,HelloCommandCircuitTest.class };
    Result rt;
    // 并发以类和方法为维度
    rt =   JUnitCore.runClasses(new ParallelComputer(true, true), cls);
    System.out.println(rt.getRunCount() + " " + rt.getFailures()  + " " + rt.getRunTime());
  }


};
