/**
 * 地区概览管理初始化
 */
var AreaTag = {
    id: "AreaTagTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
AreaTag.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '标签ID', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '名字', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '上级名称', field: 'parentAreaTagId', visible: false, align: 'center', valign: 'middle'},
            {title: '提交时间', field: 'addtime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
AreaTag.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        AreaTag.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加地区概览
 */
AreaTag.openAddAreaTag = function () {
    var index = layer.open({
        type: 2,
        title: '添加地区概览',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/areaTag/areaTag_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看地区概览详情
 */
AreaTag.openAreaTagDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '地区概览详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/areaTag/areaTag_update/' + AreaTag.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除地区概览
 */
AreaTag.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/areaTag/delete", function (data) {
            Feng.success("删除成功!");
            AreaTag.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("areaTagId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询地区概览列表
 */
AreaTag.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    AreaTag.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = AreaTag.initColumn();
    var table = new BSTable(AreaTag.id, "/areaTag/list", defaultColunms);
    table.setPaginationType("client");
    AreaTag.table = table.init();
});
