import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    static char[][] canvas = null;

    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        System.out.print("enter command: ");
        String command = scanner.nextLine().toUpperCase();
        while(!command.trim().equals("Q")) {
            if (command.isEmpty()) {
                continue;
            }
            try {

                String[] params = command.split(" ");
                if (params[0].equals("C")) {
                    canvas = createCanvas(Integer.parseInt(params[1]),Integer.parseInt(params[2]));
                }
                else if (params[0].equals("L")){
                    drawLine(Integer.parseInt(params[1]),Integer.parseInt(params[2]),Integer.parseInt(params[3]),Integer.parseInt(params[4]));
                }
                else if (params[0].equals("R")){
                    drawRectangle(Integer.parseInt(params[1]),Integer.parseInt(params[2]),Integer.parseInt(params[3]),Integer.parseInt(params[4]));
                }
                else if (params[0].equals("B")){
                    Coordinates startingPoint = new Coordinates();
                    startingPoint.x = Integer.parseInt(params[1]);
                    startingPoint.y = Integer.parseInt(params[2]);

                    bucketFill(startingPoint,params[3].charAt(0));
                }
                else {
                    System.out.println("Invalid Command!");
                }
                printCanvas();
            }
            catch (NullPointerException e){
                if (canvas == null){
                    System.out.println("Canvas has not been created yet!");
                }
            }
            catch (ArrayIndexOutOfBoundsException e){
                System.out.println("Invalid coordinates!");
            }
            catch (Exception e){
                System.out.println("Invalid Command!");
            }

            System.out.print("enter command: ");
            command = scanner.nextLine().toUpperCase();
        }

    }

    public static char[][] createCanvas(int width, int height){
        char[][] canvas = new char[height+2][width+2];
        for (int i = 0; i< canvas[0].length; i++){
            canvas[0][i] = '-';
        }

        for (int i = 1; i<= height; i++){
            canvas[i][0] = '|';
            for (int j = 1; j< canvas[i].length-1; j++){
                canvas[i][j]= ' ';
            }
            canvas[i][canvas[i].length-1] = '|';
        }
        for (int i = 0; i< canvas[0].length; i++){
            canvas[canvas.length-1][i] = '-';
        }

        return canvas;
    }
    public static void drawLine(int x1, int y1, int x2, int y2){
        Coordinates startingPoint = new Coordinates();
        Coordinates endPoint = new Coordinates();
        if (x1 < x2 || y1 < y2){
            startingPoint.x = x1;
            startingPoint.y = y1;

            endPoint.x = x2;
            endPoint.y = y2;
        }
        else {
            startingPoint.x = x2;
            startingPoint.y = y2;

            endPoint.x = x1;
            endPoint.y = y1;
        }

        if (!validCoordinates(startingPoint) || !validCoordinates(endPoint)){
            throw new ArrayIndexOutOfBoundsException();
        }
        if (startingPoint.y  == endPoint.y){
            for (int i = startingPoint.x; i<=endPoint.x; i++){
                canvas[y1][i] = 'X';
            }
        }
        else if (startingPoint.x == endPoint.x){
            for (int i = startingPoint.y; i<=endPoint.y; i++){
                canvas[i][x1] = 'X';
            }
        }
        else {
            System.out.println("Diagonal Lines are not supported");
        }


    }
    public static void drawRectangle(int x1, int y1, int x2, int y2){
        Coordinates startingPoint = new Coordinates();
        startingPoint.x = x1;
        startingPoint.y = y1;
        Coordinates endPoint = new Coordinates();
        endPoint.x = x2;
        endPoint.y = y2;
        if (!validCoordinates(startingPoint) || !validCoordinates(endPoint)){
            throw new ArrayIndexOutOfBoundsException();
        }
        drawLine(x1, y1, x1, y2);
        drawLine(x1, y1, x2, y1);
        drawLine(x1, y2, x2, y2);
        drawLine(x2, y1, x2, y2);
    }

    public static void bucketFill(Coordinates startingPoint,char colour ){
        if (!validCoordinates(startingPoint) ){
            throw new ArrayIndexOutOfBoundsException();
        }
        Queue<Coordinates> queue = new LinkedList<>();

        queue.add(startingPoint);

        while (!queue.isEmpty()){
            Coordinates currentPoint = queue.remove();

            Coordinates upPoint = new Coordinates();
            upPoint.x = currentPoint.x;
            upPoint.y = currentPoint.y-1;
            if (validCoordinatesForBucketFill(upPoint, colour)){
                canvas[upPoint.y][upPoint.x] = colour;
                queue.add(upPoint);
            }

            Coordinates downPoint = new Coordinates();
            downPoint.x = currentPoint.x;
            downPoint.y = currentPoint.y+1;
            if (validCoordinatesForBucketFill(downPoint, colour)){
                canvas[downPoint.y][downPoint.x] = colour;
                queue.add(downPoint);
            }

            Coordinates leftPoint = new Coordinates();
            leftPoint.x = currentPoint.x-1;
            leftPoint.y = currentPoint.y;
            if (validCoordinatesForBucketFill(leftPoint, colour)){
                canvas[leftPoint.y][leftPoint.x] = colour;
                queue.add(leftPoint);
            }

            Coordinates rightPoint = new Coordinates();
            rightPoint.x = currentPoint.x+1;
            rightPoint.y = currentPoint.y;
            if (validCoordinatesForBucketFill(rightPoint, colour)){
                canvas[rightPoint.y][rightPoint.x] = colour;
                queue.add(rightPoint);
            }
        }
    }
    public static boolean validCoordinatesForBucketFill(Coordinates point, char colour)
    {

        if (!(validCoordinates(point)) || canvas[point.y][point.x] == colour || canvas[point.y][point.x] == 'X'){
            return false;
        }
        else return true;

    }
    public static boolean validCoordinates(Coordinates point)
    {
        if (point.x <= 0 || point.y <= 0) {
            return false;
        }
        if (point.x >= canvas[0].length-1 || point.y >= canvas.length-1) {
            return false;
        }

        return true;

    }
    public static void printCanvas(){
        for (int i = 0; i< canvas.length; i++){
            for (int j = 0; j< canvas[i].length; j++){
                System.out.print(canvas[i][j]);
            }
            System.out.println();
        }

    }


}
