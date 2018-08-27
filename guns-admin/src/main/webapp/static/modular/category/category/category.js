/**
 * 产品分类管理初始化
 */
var Category = {
    id: "CategoryTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Category.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '分类ID', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '分类名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '分类图标', field: 'ico', visible: false, align: 'center', valign: 'middle'},
        {title: '应用指南', field: 'baseId', visible: false, align: 'center', valign: 'middle'},
        {title: '同步时间', field: 'syncTime', visible: true, align: 'center', valign: 'middle'},
        {title: '更新时间', field: 'updateTime', visible: true, align: 'center', valign: 'middle'},
        {title: '分类ID', field: 'categoryId', visible: true, align: 'center', valign: 'middle'},
        {title: '创建人', field: 'creater', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Category.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Category.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加产品分类
 */
Category.openAddCategory = function () {
    var ajax = new $ax(Feng.ctxPath + "/category/add", function () {
        Category.table.refresh();
        Feng.success("同步成功!");
    }, function () {

    });
    ajax.set();
    ajax.start();
};



/**
 * 点击添加我的商品分类
 */
Category.openCategory = function () {

    var index = layer.open({
        type: 2,
        title: '添加我的商品分类',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/category/category_add'
    });
    this.layerIndex = index;
};


/**
 * 打开查看产品分类详情
 */
Category.openCategoryDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '产品分类详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/category/category_update/' + Category.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除产品分类
 */
Category.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/category/delete", function (data) {
            Feng.success("删除成功!");
            Category.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("categoryId", this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询产品分类列表
 */
Category.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Category.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Category.initColumn();
    var table = new BSTable(Category.id, "/category/list", defaultColunms);
    table.setPaginationType("client");
    Category.table = table.init();
});
