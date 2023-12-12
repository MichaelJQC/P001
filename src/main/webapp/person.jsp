<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Personas</title>
</head>
<body>

<jsp:include page="shared/navbar.jsp"></jsp:include>
<div id="divResultado"></div>

<button id="controlBtnNuevo">Nuevo</button>

<!-- Modal de Edición -->
<div class="modal fade" id="modalEdicion" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Título del Modal</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <!-- Aquí van los campos del formulario -->
                <form id="formulario">
                    <input type="hidden" id="accion" name="accion" value=""/>
                    <input type="hidden" id="frmPersonId" name="frmPersonId" value=""/>

                    <!-- Agrega los campos del formulario según tus necesidades -->
                    <label for="frmFirstName">Nombre:</label>
                    <input type="text" id="frmFirstName" name="frmFirstName" required/>

                    <!-- Agrega los demás campos aquí -->

                    <button type="button" id="btnProcesar">Procesar</button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Agrega aquí las referencias a las librerías necesarias (Bootstrap, SweetAlert2, etc.) -->

<!-- Tu código JavaScript (person.js) -->
<script type="text/javascript" src="js/person.js"></script>

</body>
</html>
