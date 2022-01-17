package com.rest;

public class MyClass {

    public static void main(String[] args){
        MyInterface myInterface = new MyClassImpl();
        myInterface.printMe();
    }
}
