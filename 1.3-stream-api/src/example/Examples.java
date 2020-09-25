package example;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class Examples {
    public static void main(String[] args) {
        //Пример1 Маппинг лицензий
        var kfLicenses = List.of(new KfLicence("1", "N_1"), new KfLicence("2", "N_2"));
        var licences = transformLicences(kfLicenses);
        System.out.println(licences);

    }

    private static <S, T> List<T> transformList(List<S> list, Function<S, T> itemTransformer) {
        if (list == null || list.size() == 0) {
            return Collections.emptyList();
        }
        return list.stream()
                .filter(Objects::nonNull)
                .map(itemTransformer)
                .collect(toList());
    }

    public static List<License> transformLicences(List<KfLicence> kfLicences) {
        return transformList(kfLicences, Examples::mapLicence);
    }

    private static License mapLicence(KfLicence source) {
        var target = new License();
        target.setId(source.code);
        target.setNumber(source.licenseNumber);
        return target;
    }


    private static class License {
        String id;
        String number;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        @Override
        public String toString() {
            return "License{" +
                    "id='" + id + '\'' +
                    ", number='" + number + '\'' +
                    '}';
        }
    }

    private static class KfLicence {
        String code;
        String licenseNumber;

        public KfLicence(String code, String licenseNumber) {
            this.code = code;
            this.licenseNumber = licenseNumber;
        }
    }


    private static class Client {
        private String inn;
        private List<Account> accounts;

        public String getInn() {
            return inn;
        }

        public List<Account> getAccounts() {
            return accounts;
        }

    }

    private List<Input> createInputs(List<Client> clients) {
        return clients.stream() //Stream<Client>
                .map(this::createInputs) //Stream<List<Input>>
                .flatMap(Collection::stream) //Stream<Input>
                .collect(toList());
    }

    private List<Input> createInputs(Client client) {
        return client.getAccounts().stream()
                .map(account -> new Input(client.getInn(), account))
                .collect(toList());
    }

    private class Input {
        private String inn;
        private Account account;

        public Input(String inn, Account account) {
            this.inn = inn;
            this.account = account;
        }
    }

    private class Account {
        private String id;
    }
}
