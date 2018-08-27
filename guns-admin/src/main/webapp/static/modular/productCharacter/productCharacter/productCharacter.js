/**
 * 产品特性管理初始化
 */
var ProductCharacter = {
    id: "ProductCharacterTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ProductCharacter.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '商品特性ID', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '产品名称', field: 'productId', visible: false, align: 'center', valign: 'middle'},
             {title: '产品名称', field: 'productName', visible: true, align: 'center', valign: 'middle'},
            {title: '标题', field: 'title', visible: true, align: 'center', valign: 'middle'},
            {title: '图标', field: 'ico', visible: false, align: 'center', valign: 'middle'},
            {title: '内容', field: 'content', visible: true, align: 'center', valign: 'middle'},
            {title: 'APP展示的顺序', field: 'sort', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'commit_time', visible: true, align: 'center', valign: 'middle'},
            {title: '更新时间', field: 'create_time', visible: true, align: 'center', valign: 'middle'},
            {title: '创建人', field: 'creater', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ProductCharacter.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ProductCharacter.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加产品特性
 */
ProductCharacter.openAddProductCharacter = function () {
    var index = layer.open({
        type: 2,
        title: '添加产品特性',
        area: ['800px', '800px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/productCharacter/productCharacter_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看产品特性详情
 */
ProductCharacter.openProductCharacterDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '产品特性详情',
            area: ['800px', '800px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/productCharacter/productCharacter_update/' + ProductCharacter.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除产品特性
 */
ProductCharacter.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/productCharacter/delete", function (data) {
            Feng.success("删除成功!");
            ProductCharacter.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("productCharacterId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询产品特性列表
 */
ProductCharacter.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ProductCharacter.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ProductCharacter.initColumn();
    var table = new BSTable(ProductCharacter.id, "/productCharacter/list", defaultColunms);
    table.setPaginationType("client");
    ProductCharacter.table = table.init();
});
