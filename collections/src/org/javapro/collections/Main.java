//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
package org.javapro.collections;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Integer resInt;

        //Найдите в списке целых чисел 3-е наибольшее число
        List<Integer> numbers = Arrays.asList(5, 2, 10, 9, 12, 12, 12, 16, 16, 13, 4, 3, 10, 1);
        resInt = findThirdLargest(numbers);
        System.out.println("The third largest number is: " + (resInt == null ? "n/a" : resInt));

        //Найдите в списке целых чисел 3-е наибольшее уникальное число
        resInt = findThirdLargestUnique(numbers);
        System.out.println("The third unique largest number is: " + (resInt == null ? "n/a" : resInt));

        List<Employee> employees = Arrays.asList(
                new Employee("Alice", 30, Employee.Position.ENGINEER),
                new Employee("Bob", 32, Employee.Position.ENGINEER),
                new Employee("Charlie", 50, Employee.Position.ENGINEER),
                new Employee("David", 35, Employee.Position.MANAGER),
                new Employee("Marry", 35, Employee.Position.DIRECTOR),
                new Employee("Eve", 45, Employee.Position.ENGINEER),
                new Employee("Mark", 55, Employee.Position.ENGINEER),
                new Employee("Max", 32, Employee.Position.ENGINEER)
        );

        // необходимо получить список имен 3 самых старших сотрудников с должностью «Инженер», в порядке убывания возраста
        System.out.println("Three eldest engineers are: " + findTopThreeEldestEngineers(employees));

        // Посчитайте средний возраст сотрудников с должностью «Инженер»
        double avgAge = calculateAverageAgeOfEngineers(employees);
        System.out.println("Average age of engineers is: " + (avgAge < 0.0 ? "n/a" : (int) avgAge));

        // Найдите в списке слов самое длинное
        List<String> words = Arrays.asList("apple", "banana", "banana", "banana", "cherry", "cherry", "date", "supervisor");
        String rWord = findLongestWord(words);
        System.out.println("The longest word is: " + (rWord.isEmpty() ? "n/a" : rWord));

        // Имеется строка с набором слов в нижнем регистре, разделенных пробелом. Постройте хеш-мапы, в которой будут хранится пары: слово - сколько раз оно встречается во входной строке
        String sentence = "apple banana apple orange banana apple";
        System.out.println("Words counting in hash-map: " + buildWordFrequencyMap(sentence));

        // Отпечатайте строки в порядке увеличения длины слова
        List<String> sentences = Arrays.asList("big dog", "long cat", "elephant is big", "antelopes are fast and first", "bat is speedy");
        printSortedSentences(sentences);

        // Найдите среди всех слов самое длинное, если таких слов несколько, получите любое из них
        String[] wordArrays = {"one two three longestword five", "six seven eight nine ten", "eleven twelve thirteen fourteen fifteen"};
        rWord = findLongestWordInArray(wordArrays);
        System.out.println("The longest word in array is: " + (rWord.isEmpty() ? "n/a" : rWord));
    }

    private static Integer findThirdLargest(List<Integer> numbers) {
        return numbers.stream()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst()
                .orElse(null);
    }

    private static Integer findThirdLargestUnique(List<Integer> numbers) {
        return numbers.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst()
                .orElse(null);
    }

    private static List<String> findTopThreeEldestEngineers(List<Employee> employees) {
        return employees.stream()
                .filter(e -> e.getPosition().equals(Employee.Position.ENGINEER))
                .sorted(Comparator.comparing(Employee::getAge).reversed())
                .map(Employee::getName)
                .limit(3)
                .collect(Collectors.toList());
    }

    private static double calculateAverageAgeOfEngineers(List<Employee> employees) {
        return employees.stream()
                .filter(e -> e.getPosition().equals(Employee.Position.ENGINEER))
                .mapToInt(Employee::getAge)
                .average()
                .orElse(-1.0);
    }

    private static String findLongestWord(List<String> words) {
        return words.stream()
                .max(Comparator.comparingInt(String::length))
                .orElse("");
    }

    private static Map<String, Long> buildWordFrequencyMap(String sentence) {
        return Arrays.stream(sentence.split(" "))
                .collect(Collectors.groupingBy(word -> word, Collectors.counting()));
    }

    private static void printSortedSentences(List<String> sentences) {
        sentences.stream().map(s -> Arrays.stream(s.split(" "))
                .sorted(Comparator.comparing(String::length).thenComparing(Comparator.naturalOrder())).collect(Collectors.joining(" ")))
                .forEach(System.out::println);
    }

    private static String findLongestWordInArray(String[] wordArrays) {
        return Arrays.stream(wordArrays)
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .max(Comparator.comparingInt(String::length))
                .orElse("");
    }
}