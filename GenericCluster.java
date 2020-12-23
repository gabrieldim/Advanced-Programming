import java.util.*;

/**
 * January 2016 Exam problem 2
 */
public class ClusterTest {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Cluster<Point2D> cluster = new Cluster<>();
    int n = scanner.nextInt();
    scanner.nextLine();
    for (int i = 0; i < n; ++i) {
      String line = scanner.nextLine();
      String[] parts = line.split(" ");
      long id = Long.parseLong(parts[0]);
      float x = Float.parseFloat(parts[1]);
      float y = Float.parseFloat(parts[2]);
      cluster.addItem(new Point2D(id, x, y));
    }
    int id = scanner.nextInt();
    int top = scanner.nextInt();
    cluster.near(id, top);
    scanner.close();
  }
}

// your code hereclass Point2D{
    
class Point2D{
     private long id;
     private float x;
     private float y;
     private float distance;

     public Point2D(){}

     public Point2D(long id, float x, float y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public double getDistance(Point2D variable){
        return Math.sqrt(Math.pow(x-variable.x,2) +(Math.pow(y-variable.y,2)));
    }

    public void setDistance(float d){
         if(d==0.0) distance = Float.MAX_VALUE;
         else distance = d;
    }
    public double getDistance(){
         return distance;
    }

    public String toString(){
         return String.format("%d -> %.3f", (int) id , getDistance());
    }

    public long getId() {
        return id;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}


class Cluster<T extends Point2D> {

    List<T> lista ;
    public Cluster()
    {
        lista = new ArrayList<T>();
    }

    public void addItem(T element)
    {
        lista.add(element);
    }


    public void near(long id, int top)
    {
        Point2D test = new Point2D();
        for(int i=0;i<lista.size();i++)
        {
            if(lista.get(i).getId()==id)
            {
                test=lista.get(i);
            }
        }


        for(int i=0;i<lista.size();i++)
        {
            lista.get(i).setDistance((float)lista.get(i).getDistance(test));
        }
        lista.sort(Comparator.comparing(Point2D::getDistance));
        for(int i=0;i<top;i++)
        {
            System.out.println(i+1+ ". " + lista.get(i).toString());
        }



    }



}

