/**
 * 初始化点赞详情对话框
 */
var OtherProductCommitInfoDlg = {
    otherProductCommitInfoData : {}
};

/**
 * 清除数据
 */
OtherProductCommitInfoDlg.clearData = function() {
    this.otherProductCommitInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
OtherProductCommitInfoDlg.set = function(key, val) {
    this.otherProductCommitInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
OtherProductCommitInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
OtherProductCommitInfoDlg.close = function() {
    parent.layer.close(window.parent.OtherProductCommit.layerIndex);
}

/**
 * 收集数据
 */
OtherProductCommitInfoDlg.collectData = function() {
    this
    .set('id')
    .set('otherProductId')
    .set('userId')
    .set('commitTime')
    .set('productId')
    .set('newId')
    .set('productName')
    .set('productImgUrl');
}

/**
 * 提交添加
 */
OtherProductCommitInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/otherProductCommit/add", function(data){
        Feng.success("添加成功!");
        window.parent.OtherProductCommit.table.refresh();
        OtherProductCommitInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.otherProductCommitInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
OtherProductCommitInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/otherProductCommit/update", function(data){
        Feng.success("修改成功!");
        window.parent.OtherProductCommit.table.refresh();
        OtherProductCommitInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.otherProductCommitInfoData);
    ajax.start();
}

$(function() {

});
