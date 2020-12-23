import java.util.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class BlockContainer <T extends Comparable<T>>{
    private List<TreeSet<T>> container;
    private int n;

    public BlockContainer(int n) {
        this.n = n;
        container = new ArrayList<TreeSet<T>>();

    }

    public void add(T a){
      if(container.isEmpty())
      {
          TreeSet<T> newSet = new TreeSet<>();
          newSet.add(a);
          container.add(newSet);
      }else if(container.get(container.size()-1).size()<n){
          container.get(container.size()-1).add(a);
      }else{
          TreeSet<T> newSet = new TreeSet<>();
          newSet.add(a);
          container.add(newSet);
      }
    }

    public boolean remove(T a){

         if(container.get(container.size()-1).size()==1)
        {
        container.remove(container.size()-1);
        }
            return container.get(container.size()-1).remove(a);
    }

    public void sort(){
        List<T> elementi = container.stream().flatMap(Collection::stream).collect(Collectors.toList());
        elementi.sort(Comparable::compareTo);
        container.clear();
        for(T t : elementi)
        {
           this.add(t);
        }

    }
//    @Override
//    public String toString() {
//        return blocks.stream().map(block -> "[" +
//                block.stream().map(Object::toString).collect(Collectors.joining(", "))
//                + "]").collect(Collectors.joining(","));
//    }

    public String toString(){
        return container.stream().map(block-> "[" +
                block.stream().map(Objects::toString).collect(Collectors.joining(", "))
                +"]").collect(Collectors.joining(","));
    }



}



public class BlockContainerTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int size = scanner.nextInt();
        BlockContainer<Integer> integerBC = new BlockContainer<Integer>(size);
        scanner.nextLine();
        Integer lastInteger = null;
        for(int i = 0; i < n; ++i) {
            int element = scanner.nextInt();
            lastInteger = element;
            integerBC.add(element);
        }
        System.out.println("+++++ Integer Block Container +++++");
        System.out.println(integerBC);
        System.out.println("+++++ Removing element +++++");
        integerBC.remove(lastInteger);
        System.out.println("+++++ Sorting container +++++");
        integerBC.sort();
        System.out.println(integerBC);
        BlockContainer<String> stringBC = new BlockContainer<String>(size);
        String lastString = null;
        for(int i = 0; i < n; ++i) {
            String element = scanner.next();
            lastString = element;
            stringBC.add(element);
        }
        System.out.println("+++++ String Block Container +++++");
        System.out.println(stringBC);
        System.out.println("+++++ Removing element +++++");
        stringBC.remove(lastString);
        System.out.println("+++++ Sorting container +++++");
        stringBC.sort();
        System.out.println(stringBC);
    }
}
