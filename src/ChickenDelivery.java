import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class ChickenDelivery {

    static int N;
    static int M;

    static int[][] map;

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        M = sc.nextInt();

        ArrayList<Point3> chickens = new ArrayList<>();
        ArrayList<Point3> houses = new ArrayList<>();

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

        for (int i = 0; i < size - 2; i++) {

        }



    }

    static int calcDistance(Point3 house) {
        LinkedList<Point3> queue = new LinkedList<>();
        queue.add(house);

        int[][] visit = new int[N][N];
        Point3 closest = new Point3(house.r, house.c);

        outer : while (queue.size() > 0) {
            Point3 point = queue.poll();
            visit[point.r][point.c] = 1;

            for (int i = 0; i < 4; i++) {
                int ddx = point.r + dx[i];
                int ddy = point.c + dy[i];

                if (ddx >= 0 && ddx < N && ddy >= 0 && ddy < N) {
                    if (visit[ddx][ddy] == 0 && map[ddx][ddy] != 2) {
                        queue.add(new Point3(ddx, ddy));
                    } else if (map[ddx][ddy] == 2) {
                        closest = new Point3(ddx, ddy);
                        break outer;
                    }
                }
            }
        }

        return Math.abs(closest.r - house.r) + Math.abs(closest.c - house.c);
    }
}

class Point3 {
    int r;
    int c;

    Point3(int r, int c) {
        this.r = r;
        this.c = c;
    }
}
