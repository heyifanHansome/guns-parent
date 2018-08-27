/**
 * 初始化公司信息详情对话框
 */
var AboutCompanyInfoDlg = {
    aboutCompanyInfoData : {}
};

/**
 * 清除数据
 */
AboutCompanyInfoDlg.clearData = function() {
    this.aboutCompanyInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AboutCompanyInfoDlg.set = function(key, val) {
    this.aboutCompanyInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AboutCompanyInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
AboutCompanyInfoDlg.close = function() {
    parent.layer.close(window.parent.AboutCompany.layerIndex);
}

/**
 * 收集数据
 */
AboutCompanyInfoDlg.collectData = function() {

    this
    .set('id')
    .set('aboutPicture')
    .set('aboutCompany')
    .set('contactUs');
}

/**
 * 提交添加
 */
AboutCompanyInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/aboutCompany/add", function(data){
        Feng.success("添加成功!");
        window.parent.AboutCompany.table.refresh();
        AboutCompanyInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.aboutCompanyInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
AboutCompanyInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/aboutCompany/update", function(data){
        Feng.success("修改成功!");
        window.parent.AboutCompany.table.refresh();
        AboutCompanyInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.aboutCompanyInfoData);
    ajax.start();
}

$(function() {

    // 初始化头像上传
    var fileServerPathUp = new $WebUpload("aboutPicture");
    fileServerPathUp.setUploadBarId("progressBar");
    fileServerPathUp.init();

});
