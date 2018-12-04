package app.studiotft.sid_radio.maps;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;

/**
 * Created by YeonJung on 2015-07-12.
 * 맵뷰 마커
 */
public class MapMarkers {
	private double latitude, longitude;
	private Integer res;
	private String title;
	private StringBuilder url = new StringBuilder("http://map.naver.com/local/siteview.nhn?code=");
	private int tag;

	public MapMarkers(String title, Integer res, int tag, double latitude, double longitude, String url) {
		this.title = title;
		this.res = res;
		this.latitude = latitude;
		this.longitude = longitude;
		this.tag = tag;
		this.url.append(url);
	}

	public MapPOIItem getMapPoi() {
		MapPOIItem customMarker = new MapPOIItem();
		customMarker.setItemName(this.title);
		customMarker.setTag(this.tag);
		customMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(this.latitude, this.longitude));
		customMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커타입을 커스텀 마커로 지정.
		customMarker.setCustomImageResourceId(this.res); // 마커 이미지.
		customMarker.setCustomImageAutoscale(false);
		customMarker.setCustomImageAnchor(0.5f, 1.0f);
		return customMarker;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public String getUrl() {
		return this.url.toString();
	}
}
