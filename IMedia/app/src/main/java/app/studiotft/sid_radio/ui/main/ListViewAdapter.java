package app.studiotft.sid_radio.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.studiotft.sid_radio.R;
import app.studiotft.sid_radio.ui.EventMapView;
import app.studiotft.sid_radio.ui.info.Info;
import app.studiotft.sid_radio.ui.setting.Setting;


public class ListViewAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<MainMenuItem> itemList = null;
	private Context context;

	public ListViewAdapter(Context context, List<MainMenuItem> itemList) {
		this.itemList = itemList;
		this.inflater = LayoutInflater.from(context);
		this.context = context;
	}

	@Override
	public int getCount() {
		return itemList.size();
	}

	@Override
	public Object getItem(int position) {
		return itemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.item_menu, null);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.backgroundImageView = (ImageView) view.findViewById(R.id.item_menu_background);
		holder.logoImageView = (ImageView) view.findViewById(R.id.item_menu_logo);
		holder.menuTextView = (TextView) view.findViewById(R.id.item_menu_text);

		holder.backgroundImageView.setImageResource(itemList.get(position).getBackgroundImgResId());
		holder.logoImageView.setImageResource(itemList.get(position).getLogImgResId());
		holder.menuTextView.setText(itemList.get(position).getMenu());

		holder.menuTextView.setSelected(true);

		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Uri uri;
				switch (position) {
					case 0:
						/* 공지사항 */
						context.startActivity(new Intent(context, Info.class));
						break;
					case 1:
						/* 상품문의 */
						context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.sabostore.com/shop/shopbrand.html?xcode=021&type=X&mcode=005")));
						break;
					case 2:
						context.startActivity(new Intent(context, EventMapView.class));
						break;
					case 3:
						/* 팬카페 */
						context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://cafe.naver.com/innocentnova")));
						break;
					case 4:
						/* 공식 홈페이지 */
						context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://innocent-music.kr/")));
						break;
					case 5:
						/* 공식 트위터 */
						context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/IMusic_Official?lang=ko")));
						break;
					case 6:
						/* 설정 */
						context.startActivity(new Intent(context, Setting.class));
						break;
				}
			}
		});

		return view;
	}

	public class ViewHolder {
		ImageView backgroundImageView;
		ImageView logoImageView;
		TextView menuTextView;
	}
}
