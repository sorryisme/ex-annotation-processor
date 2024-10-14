package org.example;


public class App {
    public static void main( String[] args ) {
        try {
            MemberDto member = new MemberDto();
        } catch (Error e) {
            e.printStackTrace();
        }
    }
}