<%@page contentType="text/html; utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<script>
    $(function () {
        $("#albumList").jqGrid({
            url:"${pageContext.request.contextPath}/album/findByPage",
            editurl:"${pageContext.request.contextPath}/album/edit",
            datatype:"json",
            colNames:["ID","标题","分数","作者","播音员","章节数","专辑简介","上传时间","发行时间","插图"],
            colModel:[
                {name:"id"},
                {name:"title",editable:true},
                {name:"score",editable:true},
                {name:"author",editable:true},
                {name:"beam",editable:true},
                {name:"count",editable:true},
                {name:"content",editable:true},
                {name:"upload_date",editable:true},
                {name:"publish_date",editable:true},
                {name:"cover",editable:true,edittype:"file",
                    formatter:function (a, b, c) {
                        return "<img style='width:100px;height:50px' src='${pageContext.request.contextPath}/img/"+a+"' />"
                    }}
            ],
            styleUI:"Bootstrap",
            autowidth:true,
            height:"60%",
            pager:"#albumPager",
            page:1,
            rowNum:2,
            multiselect:true,
            rowList:[2,4,6],
            viewrecords:true,
            subGrid : true,
            subGridRowExpanded:function (subgrid_id, albumId) {
                addSubGrid(subgrid_id,albumId);
            }
        }).jqGrid("navGrid","#albumPager",
            {
                search:false
            },
            {
                closeAfterEdit:true,
                beforeShowForm:function (obj) {
                    obj.find("#title").attr("readonly",true);
                    obj.find("#cover").attr("disabled",true);
                    obj.find("#author").attr("readonly",true);
                    obj.find("#beam").attr("readonly",true);
                    obj.find("#count").attr("readonly",true);
                    obj.find("#content").attr("readonly",true);
                    obj.find("#upload_date").attr("readonly",true);
                    obj.find("#publish_date").attr("readonly",true);
                }
            },
            {
                closeAfterAdd:true,
                afterSubmit:function (response) {
                    var albumId = response.responseText;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/album/upload",
                        fileElementId:"cover",
                        data:{albumId:albumId},
                        success:function (data) {
                            $("#albumList").trigger("reloadGrid");
                        }
                    });
                    return response
                }

            },
            {
            }
        )
    });

    function addSubGrid(subgrid_id,albumId) {
        var tableId=subgrid_id+"table";
        var divId=subgrid_id+"div";
        $("#"+subgrid_id).html(
            "<table id='"+tableId+"' ></table>"+"<div id='"+divId+"' ></div>"
        );
        $("#"+tableId).jqGrid({
            url:"${pageContext.request.contextPath}/chapter/findByPage?albumId="+albumId,
            editurl:"${pageContext.request.contextPath}/chapter/edit?albumId="+albumId,
            datatype:"json",
            colNames:["ID","标题","大小","时长","上传时间","音频","操作"],
            colModel:[
                {name:"id"},
                {name:"title",editable:true},
                {name:"size"},
                {name:"time"},
                {name:"upload_date"},
                {name:"video",editable:true,edittype:"file"},
                {name:"video",
                    formatter:function (cellValue, options, rowObject) {
                        return "<a onclick=\"playAudio('"+cellValue+"')\" href='#'><span class='glyphicon glyphicon-play-circle'></span></a>"+"                       "+
                            "<a onclick=\"downloadAudio('"+cellValue+"')\" href='#'><span class='glyphicon glyphicon-download'></span></a>"
                    }
                }
            ],
            styleUI:"Bootstrap",
            autowidth:true,
            height:"60%",
            pager:"#"+divId,
            page:1,
            rowNum:2,
            multiselect:true,
            rowList:[2,4,6],
            viewrecords:true
        }).jqGrid("navGrid","#"+divId,
            {},
            {},
            {
                closeAfterAdd:true,
                afterSubmit:function (response) {
                    var chapterId = response.responseText;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/chapter/upload?albumId="+albumId,
                        fileElementId:"video",
                        data:{chapterId:chapterId},
                        success:function (data) {
                            $("#"+tableId).trigger("reloadGrid");
                            $("#albumList").trigger("reloadGrid");
                        }
                    });
                    return response
                }
            },
            {
                afterSubmit:function () {
                    $("#"+tableId).trigger("reloadGrid");
                    $("#albumList").trigger("reloadGrid");
                    return "adf";
                }
            }
        )

    }
    function playAudio(d) {
        $("#dialogId").modal("show");
        $("#audioId").attr("src","${pageContext.request.contextPath}/audio/"+d);
    }
    function downloadAudio(a) {
        location.href="${pageContext.request.contextPath}/chapter/download?audioName="+a;
    }
</script>

<div id="dialogId" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <audio id="audioId" controls src=""> </audio>
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<table id="albumList"></table>
<div id="albumPager"></div>