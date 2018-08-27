/**
 * 咨询留言表管理初始化
 */
var MessageBoard = {
    id: "MessageBoardTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MessageBoard.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '留言板分类标签id', field: 'tagId', visible: false, align: 'center', valign: 'middle'},
        {title: '留言内容', field: 'content', visible: true, align: 'center', valign: 'middle'},
        {title: '公司名称', field: 'company_name', visible: true, align: 'center', valign: 'middle'},
        {title: '联系电话', field: 'phone', visible: true, align: 'center', valign: 'middle'},
        {title: '市', field: 'city', visible: false, align: 'center', valign: 'middle'},
        {title: '省', field: 'province', visible: false, align: 'center', valign: 'middle'},
        {title: '联系人', field: 'Contact', visible: true, align: 'center', valign: 'middle'},
        {title: '提交时间', field: 'commit_time', visible: true, align: 'center', valign: 'middle'},
        {title: '留言板分类标签', field: 'tagName', visible: true, align: 'center', valign: 'middle'},
        {title: '市', field: 'cityName', visible: true, align: 'center', valign: 'middle'},
        {title: '省', field: 'provinceName', visible: true, align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
MessageBoard.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        MessageBoard.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加咨询留言表
 */
MessageBoard.openAddMessageBoard = function () {
    var index = layer.open({
        type: 2,
        title: '添加咨询留言表',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/messageBoard/messageBoard_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看咨询留言表详情
 */
MessageBoard.openMessageBoardDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '咨询留言表详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/messageBoard/messageBoard_update/' + MessageBoard.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除咨询留言表
 */
MessageBoard.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/messageBoard/delete", function (data) {
            Feng.success("删除成功!");
            MessageBoard.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("messageBoardId", this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询咨询留言表列表
 */
MessageBoard.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    MessageBoard.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = MessageBoard.initColumn();
    var table = new BSTable(MessageBoard.id, "/messageBoard/list", defaultColunms);
    table.setPaginationType("client");
    MessageBoard.table = table.init();
});
