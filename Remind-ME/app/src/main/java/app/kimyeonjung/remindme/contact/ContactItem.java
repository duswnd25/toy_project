package app.kimyeonjung.remindme.contact;

import java.util.Date;

/**
 * Created by YeonJung on 2015-06-25.
 * 연락처 클래스
 */
public class ContactItem {

	private String name;
	private String tel;
	private Date lastContact;
	private boolean manage;
	private boolean block;
	private String memo;

	public ContactItem() {

	}

	public ContactItem(String name, String tel, String memo, boolean manage, boolean block) {
		this.name = name;
		this.tel = tel;
		this.memo = memo;
		this.manage = manage;
		this.block = block;
	}

	public void setBlock(boolean block) {
		this.block = block;
	}

	public boolean getBlock() {
		return this.block;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setManage(boolean needCare) {
		this.manage = needCare;
	}

	public boolean getManage() {
		return this.manage;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getName() {
		return this.name;
	}

	public String getTel() {
		return this.tel;
	}

	public void setLastContact(Date lastContact) {
		this.lastContact = lastContact;
	}

	public Date getLastContact() {
		return this.lastContact;
	}
}
