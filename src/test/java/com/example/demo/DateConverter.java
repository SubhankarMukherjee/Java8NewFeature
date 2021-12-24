package com.example.demo;

import org.junit.jupiter.api.Test;


import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.time.LocalDate;

public class DateConverter {
    @Test
    void UtilDateToLocalDate()
    {
        // Java.util.Date or LocalDate
        Date date= new Date();
        System.out.println(date); //Fri Dec 24 13:57:16 IST 2021
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        System.out.println(localDate);//2021-12-24
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        System.out.println(localDateTime);//2021-12-24


    }
    @Test
    void LocalDateToUtilDate()
    {
       LocalDate localDate= LocalDate.now();
        Instant instant = localDate.atTime(LocalTime.now()).atZone(ZoneId.systemDefault()).toInstant();


        Date date=  Date.from(instant);
        System.out.println(date);
    }
    @Test
    void SQLDateTOLocalDate()
    {

        LocalDate localDate= LocalDate.now();
        java.sql.Date date=  java.sql.Date.valueOf(localDate);
        System.out.println(date); //2021-12-24

        //vice versa

        LocalDate localDate1 = date.toLocalDate();
        System.out.println(localDate1);

    }

    @Test
    void DateFormatter()
    {
        // This class introduced in 1.8 in java.time.format package

        // parse: Converting a String to LocalDate/LocalTime/LocalDateTime
        //Format: Converting a LocalDate/LocalTime/LocalDateTime to a String
        // ISO_LOCAL_DATE : YYYY-MM-DD
        // ISO_OFFSET_DATE : YYYY-MM-DD-Offset
        //ISO_DATE supports both ISO date and ISO Local Date

        // Formating LocalDateExample

        perseLocalDate();
        formatLocaldate();

    }
    @Test
    void TimeFormatter()
    {
        parseLocalTime();
        formatLocalTime();
    }
    @Test
    void DateTimeFormatter()
    {
        parseLocalDateTime();
        formatLocalDateTime();
    }





    void perseLocalDate()
   {
        String date= "2021-12-10";
        LocalDate localDate= LocalDate.parse(date); //by default implementation: DateTimeFormatter.ISO_LOCAL_DATE
       /*
       *  public static final DateTimeFormatter ISO_LOCAL_DATE;
    static {
        ISO_LOCAL_DATE = new DateTimeFormatterBuilder()
                .appendValue(YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
                .appendLiteral('-')
                .appendValue(MONTH_OF_YEAR, 2)
                .appendLiteral('-')
                .appendValue(DAY_OF_MONTH, 2)
                .toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
    }
       *
       * */

       System.out.println(localDate);

       String date1= "20180428"; // yyyyMMdd
       LocalDate localDate1= LocalDate.parse(date1, DateTimeFormatter.BASIC_ISO_DATE);
       System.out.println(localDate1);
/*

 public static final DateTimeFormatter BASIC_ISO_DATE;
    static {
        BASIC_ISO_DATE = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendValue(YEAR, 4)
                .appendValue(MONTH_OF_YEAR, 2)
                .appendValue(DAY_OF_MONTH, 2)
                .optionalStart()
                .parseLenient()
                .appendOffset("+HHMMss", "Z")
                .parseStrict()
                .toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
    }
 */

       // for custom pattern

       String date3="2018/12/24";
       //there is no in build in constant avalble for this
       DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("yyyy/MM/dd");
       LocalDate localDate2= LocalDate.parse(date3,dateTimeFormatter);
       System.out.println(localDate2);
       String date4="2018*12*24";
       //there is no in build in constant avalble for this
       DateTimeFormatter dateTimeFormatter1= DateTimeFormatter.ofPattern("yyyy*MM*dd");
       LocalDate localDate3= LocalDate.parse(date4,dateTimeFormatter1);
       System.out.println(localDate3);

//       #can not be used as it is future reserved keyword

    }

    void formatLocaldate()
    {
        LocalDate localDate= LocalDate.now();
        DateTimeFormatter dateTimeFormatter1= DateTimeFormatter.ofPattern("yyyy*MM*dd");
        String format = localDate.format(dateTimeFormatter1);
        System.out.println("Formatted String"+format);
    }
    void parseLocalTime()
    {
        String time="13:00";

        System.out.println(LocalTime.parse(time)); //r.ISO_LOCAL_TIME);

        /*
         public static final DateTimeFormatter ISO_LOCAL_TIME;
    static {
        ISO_LOCAL_TIME = new DateTimeFormatterBuilder()
                .appendValue(HOUR_OF_DAY, 2)
                .appendLiteral(':')
                .appendValue(MINUTE_OF_HOUR, 2)
                .optionalStart()
                .appendLiteral(':')
                .appendValue(SECOND_OF_MINUTE, 2)
                .optionalStart()
                .appendFraction(NANO_OF_SECOND, 0, 9, true)
                .toFormatter(ResolverStyle.STRICT, null);
    }
         */

        //Custom Formta
        String time2= "13*10*12";
        DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("HH*mm*ss");
        LocalTime time1= LocalTime.parse(time2,dateTimeFormatter);
        System.out.println(time1);

    }
    void formatLocalTime()
    {
        DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("HH*mm*ss");
        LocalTime localTime= LocalTime.NOON;
        String format = localTime.format(dateTimeFormatter);
        System.out.println(format);
    }
    void parseLocalDateTime()
    {
        String dateTime="2021-12-24T16:41:00";
        LocalDateTime localDateTime= LocalDateTime.parse(dateTime);
        System.out.println(localDateTime);

        /*
           ISO_LOCAL_DATE_TIME = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(ISO_LOCAL_DATE)
                .appendLiteral('T')
                .append(ISO_LOCAL_TIME)
                .toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
         */


        // Custom Format:
        String dateTime1="2021/12/24T16-41-00";
        DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("yyyy/MM/dd'T'HH-mm-ss");
        LocalDateTime parse = LocalDateTime.parse(dateTime1, dateTimeFormatter);
        System.out.println("Parsed Date:"+ parse);

    }
    void formatLocalDateTime(){

        DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("yyyy/MM/dd'T'HH-mm-ss");
        LocalDateTime localDateTime= LocalDateTime.now();
        String format = localDateTime.format(dateTimeFormatter);
        System.out.println(format);//2021/12/24T16-49-03

        DateTimeFormatter dateTimeFormatter1= DateTimeFormatter.ofPattern("yyyy/MM/dd'Time'HH-mm-ss");
        LocalDateTime localDateTime1= LocalDateTime.now();
        String format1 = localDateTime1.format(dateTimeFormatter1);
        System.out.println(format1); //2021/12/24Time16-49-03
    }
}
