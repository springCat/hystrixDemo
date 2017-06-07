package org.springcat.demo.timeout;

import org.junit.Test;
import org.springcat.demo.circuit.HelloCommandCircuit;

import static org.junit.Assert.*;

/**
 * Created by 32983 on 2017/6/7.
 */
public class CommandTimeoutTest {
  @Test
  public void testSynchronous() throws InterruptedException {
    System.out.println(new CommandTimeout("World").execute());
  }
}