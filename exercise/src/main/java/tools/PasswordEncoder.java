package tools;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;


public class PasswordEncoder
{
  private String encodHash = "sha-256";
  
  public String getEncodHash()
  {
    return this.encodHash;
  }
  
  public void setEncodHash(String encodHash)
  {
    this.encodHash = encodHash;
  }
  
  public static String encode(String password)
  {
    try
    {
      MessageDigest md = MessageDigest.getInstance("sha-256");
      byte[] digest = md.digest(password.getBytes("UTF-8"));
      return new String(Base64.encodeBase64(digest));
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
}
