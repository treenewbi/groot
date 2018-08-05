$(function () {
    $('#fileUpload').click(function () {
        $('#uploadDialog').dialog({
            height: 240,
            width: 300,
            buttons: {
                'form提交': function () {
                    $('#uploadForm').ajaxSubmit({
                        success: function () {
                            alert("上传成功");
                        },
                        error: function () {
                            alert("上传失败");
                        },
                        url: 'http://localhost:8080/user/upload',
                        type: 'post',
                        crossDomain: true,
                        dataType: 'json'
                    });
                },
                'ajax提交': function () {
                    var files = $('#userFile').prop('files');
                    var data = new FormData();
                    data.append("file", files[0]);
                    $.ajax({
                        url: 'http://localhost:8080/user/upload',
                        type: 'POST',
                        data: data,
                        cache: false,
                        processData: false,
                        contentType: false,
                        success: function () {
                            alert(111);
                        },
                        error: function (data) {
                            alert(data.data);
                        }
                    });
                },
                '取消': function () {
                    $('#uploadDialog').dialog('close');
                    $('#uploadDialog').dialog('open');
                }
            }
        });
    });


});

