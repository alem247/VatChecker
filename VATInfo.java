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
    // brez eksternih librarijev
    public static VATInfo fromJson(String json) {
        VATInfo vatInfo = new VATInfo();

        int nameIndex = json.indexOf("\"name\"");
        if (nameIndex != -1) {
            int nameStartIndex = json.indexOf("\"", nameIndex + 7) + 1;
            int nameEndIndex = json.indexOf("\"", nameStartIndex);
            vatInfo.setName(json.substring(nameStartIndex, nameEndIndex));
        }

        int addressIndex = json.indexOf("\"address\"");
        if (addressIndex != -1) {
            int addressStartIndex = json.indexOf("\"", addressIndex + 10) + 1;
            int addressEndIndex = json.indexOf("\"", addressStartIndex);
            vatInfo.setAddress(json.substring(addressStartIndex, addressEndIndex));
        }

        return vatInfo;
    }
}