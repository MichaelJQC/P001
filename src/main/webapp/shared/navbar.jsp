<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Sistema de Ventas</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<!-- Barra de menú con Bootstrap 5 -->
<nav class="navbar navbar-expand-lg navbar-light" style="background: #2747ff;">
    <div class="container">
        <a class="navbar-brand text-white" href="#">Sistema de Ventas</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link text-white" href="producto.jsp">Productos</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white" href="person.jsp">Clientes</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white" href="#">Ventas</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white" href="#">Reportes</a>
                </li>
                <!-- Puedes agregar más elementos del menú aquí -->
            </ul>
        </div>
    </div>
</nav>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

</body>
</html>
