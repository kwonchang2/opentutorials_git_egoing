<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
#map{
	width : 70%;
	height : 800px;
	border : 1px solid red;
	float : left;
}

#info{
	width : 25%;
	height: 800px;
	border : 1px solid blue;
	float : left;
	background : yellow;
}

ul{
	width : 90%;
}

ul li{
	list-style : none;
}

ul li img{
	width : 90%;
}

textarea{
	width : 90%;
}

#star img{
	width : 16%;
}
</style>

<script>

//원하는 위도 경도 및 줌을 설정하여 지도를 출력하자
//callback은 개발자가 호출하는 것이 아닌 시스템이 호출하는 것이다.
function loadMap(){
	var container = document.getElementById("map");
	 
	var option = {
			center : new google.maps.LatLng(37.497594, 127.038105),
			zoom : 30
	}
	
	var map = new google.maps.Map(container,option);
	var lati;
	var lng;
	
	map.addListener('click', function(event) {
	    // 3 seconds after the center of the map has changed, pan back to the
	    // marker.
	   //alert(event.latLng.lat() + "," + event.latLng.lng());
	   lati=event.latLng.lat();
	   lng= event.latLng.lng();
	
	   form1.lati.value=lati;
	   form1.lng.value=lng;
	   
	   addMarker({lat: lati, lng: lng},map);
	 });
	
	// Adds a marker to the map.
	function addMarker(location, map) {
	  
	  var contentString = '<div id="content">'+
      '<div id="siteNotice">'+
      '</div>'+
      '<div id="bodyContent">'+
      '<p>여기가 진정한 맛집</p>'+
      '</div>'+
      '</div>';

	  var infowindow = new google.maps.InfoWindow({
	    content: contentString
	  });
	  
	   var marker = new google.maps.Marker({
		    position: {lat: lati, lng: lng},
		    map: map,
		    title: "여기가 맛집이다.",
		    animation : google.maps.Animation.DROP,
		   // icon : "/MapApp/images/full.png"
	 	});
	   
	   marker.addListener("click", function(){
		   infowindow.open(map, marker);
	   });
	}
}

function regist(){
	form1.encoding="multipart/form-data";
	form1.action="/admin/regist";
	form1.submit();
	
}

/*
 * 매개변수로 넘길 input : file 컴포넌트
 */
function preView(){
	   
      var oFReader = new FileReader();
      oFReader.readAsDataURL(document.getElementById("img").files[0]);

      oFReader.onload = function (oFREvent) {
          document.getElementById("preImg").src = oFREvent.target.result;
      };

}
  
 function Score(option){
	 var star=document.getElementById("star");
	 if(option==0.5){
		 star.innerHTML="<img src=\"/images/half.png\">";
	 }else if(option==1){
		 star.innerHTML="<img src=\"/images/full.png\">";
	 }else if(option==1.5){
		 star.innerHTML="<img src=\"/images/full.png\"><img src=\"/images/half.png\">";
	 }else if(option==2){
		 star.innerHTML="<img src=\"/images/full.png\"><img src=\"/images/full.png\">";
	 }else if(option==2.5){
		 star.innerHTML="<img src=\"/images/full.png\"><img src=\"/images/full.png\"><img src=\"/images/half.png\">";
	 }else if(option==3){
		 star.innerHTML="<img src=\"/images/full.png\"><img src=\"/images/full.png\"><img src=\"/images/full.png\">";
	 }else if(option==3.5){
		 star.innerHTML="<img src=\"/images/full.png\"><img src=\"/images/full.png\"><img src=\"/images/full.png\"><img src=\"/images/half.png\">";
	 }else if(option==4){
		 star.innerHTML="<img src=\"/images/full.png\"><img src=\"/images/full.png\"><img src=\"/images/full.png\"><img src=\"/images/full.png\">";
	 }else if(option==4.5){
		 star.innerHTML="<img src=\"/images/full.png\"><img src=\"/images/full.png\"><img src=\"/images/full.png\"><img src=\"/images/full.png\"><img src=\"/images/half.png\">";
	 }else if(option==5){
		 star.innerHTML="<img src=\"/images/full.png\"><img src=\"/images/full.png\"><img src=\"/images/full.png\"><img src=\"/images/full.png\"><img src=\"/images/full.png\">";
	 }
	 
 }
</script>
</head>
<body>
	<div id="map">여기에 지도 나올 예정</div>
	<div id="info">
	<form name="form1" method="post">
		<ul>
			<li>위도 : <input type = "text" name="lati"></li>
			<li>경도 : <input type = "text" name="lng"></li>
			<li>상호명 : <input type = "text" name="name"></li>
			<li>사진 : <input type = "file" id="img" name="img" onChange="preView()"></li>
			<li><img id="preImg" name="preImg" src="http://cfile8.uf.tistory.com/image/1442534D4FC3A17004001B"></li>
			<li><textarea name="content"></textarea></li>
			<li>
				<select name="score" onChange="Score(this.value)">
					<%for(int i=1;i<=10;i++){ %>
					<option><%=i*0.5 %></option>
					<%}%>
				</select>
			</li>
			<li>
				<span id="star" name="star">
					<img src="/images/half.png">
				</span>
			</li>
			<li><input type="button" value="등록" onClick="regist()"></li>
			
		</ul>
	</form>
	</div>
</body>
</html>

<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD0RriAhjUWzjJL9AIANougGoQCUXNXzPE&callback=loadMap"></script>