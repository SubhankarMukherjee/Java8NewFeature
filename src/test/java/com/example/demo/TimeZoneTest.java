package com.example.demo;

import org.junit.jupiter.api.Test;

import java.time.*;

public class TimeZoneTest {
    // ZoneDateTime, ZoneId,ZoneOffset


    @Test
    void testTimeZone()
    {

        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println(zonedDateTime);

        //2021-12-24T12:51:51.334965100+05:30[Asia/Calcutta]
     //+05:30[Asia/Calcutta]-----> This is offset[ZoneID]
        System.out.println("Zone Offset :"+ zonedDateTime.getOffset());//Zone Offset :+05:30
        System.out.println("Zone ID :"+ zonedDateTime.getZone());//Zone ID :Asia/Calcutta

        // Number of available Zone

//        ZoneId.getAvailableZoneIds().stream()
//                .forEach(zone-> System.out.println(zone));

        System.out.println(ZoneId.getAvailableZoneIds().stream().count()); //600  // total 600 zones
        //or
        System.out.println(ZoneId.getAvailableZoneIds().size());

        // get time from zone id
        System.out.println(ZonedDateTime.now(ZoneId.of("America/Denver")));
        //2021-12-24T01:04:04.952719600-07:00[America/Denver]

//        Creating LocalDateTime using TimeZone


        LocalDateTime localDateTime= LocalDateTime.now(ZoneId.of("America/Denver"));
        System.out.println(localDateTime); //2021-12-24T01:04:04.955572400
    //using clock
        LocalDateTime localDateTime1= LocalDateTime.now(Clock.system(ZoneId.of("America/Denver")));
        System.out.println(localDateTime1); //2021-12-24T01:04:04.955572400
        //it is without zone id and offset

        //
        System.out.println("LocalDate Time with ofInstant method"+
                LocalDateTime.ofInstant(Instant.now(),ZoneId.systemDefault())
                );

        //Cnvert LocalDateTime, instant to ZonedLocalDateTime
        LocalDateTime localDateTime2= LocalDateTime.now();
        ZonedDateTime zonedDateTime1 = localDateTime2.atZone(ZoneId.of("Asia/Calcutta"));
        System.out.println(zonedDateTime1);

        //Usuing instant
        ZonedDateTime zonedDateTime2 = Instant.now().atZone(ZoneId.of("Asia/Calcutta"));
        System.out.println(zonedDateTime2);

        //OffsetDateAndTime -> it will only take offset not zone id

        OffsetDateTime offsetDateTime = localDateTime.atOffset(ZoneOffset.ofHours(-5));
        System.out.println(offsetDateTime);


    }
}
