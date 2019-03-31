package coil.geek.support;

import org.testcontainers.containers.GenericContainer;

public class RedisContainer extends GenericContainer<RedisContainer> {
	private static final int REDIS_PORT = 6379;
	private static final String REDIS_VERSION = "5";
	
	public RedisContainer() {
		super("redis:" + REDIS_VERSION);
		withExposedPorts(REDIS_PORT);
	}
	
	public int getPort() {
		return getMappedPort(REDIS_PORT);
	}

}
