var url=$("#playurl").val();
var videoObject = {
    container: '.video',//“#”代表容器的ID，“.”或“”代表容器的class
    variable: 'player',//该属性必需设置，值等于下面的new chplayer()的对象
    loaded:'loadedHandler',//监听播放器加载成功
    video: url//视频地址
};
var player=new ckplayer(videoObject);
function loadedHandler(){//播放器加载后会调用该函数
    player.addListener('time', timeHandler); //监听播放时间,addListener是监听函数，需要传递二个参数，'time'是监听属性，这里是监听时间，timeHandler是监听接受的函数
}
function timeHandler(t){
    console.log('当前播放的时间：'+t);
}