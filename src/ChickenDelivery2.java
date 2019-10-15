import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class ChickenDelivery2 {

    static int N;
    static int M;

    static int[][] map;
    static int answer = Integer.MAX_VALUE;

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    static int[] visit;

    static ArrayList<Point3> chickens;
    static ArrayList<Point3> houses;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        M = sc.nextInt();

        chickens = new ArrayList<>();
        houses = new ArrayList<>();
        map = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int tile = sc.nextInt();
                map[i][j] = tile;

                if (tile == 2) {
                    chickens.add(new Point3(i, j));
                } else if (tile == 1) {
                    houses.add(new Point3(i, j));
                }
            }
        }

        int size = chickens.size();
        visit = new int[size];

        dfs(visit, -1);

        System.out.println(answer);

    }

    static void dfs(int[] visit, int index) {

        int[] newVisit = new int[visit.length];
        int count = 0;
        for (int i = 0; i < visit.length; i++) {
            newVisit[i] = visit[i];
            if (visit[i] == 1) {
                count++;
            }
        }

        if (count == M) {

//            System.out.println("newVisit : " + Arrays.toString(newVisit));
//
//            System.out.println("count " + count);

            int sum = 0;
            for (int i = 0; i < houses.size(); i++) {

                    Point3 house = houses.get(i);

                    int min = Integer.MAX_VALUE;

                    for (int j = 0; j < chickens.size(); j++) {
                        if (newVisit[j] == 1) {
                            Point3 chicken = chickens.get(j);
                            min = Math.min(min, Math.abs(chicken.r - house.r) + Math.abs(chicken.c - house.c));
                        }
                    }

                    sum += min;

            }

//            System.out.println("sum " + sum);
//            System.out.println();

            answer = Math.min(sum, answer);
        }


        if (index == visit.length - 1) {
            return;
        }

        newVisit[index + 1] = 1;
        dfs(newVisit, index + 1);

        newVisit[index + 1] = 0;
        dfs(newVisit, index + 1);


    }
}
