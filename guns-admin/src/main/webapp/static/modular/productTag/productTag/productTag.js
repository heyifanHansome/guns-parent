/**
 * 产品标签管理初始化
 */
var ProductTag = {
    id: "ProductTagTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ProductTag.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '分类标签ID', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '分类标签名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'commit_time', visible: true, align: 'center', valign: 'middle'},
            {title: '更新时间', field: 'create_time', visible: true, align: 'center', valign: 'middle'},
            {title: '操作人', field: 'creater', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ProductTag.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ProductTag.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加产品标签
 */
ProductTag.openAddProductTag = function () {
    var index = layer.open({
        type: 2,
        title: '添加产品标签',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/productTag/productTag_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看产品标签详情
 */
ProductTag.openProductTagDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '产品标签详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/productTag/productTag_update/' + ProductTag.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除产品标签
 */
ProductTag.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/productTag/delete", function (data) {
            Feng.success("删除成功!");
            ProductTag.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("productTagId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询产品标签列表
 */
ProductTag.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ProductTag.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ProductTag.initColumn();
    var table = new BSTable(ProductTag.id, "/productTag/list", defaultColunms);
    table.setPaginationType("client");
    ProductTag.table = table.init();
});
