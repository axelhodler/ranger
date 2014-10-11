package xorrr.github.io;

import java.net.UnknownHostException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import xorrr.github.io.di.Module;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {
    public static void main(String[] args) throws UnknownHostException {
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.INFO);

        Injector injector = Guice.createInjector(new Module());
    }
}
