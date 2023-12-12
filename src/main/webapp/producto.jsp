<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!DOCTYPE html>

<html>
<head>
    <meta charset="ISO-8859-1">
    <meta charset="UTF-8">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.0.18/dist/sweetalert2.min.css">

    <title>CRUD DE PRODUCTOS</title>
</head>

<body>

<jsp:include page="shared/navbar.jsp"></jsp:include>

<div class="container">

    <h1>GESTION DE PRODUCTOS</h1>
    <div class="form-check form-switch">
        <input class="form-check-input" type="checkbox" role="switch" id="toggleSwitch">
        <label class="form-check-label" for="toggleSwitch">Mostrar Inactivos</label>
    </div>


    <!-- LISTADO DE PRODUCTOS -->
    <div class="card" id="divResultado">
        <div class="card-header">Resultado</div>
        <div class="card-body">
            <table class="table">
                <thead>
                <tr>
                    <th>NOMBRE</th>
                    <th>CÓDIGO</th>
                    <th>CANTIDAD</th>
                    <th>PRECIO</th>
                    <th>CATEGORIA</th>
                    <th>ESTADO</th>
                    <th>ACCIONES</th>
                </tr>
                </thead>
                <tbody id="detalleTabla">
                </tbody>
            </table>
        </div>
    </div>

    <!-- BOTÓN NUEVO -->
    <button type="button" class="btn btn-success" id="controlBtnNuevo" >Nuevo Producto</button>

    <!-- MODAL DE EDICIÓN -->
    <div class="modal fade" id="modalEdicion" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">NUEVO PRODUCTO</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="formProducto" >
                        <input type="hidden" id="accion" name="accion">
                        <div class="row mb-3">
                            <label for="frmId" class="col-sm-2 col-form-label">ID</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="frmId" name="product_id" readonly="readonly" value="0">

                            </div>
                        </div>
                        <div class="row mb-3">
                            <label for="frmNombre" class="col-sm-2 col-form-label">Nombre</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="frmNombre" name="nombre">
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label for="frmCodigo" class="col-sm-2 col-form-label">Código</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="frmCodigo" name="codigo">
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label for="frmCantidad" class="col-sm-2 col-form-label">Cantidad</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="frmCantidad" name="cantidad">
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label for="frmPrecio" class="col-sm-2 col-form-label">Precio</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="frmPrecio" name="precio">
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label for="frmCategoria" class="col-sm-2 col-form-label">Categoría</label>
                            <div class="col-sm-10">
                                <select class="form-control" id="frmCategoria" name="categoria_id">
                                    <!-- Las opciones se llenarán dinámicamente al consultar las categorías -->
                                </select>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label for="frmEstado" class="col-sm-2 col-form-label">Estado</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" disabled="disabled" id="frmEstado" name="active" value="A">
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    <button type="button" class="btn btn-primary" id="btnProcesar" onclick="fnBtnProcesar()">Procesar</button>
                </div>
            </div>
        </div>
    </div>


</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>

<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.0.18/dist/sweetalert2.all.min.js"></script>

<script type="text/javascript" src="js/producto.js"></script>

</body>
</html>
