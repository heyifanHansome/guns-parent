/**
 * 初始化产品特性详情对话框
 */
var ProductCharacterInfoDlg = {
    ProductCharacterInfoData: {},
        editor: null,

};

/**
 * 清除数据
 */
ProductCharacterInfoDlg.clearData = function () {
    this.productCharacterInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProductCharacterInfoDlg.set = function (key, val) {
    this.productCharacterInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProductCharacterInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ProductCharacterInfoDlg.close = function () {
    parent.layer.close(window.parent.ProductCharacter.layerIndex);
}

/**
 * 收集数据
 */
ProductCharacterInfoDlg.collectData = function () {
    this.productCharacterInfoData['content'] =   ProductCharacterInfoDlg.editor.txt.html() ;
    this
        .set('id')
        .set('productId')
        .set('title')
        .set('ico')
        .set('type')
        .set('sort')
        .set('baseId');
}

/**
 * 提交添加
 */
ProductCharacterInfoDlg.addSubmit = function () {
    this.clearData();
    this.collectData();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/productCharacter/add", function (data) {
        Feng.success("添加成功!");
        window.parent.ProductCharacter.table.refresh();
        ProductCharacterInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.productCharacterInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ProductCharacterInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/productCharacter/update", function (data) {
        Feng.success("修改成功!");
        window.parent.ProductCharacter.table.refresh();
        ProductCharacterInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.productCharacterInfoData);
    ajax.start();
}

$(function () {
    /**
     * 动态获取产品
     */
    var ajax = new $ax(Feng.ctxPath + "/product/getProductList", function (data) {
        for (var i = 0; i < data.length; i++) {
            var jsonObj = data[i];
            var optionstring = "";
            console.log(jsonObj)
            $("#productId").append('<option value="' + jsonObj.id + '">' + jsonObj.name + '</option>');
        }

    }, function (data) {

    });
    ajax.start();

    // 初始化头像上传
    var fileServerPathUp = new $WebUpload("ico");
    fileServerPathUp.setUploadBarId("progressBar");
    fileServerPathUp.init();



    //初始化编辑器
    var E = window.wangEditor;
    var editor = new E('#editor');
    editor.create();
    editor.txt.html($("#contentVal").val());
    ProductCharacterInfoDlg.editor = editor;

});
