/**
 * 初始化用户产品评论详情对话框
 */
var ProductCommitUserInfoDlg = {
    productCommitUserInfoData : {}
};

/**
 * 清除数据
 */
ProductCommitUserInfoDlg.clearData = function() {
    this.productCommitUserInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProductCommitUserInfoDlg.set = function(key, val) {
    this.productCommitUserInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProductCommitUserInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ProductCommitUserInfoDlg.close = function() {
    parent.layer.close(window.parent.ProductCommitUser.layerIndex);
}

/**
 * 收集数据
 */
ProductCommitUserInfoDlg.collectData = function() {
    this
    .set('id')
    .set('commitId')
    .set('productId')
    .set('userId')
    .set('commitTime');
}

/**
 * 提交添加
 */
ProductCommitUserInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/productCommitUser/add", function(data){
        Feng.success("添加成功!");
        window.parent.ProductCommitUser.table.refresh();
        ProductCommitUserInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.productCommitUserInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ProductCommitUserInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/productCommitUser/update", function(data){
        Feng.success("修改成功!");
        window.parent.ProductCommitUser.table.refresh();
        ProductCommitUserInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.productCommitUserInfoData);
    ajax.start();
}

$(function() {

});
