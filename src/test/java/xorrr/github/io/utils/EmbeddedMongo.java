package xorrr.github.io.utils;

import java.io.IOException;
import java.net.UnknownHostException;

import de.flapdoodle.embed.mongo.Command;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.ArtifactStoreBuilder;
import de.flapdoodle.embed.mongo.config.DownloadConfigBuilder;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.config.RuntimeConfigBuilder;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.config.io.ProcessOutput;
import de.flapdoodle.embed.process.io.progress.IProgressListener;
import de.flapdoodle.embed.process.runtime.Network;

public abstract class EmbeddedMongo {

    public static MongodExecutable getEmbeddedMongoExecutable()
            throws UnknownHostException, IOException {
        IRuntimeConfig runtimeConfig = buildRuntimeConfigToDisableSomeLogging();

        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(EnvVars.MONGO_PORT, Network.localhostIsIPv6()))
                .build();

        MongodStarter runtime = MongodStarter.getInstance(runtimeConfig);

        return runtime.prepare(mongodConfig);
    }

    private static IRuntimeConfig buildRuntimeConfigToDisableSomeLogging() {
        return new RuntimeConfigBuilder()
                .defaults(Command.MongoD)
                .processOutput(ProcessOutput.getDefaultInstanceSilent())
                .artifactStore(
                        new ArtifactStoreBuilder()
                                .defaults(Command.MongoD)
                                .download(
                                        new DownloadConfigBuilder()
                                                .defaultsForCommand(
                                                        Command.MongoD)
                                                .progressListener(
                                                        new IProgressListener() {

                                                            @Override
                                                            public void start(
                                                                    String label) {
                                                            }

                                                            @Override
                                                            public void progress(
                                                                    String label,
                                                                    int percent) {
                                                            }

                                                            @Override
                                                            public void info(
                                                                    String label,
                                                                    String message) {
                                                            }

                                                            @Override
                                                            public void done(
                                                                    String label) {
                                                            }
                                                        }))).build();
    }
}
