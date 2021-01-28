package G7;

import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

class Contact {
    private String name;
    private int age;
    private long phoneNumber;
    private String company;
    private List<String> emails;

    public Contact (String name, int age, long phone_number, String company, List<String> emails) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phone_number;
        this.company = company;
        this.emails = new ArrayList<>(emails);
    }

    public String toString () {
        StringBuilder builder = new StringBuilder();
        builder.append(this.name).append(";");
        builder.append(this.age).append(";");
        builder.append(this.phoneNumber).append(";");
        builder.append(this.company).append(";");
        builder.append("{");
        for (String s : this.emails) {
            builder.append(s).append(";");
        }
        builder.append("}");
        return builder.toString();
    }

    public static Contact deserialize(DataInputStream in) throws IOException {
        String nome = in.readUTF();
        int age = in.readInt();
        long phone = in.readLong();
        boolean b = in.readBoolean();
        String company = null;
        if (b) {
            company = in.readUTF();
        }
        int size = in.readInt();
        List<String> emails = new ArrayList<>();
        for (int i = 0; i<size; i++) {
            emails.add(in.readUTF());
        }
        return new Contact(nome,age,phone,company,emails);
    }

    public void serialize(DataOutputStream out) throws IOException {
        out.writeUTF(this.name);
        out.writeInt(this.age);
        out.writeLong(this.phoneNumber);
        if (this.company == null)
            out.writeBoolean(false);
        else  {
            out.writeBoolean(true);
            out.writeUTF(this.company);
        }
        out.writeInt(emails.size());
        for (String e : emails) {
            out.writeUTF(e);
        }
        out.flush();
    }
}