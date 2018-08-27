/**
 * 公司信息管理初始化
 */
var AboutCompany = {
    id: "AboutCompanyTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
AboutCompany.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '关于我们表ID', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '云科图片', field: 'aboutPicture', visible: false, align: 'center', valign: 'middle'},
        {title: '关于我们公司', field: 'aboutCompany', visible: false, align: 'center', valign: 'middle'},
        {title: '联系我们', field: 'contactUs', visible: false, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'commitTime', visible: true, align: 'center', valign: 'middle'},
        {title: '更新时间', field: 'updateTime', visible: true, align: 'center', valign: 'middle'}

    ];
};

/**
 * 检查是否选中
 */
AboutCompany.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        AboutCompany.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加公司信息
 */
AboutCompany.openAddAboutCompany = function () {
    var index = layer.open({
        type: 2,
        title: '添加公司信息',
        area: ['800px', '800px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/aboutCompany/aboutCompany_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看公司信息详情
 */
AboutCompany.openAboutCompanyDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '公司信息详情',
            area: ['800px', '800px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/aboutCompany/aboutCompany_update/' + AboutCompany.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除公司信息
 */
AboutCompany.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/aboutCompany/delete", function (data) {
            Feng.success("删除成功!");
            AboutCompany.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("aboutCompanyId", this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询公司信息列表
 */
AboutCompany.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    AboutCompany.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = AboutCompany.initColumn();
    var table = new BSTable(AboutCompany.id, "/aboutCompany/list", defaultColunms);
    table.setPaginationType("client");
    AboutCompany.table = table.init();
});
