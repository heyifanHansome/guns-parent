/**
 * app用户管理管理初始化
 */
var LoginUser = {
    id: "LoginUserTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
LoginUser.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '用户ID', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '用户姓名', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '头像', field: 'photo', visible: false, align: 'center', valign: 'middle'},
            {title: '企业名称', field: 'company_name', visible: true, align: 'center', valign: 'middle'},
            {title: '省_id', field: 'areaTagProvince', visible: false, align: 'center', valign: 'middle'},
            {title: '市_id', field: 'areaTagCity', visible: false, align: 'center', valign: 'middle'},
            {title: '省', field: 'provinceName', visible: true, align: 'center', valign: 'middle'},
            {title: '市', field: 'heyifan', visible: true, align: 'center', valign: 'middle'},
            {title: '详细地址', field: 'address', visible: true, align: 'center', valign: 'middle'},
            {title: '是否获取微信信息,0:否,1:是', field: 'wechat', visible: false, align: 'center', valign: 'middle'},
            {title: '账号设置/手机号/登录帐号', field: 'phone', visible: true, align: 'center', valign: 'middle'},
            {title: '用户提交时间', field: 'user_commit_time', visible: true, align: 'center', valign: 'middle'},
        {title: '是否获取微信信息', field: 'wechatName', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
LoginUser.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        LoginUser.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加app用户管理
 */
LoginUser.openAddLoginUser = function () {
    var index = layer.open({
        type: 2,
        title: '添加app用户管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/loginUser/loginUser_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看app用户管理详情
 */
LoginUser.openLoginUserDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: 'app用户管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/loginUser/loginUser_update/' + LoginUser.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除app用户管理
 */
LoginUser.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/loginUser/delete", function (data) {
            Feng.success("删除成功!");
            LoginUser.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("loginUserId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询app用户管理列表
 */
LoginUser.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    LoginUser.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = LoginUser.initColumn();
    var table = new BSTable(LoginUser.id, "/loginUser/list", defaultColunms);
    table.setPaginationType("client");
    LoginUser.table = table.init();
});
