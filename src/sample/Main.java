package sample;

import java.util.Scanner;

public class Main {
    public static void main(String[] arg){
        Scanner input = new Scanner(System.in);
        System.out.println("Type your word");
        String inputWord = input.nextLine();
        WordEngine txtMsg = new WordEngine();
        txtMsg.loadFile();
        txtMsg.compare(inputWord);
    }
}
