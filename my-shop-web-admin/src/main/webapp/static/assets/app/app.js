var App = function () {

    // iCheck
    var _masterCheckbox = null;
    var _checkbox = null;

    //用于存放数组
    var _idArray;

    // 默认的 Dropzone 参数
    var defaultDropzoneOpts = {
        url: "",
        paramName: "dropFile",
        maxFiles: 1, // 一次性上传的文件数量上限
        maxFilesize: 2, // 文件大小，单位：MB
        acceptedFiles: ".jpg,.gif,.png,.jpeg", // 上传的类型
        addRemoveLinks: true,
        parallelUploads: 1, // 一次上传的文件数量
        dictDefaultMessage: '拖动文件至此或者点击上传',
        dictMaxFilesExceeded: "您最多只能上传 1 个文件！",
        dictResponseError: '文件上传失败!',
        dictInvalidFileType: "文件类型只能是*.jpg,*.gif,*.png,*.jpeg",
        dictFallbackMessage: "浏览器不受支持",
        dictFileTooBig: "文件过大上传文件最大支持",
        dictRemoveLinks: "删除",
        dictCancelUpload: "取消"
    };

    /**
     * 初始化
     */
    var handlerInitCheckbox = function () {
        // 激活 iCheck
        $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
            checkboxClass: 'icheckbox_minimal-blue',
            radioClass: 'iradio_minimal-blue'
        });

        // 获取控制端 Checkbox
        _masterCheckbox = $('input[type="checkbox"].minimal.icheck_master');
        // 获取全部 Checkbox 集合
        _checkbox = $('input[type="checkbox"].minimal');

    };

    /**
     * Checkbox 全选功能
     */
    var handlerAllCheckbox = function () {
        _masterCheckbox.on('ifClicked', function (e) {
            // 当前状态已选中，点击后应取消选择
            if (e.target.checked) {
                _checkbox.iCheck("uncheck");
            }

            // 当前状态未选中，点击后应全选
            else {
                _checkbox.iCheck("check");
            }
        });

    };

    /**
     * 批量删除
     */
    var handlerDeleteMulti = function (url,uid) {
        _idArray = new Array();

        // 将选中 ID 放入数组中
        _checkbox.each(function () {
            var _id = $(this).attr('id');
            if (_id != null && _id != "undefine" && $(this).is(':checked')) {
                _idArray.push(_id);
            }
        });

        if(uid!='') {
            _idArray.push(uid);
        }

        if (_idArray.length === 0) {
            $('#modal-message').html('您还没有选择数据项，请选择至少选择一条数据项！');
        } else {
            $('#modal-message').html('您确定删除数据项吗?');
        }

        $('#modal-default').modal('show');

        $('#btnModalOK').bind('click', function () {
            del()
        })

        function del() {

            $('#modal-default').modal('hide');
            $('#btnModalOK').unbind('click');

            setTimeout(function () {
                $.ajax({
                    url: url,
                    type: "POST",
                    data: {"ids": _idArray.toString()},
                    dataType: "JSON",
                    success: function (data) {
                        // 请求成功后，无论是成功或是失败都需要弹出模态框进行提示，所以先解绑原来都click事件
                        $("#btnModalOK").unbind("click");

                        // 请求成功
                        if (data.status === 200) {
                            // 刷新页面
                            $('#btnModalOK').click(function () {
                                window.location.reload();
                            });
                        }

                        // 请求失败
                        else {
                            // 确定按钮的事件改为隐藏模态框
                            $('#btnModalOK').bind('click', function () {
                                $('#modal-default').modal('hide');
                            });
                        }

                        // 无论如何，必须调用
                        $('#modal-message').html(data.message);
                        // 不是立即显示，跑完setTimeout才显示
                        $('#modal-default').modal('show');
                    }
                });
            }, 500);

        }

    };

    /**
     * dataTable分页
     * @param url
     * @param columns
     */
    var handlerInitDataTables = function (url, columns) {
        var _dataTable = $('#dataTable').DataTable({
            // 是否开启本地分页
            "paging": true,
            // 是否开启本地排序
            "ordering": false,
            // 是否显示左下角信息
            "info": true,
            // 是否允许用户改变表格每页显示的记录数
            "lengthChange": false,
            // 是否显示处理状态(排序的时候，数据很多耗费时间长的话，也会显示这个)
            "processing": true,
            // 是否允许 DataTables 开启本地搜索
            "searching": false,
            //开启服务器模式
            serverSide: true,
            // 控制 DataTables 的延迟渲染，可以提高初始化的速度
            "deferRender": true,
            // 增加或修改通过 Ajax 提交到服务端的请求数据
            "ajax": {
                "url": url,
            },
            // 分页按钮显示选项
            "pagingType": "full_numbers",
            // 设置列的数据源
            "columns": columns,
            // 国际化
            "language": {
                "sProcessing": "处理中...",
                "sLengthMenu": "显示 _MENU_ 项结果",
                "sZeroRecords": "没有匹配结果",
                "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
                "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                "sInfoPostFix": "",
                "sSearch": "搜索:",
                "sUrl": "",
                "sEmptyTable": "表中数据为空",
                "sLoadingRecords": "载入中...",
                "sInfoThousands": ",",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "上页",
                    "sNext": "下页",
                    "sLast": "末页"
                },
                "oAria": {
                    "sSortAscending": ": 以升序排列此列",
                    "sSortDescending": ": 以降序排列此列"
                }
            },
            // 表格重绘的回调函数
            "drawCallback": function (settings) {
                handlerInitCheckbox();
                handlerAllCheckbox();
            },
        });
        return _dataTable;
    };

    /**
     * 初始化 ZTree
     * @param url
     * @param autoParam
     * @param callback
     */
    var handlerInitZTree = function (url,autoParam,callback) {
        var setting = {
            view: {
                selectedMulti: false
            },
            async: {
                enable: true,
                url: url,
                autoParam: autoParam,
            }
        };

        $.fn.zTree.init($("#myTree"), setting);

        $("#btnModalOK").bind("click",function () {
            var zTree = $.fn.zTree.getZTreeObj("myTree");
            var nodes = zTree.getSelectedNodes();

            // 未选择
            if(nodes.length == 0){
                alert("请先选择一个节点");
            }

            // 已选择
            else{
                callback(nodes);
            }

        });
    };

    /**
     * 初始化 Dropzone
     * @param opts
     */
    var handlerInitDropzone = function (opts) {
        // 关闭 Dropzone 的自动发现功能
        Dropzone.autoDiscover = false;
        // 继承
        $.extend(defaultDropzoneOpts, opts);
        new Dropzone(defaultDropzoneOpts.id, defaultDropzoneOpts);
    };

    var handlerInitWangEditor = function () {
        var E = window.wangEditor;
        var editor = new E('#editor');
        editor.customConfig.uploadImgServer = '/upload';
        editor.customConfig.uploadFileName = 'editFile';
        editor.create();
        $("#btnSubmit").bind("click",function () {
            var contentHtml = editor.txt.html();
            $("#content").val(contentHtml);
        })
    };

    /**
     * 查看详情
     * @param url
     */
    var handlerShowDetail = function (url) {
        // 通过 Ajax 请求 html 方式将 jsp 装载进模态框中
        $.ajax({
            url: url,
            type: "get",
            datType: "html",
            success: function (data) {
                $("#modal-detail-body").html(data);
                $("#modal-detail").modal("show");
            }
        });
    };

    return {
        /**
         * 初始化
         */
        init: function () {
            handlerInitCheckbox();
            handlerAllCheckbox();
        },

        /**
         * 批量删除
         * @param url
         */
        deleteMulti: function (url,uid) {
            handlerDeleteMulti(url,uid);
        },

        /**
         * 初始化 DataTables
         * @param url
         * @param columns
         */
        initDataTables: function (url, columns) {
            return handlerInitDataTables(url, columns);
        },

        /**
         * 初始化 ZTree
         * @param url
         * @param autoParam
         * @param callback
         */
        initZTree: function (url,autoParam,callback) {
            handlerInitZTree(url,autoParam,callback);
        },

        /**
         * 初始化 Dropzone
         * @param opts
         */
        initDropzone: function(opts) {
            handlerInitDropzone(opts);
        },

        initWangEditor: function(){
            handlerInitWangEditor();
        } ,

        /**
         * 显示详情
         * @param url
         */
        showDetail: function (url) {
            handlerShowDetail(url);
        }
    };
}();

$(document).ready(function () {
    App.init();
})

