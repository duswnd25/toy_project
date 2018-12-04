package app.kimyeonjung.cuk_dom;

import android.os.Bundle;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class Autolog extends CommonClass {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.auto_log);

		setActionBarHomeButton();

		Crouton.makeText(Autolog.this, "서비스 종료되었습니다.", Style.ALERT).show();
	}

}
