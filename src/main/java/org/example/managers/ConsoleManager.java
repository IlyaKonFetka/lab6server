package org.example.managers;

import org.example.interfaces.Console;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Класс реализующий логику консоли ввода-вывода
 */
public class ConsoleManager implements Console {
    /**
     * промт
     */
    private static final String P = "$ ";
    /**
     * Сканнер из файла
     */
    private Scanner fileScanner;
    /**
     * Дефолтный сканнер из консоли
     */
    private static final Scanner defScanner = new Scanner(System.in);

    /**
     * @see Console#print(Object)
     * @param obj
     */
    @Override
    public void print(Object obj) {
        System.out.print(obj);
    }

    /**
     * @see Console#println(Object)
     * @param obj
     */
    @Override
    public void println(Object obj) {
        System.out.println(obj);
    }

    /**
     * @see Console#read()
     */
    @Override
    public String read() throws NoSuchElementException, IllegalStateException {
        if (fileScanner != null){
            if (fileScanner.hasNext())
                return fileScanner.next();
            return "exit";
        }else return defScanner.next();
    }
    /**
     * @see Console#readln()
     */
    @Override
    public String readln() throws NoSuchElementException, IllegalStateException {
        if (fileScanner != null){
            if (fileScanner.hasNextLine())
                return fileScanner.nextLine();
            return "exit";
        }else return defScanner.nextLine();
    }
    /**
     * @see Console#isCanReadln()
     */
    @Override
    public boolean isCanReadln() throws IllegalStateException {
        return (fileScanner != null ? fileScanner : defScanner).hasNextLine();
    }

    /**
     * @see Console#printWarning(Object)
     */
    @Override
    public void printWarning(Object obj) {
        String brightYellowColorCode = "\u001B[33;1m";
        String resetColorCode = "\u001B[0m";
        System.out.println(brightYellowColorCode + obj + resetColorCode);
    }
    /**
     * @see Console#printSuccessful(Object)
     */
    @Override
    public void printSuccessful(Object o) {
        String brightGreenColorCode = "\u001B[32;1m";
        String resetColorCode = "\u001B[0m";
        System.out.println(brightGreenColorCode + o + resetColorCode);
    }
    /**
     * @see Console#printError(Object)
     */
    @Override
    public void printError(Object o) {
        String brightRedColorCode = "\u001B[31;1m";
        String resetColorCode = "\u001B[0m";
        System.out.println(brightRedColorCode + o + resetColorCode);
    }

    /**
     * @see Console#prompt()
     */
    @Override
    public void prompt() {
        System.out.print(P);
    }

    /**
     * @see Console#selectFileScanner(Scanner)
     */
    @Override
    public void selectFileScanner(Scanner scanner) {
        fileScanner = scanner;
    }
    /**
     * @see Console#selectConsoleScanner()
     */
    @Override
    public void selectConsoleScanner() {
        fileScanner = null;
    }
}