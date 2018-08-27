/**
 * 初始化地区概览详情对话框
 */
var AreaTagInfoDlg = {
    areaTagInfoData : {}
};

/**
 * 清除数据
 */
AreaTagInfoDlg.clearData = function() {
    this.areaTagInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AreaTagInfoDlg.set = function(key, val) {
    this.areaTagInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AreaTagInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
AreaTagInfoDlg.close = function() {
    parent.layer.close(window.parent.AreaTag.layerIndex);
}

/**
 * 收集数据
 */
AreaTagInfoDlg.collectData = function() {
    this
    .set('id')
    .set('name')
    .set('parentAreaTagId')
    .set('addtime');
}

/**
 * 提交添加
 */
AreaTagInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/areaTag/add", function(data){
        Feng.success("添加成功!");
        window.parent.AreaTag.table.refresh();
        AreaTagInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.areaTagInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
AreaTagInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/areaTag/update", function(data){
        Feng.success("修改成功!");
        window.parent.AreaTag.table.refresh();
        AreaTagInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.areaTagInfoData);
    ajax.start();
}

$(function() {

});
