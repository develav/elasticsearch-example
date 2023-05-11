package com.elastic.example.util;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;


@UtilityClass
@Log4j2
public class PropertiesHolder {
    public static Configuration config;

    static {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties()
                                .setFileName("application.properties"));
        try {
            config = builder.getConfiguration();
        } catch (
                ConfigurationException cex) {
           log.error("ошибка во время загрузки application.properties");
        }
    }

}
