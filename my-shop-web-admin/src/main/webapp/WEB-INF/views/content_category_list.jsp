<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sys" tagdir="/WEB-INF/tags/sys" %>
<!DOCTYPE html>
<html>
<head>
    <title>我的商城 | 内容分类</title>
    <jsp:include page="../includes/header.jsp"/>
    <link rel="stylesheet" href="/static/assets/plugins/treeTable/themes/vsStyle/treeTable.min.css">
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
                内容管理
                <small></small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li class="active">内容管理</li>
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

                    <div class="box">
                        <div class="box-header">
                            <h3 class="box-title">分类列表</h3>
                            <div class="row">
                                <div class="col-xs-12">
                                    <a style="margin-left: 10px;margin-top: 10px;" href="/content/category/form" type="button"
                                       class="btn btn-sm btn-default"><i class="fa fa-fw fa-plus"></i>新增</a>
                                    </button>
                                    <a style="display: none; margin-left: 10px;margin-top: 10px;" href="#" type="button"
                                       class="btn btn-sm btn-default"><i class="fa fa-fw fa-download"></i>导入</a>
                                    <a style="display: none; margin-left: 10px;margin-top: 10px;" href="#" type="button"
                                       class="btn btn-sm btn-default"><i class="fa fa-fw fa-upload"></i>导出</a>
                                </div>
                            </div>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body table-responsive no-padding">
                            <table id="treeTable" class="table table-hover">
                                <thead>
                                <tr>
                                    <td>ID</td>
                                    <td>名称</td>
                                    <td>排序</td>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${tbContentCategories}" var="tbContentCategory">
                                    <tr id="${tbContentCategory.id}" pId="${tbContentCategory.parent.id}">
                                        <td>${tbContentCategory.id}</td>
                                        <td>${tbContentCategory.name}</td>
                                        <td>${tbContentCategory.sortOrder}</td>
                                        <td>
                                            <a href="/content/category/form?id=${tbContentCategory.id}" type="button" class="btn btn-sm btn-primary"><i class="fa fa-edit"></i> 编辑</a>
                                            <button type="button" class="btn btn-sm btn-danger"><i class="fa fa-trash-o"></i> 删除</button>
                                            <a href="/content/category/form?parent.id=${tbContentCategory.id}&parent.name=${tbContentCategory.name}" type="button" class="btn btn-sm btn-default"><i class="fa fa-plus"></i> 新增下级菜单</a>
                                        </td>
                                    </tr>
                                </c:forEach>
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
<!-- treeTable -->
<script src="/static/assets/plugins/treeTable/jquery.treeTable.min.js"></script>
<script type="text/javascript">
    $(function () {
        $('#treeTable').treeTable({
            column: 1,
            expandLevel: 2
        });
    });
</script>
</body>
</html>