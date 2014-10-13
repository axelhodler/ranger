package xorrr.github.io.healthcheck;

import xorrr.github.io.utils.EnvVars;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.MongoClient;

public class DBHealthCheck extends HealthCheck {

    @Override
    protected Result check() {
        try {
            MongoClient client = new MongoClient("localhost", EnvVars.MONGO_PORT);
            client.getDatabaseNames();
        } catch (Exception e) {
            return Result.unhealthy("DB seems to be down");
        }
        return Result.healthy();
    }

}
