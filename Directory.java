import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.*;

public class PhoneBookTest {

	public static void main(String[] args) {
		PhoneBook phoneBook = new PhoneBook();
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		scanner.nextLine();
		for (int i = 0; i < n; ++i) {
			String line = scanner.nextLine();
			String[] parts = line.split(":");
			try {
				phoneBook.addContact(parts[0], parts[1]);
			} catch (DuplicateNumberException e) {
				System.out.println(e.getMessage());
			}
		}
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
            System.out.println(line);
			String[] parts = line.split(":");
			if (parts[0].equals("NUM")) {
				phoneBook.contactsByNumber(parts[1]);
			} else {
				phoneBook.contactsByName(parts[1]);
			}
		}
	}

}



class Contact{
    private String name;
    private String number;

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
    public String toString(){
        return String.format("%s %s",name,number);
    }

}


class PhoneBook{
    Map<String,Contact> contacts;

    public PhoneBook() {
        this.contacts = new HashMap<String,Contact>();
    }

    public void addContact(String name, String number) throws DuplicateNumberException {
        if(contacts.containsKey(number)) throw new DuplicateNumberException(number);
    else contacts.putIfAbsent(number,new Contact(name, number));
    }

    public void contactsByNumber(String number){
        List<Contact> list = contacts.values().stream().filter(x->x.getNumber().contains(number))
                .sorted(Comparator.comparing(Contact::getName).thenComparing(Contact::getNumber))
                .collect(Collectors.toList());
        if(list.isEmpty())
        {
            System.out.println("NOT FOUND");
        }else{
            list.stream().forEach(System.out::println);
        }
    }

    public void contactsByName(String name){
        List<Contact> list = contacts.values().stream().filter(x->x.getName().equals(name))
                .sorted(Comparator.comparing(Contact::getName).thenComparing(Contact::getNumber))
                .collect(Collectors.toList());
        if(list.isEmpty())
        {
            System.out.println("NOT FOUND");
        }else{
            list.stream().forEach(System.out::println);
        }
    }






}
class DuplicateNumberException extends Exception{
    public DuplicateNumberException(String num) {
        super(num + "is duplicate");
    }
}
