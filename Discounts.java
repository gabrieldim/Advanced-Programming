import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

class Proizvod{
    private String name;
    private float cena;
    private float popust;
    private float p;

    public Proizvod(float popust, float cena) {
        this.popust = popust;
        this.cena = cena;
        this.p = (Math.abs(cena-popust)/cena)*100;
 
    }


    public float getP() {
        return p;
    }


    public Proizvod(String name, int popust, int cena) {
        this.name = name;
        this.popust = popust;
        this.cena = cena;
    }

    public String getName() {
        return name;
    }

    public float getCena() {
        return cena;
    }

    public float getPopust() {
        return popust;
    }
}

class Store{
    String name;
    List<Proizvod> ceni;

    public Store(String name, List<Proizvod> ceni) {
        this.name = name;
        this.ceni = ceni;
    }

    public String getName() {
        return name;
    }

    public List<Proizvod> getCeni() {
        return ceni;
    }

    public double detAverageDiscount(){
       double sum=0.0;
        for(int i=0;i<ceni.size();i++)
       {
           sum+=((ceni.get(i).getCena()-ceni.get(i).getPopust())*100)/ceni.get(i).getCena();
       }
        return sum/ceni.size();
    }


    public int getTotalDiscount(){
        return (int) ceni.stream().mapToDouble(s->s.getCena()-s.getPopust()).sum();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(getName());
        sb.append('\n' + "Average discount: "+ detAverageDiscount());
        sb.append('\n' + "Total discount: " + getTotalDiscount() + '\n');

        List<Proizvod> list = new ArrayList<Proizvod>();
        for(int i=0;i<ceni.size();i++)
        {
            int p=0;
            list.add(new Proizvod(ceni.get(i).getPopust(),ceni.get(i).getCena()));
        }

       list= list.stream().sorted(Comparator.comparing(Proizvod::getP).reversed().thenComparing(Proizvod::getName)).collect(Collectors.toList());
        for(int i=0;i<list.size();i++)
        {
            sb.append(list.get(i).getP()+" ");
            sb.append(list.get(i).getPopust()+" "+ list.get(i).getCena() + "\n");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
}



class Discounts{

    private Map<String,Store> map;

    public Discounts() {
        this.map = new HashMap<String, Store>();
    }




    public int readStores(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String s;
        String [] pom;

        while((s=br.readLine())!=null)
        {
            pom=s.split("\\s+");
            String name = pom[0];
            List<Proizvod> CENI = new ArrayList<>();
            for(int i=1;i<pom.length;i++)
            {
                String [] delovi = pom[i].split(":");
                int cena =Integer.parseInt(delovi[1]) ;
                int popust = Integer.parseInt(delovi[0]);
                CENI.add(new Proizvod(popust,cena));
            }
            map.putIfAbsent(name,new Store(name,CENI));
        }
        return map.size();
    }



    public List<Store> byAverageDiscount(){
        List<Store> list = new ArrayList<>();
        list =map.values().stream().sorted(Comparator.comparing(Store::detAverageDiscount).reversed().thenComparing(Store::getName))
                .limit(3).collect(Collectors.toList());
    return list;
    }

    public List<Store> byTotalDiscount(){
        List<Store> list = map.values().stream().sorted(Comparator.comparing(Store::detAverageDiscount).reversed().thenComparing(Store::getName))
                .limit(3).collect(Collectors.toList());
        return list;
    }






}


public class DiscountsTest {
    public static void main(String[] args) throws Exception{
        Discounts discounts = new Discounts();
        int stores = discounts.readStores(System.in);
        System.out.println("Stores read: " + stores);
        System.out.println("=== By average discount ===");
        discounts.byAverageDiscount().forEach(System.out::println);
        System.out.println("=== By total discount ===");
        discounts.byTotalDiscount().forEach(System.out::println);
    }
}

