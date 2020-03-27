import java.io.*;

public class Main {

    public static void main(String[] args) {
        try {
            FileOutputStream fileOut = new FileOutputStream("katintiedot.txt");
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

            Kissa katti = new Kissa();
            System.out.println("Kissan tiedot: " + katti.getHanta());
            System.out.println("Kissa luetut tiedot: " + katti.getLuonne());

            objOut.writeObject(katti);
            objOut.close();


        } catch (Exception e) {
        }

        try {
            FileInputStream fileIn = new FileInputStream("katintiedot.txt");
            ObjectInputStream objIn = new ObjectInputStream(fileIn);

            Kissa katti2;

            katti2 = (Kissa) objIn.readObject();
            objIn.close();

            System.out.println("Kissa luetut tiedot: " + katti2.getHanta());
            System.out.println("Kissa luetut tiedot: " + katti2.getLuonne());

        } catch (Exception e) {
        }

    }
}