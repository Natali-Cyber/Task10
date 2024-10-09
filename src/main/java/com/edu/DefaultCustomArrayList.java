package com.edu;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DefaultCustomArrayList<E> implements CustomArrayList<E> {
    private Object[] elements; // Массив для хранения элементов списка
    private int size; // текущее количество элементов в списке
    private static final int DEFAULT_CAPACITY = 1; // Начальная емкость для массива, изначально устанавливается на 1

    // Конструктор инициализирует массив с заданной начальной ёмкостью
    public DefaultCustomArrayList() {
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    // add(E element) Добавляет элемент в конец списка, увеличивая размер массива при необходимости.
    @Override
    public boolean add(E element) {
        if (size == elements.length) {
            resize(); // Увеличиваем размер, если массив полон
        }
        elements[size++] = element; // Добавляем элемент в конец и увеличиваем размер
        return true;
    }

    // Ищет элемент в массиве, если находит, вызывает `removeAt(int index)`, чтобы удалить его.
    @Override
    public boolean remove(E element) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(element)) {
                removeAt(i);
                return true;
            }
        }
        return false;
    }

    /* Убирает элемент по указанному индексу.
    Остальные элементы сдвигаются, чтобы заполнить пробел, и освобождает ссылку на последний элемент.
     */
    private void removeAt(int index) {
        if (index < size) {
            System.arraycopy(elements, index + 1, elements, index, size - index - 1);
            elements[--size] = null; // Уменьшаем размер и освобождаем последний элемент
        }
    }

    /* Возвращает элемент по индексу. Проверяет, что индекс находится в допустимом диапазоне.
    Бросая исключение при неправильных индексах.
    */
    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (E) elements[index];
    }

    // Метод возвращает текущее количество элементов.
    @Override
    public int size() {
        return size;
    }

    // Метод проверяет, пустой ли список, сравнивая размер с нулем.
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // Очищает весь список, освобождая ссылки на элементы и устанавливая размер в ноль.
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null; // Удаляем ссылки на элементы
        }
        size = 0;
    }

    // Метод проверяет, содержится ли данный элемент в списке, сравнивая его с элементами массива.
    @Override
    public boolean contains(E element) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(element)) {
                return true;
            }
        }
        return false;
    }

    // Метод увеличивает размер массива вдвое, когда он заполняется.
    private void resize() {
        int newSize = elements.length * 2; // Увеличиваем размер в 2 раза
        Object[] newArray = new Object[newSize];
        System.arraycopy(elements, 0, newArray, 0, elements.length);
        elements = newArray;
    }

    /*
    Позволяет использовать класс в цикле `for-each`. Возвращает новый экземпляр итератора, который перебирает элементы.
    */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (E) elements[currentIndex++];
            }
        };
    }
}