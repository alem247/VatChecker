import java.io.StringReader;

public class VATInfo {

    private String name;
    private String address;

    public VATInfo() {
    }

    public String getName   () {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setName   (String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static VATInfo fromJson(String json) {
        VATInfo vatInfo = new VATInfo();
        int nameIndex = json.indexOf("\"name\":");
        int addressIndex = json.indexOf("\"address\":");

        if (nameIndex != -1) {
            int nameStart = json.indexOf("\"", nameIndex + 7);
            int nameEnd = json.indexOf("\"", nameStart + 1);
            vatInfo.setName(json.substring(nameStart + 1, nameEnd));
        }

        if (addressIndex != -1) {
            int addressStart = json.indexOf("\"", addressIndex + 10);
            int addressEnd = json.indexOf("\"", addressStart + 1);
            vatInfo.setAddress(json.substring(addressStart + 1, addressEnd));
        }

        return vatInfo;
    }
}