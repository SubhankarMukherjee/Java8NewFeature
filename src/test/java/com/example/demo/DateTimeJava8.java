package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertTrue;

public class DateTimeJava8 {

    //java.time package
    //immuntable
    //Supporting classes like Instant,Duraton,Period etc
    //inspired from JODA time

//    LocalDate: used to represnt a date; LocalTime is used to represent a
//LocalDateTime is used to represent Date and Time

    @Test
    void local_date_time()
    {

        // give you as per system or machine data
        LocalDate date = LocalDate.now();
        System.out.println("LocalDate:"+date);

        LocalTime time = LocalTime.now();
        System.out.println("LocalTime:"+time);

        LocalDateTime datetime = LocalDateTime.now();
        System.out.println("LocalDateTime:"+datetime);

        LocalDate inputdate= LocalDate.of(2021,01,12);
        System.out.println("Local date as input:"+ inputdate);

        //print the day by day of the year out of 365
        System.out.println("364th day of 2021 :"+LocalDate.ofYearDay(2021,364));

        //Get values from LocalDate
        System.out.println(inputdate.getMonth());
        System.out.println(inputdate.getDayOfMonth());
        System.out.println(inputdate.getEra());

        System.out.println(inputdate.get(ChronoField.DAY_OF_MONTH));
        // Modify Local date

        System.out.println("Incremented Date:"+inputdate.plusDays(2)); // it will create a new LocalDate instance
        // it will not update the local date itself. Because of its immutableity method.
        // increase the month
        System.out.println("Incremented Date:"+inputdate.plusMonths(1));

        System.out.println("Decremented  Date:"+inputdate.minusDays(10));
        System.out.println("LocalDate:"+ inputdate); //2021-01-12
        System.out.println("LocalDate with year 2017:"+ inputdate.withYear(2017)); //2017-01-12
        //Same with chorno field
        System.out.println("LocalDate with Chrono Field 2017:"+ inputdate.with(ChronoField.YEAR,2017)); //2017-01-12

        //set 1st day of next month to be set in input date
        System.out.println("LocalDate with Temporal adjuster:"+ inputdate.with(TemporalAdjusters.firstDayOfNextMonth())); //2017-01-12

        //Subtract Year by 1
       // System.out.println("LocalDate:"+ inputdate); //2021-01-12
        System.out.println("Subtracted year:" + inputdate.minusYears(1));
        //or
        System.out.println("Subtracted year:" + inputdate.minus(1, ChronoUnit.YEARS));
        // chronoUnit is enum which implemented TemoralUnit



    }
    @Test
    void  additional_Support_Method()
    {
        // check leapyear or not

        LocalDate inputdate= LocalDate.of(2016,01,12);

        System.out.println(inputdate.isLeapYear());

        // is Equal
        LocalDate date1= LocalDate.now();
        LocalDate date2= LocalDate.of(2021,12,24);
        System.out.println(date1.isEqual(date2)); //true
        System.out.println(date1.isBefore(date2)); //false
        System.out.println(date1.isAfter(date2));  //false
    }

    @Test

    void unsupported()
    {
        LocalDate inputdate= LocalDate.of(2016,01,12);


        UnsupportedTemporalTypeException unsupportedTemporalTypeException = assertThrows(UnsupportedTemporalTypeException.class, () -> {
            System.out.println(inputdate.minus(1, ChronoUnit.HOURS));
        });

        // input date does not have miutest at all

       // java.time.temporal.UnsupportedTemporalTypeException: Unsupported unit: Hours
        //to check that
        System.out.println(inputdate.isSupported( ChronoUnit.HOURS));
    }

    @Test
    void LocalTime()
    {
        LocalTime localTime= LocalTime.of(23,59,59);

        // getting values from LocalTime

        System.out.println(localTime.getHour());
        System.out.println(localTime.getMinute());
        System.out.println(localTime.getSecond());
        System.out.println(localTime.get(ChronoField.MINUTE_OF_HOUR));
        // Modifying
// subtract hrs by 2
        System.out.println(localTime.minusHours(2)); //21:59:59
       //or
        System.out.println(localTime.minus(2,ChronoUnit.HOURS));
        //setting Midnight to Localtime
        System.out.println(localTime.with(LocalTime.MIDNIGHT));

        // add some min
        System.out.println(localTime.plusMinutes(1));
    }
    @Test
    void localDateTime()
    {
        LocalDateTime localDateTime= LocalDateTime.of(2021,12,24,1,21,10);
        System.out.println(localDateTime);
        System.out.println(LocalDateTime.of(LocalDate.now(),LocalTime.now()));

        // Get the values
        //get hr and day
        System.out.println(localDateTime.getHour());
        System.out.println(localDateTime.getDayOfMonth());
        System.out.println(localDateTime.get(ChronoField.SECOND_OF_MINUTE));

        // Modifying LocalDateTime

        System.out.println(localDateTime.plusMinutes(2));
        System.out.println(localDateTime.plus(10,ChronoUnit.HOURS));
        System.out.println(localDateTime.withYear(2010));
        System.out.println(localDateTime.with(TemporalAdjusters.lastDayOfMonth()));
    }
}
