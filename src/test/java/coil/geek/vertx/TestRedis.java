package coil.geek.vertx;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import coil.geek.support.RedisContainer;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.junit5.Checkpoint;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;

@Testcontainers
@ExtendWith(VertxExtension.class)
public class TestRedis {
	
	@Container
	public RedisContainer redis = new RedisContainer();
	
	Logger logger = LoggerFactory.getLogger(TestRedis.class); 

	@Test
	void test(VertxTestContext context, Vertx vertx) {
		Checkpoint cp = context.checkpoint();
		assertTrue(redis.isRunning());
		RedisOptions opts = new RedisOptions();
		opts.setHost(redis.getContainerIpAddress());
		opts.setPort(redis.getPort());
		opts.setConnectTimeout(10);
		RedisClient redisClient = RedisClient.create(vertx, opts); 
		redisClient.info(info -> {
			logger.info("Connecting to redis with host: " + redis.getPort() + " and server data: " + info);
			if (info.failed()) {
				logger.error("Redis connection failed, aborting startup");
				context.failNow(info.cause());
			} else
				cp.flag();
		});
	}

}
