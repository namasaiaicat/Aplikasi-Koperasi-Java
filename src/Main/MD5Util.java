/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;
import java.security.MessageDigest;

/**
 *
 * @author LENOVO
 */
public class MD5Util {
    public static String hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] res = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : res) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            return "";   
        }
    }
}
