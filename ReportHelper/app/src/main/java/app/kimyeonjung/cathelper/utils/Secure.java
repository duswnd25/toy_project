package app.kimyeonjung.cathelper.utils;

/**
 * Created by YeonJung on 2015-04-29.
 * 암호화
 */
public class Secure {
	public String decoder(String input) {
		try {
			return AES256Cipher
					.AES_Decode(input, "efg34kq56haopr1tbcdijlmsyznuvwx2");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public String encoder(String input) {
		try {
			return AES256Cipher
					.AES_Encode(input, "efg34kq56haopr1tbcdijlmsyznuvwx2");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}
}
