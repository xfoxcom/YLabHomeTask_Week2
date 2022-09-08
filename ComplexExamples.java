
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class ComplexExamples {

    static class Person {
        final int id;

        final String name;

        Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Person person)) return false;
            return getId() == person.getId() && getName().equals(person.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getName());
        }
    }

    private static Person[] RAW_DATA = new Person[]{
            new Person(0, "Harry"),
            new Person(0, "Harry"), // дубликат
            new Person(1, "Harry"), // тёзка
            new Person(2, "Harry"),
            new Person(3, "Emily"),
            new Person(4, "Jack"),
            new Person(4, "Jack"),
            new Person(5, "Amelia"),
            new Person(5, "Amelia"),
            new Person(6, "Amelia"),
            new Person(7, "Amelia"),
            new Person(8, "Amelia"),
            new Person(9, null),
            null
    };
        /*  Raw data:

        0 - Harry
        0 - Harry
        1 - Harry
        2 - Harry
        3 - Emily
        4 - Jack
        4 - Jack
        5 - Amelia
        5 - Amelia
        6 - Amelia
        7 - Amelia
        8 - Amelia

        **************************************************

        Duplicate filtered, grouped by name, sorted by name and id:

        Amelia:
        1 - Amelia (5)
        2 - Amelia (6)
        3 - Amelia (7)
        4 - Amelia (8)
        Emily:
        1 - Emily (3)
        Harry:
        1 - Harry (0)
        2 - Harry (1)
        3 - Harry (2)
        Jack:
        1 - Jack (4)
     */

    public static void main(String[] args) {
//        System.out.println("Raw data:");
//        System.out.println();

//        for (Person person : RAW_DATA) {
//            System.out.println(person.id + " - " + person.name);
//        }

        System.out.println("Task 1.");
//        System.out.println("**************************************************");
//        System.out.println();
        System.out.println("Duplicate filtered, grouped by name, sorted by name and id:");
        System.out.println();

        /*
        Task1
            Убрать дубликаты, отсортировать по идентификатору, сгруппировать по имени

            Что должно получиться
                Key:Amelia
                Value:4
                Key: Emily
                Value:1
                Key: Harry
                Value:3
                Key: Jack
                Value:1
         */

        Arrays.stream(RAW_DATA).filter(p -> p != null && p.getName() != null).distinct().sorted(Comparator.comparing(Person::getId).thenComparing(Person::getName))
                .collect(groupingBy(Person::getName, Collectors.counting())).forEach((k,v) -> System.out.println("Key:" + k + "\nValue:" + v));

        System.out.println();


        /*
        Task2

            [3, 4, 2, 7], 10 -> [3, 7] - вывести пару менно в скобках, которые дают сумму - 10
         */

        System.out.println("Task 2.");
        System.out.println("Pair: " + Arrays.toString(twoSum(new int[]{3, 4, 2, 7}, 10)));
        System.out.println();


       // Task3
          //  Реализовать функцию нечеткого поиска

        System.out.println("Task 3.");
        boolean f0 = fuzzySearch("car", "ca6$$#_rtwheel"); // true
        boolean f1 = fuzzySearch("cwhl", "cartwheel"); // true
        boolean f2 = fuzzySearch("cwhee", "cartwheel"); // true
        boolean f3 = fuzzySearch("cartwheel", "cartwheel"); // true
        boolean f4 = fuzzySearch("cwheeel", "cartwheel"); // false
        boolean f5 = fuzzySearch("lw", "cartwheel"); // false
        System.out.println("Results: \n" + f0 + " " + f1 + " " + f2 + " " + f3 + " " + f4 + " " + f5);
    }

    public static boolean fuzzySearch(String what, String where) {
        if (what.length() > where.length()) return false;
        if (what.charAt(0) == where.charAt(0)) {
            if (what.substring(1).length() == 0) return true;
           return fuzzySearch(what.substring(1), where.substring(1));
        }
        return fuzzySearch(what, where.substring(1));
    }

    public static int[] twoSum(int[] numbers, int target) {
        Map<Integer, Integer> resultAndNumber = new HashMap<>();
        for (int j : numbers) {
            Integer number = resultAndNumber.get(j);
            if (number != null) return new int[]{number, j};
            resultAndNumber.put(target - j, j);
        }
        return new int[]{};
    }
}
