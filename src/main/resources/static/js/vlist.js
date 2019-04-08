//初始化表格内容
function fillin_table(result){
    $("#bodycontent").empty();
    $.each(result,function(index,item){
        var MovieName = $("<td ></td>").append(item.movieName);
        var MoviePic=$("<image class='ui tiny image' src='"+item.moviePicture+"'></image>");
        var moviePic=$("<td></td>").append(MoviePic);
        var MovieActors = $("<td></td>").append(item.movieActors);
        var MovieTime = $("<td></td>").append(item.movieTime);
        var PlayedCount=$("<td></td>").append("0");
        if(item.playedCount!=null){
             PlayedCount = $("<td></td>").append(item.playedCount);
        }
        var DetailBtn=$("<a class='mini ui left attached grey  basic button' href='/video/detail?ResourceID="+item.movieId+"'></a>").append("详情浏览");
        var PlayBtn=$("<a class='mini right attached ui teal basic button' href='/video/play?ResourceID="+item.playUrl+"89yu"+item.movieId+"'></a>").append("播放地址");
        var btnTd = $("<td style='width: 17%'></td>").append(DetailBtn).append(PlayBtn);
        $("<tr></tr>")
            .append(MovieName)
            .append(moviePic)
            .append(MovieActors)
            .append(MovieTime)
            .append(PlayedCount)
            .append(btnTd)
            .appendTo("#bodycontent");
    });
}
/*点击搜索按钮的操作*/
$("#searchBtn").click(function () {
    var key=$("#keyword").val();
    updatePage(null,key);

});

/*初始化页面*/
$(function () {
   updatePage();
});

function updatePage(pageConf,keyWord) {
    if(!pageConf){
        pageConf={};
        pageConf.pageSize=10;
        pageConf.currentPage=1;
    }
    if (!keyWord){
        keyWord="all";
    }
    var dataJson={
        "page":pageConf.currentPage,
        "pageSize":pageConf.pageSize,
        "keyword":keyWord
    };
    $.ajax({
        url:"/video/list",
        type:"GET",
        data:dataJson,
        dataType:"JSON",
        /*contentType:'application/x-www-form-urlencoded', // contentType很重要*/
        success:function(result){
            if(result["count"]==0){
                alert("没有相关数据，请更换检索条件，谢谢！");
            }
            layui.use('laypage', function() {
                var laypage = layui.laypage;
                // 执行一个laypage实例
                laypage.render({
                    elem : 'pageNav',
                    count : result["count"],
                    curr : pageConf.currentPage,
                    limits : [5,10,20,40,50],
                    limit : pageConf.pageSize,
                    first : "首页",
                    last : "尾页",
                    layout : [ 'prev', 'page', 'next', 'limit', 'count', 'skip' ],
                    theme : '#41cc45',
                    jump : function(obj, first) {
                        if (!first) {
                            pageConf.currentPage = obj.curr;
                            pageConf.pageSize = obj.limit;
                            updatePage(pageConf,keyWord);
                        }
                    }
                });
            });
            if(result["data"]){
                //初始化表格
                fillin_table(result["data"]);//result["data"]["table"]
            }else{
                alert("数据库连接超时，请联系管理员！！！");
            }

        }
    });
}