package app.kimyeonjung.cuk_dom;

import android.os.Bundle;

public class Introduce extends CommonClass {

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new IntroduceFragment())
				.commit();

	}

}