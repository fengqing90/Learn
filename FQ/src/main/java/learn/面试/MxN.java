package learn.面试;

public class MxN {

    public static void main(String[] args) {
        MxN.getLoad(10, 10);
    }

    public static void getLoad(int m, int n) {
        int lastPath = 0;//上一步

        for (int x = 0, y = 0; x < m - 1 || y < n - 1;) {
            // 方向判断函数
            int trend = MxN.getTrend(x, y, m, n, lastPath);
            lastPath = trend;
            switch (trend) {

                case 1:// 横向右移动
                    x++;
                    break;

                case 2: // 纵向下移动
                    y++;
                    break;
                case 3: // 斜向下
                    x--;
                    y++;
                    break;
                case 4: // 斜向上
                    x++;
                    y--;
                    break;
            }
            System.out.println(x + "," + y);

        }

    }

    private static int getTrend(int x, int y, int m, int n, int lastPath) {
        if ((y == 0 || y == n - 1) && x < m - 1) {
            if (lastPath == 0 || lastPath == 3 || lastPath == 4) {
                return 1;
            }
        }
        if ((x == 0 || x == m - 1) && y < n - 1) {
            if (lastPath == 3 || lastPath == 4) {
                return 2;
            }
        }

        if (x > 0 && x <= m - 1 && y < n - 1 && y < n - 1) {
            if (lastPath == 1 || lastPath == 2 || lastPath == 3) {
                return 3;
            }
        }

        if (y > 0 && y <= n - 1 && x < m - 1) {
            if (lastPath == 1 || lastPath == 2 || lastPath == 4) {
                return 4;
            }
        }
        return lastPath;
    }
}
