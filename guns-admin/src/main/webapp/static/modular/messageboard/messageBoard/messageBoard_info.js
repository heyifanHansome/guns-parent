/**
 * 初始化咨询留言表详情对话框
 */
var MessageBoardInfoDlg = {
    messageBoardInfoData : {}
};

/**
 * 清除数据
 */
MessageBoardInfoDlg.clearData = function() {
    this.messageBoardInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MessageBoardInfoDlg.set = function(key, val) {
    this.messageBoardInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MessageBoardInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MessageBoardInfoDlg.close = function() {
    parent.layer.close(window.parent.MessageBoard.layerIndex);
}

/**
 * 收集数据
 */
MessageBoardInfoDlg.collectData = function() {
    this
    .set('id')
    .set('tagId')
    .set('content')
    .set('companyName')
    .set('phone')
    .set('city')
    .set('province')
    .set('Contact')
    .set('commitTime');
}

/**
 * 提交添加
 */
MessageBoardInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/messageBoard/add", function(data){
        Feng.success("添加成功!");
        window.parent.MessageBoard.table.refresh();
        MessageBoardInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.messageBoardInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
MessageBoardInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/messageBoard/update", function(data){
        Feng.success("修改成功!");
        window.parent.MessageBoard.table.refresh();
        MessageBoardInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.messageBoardInfoData);
    ajax.start();
}

$(function() {

});
