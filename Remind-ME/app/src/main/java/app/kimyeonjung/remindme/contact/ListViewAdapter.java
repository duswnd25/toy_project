package app.kimyeonjung.remindme.contact;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import app.kimyeonjung.remindme.R;
import app.kimyeonjung.remindme.ui.PersonDetailView;
import app.kimyeonjung.remindme.utils.Keys;

public class ListViewAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<ContactItem> contactIemList = null;
	private Context context;

	public ListViewAdapter(Context context, List<ContactItem> circleListItem) {
		this.contactIemList = circleListItem;
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return contactIemList.size();
	}

	@Override
	public Object getItem(int position) {
		return contactIemList.get(position);
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
			view = inflater.inflate(R.layout.item_contact, null);
			holder.name = (TextView) view.findViewById(R.id.item_contact_name);
			holder.message = (ImageView) view.findViewById(R.id.item_contact_message);
			holder.call = (ImageView) view.findViewById(R.id.item_contact_call);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.name.setText(String.valueOf(contactIemList.get(position).getName()));
		holder.name.setSelected(true);

		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, PersonDetailView.class);
				intent.putExtra("name", contactIemList.get(position).getName());
				intent.putExtra("tel", contactIemList.get(position).getTel());
				intent.putExtra("manage", contactIemList.get(position).getManage());
				intent.putExtra("block", contactIemList.get(position).getBlock());
				intent.putExtra("memo", contactIemList.get(position).getMemo());

				context.startActivity(intent);
			}
		});

		view.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				AlertDialog.Builder send_phone_number_alert = new AlertDialog.Builder(context);
				send_phone_number_alert
						.setMessage(R.string.really_delete)
						.setCancelable(false)
						.setPositiveButton(android.R.string.yes,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {

										ParseQuery<ParseObject> query = ParseQuery.getQuery(Keys.ContactDBName);
										query.fromLocalDatastore();
										query.whereEqualTo("tel", contactIemList.get(position).getTel());
										query.whereEqualTo("name", contactIemList.get(position).getName());
										query.getFirstInBackground(new GetCallback<ParseObject>() {
											@Override
											public void done(ParseObject parseObject, ParseException e) {
												final String name = parseObject.getString("name");
												parseObject.unpinInBackground(new DeleteCallback() {
													@Override
													public void done(ParseException e) {
														if (e == null) {
															contactIemList.remove(position);
															ListViewAdapter.this.notifyDataSetChanged();
															Toast.makeText(context, name + " " + context.getString(R.string.delete_ok), Toast.LENGTH_SHORT).show();
														} else {
															Toast.makeText(context, name + " " + context.getString(R.string.delete_fail), Toast.LENGTH_SHORT).show();
														}
													}
												});
											}
										});
									}
								})
						.setNegativeButton(android.R.string.no,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
										dialog.dismiss();
									}
								});
				AlertDialog alert = send_phone_number_alert.create();
				alert.show();
				return false;
			}
		});
		holder.message.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "문자 - " + contactIemList.get(position).getName(), Toast.LENGTH_SHORT).show();
				Intent sendIntent = new Intent(Intent.ACTION_VIEW);
				sendIntent.putExtra("address", contactIemList.get(position).getTel());
				sendIntent.setType("vnd.android-dir/mms-sms");
				context.startActivity(sendIntent);
			}
		});

		holder.call.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "전화 - " + contactIemList.get(position).getName(), Toast.LENGTH_SHORT).show();
				AlertDialog.Builder send_phone_number_alert = new AlertDialog.Builder(context);
				send_phone_number_alert
						.setMessage(R.string.really_call)
						.setCancelable(false)
						.setPositiveButton(android.R.string.yes,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
										Intent intent = new Intent(Intent.ACTION_CALL,
												Uri.parse("tel:" + contactIemList.get(position).getTel()));
										context.startActivity(intent);
									}
								})
						.setNegativeButton(android.R.string.no,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
										dialog.dismiss();
									}
								});
				AlertDialog alert = send_phone_number_alert.create();
				alert.show();
			}
		});


		return view;
	}

	public class ViewHolder {
		TextView name;
		ImageView call, message;
	}
}
