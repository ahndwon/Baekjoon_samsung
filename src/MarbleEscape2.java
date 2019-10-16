import java.util.Scanner;

// https://www.acmicpc.net/problem/13460
//
public class MarbleEscape2 {
    static int N;
    static int M;
    static int moveCount = 10;
    static int answer = Integer.MAX_VALUE;

    static char[][] map;

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        M = sc.nextInt();
        sc.nextLine();
        map = new char[N][M];
        Ball redBall = new Ball(false, 0, 0);
        Ball blueBall = new Ball(false , 0 ,0);

        for (int i = 0; i < N; i++) {
            char[] line = sc.nextLine().toCharArray();

            for (int j = 0; j < M; j++) {
                char tile = line[j];
                map[i][j] = tile;

                if (tile == 'B') {
                    blueBall = new Ball(false, i, j);
                } else if (tile == 'R') {
                    redBall = new Ball(true, i, j);
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }

        int[][] redVisit = new int[N][M];
        int[][] blueVisit = new int[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == '#') {
                    redVisit[i][j] = 1;
                    blueVisit[i][j] = 1;
                } else if (map[i][j] == '.') {
                    redVisit[i][j] = 0;
                    blueVisit[i][j] = 0;
                } else if (map[i][j] == 'B') {
                    redVisit[i][j] = 0;
                    blueVisit[i][j] = 0;
                } else if (map[i][j] == 'R') {
                    redVisit[i][j] = 0;
                    blueVisit[i][j] = 0;
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            System.out.println();
            System.out.println("redBall pos : " + redBall.x + ", " + redBall.y);
            System.out.println("blueBall pos : " + blueBall.x + ", " + blueBall.y);
//            moveMap(0, redVisit, blueVisit, -2, -2);
            moveMap(redBall, blueBall, 1, copyVisit(redVisit), copyVisit(blueVisit), dx[i], dy[i]);
            System.out.println();
        }

        if (answer == Integer.MAX_VALUE) {
            System.out.println(-1);
        } else {
            System.out.println(answer);
        }
    }

    static void moveMap(Ball redBall, Ball blueBall, int count, int[][] redVisit, int[][] blueVisit, int dx, int dy) {
        if (count > 10) {
            return;
        }

        System.out.println("count : " + count);
        System.out.println("direction : " + dx + ", " + dy);

        redBall.setDirections(dx, dy);
        blueBall.setDirections(dx, dy);

        if (redVisit[redBall.x][redBall.y] == 1 && blueVisit[blueBall.x][blueBall.y] == 1) {
            System.out.println("visit == 1");
            return;
        } else {
            redVisit[redBall.x][redBall.y] = 1;
            blueVisit[blueBall.x][blueBall.y] = 1;
        }

        boolean isRedOver = false;
        boolean isBlueOver = false;

//        while (redBall.dx != -2 && map[redBall.nextX()][redBall.nextY()] != '#') {
        while (map[redBall.nextX()][redBall.nextY()] != '#') {
            System.out.println("redMove");
            redBall.move();
            if (map[redBall.x][redBall.y] == 'O') {
                isRedOver = true;
                break;
            }
        }

//        while (blueBall.dx != -2 && map[blueBall.nextX()][blueBall.nextY()] != '#') {
        while (map[blueBall.nextX()][blueBall.nextY()] != '#') {
            System.out.println("blueMove");
            blueBall.move();
            if (map[blueBall.x][blueBall.y] == 'O') {
                isBlueOver = true;
                break;
            }
        }

        System.out.println("redBall pos : " + redBall.x + ", " + redBall.y);
        System.out.println("blueBall pos : " + blueBall.x + ", " + blueBall.y);

        System.out.println("isRedOver : " + isRedOver + " isBlueOver: " + isBlueOver);

        if (isBlueOver && isRedOver) return;
        else if (isBlueOver) return;
        else if (isRedOver) {
            System.out.println("count answer : " + count);
            answer = Math.min(count, answer);
            return;
        }

        // 겹칠 경우
        if (redBall.x == blueBall.x && redBall.y == blueBall.y) {
            if (redBall.distance > blueBall.distance) {
                redBall.moveReverse();
            } else {
                blueBall.moveReverse();
            }
        }

        for (int i = 0; i < 4; i++) {
            moveMap(copyBall(redBall), copyBall(blueBall), count + 1, copyVisit(redVisit), copyVisit(blueVisit), MarbleEscape2.dx[i], MarbleEscape2.dy[i]);
        }

    }

//    static int[][] mapToVisit(char[][] map) {
//        int[][] visit = new int[N][M];
//
//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j < M; j++) {
//                if (map[i][j])
//                copied[i][j] = origin[i][j];
//            }
//        }
//
//        return
//    }
//
    static int[][] copyVisit (int[][] origin) {
        int[][] copied = new int[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                copied[i][j] = origin[i][j];
            }
        }

        return copied;
    }

    static Ball copyBall(Ball ball) {
        return (Ball) ball.clone();
    }
}

class Ball {
    boolean isRed;
    int dx;
    int dy;
    int x;
    int y;
    int distance;

    public Ball(boolean isRed, int dx, int dy, int x, int y, int distance) {
        this.isRed = isRed;
        this.dx = dx;
        this.dy = dy;
        this.x = x;
        this.y = y;
        this.distance = distance;
    }

    public Ball(boolean isRed, int x, int y) {
        this.isRed = isRed;
        this.x = x;
        this.y = y;
        this.dx = -2;
        this.dy = -2;
        this.distance = 0;
    }

    void move() {
        x += dx;
        y += dy;
    }

    void moveReverse() {
        x -= dx;
        y -= dy;
    }

    int nextX() {
        return x + dx;
    }

    int nextY() {
        return y + dy;
    }

    void setDirections(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    protected Object clone() {
        try {
            return (Ball) super.clone();
        } catch (CloneNotSupportedException e) {
            return new Ball(isRed, dx, dy, x, y, distance);
        }

    }
}
