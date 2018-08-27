/**
 * 用户产品评论管理初始化
 */
var ProductCommitUser = {
    id: "ProductCommitUserTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ProductCommitUser.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '商品评论关系表ID', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '评论id', field: 'commitId', visible: true, align: 'center', valign: 'middle'},
            {title: '商品id', field: 'productId', visible: true, align: 'center', valign: 'middle'},
            {title: '用户id', field: 'userId', visible: true, align: 'center', valign: 'middle'},
            {title: '提交时间', field: 'commitTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ProductCommitUser.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ProductCommitUser.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加用户产品评论
 */
ProductCommitUser.openAddProductCommitUser = function () {
    var index = layer.open({
        type: 2,
        title: '添加用户产品评论',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/productCommitUser/productCommitUser_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看用户产品评论详情
 */
ProductCommitUser.openProductCommitUserDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '用户产品评论详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/productCommitUser/productCommitUser_update/' + ProductCommitUser.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除用户产品评论
 */
ProductCommitUser.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/productCommitUser/delete", function (data) {
            Feng.success("删除成功!");
            ProductCommitUser.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("productCommitUserId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询用户产品评论列表
 */
ProductCommitUser.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ProductCommitUser.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ProductCommitUser.initColumn();
    var table = new BSTable(ProductCommitUser.id, "/productCommitUser/list", defaultColunms);
    table.setPaginationType("client");
    ProductCommitUser.table = table.init();
});
