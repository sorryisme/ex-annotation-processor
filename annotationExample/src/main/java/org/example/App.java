package org.example;

import org.example.study.MagicMoja;
import org.example.study.Moja;

public class App {
    public static void main( String[] args ) {
        Moja moja = new MagicMoja();
        System.out.println(moja.pullOut());
    }
}
