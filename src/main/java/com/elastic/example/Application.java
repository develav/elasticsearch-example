package com.elastic.example;


import com.elastic.example.util.Util;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.time.LocalDateTime;

@Log4j2
public class Application {


    public static void main(String[] args) throws IOException {
        Util.createConnection();

        if (!PersonService.checkIfIndexExist()) {
            PersonService.createPersonDataIndex();
        }

        PersonService.deleteAllFromIndex();

        Person person = new Person(LocalDateTime.now(), 2, "Sara", "sara@email.com", 34);

        PersonService.insertPerson(person);

        System.out.println(PersonService.getPersonById("2"));

        Util.closeConnection();
    }
}
