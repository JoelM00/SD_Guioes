package G7;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

class ContactList {
    private List<Contact> contacts;
    private ReentrantLock l;

    public ContactList() {
        contacts = new ArrayList<>();
        l = new ReentrantLock();
        contacts.add(new Contact("John", 20, 253123321, null, new ArrayList<>(Arrays.asList("john@mail.com"))));
        contacts.add(new Contact("Alice", 30, 253987654, "CompanyInc.", new ArrayList<>(Arrays.asList("alice.personal@mail.com", "alice.business@mail.com"))));
        contacts.add(new Contact("Bob", 40, 253123456, "Comp.Ld", new ArrayList<>(Arrays.asList("bob@mail.com", "bob.work@mail.com"))));
    }

    public boolean addContact(DataInputStream in) throws IOException {
        Contact c = Contact.deserialize(in);
        try {
            l.lock();
            this.contacts.add(c);
            return true;
        } finally {
            l.unlock();
        }
    }

    public void getContacts(DataOutputStream out) throws IOException {
        try {
            l.lock();
            out.writeInt(contacts.size());
            for (Contact c : this.contacts) {
                c.serialize(out);
            }
            out.flush();
        } finally {
            l.unlock();
        }
    }
}




