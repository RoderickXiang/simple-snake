import java.io.*;

public class Point {
    static int point = 0;
    static int bestScore;

    public static void getNormalFoodPoint() {
        point += 50;
    }

    public static void getBonusFoodPoint() {
        point += 100;
    }

    public static void writeResult() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("记录.txt", true));
        bw.write("player" + "\t" + point);
        bw.newLine();
        bw.close();
    }

    public static void readBestResult() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("记录.txt"));
        String line;
        int score;
        while ((line = br.readLine()) != null) {
            String[] tmp = line.split("\t");
            score = Integer.parseInt(tmp[1]);
            if (score > bestScore) {
                bestScore = score;
            }
        }
    }
}
