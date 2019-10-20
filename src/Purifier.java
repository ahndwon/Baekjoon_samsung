import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class Purifier {

    static int R;
    static int C;
    static int T;

    static int[][] map;
    static int[][] newMap;

    static HashSet<Dust> dustSet = new HashSet<>();
    static ArrayList<Point4> airCleaner = new ArrayList<>();

    static int[] dy = {-1, 1, 0, 0};
    static int[] dx = {0, 0, -1, 1};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        R = sc.nextInt();
        C = sc.nextInt();
        T = sc.nextInt();

        map = new int[R][C];

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                int tile = sc.nextInt();
                map[i][j] = tile;

                if (tile == -1) {
                    airCleaner.add(new Point4(j, i));
                }

            }
        }

//        for (int i = 0; i < R; i++) {
//            for (int j = 0; j < C; j++) {
//                System.out.print(map[i][j] + " ");
//            }
//            System.out.println();
//        }

        for (int i = 0; i < T; i++) {
            spreadDust();
        }


        int answer = 0;
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (map[i][j] > 0) {
                    answer += map[i][j];
                }
            }
        }

        System.out.print(answer);

    }

    public static void spreadDust() {
//        ArrayList<Point4> airCleaner = new ArrayList<>();
        newMap = new int[R][C];

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (map[i][j] > 0) {
                    int cnt = 0;
                    for (int d = 0; d < 4; d++) {
                        int ny = i + dy[d];
                        int nx = j + dx[d];

                        if (ny < 0 || ny > R - 1 || nx < 0 || nx > C - 1 || map[ny][nx] == -1)
                            continue;

                        newMap[ny][nx] += map[i][j] / 5;
                        cnt++;
                    }
                    newMap[i][j] += map[i][j] - (map[i][j] / 5) * cnt;
                } else if (map[i][j] == -1) {// 공기청정기 위치.
                    newMap[i][j] = -1;
//                    airCleaner.add(new Point4(j, i));
                }
            }
        }

        for (int i = 0; i < R; i++) {
            map[i] = Arrays.copyOf(newMap[i], C);
        }

        cleanAir(airCleaner.get(0), airCleaner.get(1));

    }

    static void cleanAir(Point4 up, Point4 down) {
        for (int i = 0; i < C - 1; i++) {
            if (newMap[up.y][i] == -1 || newMap[down.y][i] == -1) {
                map[up.y][i + 1] = 0;
                map[down.y][i + 1] = 0;
            } else {
                map[up.y][i + 1] = newMap[up.y][i];
                map[down.y][i + 1] = newMap[down.y][i];
            }

            map[0][i] = newMap[0][i + 1];
            map[R - 1][i] = newMap[R - 1][i + 1];
        }

        for (int j = 0; j < R - 1; j++) {
            if (j < up.y) {
                map[j + 1][0] = newMap[j][0];
                map[j][C - 1] = newMap[j + 1][C - 1];
            } else if (j >= down.y) {
                map[j + 1][C - 1] = newMap[j][C - 1];
                map[j][0] = newMap[j + 1][0];
            }
        }

        map[up.y][up.x] = -1;
        map[down.y][down.x] = -1;

    }
}

class Point4 {
    int x;
    int y;

    public Point4(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }

}

class Dust {
    int r;
    int c;
    int value;

    public Dust(int r, int c, int value) {
        this.r = r;
        this.c = c;
        this.value = value;
    }

}

