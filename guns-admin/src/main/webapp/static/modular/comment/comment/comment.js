/**
 * 用户评论管理初始化
 */
var Comment = {
    id: "CommentTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Comment.initColumn = function () {
    return [
        {field: 'selectItem', radio: false},
        {title: '评论ID', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '我们的产品ID', field: 'myProductId', visible: false, align: 'center', valign: 'middle'},
        {title: '云科的产品', field: 'productName', visible: true, align: 'center', valign: 'middle'},
        {title: '用户id', field: 'userId', visible: false, align: 'center', valign: 'middle'},
        {title: '管理员ID', field: 'admin', visible: true, align: 'center', valign: 'middle'},
        {title: '评论内容', field: 'content', visible: true, align: 'center', valign: 'middle'},
        {title: '提交评论时间', field: 'commit_time', visible: true, align: 'center', valign: 'middle'},
        {title: '0,否,1.是', field: 'checkUp', visible: false, align: 'center', valign: 'middle'},
        {title: '审核是否通过', field: 'checkUpName', visible: true, align: 'center', valign: 'middle'},
        {title: '评论用户名称', field: 'LoginUserName', visible: true, align: 'center', valign: 'middle'},
        {title: '热销/推荐', field: 'product_name', visible: true, align: 'center', valign: 'middle'}
    ];
};
var commentId = [];

/**
 * 检查是否选中
 */
Comment.check = function () {
    selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Comment.seItem = selected.id;
        return true;
    }
};



/**
 * 审批是否选中
 */
Comment.approvalCheck = function () {
    selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        for(var i=0; i<selected.length; i++) {
            commentId.push(selected[i].id);
        }
        return true;
    }
};



/**
 * 点击添加用户评论
 */
Comment.openAddComment = function () {

    var index = layer.open({
        type: 2,
        title: '添加用户评论',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/comment/comment_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看用户评论详情
 */
Comment.openCommentDetail = function () {
    debugger;
    if (this.approvalCheck()) {
        var ajax = new $ax(Feng.ctxPath + "/comment/comment_update/" + commentId, function (data) {
            Feng.success("审核成功!");
            Comment.table.refresh();
        }, function (data) {
            Feng.success("审核成功!");
        });
        ajax.set();
        ajax.start();
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        Comment.table.refresh({query: queryData});
    }
};

/**
 * 删除用户评论
 */
Comment.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/comment/delete", function (data) {
            Feng.success("删除成功!");
            Comment.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("commentId", this.seItem.id);
        ajax.start();

    }
};

/**
 * 查询用户评论列表
 */
Comment.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Comment.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Comment.initColumn();
    var table = new BSTable(Comment.id, "/comment/list", defaultColunms);
    table.setPaginationType("client");
    Comment.table = table.init();
});
