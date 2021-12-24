package com.example.demo;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class LocalDateTimeConverter {
    @Test
    void localDate_To_LocalDateTime()
    {
        LocalDate localDate= LocalDate.of(2021,12,24);
        LocalDateTime localDateTime= localDate.atTime(23,59);
        System.out.println(localDateTime);
    }
    @Test
    void localTime_To_LocalDateTime()
    {
        LocalDate localDate= LocalDate.of(2021,12,24);
        LocalTime localTime= LocalTime.NOON;
        LocalDateTime localDateTime = localTime.atDate(localDate);
        System.out.println(localDateTime);
    }
    @Test
    void localDateTime_To_LocalDate_And_localTime()
    {
        LocalDateTime localDateTime= LocalDateTime.now();
        LocalDate localDate = localDateTime.toLocalDate();
        System.out.println(localDate);
        LocalTime localTime = localDateTime.toLocalTime();
        System.out.println(localTime);

    }
    @Test
    void Period()
    {
        //Period class is date based representation of Time in days ,months and years
        //Compatible with LocalDate
        Period period= Period.ofDays(10);
        System.out.println(period);

        // use case: calculate difference between  2 dates
        LocalDate date1= LocalDate.of(2021,10,1);
        LocalDate date2=LocalDate.of(2021,9,28);
        Period period1 = Period.between(date1, date2); // between only accept localdate
        System.out.println(period1);

        Period period2 = date1.until(date2);
        System.out.println(period2.getDays());
        System.out.println(period2.getMonths());

        Period period3= Period.ofYears(10);
        System.out.println(period3.getYears()); //10
        System.out.println(period3.toTotalMonths());//120 no other factory method to get days amd months etc
    }

    @Test
    void Durtion(){
//        Duration is compatible with LocalTime and LocalDateTime

        Duration duration = Duration.ofHours(3);
        LocalTime time1= LocalTime.of(8,20);
        LocalTime time2= LocalTime.of(7,20);
        Duration duration1= Duration.between(time1,time2);
        System.out.println(duration1);
        System.out.println(duration1.getSeconds());
        //or
        long until = time1.until(time2, ChronoUnit.MINUTES);
        System.out.println(until);
        Duration duration2= Duration.ofDays(1);
        System.out.println(duration2.toMinutes());

        //checkcompatibility

        LocalDate date= LocalDate.now();
        LocalDate date2= LocalDate.now().plusDays(1);
        Duration duration3= Duration.between(date,date2);
        System.out.println(duration3); //.UnsupportedTemporalTypeException: Unsupported unit: Seconds
        //Duratio is not supported for Date


    }

    @Test
    void instant()
    {
        //Instant class is machine readable format for Date
        System.out.println(Instant.now()); // in seconds from EPOCH 1st JAN, 1970
        //2021-12-24T07:08:06.823279300Z
        // it is actually calculated in seconds but toString method is converting it to Pure human readbale
//        date format

        // it has no method of get Month, getseconds etc
        //it has only get nono and get Epoch second
        System.out.println(Instant.now().getEpochSecond()); //1640329826

        System.out.println(Instant.ofEpochSecond(0)); //1970-01-01T00:00:00Z

        //find diff

        Instant now = Instant.now();
        Instant epoch= Instant.ofEpochSecond(0);
       Duration duration= Duration.between(now,epoch);
        System.out.println(duration.toMinutes() );
    }

}
