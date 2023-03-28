public class task1 {
    // Преобразование строки IPv4 адреса в int32
    public static int ipToInt(String ipAddress) throws IllegalArgumentException {
        String[] octets = ipAddress.split("\\.");
        if (octets.length != 4) {
            throw new IllegalArgumentException("Invalid IPv4 address format");
        }
        int result = 0;
        for (int i = 0; i < octets.length; i++) {
            try {
                int octet = Integer.parseInt(octets[i]);
                if (octet < 0 || octet > 255) {
                    throw new IllegalArgumentException("Invalid IPv4 address format");
                }
                result |= octet << ((3 - i) * 8);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid IPv4 address format");
            }
        }
        return result;
    }

    // Преобразование int32 в строку IPv4 адреса
    public static String intToIp(int ipAddress) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.insert(0, Integer.toString((ipAddress >> (i * 8)) & 0xff));
            if (i < 3) {
                sb.insert(0, '.');
            }
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        try {
            int ip1 = task1.ipToInt("128.32.10.0");
            String ip2 = task1.intToIp(255);

            System.out.println(ip1); // 2149583360
            System.out.println(ip2); // "0.0.0.255"
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}


