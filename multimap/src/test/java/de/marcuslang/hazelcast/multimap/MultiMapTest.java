package de.marcuslang.hazelcast.multimap;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MultiMap;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class MultiMapTest {

  private Main main;

  @Before
  public void setUp() throws Exception {
    main = Main.buildMain();
  }

  @After
  public void tearDown() throws Exception {
    main.destroy();
  }

  @org.junit.Test
  public void test001() throws Exception {
    main.store("empty", new ArrayList(0));
    Assert.assertEquals(1, main.size());
  }

  @Test
  public void test002() throws Exception {
    main.store("one", new ArrayList(0));
    main.store("two", new ArrayList(1));

    main.remove("one");
    Assert.assertEquals(1, main.size());
  }
}

class Main {

  public static final String MULTI_MAP_MAIN = "MultiMapMain";
  private MultiMap<Object, Object> multiMap;
  private static HazelcastInstance hazelcastInstance;


  private Main(MultiMap<Object, Object> multiMap) {
    this.multiMap = multiMap;
  }

  public static Main buildMain() throws Exception {
    Config config = new Config(MULTI_MAP_MAIN);
    hazelcastInstance = Hazelcast.newHazelcastInstance(config);
    return new Main(hazelcastInstance.getMultiMap(MULTI_MAP_MAIN));
  }

  public void destroy() {
    hazelcastInstance.shutdown();
  }

  public void store(String key, Collection collection) {
    multiMap.put(key, collection);
  }

  public void remove(String key){
    multiMap.remove(key);
  }

  public int size(){
    return multiMap.size();
  }

}





