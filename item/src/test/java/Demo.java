import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * @author : 张江坤
 * @description:TODO
 * @date :2020/4/7 19:39
 */

public class Demo {

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        if(in.hasNextInt()){
            int num = in.nextInt();
            TreeSet<Integer> set = new TreeSet();
            for(int i=0;i<num;i++){
                set.add(in.nextInt());
            }
            Iterator it = set.iterator();
            while(it.hasNext()){
                System.out.println(it.next());
            }
        }
    }

    private static String spaceTo20(String str){
        str = str.replaceAll(" ","20%");
        return str;
    }
    public static boolean Find(int target, int [][] array) {
        int row = array.length;//行数
        int col = array[0].length;//列数
        if(row == 0 || col == 0){
            return false;
        }
        int minX = 0;
        int maxX = row-1;
        for(int i=0;i<row;i++){
            if(array[i][0] == target){
                return true;
            }else if(array[i][0]>target){
                if(i-1 < 0){//如果[0][0]都
                    return false;
                }else{
                    maxX = i-1;
                    break;
                }
            }
        }
        for(int j=0;j<col;j++){
            if(array[j][col-1]==target){
                return true;
            }else if(array[j][col-1]>target){
                minX = j;
                break;
            }
        }
        for(int i=minX;i<=maxX;i++){
            for(int j=0;j<col;j++){
                if(array[i][j] == target){
                    return true;
                }
            }
        }
        return false;
    }

}
