import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

//  https://www.acmicpc.net/problem/16236
// 16236번 아기상어


// 놓친 점 : 아기상어의 처음 위치 문제. 0으로 바꿈으로써 해결

public class BabyShark {

    static int N;
    static int[][] map;
    static Shark shark;
    static int time = 0;
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();

        map = new int[N][N];


        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int tile = sc.nextInt();
                map[i][j] = tile;

                if (tile == 9) {
                    shark = new Shark(i, j);
                    map[i][j] = 0;
                }

            }
        }

        bfs();

        System.out.println(time);
    }

    static void bfs() {
        while (true) {
            LinkedList<Point2> queue = new LinkedList<>();
            int[][] visit = new int[N][N];

            queue.add(new Point2(shark.r, shark.c));

            ArrayList<Point2> eatCandidates = new ArrayList<>();

            while (queue.size() > 0) {
                Point2 point = queue.poll();
//                visit[point.r][point.c] = 1;


                for (int i = 0; i < 4; i++) {
                    int ddr = point.r + dr[i];
                    int ddc = point.c + dc[i];


                    if (ddr >= 0 && ddr < N
                            && ddc >= 0 && ddc < N
                    ) {

                        if (visit[ddr][ddc] == 0
                                && shark.size >= map[ddr][ddc]) {

                            if (map[ddr][ddc] == 0) {
                                Point2 newPoint = new Point2(ddr, ddc, point.distance + 1);
                                queue.add(newPoint);
                                visit[ddr][ddc] = 1;

                            } else if (shark.size > map[ddr][ddc]) {
                                Point2 newPoint = new Point2(ddr, ddc, point.distance + 1);
                                queue.add(newPoint);
                                visit[ddr][ddc] = 1;

                                if (eatCandidates.isEmpty()) eatCandidates.add(newPoint);
                                else if (eatCandidates.get(0).distance == newPoint.distance)
                                    eatCandidates.add(newPoint);

                            } else if (shark.size == map[ddr][ddc]) {
                                Point2 newPoint = new Point2(ddr, ddc, point.distance + 1);
                                queue.add(newPoint);
                                visit[ddr][ddc] = 1;
                            }
                        }
                    }
                }
            }


            if (eatCandidates.isEmpty()) break;

            eatCandidates.sort((o1, o2) -> {
                int comp = Integer.compare(o1.r, o2.r);
                if (comp == 0) {
                    return Integer.compare(o1.c, o2.c);
                }
                return comp;
            });

            Point2 eat = eatCandidates.get(0);

//            System.out.println("candidates : " + eatCandidates);
//            System.out.println("eat : " + eat);
//            System.out.println("size : " + shark.size);

            map[eat.r][eat.c] = 0;
            shark.r = eat.r;
            shark.c = eat.c;
            shark.eat();
            time += eat.distance;

//            for (int i = 0; i < N; i++) {
//                for (int j = 0; j < N; j++) {
//                    if (shark.r == i && shark.c == j) System.out.print(9 + " ");
//                    else
//                        System.out.print(map[i][j] + " ");
//                }
//                System.out.println();
//            }
        }


    }

}

class Point2 {
    int r;
    int c;
    int distance;

    public Point2(int r, int c) {
        this.r = r;
        this.c = c;
        this.distance = 0;
    }

    public Point2(int r, int c, int distance) {
        this.r = r;
        this.c = c;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Point2(" + r + ", " + c + ", " + distance + ")";
    }
}

class Shark {
    int r;
    int c;
    int size;
    int count;

    public Shark(int r, int c) {
        this.r = r;
        this.c = c;
        this.size = 2;
        this.count = 0;
    }

    void eat() {
        count++;
        if (count == size) {
            size++;
            count = 0;
        }
    }
}