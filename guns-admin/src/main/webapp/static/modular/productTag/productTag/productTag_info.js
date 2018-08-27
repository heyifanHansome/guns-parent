/**
 * 初始化产品标签详情对话框
 */
var ProductTagInfoDlg = {
    productTagInfoData : {}
};

/**
 * 清除数据
 */
ProductTagInfoDlg.clearData = function() {
    this.productTagInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProductTagInfoDlg.set = function(key, val) {
    this.productTagInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProductTagInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ProductTagInfoDlg.close = function() {
    parent.layer.close(window.parent.ProductTag.layerIndex);
}

/**
 * 收集数据
 */
ProductTagInfoDlg.collectData = function() {
    $("#commitTime").val(new Date());
    this
    .set('id')
    .set('name');
}

/**
 * 提交添加
 */
ProductTagInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/productTag/add", function(data){
        Feng.success("添加成功!");
        window.parent.ProductTag.table.refresh();
        ProductTagInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.productTagInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ProductTagInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/productTag/update", function(data){
        Feng.success("修改成功!");
        window.parent.ProductTag.table.refresh();
        ProductTagInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.productTagInfoData);
    ajax.start();
}

$(function() {

});
