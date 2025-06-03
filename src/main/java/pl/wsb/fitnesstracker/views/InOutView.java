package pl.wsb.fitnesstracker.views;

/**
 * Definicja widoków
 * output - wszystkie pola widoczne
 * input - ukryte pole z ID encji (ID treningu, ID użytkownika...)
 *         nie chcę go wyświetlać w body operacji POST, bo ID jest
 *         już przekazywane jako parametr w URI
 */

public class InOutView {
    public static class Input {}
    public static class Output extends Input {}
}
