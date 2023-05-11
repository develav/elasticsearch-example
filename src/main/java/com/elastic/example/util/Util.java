package com.elastic.example.util;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.experimental.UtilityClass;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;

import java.io.IOException;

import static com.elastic.example.util.PropertiesHolder.config;

@UtilityClass
public class Util {

    private final String HOST = config.getString("host");
    private final int PORT = config.getInt("port");
    private final String USER = config.getString("user");
    private final String PASSWORD = config.getString("password");

    private ElasticsearchClient client;
    private ElasticsearchTransport transport;


    public static void createConnection() {
        if (client != null) {
            return;
        }
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(USER, PASSWORD));

        RestClient restClient = RestClient
                .builder(new HttpHost(HOST, PORT))
                .setHttpClientConfigCallback(hc -> hc
                        .setDefaultCredentialsProvider(credentialsProvider)
                )
                .build();

        transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        client = new ElasticsearchClient(transport);
    }

    public ElasticsearchClient getClient() {
        if (client != null) {
            return client;
        } else {
            throw new ConnectionNotCreatedException("Соединение не было создано");
        }
    }


    public static void closeConnection() throws IOException {
        if (transport != null) {
            transport.close();
        }
    }
}
