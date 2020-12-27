package com.company;

import javax.swing.plaf.SpinnerUI;
import java.io.PrintStream;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        //Part2();
        //Part3();
        //Part4();
        //Part5();
        //Part6();
        Part7();
    }

    private static void Part7() {

        // bound and unbound method references.
        Consumer<String> stringConsumer = System.out::println;
        stringConsumer.accept("Bound");

        BiConsumer<PrintStream, String> stringBiConsumer = PrintStream::println;
        stringBiConsumer.accept(System.out,"unBound");
        
        //static method references
        Supplier<Thread> currentThread = Thread::currentThread;
        System.out.println(currentThread.get());

        //Constructor method reference
        Supplier<Calendar> calendarSupplier = GregorianCalendar::new;
        Calendar cal = calendarSupplier.get();
        System.out.println("Calendar: "+cal);


    }

    private static void Part6() {
        Exam javaCert = new Exam("JavaCert");
        Exam maven101 = new Exam("MavenCert");
        Exam apache = new Exam("apacheCert");


        List<StudentExamResult> results = new LinkedList<>();
        results.add(new StudentExamResult(56, "Ali", maven101));
        results.add(new StudentExamResult(72, "Veli", javaCert));
        results.add(new StudentExamResult(26, "Alptekin", javaCert));
        results.add(new StudentExamResult(72, "Seçkin", maven101));
        results.add(new StudentExamResult(88, "Mahmut", javaCert));
        results.add(new StudentExamResult(92, "Osman", apache));
        results.add(new StudentExamResult(90, "Osman", javaCert));


        System.out.println(results);

        List<String> topStudents = results.stream()
                .filter(p -> p.roundedPercentage > 70)
                .map(p -> p.name)
                .sorted()
                .distinct()
                .collect(Collectors.toList());

        System.out.println(topStudents);


    }

    static class StudentExamResult {
        int roundedPercentage;
        String name;
        Exam exam;

        public StudentExamResult(int roundedPercentage, String name, Exam exam) {
            this.roundedPercentage = roundedPercentage;
            this.name = name;
            this.exam = exam;
        }

        @Override
        public String toString() {
            return "StudentExamResult{" +
                    "roundedPercentage=" + roundedPercentage +
                    ", name='" + name + '\'' +
                    ", exam=" + exam +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            StudentExamResult that = (StudentExamResult) o;
            return roundedPercentage == that.roundedPercentage && Objects.equals(name, that.name) && Objects.equals(exam, that.exam);
        }

        @Override
        public int hashCode() {
            return Objects.hash(roundedPercentage, name, exam);
        }
    }

    static class Exam {
        String name;

        public Exam(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Exam{" +
                    "name='" + name + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Exam exam = (Exam) o;
            return Objects.equals(name, exam.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    private static void Part5() {
        Runnable runnable = () -> System.out.println("Hello Lamda");
        runnable.run();

        Runnable runnable2 = () -> {
            System.out.println("Hello Lamda 1");
            System.out.println("Hello Lamda 2");
        };
        runnable2.run();

        //Ders 19
        StringRunnable stringRunnable = name -> System.out.println("Merhaba " + name);
        stringRunnable.run("Alptekin");

        Consumer<String> stringConsumer = greetings -> System.out.println(greetings);
        Consumer<String> stringConsumer2 = System.out::println;
        stringConsumer2.accept("1");
        stringConsumer.accept("2");

        Supplier<Integer> integerSupplier = () -> 42;
        System.out.println("-> " + integerSupplier.get());

        Function<String, Integer> stringIntegerFunction = name -> Integer.parseInt(name);
        System.out.println("stringIntegerFunction: " + stringIntegerFunction.apply("5"));

        Function<Integer, String> integerStringFunction = age -> age > 21 ? "Yes" : "NO";
        System.out.println("stringIntegerFunction: " + integerStringFunction.apply(10));
        System.out.println("stringIntegerFunction: " + integerStringFunction.apply(30));

        Predicate<Person> personPredicate = person -> person.getAge() >= 65;
        System.out.println("personPredicate: " + personPredicate.test(new Person("Ali", 6)));
        System.out.println("personPredicate: " + personPredicate.test(new Person("Veli", 66)));

    }

    @FunctionalInterface
    public interface StringRunnable {

        public abstract void run(String s);
    }

    private static String myMethod() {
        return "Benim şifrem";
    }

    private static void Part4() {
        System.out.println("-------------------------------------------");
        /*
        Optional API
        Create Optional
        Check for their nullity
        check for the values they contain
        supply default values
        Conditional return varlues and Map

        Creatinf optional with values
        Optional.of

        without values
        optional.empty()

        with nullable values
        Optional.ofNullable()
        allows null dont throw nullpointerexcaption

        isPresent() -> return an object if optional holds an object
        isEmpty() -> return true if optional refers to null

        to get accesing value use .get() but before use isPresent or isEmpty

        .orElse() -> dönmesi istenen değer dönmemişse default value döner
        .orElseGet() -> dönmesi gereken değer dönmemiş se default fonksiyon çağrılır örnekler aşağıda.
        .orElseThrow() -> yoksa excaption yarat.

        Mapping optional values

        map -> map always return an Optional of the result
        usr map when the mathod you pass return the object itself
        flatMap -> use flatMap when the method you pass returns an Optional of the object awoid double warapping
        isue optinal of optional

        interafe içerisindeki default ve static methodlar
        java 8 ile bunlarda gelmiştir eskiden olduğu gibi interface içindeki abstract methodlar sadece olmuuş olsa
        lamda exp ile bunlar kullanılamazdı çünkü abstract methodların çağrıldıkları class içerisinde implemente olmaları
        gerekir bu nedenle default ve static gelmiştir implement gerektirmez direk çağrılabilirler.

        Types of method referances :: olayı.

        intance method referances
        bound and unbound
        örnek aşağıda;

        Consttructor Method referances

         */
        String name = "Alptekin";
        Optional<String> optionalS = Optional.of(name); // buradaki sadece eğitim amaçlı anlamsız.

        String userName = optionalS.orElse("Deneme");
        String password = optionalS.orElseGet(() -> myMethod());

        Optional<String> optionalS1 = Optional.of("Deneme"); // buradaki sadece eğitim amaçlı anlamsız.
        Optional<String> optionalS2 = optionalS1.map(s -> s.toUpperCase());
        String sonuc = optionalS2.get();


        Optional<Person> optionalPerson = Optional.of(new Person("Alptekin", 20));
        boolean isTeen = optionalPerson.map(p -> p.getAge())
                .filter(p -> p >= 10)
                .filter(p -> p <= 25)
                .isPresent();

        System.out.println("isTeen: " + isTeen);

        // static method referances String in int out
        Function<String, Integer> stringStringFunction = Integer::parseInt;
        System.out.println(stringStringFunction.apply("77"));

        // unbound method ref String in String out
        Function<String, String> stringStringFunction1 = String::toUpperCase;
        System.out.println(stringStringFunction1.apply("asd"));

        //bound ref type
        String mesaj1 = "bunlar küçük harf";
        Supplier<String> stringStringFunction2 = mesaj1::toUpperCase;
        System.out.println(stringStringFunction2.get());

        //constructr ref type
        Supplier<List<String>> listSupplier = LinkedList::new;
        List<String> strings = listSupplier.get();


    }


    private static void Part3() {
        System.out.println("-------------------------------------------");
        /*
        Creating Stream
        Create them with;
        mySet.stream or myList.stream for collections sets and lists
        Stream.of for arrays
        Can stream Maps too, just not directly
        (use set return method instead eg entrySet and stream from that)
        Stream API owerview
        Intermadita Operaions;
        Filtering elements of the Stream;
        filter the filter elements of the stream (pass a predicate)

        Mapping elements of the stream; -> one format to another example Employee objects just name
        map to map elements of the stream (pass a function)

        Other operations
        .sorted()
        .distinct()

        Terminal operations;
        creating result collections;
        toSet() -> creates Set of the elements in the stream
        toList() -> cretes List of the elements int the stream

        Testing elements in stream;
        allMatch/anyMatch to test elements in the stream pass a predicate return boolean
        noneMatch tests all elements to check a condition isnt true
        firstMatch return an optional of the first predicate passing elements in the stream
         */
        ArrayList arrayList = new ArrayList();
        arrayList.add("Alptekin");
        arrayList.add("Ahmet");
        arrayList.add("Tuna");
        arrayList.add("Tuna");


    }

    private static void Part2() {
        System.out.println("-------------------------------------------");
        Runnable lamda1 = () ->
        {
            System.out.println("Lamda1");
            System.out.println("Lamda2");
        };
        lamda1.run();

        IntBinaryOperator intBinaryOperator = (int a, int b) -> a * b;
        System.out.println(intBinaryOperator.applyAsInt(2, 5));
        DoubleBinaryOperator intBinaryOperator2 = (a, b) -> a * b;
        System.out.println(intBinaryOperator2.applyAsDouble(2D, 5D));

        // string in string out
        Function<String, String> stringStringFunction = (String a) -> a;
        System.out.println(stringStringFunction.apply("Deneme"));

        String b = "A";
        Supplier<String> supplier = () -> b;
        System.out.println(supplier.get());

    }
}

