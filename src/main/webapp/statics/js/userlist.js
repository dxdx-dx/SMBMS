var userObj;

//用户管理页面上点击删除按钮弹出删除框(userlist.jsp)
function deleteUser(obj) {
    $.ajax({
        type: "GET",
        url: path + "/user/deluser",
        data: {uid: obj.attr("userid")},
        dataType: "json",
        success: function (data) {
            if (data.delResult == "true") {//删除成功：移除删除行
                cancleBtn();
                //  changeDLGContent("删除用户【" + obj.attr("username") + "】成功");
                obj.parents("tr").remove();
            } else if (data.delResult == "false") {//删除失败
                //alert("对不起，删除用户【"+obj.attr("username")+"】失败");
                changeDLGContent("对不起，删除用户【" + obj.attr("username") + "】失败");
            } else if (data.delResult == "notexist") {
                //alert("对不起，用户【"+obj.attr("username")+"】不存在");
                changeDLGContent("对不起，用户【" + obj.attr("username") + "】不存在");
            }
        },
        error: function (data) {
            //alert("对不起，删除失败");
            changeDLGContent("对不起，删除失败");
        }
    });
}

function openYesOrNoDLG() {
    $('.zhezhao').css('display', 'block');
    $('#removeUse').fadeIn();
}

function cancleBtn() {
    $('.zhezhao').css('display', 'none');
    $('#removeUse').fadeOut();
}

function changeDLGContent(contentStr) {
    var p = $(".removeMain").find("p");
    p.html(contentStr);
}

$(function () {
    //通过jquery的class选择器（数组）
    //对每个class为viewUser的元素进行动作绑定（click）
    /**
     * bind、live、delegate
     * on
     */
    $(".viewUser").on("click", function () {
        //将被绑定的元素（a）转换成jquery对象，可以使用jquery方法
        var obj = $(this);
        //window.location.href=path+"/user/view/" + obj.attr("userid");
        $.ajax({
            url: path + "/user/view.json",
            type: "GET",
            data: {id: obj.attr("userid")},
            dataType: "json",
            success: function (data) {
                if (data == "nodata") {
                    alert("错误")
                } else {
                    $("#v_userCode").val(data.userCode);
                    $("#v_userName").val(data.userName);
                    if (data.gender == 1) {
                        $("#v_gender").val("女");
                    } else {
                        $("#v_gender").val("男");
                    }
                    $("#v_birthday").val(data.birthday);
                    $("#v_phone").val(data.phone);
                    $("#v_userRoleName").val(data.userRoleName);
                    $("#v_address").val(data.address);
                    $("#v_creationDate").val(data.creationDate);
                }
            },
            error: function () {
                alert("ajax错误")
            }
        })
    });

    $(".modifyUser").on("click", function () {
        var obj = $(this);
        window.location.href = path + "/user/usermodify?uid=" + obj.attr("userid");
    });

    $('#no').click(function () {
        cancleBtn();
    });

    $('#yes').click(function () {
        deleteUser(userObj);
    });

    $(".deleteUser").on("click", function () {
        userObj = $(this);
        changeDLGContent("你确定要删除用户【" + userObj.attr("username") + "】吗？");
        openYesOrNoDLG();
    });

    // $(".deleteUser").on("click", function () {
    //     var obj = $(this);
    //     if (confirm("你确定要删除用户【" + obj.attr("username") + "】吗？")) {
    //         $.ajax({
    //             type: "GET",
    //             url: path + "/user/deluser",
    //             data: {uid: obj.attr("userid")},
    //             dataType: "json",
    //             success: function (data) {
    //                 // alert(data.delResult)
    //                 if (data.delResult == "true") {//删除成功：移除删除行
    //                     alert("删除成功");
    //                     obj.parents("tr").remove();
    //                 } else if (data.delResult == "false") {//删除失败
    //                     alert("对不起，删除用户【" + obj.attr("username") + "】失败");
    //                 } else if (data.delResult == "notexist") {
    //                     alert("对不起，用户【" + obj.attr("username") + "】不存在");
    //                 }
    //             },
    //             error: function (data) {
    //                 alert("对不起，删除失败");
    //             }
    //         });
    //     }
    // });
});