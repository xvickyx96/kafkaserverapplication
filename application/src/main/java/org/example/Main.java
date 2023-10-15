package org.example;

import vikram.kafka.payload.Student;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.json.simple.JSONObject;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws MalformedURLException {
        System.out.println("Hello world!");

        userMenu();

    }

    public static void userMenu() {
        String userChoise = "";
        do {
            printMenu();

            Scanner scan = new Scanner(System.in);
            System.out.println("Do you choise: ");
            userChoise = scan.nextLine();

            switch (userChoise) {
                case "1": {
                    userInputForKafka();
                    break;
                }
                case "2": {
                    getDataFromKafka("studentTopicNew");
                    break;
                }
                case "0": {
                    break;
                }
                default: {
                    System.out.println("Wrong input, try again");
                    break;
                }


            }

            if (!userChoise.equals("0")) {
                System.out.println("Press any key to continue");
                scan.nextLine();
            }

        } while (!userChoise.equals("0"));
    }

    public static void printMenu() {
        System.out.println("Choose from menu");
        System.out.println("-----------------");
        System.out.println("1. Add student");
        System.out.println("2. See all students");
        System.out.println("0. Exit");


    }

    public static void userInputForKafka() {

        Student student = new Student();
        System.out.println("Write student id: ");
        Scanner scan = new Scanner(System.in);
        long Id = scan.nextLong();
        student.setId(Id);
        scan.nextLine();
        System.out.println("Write student firstname: ");
        String firstName = scan.nextLine();
        student.setStudentFirstName(firstName);
        System.out.println("Write student lastname: ");
        String lastName = scan.nextLine();
        student.setStudentLastName(lastName);

        JSONObject myObj = new JSONObject();
        myObj.put("id", student.getId());
        myObj.put("studentFirstName", student.getStudentFirstName());
        myObj.put("studentLastName", student.getStudentLastName());

        // Skicka payload till WebAPI med en förfrågan
        sendToWebAPI(myObj);
    }


    public static String sendToWebAPI(JSONObject myObj) {
        String returnResp = "";
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost("http://localhost:8080/api/v1/kafka/publishStudent");

            // Skapa en JSON-förfrågningskropp
            String jsonPayload = myObj.toJSONString();
            StringEntity entity = new StringEntity(jsonPayload, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);

            // Skicka förfrågan och hantera svaret
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    String responseString = EntityUtils.toString(responseEntity);
                    System.out.println("Svar från server: " + responseString);
                    returnResp = responseString;


                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnResp;
    }

    public static ArrayList<Student> getDataFromKafka(String topicName) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "fetchingGroup");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put("spring.json.trusted.packages", "*");

        Consumer<String, Student> consumer = new KafkaConsumer<>(props);
        consumer.assign(Collections.singletonList(new TopicPartition(topicName, 0)));

        // Hämta från början av topic
        consumer.seekToBeginning(consumer.assignment());

        // Skapa arraylist för student
        ArrayList<Student> students = new ArrayList<>();

        consumer.seekToBeginning(consumer.assignment());

        // WhileLoop som hämtar i JSON format
        while (true) {
            ConsumerRecords<String, Student> records = consumer.poll(Duration.ofMillis(100));
            if (records.isEmpty()) continue;
            for (ConsumerRecord<String, Student> record : records) {
                students.add(record.value());
            }
            break;
        }

        // Skriver ut arrayList
        for (Student student : students) {
            System.out.println("ID " + student.getId() + ": " + student.getStudentFirstName() + " " + student.getStudentLastName());
        }
        return students;
    }

}


