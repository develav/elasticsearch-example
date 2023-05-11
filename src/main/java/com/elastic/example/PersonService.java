package com.elastic.example;


import co.elastic.clients.elasticsearch._types.mapping.DateProperty;
import co.elastic.clients.elasticsearch._types.mapping.IntegerNumberProperty;
import co.elastic.clients.elasticsearch._types.mapping.KeywordProperty;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.DeleteByQueryRequest;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.DeleteIndexRequest;
import co.elastic.clients.elasticsearch.indices.ExistsRequest;
import com.elastic.example.util.PropertiesHolder;
import com.elastic.example.util.Util;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

@Log4j2
public class PersonService {

    private static final String INDEX = PropertiesHolder.config.getString("index");

    public static Optional<Person> getPersonById(String id) throws IOException {
        GetResponse<Person> response = Util.getClient()
                .get(
                        g -> g.index(INDEX).id(id),
                        Person.class
                );
        return Optional.ofNullable(response.source());
    }


    public static void insertPerson(Person person) throws IOException {
        Util.getClient()
                .index(i -> i.index(INDEX)
                        .id(String.valueOf(person.getId()))
                        .document(person)
                );
    }


    public static String deletePersonById(String id) throws IOException {
        return Util.getClient()
                .delete(DeleteRequest.of(n -> n.index(INDEX).id(id)))
                .result()
                .toString();
    }


    public static String updatePersonById(String id, Person person) throws IOException {
        return Util.getClient()
                .update(p -> p.index(INDEX)
                        .id(id)
                        .doc(person), Person.class)
                .result()
                .toString();
    }

    public static String deleteAllFromIndex() throws IOException {
        return Util.getClient()
                       .deleteByQuery(DeleteByQueryRequest.of(f -> f.index(INDEX)
                               .query(QueryBuilders.matchAll().build()._toQuery())))
                       .deleted() + "";
    }


    public static void createPersonDataIndex() throws IOException {
        Util.getClient()
                .indices().create(CreateIndexRequest.of(
                        n -> n.index(INDEX)
                                .mappings(maps -> maps.properties(getFields()))));
    }

    public static String deleteIndex() throws IOException {
        return Util.getClient()
                .indices().delete(DeleteIndexRequest.of(n -> n.index(INDEX))).toString();
    }

    public static boolean checkIfIndexExist() throws IOException {
        return Util.getClient()
                .indices().exists(ExistsRequest.of(e -> e.index(INDEX))).value();
    }


    private static HashMap<String, Property> getFields() {
        HashMap<String, Property> map = new HashMap<>();
        map.put("createDate", DateProperty.of(p -> p)._toProperty());
        map.put("id", IntegerNumberProperty.of(p -> p)._toProperty());
        map.put("name", KeywordProperty.of(p -> p)._toProperty());
        map.put("email", KeywordProperty.of(p -> p)._toProperty());
        map.put("age", IntegerNumberProperty.of(p -> p)._toProperty());
        return map;
    }

}
