import java.util.HashMap;

class DynamicProgramming {
  int getClimbingWays(int n) {
      if (n < 1) {
        return 0;
      }
      if (n == 1) {
        return 1;
      }
      if (n == 2) {
        return 2;
      }
      return getClimbingWays(n-1) + getClimbingWays(n-2);
  }

  // int getClimbingWaysMemento(int n, HashMap<Integer, Integer> map) {
  //   if(n < 1){
  //     return 0;
  //   }
  //   if(n == 1) {
  //     return 1;
  //   }
  //   if(n == 2) {
  //     return 2;
  //   }
  //   if (map.contains(n)) {
  //     return map.get(n);
  //   }
  //   else {
  //     int value = getClimbingWays(n-1) + getClimbingWays(n-2);
  //     map.put(n, value);
  //     return value;
  //   }
  // }

  int getClimbingWaysDynamic(int n) {
    if (n < 1) {
      return 0;
    }
    if (n == 1) {
      return 1;
    }
    if (n == 2) {
      return 2;
    }
    int a = 1;
    int b = 2;
    int temp = 0;
    for (int i = 3; i <=n; i++) {
      temp = a + b;
      a = b;
      b = temp;
    }
    return temp;
  }

  /**
   * 获得金矿最优收益
   * @param w 工人数量
   * @param n 可选金矿数量
   * @param p 金矿开采所需的工人数量
   * @param g 金矿储量
   */
   // 递归
   // from book P220
   public static int getBestGoldMining(int w, int n, int[] p, int[] g) {
     if (w== 0 || n==0) {
       return 0;
     }
     if (w<p[n-1]) {
       return getBestGoldMining(w, n-1, p, g);
     }
     return Math.max(getBestGoldMining(w, n-1, p, g), getBestGoldMining(w-p[n-1], n-1, p, g)+g[n-1]);
   }

   /**
    * 获得金矿最优收益
    * @param w 工人数量
    * @param p 金矿开采所需的工人数量
    * @param g 金矿储量
    */
  public static int getBestGoldMiningV2(int w, int[] p, int[] g) {
    // 创建表格
    int[][] resultTable = new int[g.length+1][w+1];
    // 填充表格
    for (int i=1; i<=g.length; i++) {
      for (int j=1; j<=w; j++) {
        if (j<p[i-1]) {
          resultTable[i][j] = resultTable[i-1][j];
        } else {
          resultTable[i][j] = Math.max(resultTable[i-1][j], resultTable[i-1][j-p[i-1]]+g[i-1]);
        }
      }
    }
    // 返回最后 1 个格子的值
    return resultTable[g.length][w];
  }

  /**
   * 获得金矿最优收益
   * @param w 工人数量
   * @param p 金矿开采所需的工人数量
   * @param g 金矿储量
   */
   public static int getBestGoldMiningV3(int w, int[] p, int[] g) {
     // 创建当前结果
     int[] results = new int[w+1];
     // 填充一维数组
     for (int i=1; i<g.length; i++) {
       for (int j=w; j>=1; j--) {
         if (j>=p[i-1]) {
           results[j] = Math.max(results[j], results[j-p[i-1]]+g[i-1]);
         }
       }
     }
     // 返回最后 1 个格子的值
     return results[w];
   }

  int getMostGold(int n, int w, int[] g, int[] p) {
    int[] preResults = new int[p.length];
    int[] results = new int[p.length];

    // 填充边界格子的值
    for (int i = 0; i <= n; i++) {
      if (i < p[0]) {
        preResults[i] = 0;
      } else {
        preResults[i] = g[0];
      }
    }
    // 填充其余格子的值，外层循环是金矿数量，内层循环是工人数
    for (int i = 0; i < n; i++) {
      for (int j = 0; j <= w; j++) {
        if (j < p[i]) {
          results[j] = preResults[j];
        } else {
          results[j] = Math.max(preResults[j], preResults[j-p[i]]+g[i]);
        }
      }
      preResults = results;
    }
    return results[n];
  }

  public static void main(String[] args) {
    int w = 10;
    int[] p = { 5, 5, 3, 4, 3 };
    int[] g = { 400, 500, 200, 300, 350 };
    System.out.println("Best Benefit V1: " + getBestGoldMining(w, g.length, p, g));

    System.out.println("Best Benefit V2: " + getBestGoldMiningV2(w, p, g));

    System.out.println("Best Benefit V3: " + getBestGoldMiningV3(w, p, g));
  }
}
