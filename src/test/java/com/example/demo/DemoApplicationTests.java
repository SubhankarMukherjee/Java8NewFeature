package com.example.demo;

import net.minidev.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sound.midi.Soundbank;
import java.util.*;
import java.util.function.*;
import java.util.stream.IntStream;

//@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testjava8() {

        //sum 1 to 100
        int sum = IntStream.rangeClosed(0, 100).
                parallel().     // multi threaded env
                        sum();
        System.out.println("Sum :" + sum);

        //Unique List

        List<Integer> uniqueList = Arrays.asList(1, 2, 2, 2, 2, 24, 1, 5, 5, 5, 6, 7, 6);
        uniqueList.stream().distinct().forEach(System.out::println);

    }

    @Test
    void Lamda() {
        // create runnbale unterface prior to java 8

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println("value of i is :" + i);
                }
            }
        };

        Thread t1 = new Thread(runnable);
        t1.start();

        //post java 8

        new Thread(() -> IntStream.rangeClosed(0, 10).forEach(System.out::println)).start();
        //or
        Runnable runnable1 = () -> System.out.println("hello from runnable");
        new Thread(runnable1).start();


        //Compartor before java 8

        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);  //0 in case of equal
            }
        };
        System.out.println("Compartor Result:" + comparator.compare(1, 3));
        System.out.println("Compartor Result:" + comparator.compare(1, 1));
        System.out.println("Compartor Result:" + comparator.compare(1, 0));


        //with Java 8

        Comparator<Integer> comparator1 = (o1, o2) -> o1.compareTo(o2);
        System.out.println("Compartor8 Result:" + comparator1.compare(1, 1));
        System.out.println("Compartor8 Result:" + comparator1.compare(1, 0));
    }


    // Consume chaining for-each is taking consume
    @Test
    void consumerTest() {
//        Consumer<String> consumerString= new Consumer<String>() {
//            @Override
//            public void accept(String s) {
//                System.out.println(s);
//            }
//        };

        Consumer<String> consumerString = (s) -> System.out.println(s);
        Consumer<String> consumerToUpstring = (s1) -> System.out.println(s1.toUpperCase());
        consumerString.accept("Hello");

        Consumer<Student> consumerStudent = (s) -> System.out.println(s);
        Consumer<Student> consumerActvities = (s1) -> System.out.println(s1.getActivities());
        List<Student> students = StudentDB.getStudents();
        students.forEach(consumerStudent.andThen(consumerActvities));
        //Sort by Age and then Score


        System.out.println("Consumer Chaining");
        consumerStudent.andThen(consumerActvities).accept(students.stream()
                .filter(s -> s.getName() == "Sam").findFirst().get());


        //Comparator Chaining
        Comparator<Student> comparator1 = (student1, student2) -> {
            if (student1.getAge() == student2.getAge()) return 0;
            return (student1.getAge() > student2.getAge()) == true ? 1 : -1;
        };

        Comparator<Student> comparator2 = (student1, student2) -> {
            if (student1.getGrade() == student2.getGrade()) return 0;
            return (student1.getGrade() > student2.getGrade()) == true ? 1 : -1;
        };
        //Consumer<Student> consumerGrades = (student)-> System.out.println(student.getGrade());
        System.out.println("**********************************");
        students.stream().sorted(comparator1.thenComparing(comparator2)).forEach(System.out::println);
    }

    @Test
    void BiConsumerTest() {
        List<Student> students = StudentDB.getStudents();

        BiConsumer<String, List<String>> biConsumer = (name, activities) -> System.out.println("Student Name: " + name + " , activitie are:" + activities);
        students.forEach(student -> biConsumer.accept(student.getName(), student.getActivities()));

    }

    //Predicate take input and return boolean //other functions are negate and or
    @Test
    void testPredicate() {
        //given a int check evem or odd
//        Predicate<Integer> predicate= new Predicate<Integer>() {
//            @Override
//            public boolean test(Integer integer) {
//                return integer%2==0?true:false;
//            }
//        };

        //Predicate<Integer> predicate= i-> {return i %2==0};
        Predicate<Integer> predicate = i -> i % 2 == 0;
        System.out.println(predicate.test(10));
        System.out.println(predicate.test(1));  // this thing can be eaisly done but advantage of predicate is code reusablity

        //use and and or method predicate chaining

        Predicate<Integer> p = i -> i % 3 == 0;
        Predicate<Integer> p1 = i -> i % 5 == 0;
        System.out.println("FIZBUZZ:" + p1.and(p).test(15));
        System.out.println("FIZBUZZ or expected true:" + p1.or(p).test(20));

        //negative test not divisible by 3 or  5
        //nagate is reverse the result
        System.out.println("FIZBUZZ or expected true:" + p1.or(p).negate().test(19));
    }

    @Test
    void predicateTestStudent() {
        Predicate<Student> p = student -> student.getAge() > 15;
        StudentDB.getStudents().forEach(student -> System.out.println("Student -" + student.getName() + " age is >15 ?" + p.test(student)));


        Predicate<Student> p1 = student -> student.getAge() > 15;
        Predicate<Student> p2 = student -> student.getGrade() > 75;
        System.out.println("*****************************");
        StudentDB.getStudents().forEach(student -> {
            if (p1.and(p2).test(student))
                System.out.println(student);
        });


        // get the student details whose activities are not swimming
        Predicate<Student> p3 = student -> {
            return (student.getActivities().contains("swimming"));
        };
        System.out.println("************");
        StudentDB.getStudents().forEach(student -> {
            if (p3.negate().test(student))
                System.out.println(student);
        });
    }

    @Test
    void PredicateConsumerTogether() {
        // get age> 15 and grade >75 in preocate and print their name and activities as consumer

        Predicate<Student> p1 = student -> student.getAge() > 15;
        Predicate<Student> p2 = student -> student.getGrade() > 75;
        BiConsumer<String, List<String>> biConsumer = (name, activities) -> System.out.println("Name:" + name + " , activities: " + activities);

        StudentDB.getStudents().forEach(student -> {
            if (p1.and(p2).test(student)) {
                biConsumer.accept(student.getName(), student.getActivities());
            }
        });

    }

    @Test
    void bipredicateTest() {
        //Predicate<Student> p1 = student -> student.getAge() > 15;
        //Predicate<Student> p2 = student -> student.getGrade() > 75;
        BiPredicate<Student, Student> biPredicate = ((student, student2) -> student.getAge() > 15 && student2.getGrade() > 75);
        BiConsumer<String, List<String>> biConsumer = (name, activities) -> System.out.println("Name:" + name + " , activities: " + activities);

        StudentDB.getStudents().forEach(student -> {
            if (biPredicate.test(student, student)) {
                biConsumer.accept(student.getName(), student.getActivities());
            }
        });
    }

    @Test
    void functionaTest() {

        // r apply(T t)
        Function<String, Integer> f = (name) -> name.length();
        System.out.println(f.apply("Subhankar"));


        Function<String, String> f1 = (name) -> "Hello " + name;
        Function<String, String> f2 = (name) -> name.toUpperCase();

        // And Then will use first operation first
        System.out.println(f1.andThen(f2).apply("Subhankar"));
        //Compose will execute 2nd operation first
        System.out.println(f1.compose(f2).apply("Subhankar"));
    }

    @Test
    void functionaTestOnStudent() {

        // create a student map with name and grade if grade is>75
        Predicate<Student> p = student -> student.getGrade() > 75;

        Function<List<Student>, Map<String, String>> f = (students -> {
            HashMap<String, String> map = new HashMap<String, String>();
            students.forEach(student -> {
                if (p.test(student))
                    map.put(student.getName(), String.valueOf(student.getGrade()));

            });
            return map;
        });


        System.out.println(f.apply(StudentDB.getStudents()));

    }

    @Test
    void biFunctionTest() {
        // R apply(T t, U u)
        // Bi Funciona does mpt have other method, it is only having and Then method
        //USe case: Same like above

        BiFunction<List<Student>, Predicate<Student>, Map<String, Double>> f = (students, studentPredicate) -> {
            Map<String, Double> map = new HashMap<String, Double>();
            students.forEach(student -> {
                if (studentPredicate.test(student))
                    map.put(student.getName(), student.getGrade());
            });
            return map;

        };

        Predicate<Student> p = student -> student.getGrade() > 80;
        System.out.println(f.apply(StudentDB.getStudents(), p));

    }

    @Test
    void uniaryOperatorFunctionalInterfaceTest() {
        //T UnaryOperator(T t)  input and output are of same type
        UnaryOperator<String> unaryOperator = name -> name.concat(" welcome");
        System.out.println(unaryOperator.apply("Subhankar"));


    }

    @Test
    void binaryOperatorFunctionalInterfaceTest() {
        // T binaryOperator(T t, T t1)
        // takes 2 input of same type and return same type output
        // it has minby and maxby 2 static methods which accepts Compartor

        BinaryOperator<Integer> binaryOperator = (integer, integer2) -> integer * integer2;
        System.out.println(binaryOperator.apply(10, 40));

        Comparator<Integer> comparator = (a, b) -> a.compareTo(b);
        System.out.println(BinaryOperator.maxBy(comparator).apply(10, 40));
        System.out.println(BinaryOperator.minBy(comparator).apply(10, 40));
    }

    @Test
    void supplierFunctionalInterface() {
        //T get();
        Supplier<Student> studentSupplier = () -> {
            return new Student("Sam", Arrays.asList("painting", "basketball"), 20, 78.3,11);
        };

        System.out.println(studentSupplier.get());

        Supplier<List<Student>> students = () -> StudentDB.getStudents();
        System.out.println(students.get());
    }

    @Test
    void method_overriddenceTest() {

        //short cut of lamda.. lamda help us to overcome annoymous inner class , this is even better
        // Classname::instanceMethodname
        //classname::static method
        //instance::method name

        //(s)->s.toUpperCase();   --------->   String::toUpperCase()

        // Function<String, String> f =(s)->s.toUpperCase(Locale.ROOT);
        Function<String, String> f = String::toUpperCase;
        System.out.println(f.apply("Subhankar"));
    }

    @Test
    void consumerMethodOverriddence() {

            Consumer<Student> consumer= System.out::println;
            StudentDB.getStudents().forEach(consumer);
    }
    @Test
    void refactorMethodReference()
    {
       // Predicate<Student> studentPredicate= p->p.getGrade()>75;
        //System.out.println(studentPredicate.test(StudentDB.studentSupplier.get()));

        //refactor and add above logic in method to use method reference
         Predicate<Student> studentPredicate= DemoApplicationTests::gradeGreaterThanThree;
        System.out.println(studentPredicate.test(StudentDB.studentSupplier.get()));

    }
    public static boolean gradeGreaterThanThree(Student s)
    {
        return s.getGrade()>75;

    }

    @Test
    void constructor_Reference()
    {
        // ClassName::new
        // it can be only used for Functional Interfaces
        // Valid:
       // Supplier<Student> s= Student::new;
        //Invalid
        //Student student = Student::new;  // Student is not functional interfaces



        Supplier<Student> supplier= Student::new;
        System.out.println(supplier.get()); // Empty Object:com.example.demo.Student{name='null', activities=null, age=0, grade=0.0}

        Function<String,Student> f= Student::new;
        System.out.println(f.apply("Subhankar")); //com.example.demo.Student{name='Subhankar', activities=null, age=0, grade=0.0}
        //Already Constudctor with only name defined thats why it is working


    }
    @Test
    void lamdaRestrction()
    {
//        int i=0;
//        Consumer<Integer> c= i-> System.out.println(i);
        // it will not compile as I is already set as local varibale
//        Consumer<Integer> c= i-> {
//            int i =2;
//            System.out.println(i);
//        };
        //change of local varibale i is aslo not possible inside lamda


            int value=4;
            Consumer<Integer> consumer= i->{
               // value++; not possible variable used inlamda should be final or effectivity final.
                System.out.println(value+ i);
            };
            //value=6; this is also not allowed
        // if value is instance varibale i. e defined outside method scope there are no restriction
        consumer.accept(4);

        // Lamdas are allowed to use local variable but not allowed to modify even though they are not defined as final this concept is called
        //effectivtly final
        // Advantage
        // Easy to perform concurrent operation
        // Promotes functional programming and demotes the imperetive style of programming



    }
}