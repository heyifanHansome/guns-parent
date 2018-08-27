/**
 * 初始化app用户管理详情对话框
 */
var LoginUserInfoDlg = {
    loginUserInfoData : {}
};

/**
 * 清除数据
 */
LoginUserInfoDlg.clearData = function() {
    this.loginUserInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
LoginUserInfoDlg.set = function(key, val) {
    this.loginUserInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
LoginUserInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
LoginUserInfoDlg.close = function() {
    parent.layer.close(window.parent.LoginUser.layerIndex);
}

/**
 * 收集数据
 */
LoginUserInfoDlg.collectData = function() {
    this
    .set('id')
    .set('name')
    .set('photo')
    .set('companyName')
    .set('areaTagProvince')
    .set('areaTagCity')
    .set('address')
    .set('wechat')
    .set('phone')
    .set('userCommitTime');
}

/**
 * 提交添加
 */
LoginUserInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/loginUser/add", function(data){
        Feng.success("添加成功!");
        window.parent.LoginUser.table.refresh();
        LoginUserInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.loginUserInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
LoginUserInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/loginUser/update", function(data){
        Feng.success("修改成功!");
        window.parent.LoginUser.table.refresh();
        LoginUserInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.loginUserInfoData);
    ajax.start();
}

$(function() {

});
