/**
 * 点赞管理初始化
 */
var OtherProductCommit = {
    id: "OtherProductCommitTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
OtherProductCommit.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '产品点赞表ID', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '第三方商品id', field: 'otherProductId', visible: true, align: 'center', valign: 'middle'},
            {title: '用户id', field: 'userId', visible: true, align: 'center', valign: 'middle'},
            {title: '更新时间', field: 'commitTime', visible: true, align: 'center', valign: 'middle'},
            {title: '自己产品id', field: 'productId', visible: true, align: 'center', valign: 'middle'},
            {title: '新闻表id', field: 'newId', visible: true, align: 'center', valign: 'middle'},
            {title: '云科商品名称', field: 'productName', visible: true, align: 'center', valign: 'middle'},
            {title: '云科产品图片', field: 'productImgUrl', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
OtherProductCommit.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        OtherProductCommit.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加点赞
 */
OtherProductCommit.openAddOtherProductCommit = function () {
    var index = layer.open({
        type: 2,
        title: '添加点赞',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/otherProductCommit/otherProductCommit_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看点赞详情
 */
OtherProductCommit.openOtherProductCommitDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '点赞详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/otherProductCommit/otherProductCommit_update/' + OtherProductCommit.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除点赞
 */
OtherProductCommit.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/otherProductCommit/delete", function (data) {
            Feng.success("删除成功!");
            OtherProductCommit.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("otherProductCommitId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询点赞列表
 */
OtherProductCommit.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    OtherProductCommit.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = OtherProductCommit.initColumn();
    var table = new BSTable(OtherProductCommit.id, "/otherProductCommit/list", defaultColunms);
    table.setPaginationType("client");
    OtherProductCommit.table = table.init();
});
