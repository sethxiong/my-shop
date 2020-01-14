<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sys" tagdir="/WEB-INF/tags/sys" %>
<!DOCTYPE html>
<html>
<head>
    <title>我的商城 | 用户管理</title>
    <jsp:include page="../includes/header.jsp"/>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <!-- 顶部导航 -->
    <jsp:include page="../includes/nav.jsp"/>
    <!-- Left side column. contains the logo and sidebar -->
    <jsp:include page="../includes/menu.jsp"/>
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                用户管理
                <small></small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li class="active">用户管理</li>
            </ol>
        </section>
        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-xs-12">

                    <c:if test="${baseResult != null}">
                        <div class="alert alert-${baseResult.status == 200 ? "success" : "danger"} alert-dismissible">
                            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                                ${baseResult.message}
                        </div>
                    </c:if>


                    <!-- Horizontal Form -->
                    <div class="box box-info box-info-search" style="display: none;">
                        <div class="box-header with-border">
                            <h3 class="box-title">高级搜索</h3>
                        </div>
                        <!-- /.box-header -->


                        <!-- form start -->
                        <div class="box-body">
                            <div class="col-xs-12 col-sm-4">
                                <div class="form-group">
                                    <label for="username" class="col-sm-4 control-label">姓名</label>
                                    <div class="col-sm-8">
                                        <input id="username" class="form-control" placeholder="姓名">
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-4">
                                <div class="form-group">
                                    <label for="email" class="col-sm-4 control-label">邮箱</label>
                                    <div class="col-sm-8">
                                        <input id="email" class="form-control" placeholder="姓名">
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-4">
                                <div class="form-group">
                                    <label for="phone" class="col-sm-4 control-label">手机</label>
                                    <div class="col-sm-8">
                                        <input id="phone" class="form-control" placeholder="手机号">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- /.box-body -->
                        <div class="box-footer">
                            <button type="button" class="btn btn-info pull-right" onclick="searchUser();">搜索</button>
                        </div>
                        <!-- /.box-footer -->
                    </div>

                    <div class="box">
                        <div class="box-header">
                            <h3 class="box-title">用户列表</h3>
                            <div class="row">
                                <div class="col-xs-12">
                                    <a style="margin-left: 10px;margin-top: 10px;" href="/user/form" type="button"
                                       class="btn btn-sm btn-default"><i class="fa fa-fw fa-plus"></i>新增</a>
                                    <button onclick="App.deleteMulti('/user/delete','')"
                                            style="margin-left: 10px;margin-top: 10px;" type="button"
                                            class="btn btn-sm btn-default"><i class="fa fa-fw fa-trash-o"></i>删除
                                    </button>
                                    <a style="display: none; margin-left: 10px;margin-top: 10px;" href="#" type="button"
                                       class="btn btn-sm btn-default"><i class="fa fa-fw fa-download"></i>导入</a>
                                    <a style="display: none; margin-left: 10px;margin-top: 10px;" href="#" type="button"
                                       class="btn btn-sm btn-default"><i class="fa fa-fw fa-upload"></i>导出</a>
                                    <button onclick="$('.box-info-search').css('display')=='none'?$('.box-info-search').show('fast'):$('.box-info-search').hide('fast')"
                                            style="margin-left: 10px;margin-top: 10px;" type="button"
                                            class="btn btn-sm btn-default btn-info"><i class="fa fa-fw fa-search"></i>搜索
                                    </button>
                                </div>
                            </div>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body table-responsive no-padding">
                            <table id="dataTable" class="table table-hover">
                                <thead>
                                <tr>
                                    <th><input id="" type="checkbox" class="minimal icheck_master"></th>
                                    <th>ID</th>
                                    <th>用户名</th>
                                    <th>手机号</th>
                                    <th>邮箱</th>
                                    <th>更新时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
            </div>
        </section>
    </div>
    <jsp:include page="../includes/copyright.jsp"/>
</div>
<jsp:include page="../includes/footer.jsp"/>
<%--自定义模态框--%>
<sys:modal/>
<script type="text/javascript">
    var _dataTable

    $(function () {
        var columns = [
            {
                "data": function (row, type, val, meta) {
                    return '<input id="' + row.id + '" type="checkbox" class="minimal" />';
                }
            },
            {"data": "id"},
            {"data": "username"},
            {"data": "phone"},
            {"data": "email"},
            {
                "data": function (row, type, val, meta) {
                    return DateTime.format(row.updated, "yyyy-MM-dd HH:mm:ss")
                }
            },
            {
                "data": function (row, type, val, meta) {
                    var url = "/user/detail?id=" + row.id;
                    return '<button type="button" class="btn btn-sm btn-default" onclick="App.showDetail(\'' + url + '\');"> <i class="fa fa-search"></i> 查看</button>&nbsp;&nbsp;&nbsp;' +
                        '<a href="/user/form?id=' + row.id + '" type="button" class="btn btn-sm btn-primary"><i class="fa fa-edit"></i> 编辑</a>&nbsp;&nbsp;&nbsp;' +
                        '<a onclick="App.deleteMulti(\'/user/delete\',\'' + row.id + '\')" type="button" class="btn btn-sm btn-danger"><i class="fa fa-trash-o"></i> 删除</a>'
                }
            }
        ];
        _dataTable = App.initDataTables("/user/page", columns);
    });

    function searchUser() {
        var username = $("#username").val();
        var phone = $("#phone").val();
        var email = $("#email").val();

        var param = {
            "username": username,
            "phone": phone,
            "email": email,
        };

        _dataTable.settings()[0].ajax.data = param;
        _dataTable.ajax.reload();
    }
</script>
</body>
</html>