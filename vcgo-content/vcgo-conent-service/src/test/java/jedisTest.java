import java.util.HashSet;
import java.util.Set;



import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class jedisTest {

	public  void  testRedis() {
		JedisPool jPool=new JedisPool("192.168.28.134", 6380);
		Jedis jedis = jPool.getResource();
		jedis.set("key6", "11111");
		System.out.println(jedis.get("key6"));
		jedis.close();
		jPool.close();
	}
	

	public void testRedisCluster() {
		Set<HostAndPort> hostAndPorts=new HashSet<>();
		hostAndPorts.add(new HostAndPort("192.168.28.134", 7001));
		hostAndPorts.add(new HostAndPort("192.168.28.134", 7002));
		hostAndPorts.add(new HostAndPort("192.168.28.134", 7003));
		hostAndPorts.add(new HostAndPort("192.168.28.134", 7004));
		hostAndPorts.add(new HostAndPort("192.168.28.134", 7005));
		hostAndPorts.add(new HostAndPort("192.168.28.134", 7005));
		
		JedisCluster cluster=new JedisCluster(hostAndPorts);
		
		cluster.set("key22", "2222");
		System.out.println(cluster.get("key22"));
		cluster.close();
	}
}
