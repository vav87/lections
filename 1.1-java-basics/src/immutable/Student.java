package immutable;

public final class Student {

    private final int id;
    private final String name;
    private final Address address;

    public Student(int id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = new Address(address.getCity(), address.getStreet(), address.getHouseNumber());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return new Address(address.getCity(), address.getStreet(), address.getHouseNumber());
    }
}
