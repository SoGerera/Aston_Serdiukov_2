package org.example;

public class Main {
    public static void main(String[] args) {
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        map.put("one", 10);

        System.out.println("one: " + map.get("one"));
        System.out.println("two: " + map.get("two"));
        System.out.println("four: " + map.get("four"));

        // Тестирование проверки наличия ключа
        System.out.println("Contains 'three': " + map.containsKey("three"));
        System.out.println("Contains 'five': " + map.containsKey("five"));

        // Тестирование удаления
        System.out.println("Removed 'two': " + map.remove("two"));
        System.out.println("After remove - 'two': " + map.get("two"));

        // Работа с размерами
        System.out.println("Size: " + map.size());
        System.out.println("Is empty: " + map.isEmpty());

        // Вывод всей мапы
        System.out.println("Map: " + map);

        // Очистка
        map.clear();
        System.out.println("After clear - Size: " + map.size());
        System.out.println("Is empty: " + map.isEmpty());
    }
}
