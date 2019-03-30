/**
 * Glass Falling
 * Author: Burraque Khattak
 */
public class GlassFalling {
  public int[][] matrix;

  // Do not change the parameters!
  public int glassFallingRecur(int floors, int sheets) {
    // Base case check if 1 or no floors and 1 sheet
    if (floors <= 1 || sheets == 1) {
      return floors;
    }

    int minDrops = floors;
    int numDrops;

    // let i represent the floor we start dropping glass from
    for (int i = 1; i <= floors; i++) {
      // for i from 1 to the total number of floors
      // if glass shatters on given floor then we check the one under it, i-1 and we have one less sheet (sheets-1)
      // other case is that it does not shatter so we can check the floor above it i+1 and we still have same number of sheets
      numDrops = Math.max(glassFallingRecur(i - 1, sheets - 1), glassFallingRecur(floors - i, sheets)) + 1;
      // In worst case we return the max value of the two at every floor we iterate upon
      // if its any less than the number of minDrops that we have 
      // At any given floor, we care about the worse case scenario or the maximum number of drops
      // If that number is less than the minDrops of the entire building so far, we save that value as the minDrop and return it
      if (numDrops < minDrops)
        minDrops = numDrops;
    }
      return minDrops;

  }

  // Optional:
  // Pick whatever parameters you want to, just make sure to return an int.
  public int glassFallingMemoized(int floors, int sheets) {
    // Fill in here and change the return
    int [][] memoizedArr=new int[floors + 1][sheets+1]; 
    int numDrops = glassFallingMemoized(floors, sheets, memoizedArr);
    return numDrops;
  }

  //Helper function for glassFallingMemoized
  public int glassFallingMemoized(int floors, int sheets, int[][] memoizedArr) {
     if (memoizedArr[floors][sheets] != 0)
        return memoizedArr[floors][sheets];
    if (floors <= 1 || sheets == 1) {
      return floors;
    }
    // when max is reached this is the worst case when drops is equal to number of floors
     memoizedArr[floors][sheets]= floors;
     int numDrops;
     for(int i=1; i<=floors; i++ ) {
        numDrops = Math.max(glassFallingMemoized((i-1), (sheets-1), memoizedArr), glassFallingMemoized((floors-i), (sheets), memoizedArr)) + 1;
        if(numDrops < memoizedArr[floors][sheets])
           memoizedArr[floors][sheets]=numDrops;
     }
    return memoizedArr[floors][sheets]; 
  }

  // Do not change the parameters!
  public int glassFallingBottomUp(int floors, int sheets) {
    // Fill in here and change the return
    matrix = new int [floors+1][sheets+1];

    // 1 drop for one floor base cases
    // 0 drops for 0 floors base cases
    for (int i = 1; i <= sheets; i++) {
      matrix[1][i] = 1;
      matrix[0][i] = 0;
    }

    // case when you only have a single sheet of glass, then number of drops is equal number of floors
    for( int j=1; j<=floors; j++){
      matrix[j][1] = j;
    }

    int numDrops;

    for (int flr = 2; flr <= floors; flr++) {
      for (int sht = 2; sht <= sheets; sht++) {
        matrix[flr][sht] = floors;
        for (int drop = 1; drop <= flr; drop++) {
          // iterate through floors to dorp from
          // 2 cases, when shattered, iterate from floor under and --sht
          // when not shattered, sub floor from the dropped floor
          numDrops = 1 + Math.max(matrix[drop - 1][sht - 1], matrix[flr - drop][sht]);
          if (numDrops < matrix[flr][sht])
            matrix[flr][sht] = numDrops;
        }
      }
    }
    // return the number of drops, numDrops
    return matrix[floors][sheets];
  }


  public static void main(String args[]){
      GlassFalling gf = new GlassFalling();

      // Do not touch the below lines of code, and make sure
      // in your final turned-in copy, these are the only things printed
      int minTrials1Recur = gf.glassFallingRecur(27, 2);
      int minTrials1Bottom = gf.glassFallingBottomUp(27, 2);
      int minTrials2Recur = gf.glassFallingRecur(100, 3);
      int minTrials2Bottom = gf.glassFallingBottomUp(100, 3);
      System.out.println(minTrials1Recur + " " + minTrials1Bottom);
      System.out.println(minTrials2Recur + " " + minTrials2Bottom);
  }
}
