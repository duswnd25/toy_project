package app.studiotft.sid_radio.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;

import app.studiotft.sid_radio.CommonClass;
import app.studiotft.sid_radio.R;
import app.studiotft.sid_radio.maps.MapMarkers;


public class EventMapView extends CommonClass implements View.OnClickListener, MapView.POIItemEventListener, MapView.CurrentLocationEventListener {

    private List<MapMarkers> markers = new ArrayList<>();
    private MapView mapView;
    private FloatingActionsMenu fabMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_map_view);

        getMyActionBar().setDisplayHomeAsUpEnabled(true);

		/* 지도 */
        this.mapView = new MapView(this);
        this.mapView.setDaumMapApiKey("d4c287933acf29852c072a445fad2b91");
        this.mapView.setCurrentLocationEventListener(this);
        this.mapView.setPOIItemEventListener(this);

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map);
        mapViewContainer.addView(mapView);

		/*
        각각 명칭, 지도에 띄울 마커 아이콘, 태그, 좌표, 사이트 주소 순이다.
		사이트 주소는 네이버 지도 상세보기에서 http://map.naver.com/local/siteview.nhn?code=
		부분을 제외한 부분을 입력한다. MapPoi 클래스에서 앞부분을 가지고 있음
		*/

        this.markers.add(new MapMarkers("아틀리에 이노", R.drawable.ic_cafe_black, 0, 37.4768964, 126.9773558, "35823621"));
        this.markers.add(new MapMarkers("건대 인디스타", R.drawable.ic_maps_play_black, 1, 37.5382209, 127.0668402, "32294544"));
        this.markers.add((new MapMarkers("퀸 라이브홀", R.drawable.ic_maps_play_black, 2, 37.5577900, 126.9426310, "11639660")));
        this.markers.add((new MapMarkers("보라매 공원", R.drawable.ic_maps_place_black, 3, 37.4923330, 126.9182890, "11622412")));

        for (MapMarkers item : markers) {
            this.mapView.addPOIItem(item.getMapPoi());
        }

        this.mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633), true);
        this.mapView.setPOIItemEventListener(this);

        this.fabMenu = (FloatingActionsMenu) findViewById(R.id.activity_event_map_view_fab_container);
        this.fabMenu.expand();

        findViewById(R.id.activity_event_map_view_inno).setOnClickListener(this);
        findViewById(R.id.activity_event_map_view_indi_star).setOnClickListener(this);
        findViewById(R.id.activity_event_map_view_queen_live).setOnClickListener(this);
        findViewById(R.id.activity_event_map_view_bora_park).setOnClickListener(this);
        findViewById(R.id.activity_event_map_view_my_location).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_event_map_view_inno:
                this.mapView.setMapCenterPointAndZoomLevel(
                        MapPoint.mapPointWithGeoCoord(
                                markers.get(0).getLatitude(),
                                markers.get(0).getLongitude()),
                        1,
                        true
                );
                this.fabMenu.collapse();
                break;
            case R.id.activity_event_map_view_indi_star:
                this.mapView.setMapCenterPointAndZoomLevel(
                        MapPoint.mapPointWithGeoCoord(
                                markers.get(1).getLatitude(),
                                markers.get(1).getLongitude()),
                        1,
                        true
                );
                this.fabMenu.collapse();
                break;
            case R.id.activity_event_map_view_queen_live:
                this.mapView.setMapCenterPointAndZoomLevel(
                        MapPoint.mapPointWithGeoCoord(
                                markers.get(2).getLatitude(),
                                markers.get(2).getLongitude()),
                        1,
                        true
                );
                this.fabMenu.collapse();
                break;
            case R.id.activity_event_map_view_bora_park:
                this.mapView.setMapCenterPointAndZoomLevel(
                        MapPoint.mapPointWithGeoCoord(
                                markers.get(3).getLatitude(),
                                markers.get(3).getLongitude()),
                        1,
                        true
                );
                this.fabMenu.collapse();
                break;
            case R.id.activity_event_map_view_my_location:
                this.mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
                this.fabMenu.collapse();
                break;
        }
    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        mapView.setMapCenterPointAndZoomLevel(
                MapPoint.mapPointWithGeoCoord(
                        mapPOIItem.getMapPoint().getMapPointGeoCoord().latitude,
                        mapPOIItem.getMapPoint().getMapPointGeoCoord().longitude),
                1,
                true
        );
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(markers.get(mapPOIItem.getTag()).getUrl())));
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {

    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {
        Toast.makeText(this, "현재 위치를 찾지 못했습니다.", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {
        Toast.makeText(this, "현재 위치 검색 취소됨.", Toast.LENGTH_SHORT).show();
    }
}
