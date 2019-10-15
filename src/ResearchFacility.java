import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

// https://www.acmicpc.net/problem/14502
// 14502번 : 연구소

// 실수한 것 :
// 1. 벽 세우기 -> index 잘못 잡음
// 2. tempMap 안 만듬 -> 맵 복사해야 매번 새로운 맵에서 bfs 수행함


//  테스트 케이스
//        1.
//        7 7
//        2 0 0 0 1 1 0
//        0 0 1 0 1 2 0
//        0 1 1 0 1 0 0
//        0 1 0 0 0 0 0
//        0 0 0 0 0 1 1
//        0 1 0 0 0 0 0
//        0 1 0 0 0 0 0
//
//        27
//
//        2.
//        4 6
//        0 0 0 0 0 0
//        1 0 0 0 0 2
//        1 1 1 0 0 2
//        0 0 0 0 0 2
//
//        9
//
//
//        3.
//        8 8
//        2 0 0 0 0 0 0 2
//        2 0 0 0 0 0 0 2
//        2 0 0 0 0 0 0 2
//        2 0 0 0 0 0 0 2
//        2 0 0 0 0 0 0 2
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//        0 0 0 0 0 0 0 0
//
//        3


public class ResearchFacility {
    static int N;
    static int M;
    static int answer = 0;

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        M = sc.nextInt();

        int[][] map = new int[N][M];
        ArrayList<Virus> viruses = new ArrayList<>();
        ArrayList<Point> freeSpace = new ArrayList<>();

        sc.nextLine();

        // setup Map
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                map[i][j] = sc.nextInt();

                if (map[i][j] == 2) {
                    viruses.add(new Virus(i, j));
                } else if (map[i][j] == 0) {
                    freeSpace.add(new Point(i, j));
                }
            }
        }
//
//        map[3][5] = 1;
//        map[0][1] = 1;
//        map[1][0] = 1;
//        bfs(map, viruses);
//        checkMap(map);

        int size = freeSpace.size();

        for (int i = 0; i < size - 2; i++) {
            for (int j = i + 1; j < size - 1; j++) {
                for (int k = j + 1; k < size; k++) {
                    map[freeSpace.get(i).i][freeSpace.get(i).j] = 1;
                    map[freeSpace.get(j).i][freeSpace.get(j).j] = 1;
                    map[freeSpace.get(k).i][freeSpace.get(k).j] = 1;

                    bfs(map, viruses);


                    map[freeSpace.get(i).i][freeSpace.get(i).j] = 0;
                    map[freeSpace.get(j).i][freeSpace.get(j).j] = 0;
                    map[freeSpace.get(k).i][freeSpace.get(k).j] = 0;
                }
            }
        }

        System.out.println(answer);
    }

    static void checkMap(int[][] map) {
        int count = 0;
//        System.out.println();
//        System.out.println("showMap");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
//                System.out.print(map[i][j] + " ");

                if (map[i][j] == 0) {
                    count++;
                }
            }
//            System.out.println();
        }
//        System.out.println();

        if (answer < count) {
            System.out.println();
            System.out.println("showMap");
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    System.out.print(map[i][j] + " ");

                }
                System.out.println();
            }
            System.out.println();
        }


        answer = Math.max(answer, count);


    }

    static void bfs(int[][] map, ArrayList<Virus> viruses) {
        LinkedList<Point> queue = new LinkedList<>();

        int[][] check = new int[N][M];
        int[][] tempMap = new int[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                tempMap[i][j] = map[i][j];
            }
        }

        for (Virus v : viruses) {
            queue.add(new Point(v.i, v.j));
        }

        while (queue.size() > 0) {
            Point p = queue.poll();

            for (int i = 0; i < 4; i++) {
                int ddx = dx[i] + p.i;
                int ddy = dy[i] + p.j;

                if (ddx >= 0 && ddx < N && ddy >= 0 && ddy < M) {
                    if (map[ddx][ddy] != 1 && check[ddx][ddy] == 0) {
                        tempMap[ddx][ddy] = 2;
                        queue.add(new Point(ddx, ddy));
                        check[ddx][ddy] = 1;
                    }
                }
            }
        }

        checkMap(tempMap);

    }

}

class Point {
    int i;
    int j;

    public Point(int i, int j) {
        this.i = i;
        this.j = j;
    }
}

class Virus {
    int i;
    int j;

    public Virus(int i, int j) {
        this.i = i;
        this.j = j;
    }
}
