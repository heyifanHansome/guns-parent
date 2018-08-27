/**
 * 我的商品管理初始化
 */
var Product = {
    id: "ProductTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Product.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '自己产品的ID', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '产品名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '商品图片', field: 'picture', visible: false, align: 'center', valign: 'middle'},
        {title: '商品分类', field: 'categoryName', visible: true, align: 'center', valign: 'middle'},
        {title: '热销:hot,推荐', field: 'type', visible: false, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'commit_time', visible: true, align: 'center', valign: 'middle'},
        {title: '更新时间', field: 'create_time', visible: true, align: 'center', valign: 'middle'},
        {title: '商品类型', field: 'newType', visible: true, align: 'center', valign: 'middle'},
        {title: '商品图业务ID', field: 'baseId', visible: false, align: 'center', valign: 'middle'},
        {title: '创建人', field: 'creater', visible: true, align: 'center', valign: 'middle'}

    ];
};

/**
 * 检查是否选中
 */
Product.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Product.seItem = selected[0];
        return true;
    }
};


/**
 * 点击添加我的商品
 */
Product.openAddProduct = function () {

    var index = layer.open({
        type: 2,
        title: '添加我的商品',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/product/product_add'
    });
    this.layerIndex = index;
    // createFileInput(null);
};

/**
 * 打开查看我的商品详情
 */
Product.openProductDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '我的商品详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/product/product_update/' + Product.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除我的商品
 */
Product.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/product/delete", function (data) {
            Feng.success("删除成功!");
            Product.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("productId", this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询我的商品列表
 */
Product.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Product.table.refresh({query: queryData});
};


$(function () {
    var defaultColunms = Product.initColumn();
    var table = new BSTable(Product.id, "/product/list", defaultColunms);
    table.setPaginationType("client");
    Product.table = table.init();


});





