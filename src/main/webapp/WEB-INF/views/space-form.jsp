<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">

    <!-- (Giữ nếu bạn vẫn muốn vài tùy biến riêng; có thể bỏ nếu “full Bootstrap”) -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/create-space.css">

    <!-- Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <title>Thêm mặt bằng</title>
</head>
<body>
<div class="container mt-4">
    <h1 class="mb-4">Thêm mặt bằng</h1>

    <form method="post" action="${pageContext.request.contextPath}/spaces/create" novalidate>
        <!-- Mã mặt bằng -->
        <div class="mb-3">
            <label class="form-label">Mã mặt bằng (định dạng XXX-XX-XX)</label>
            <input type="text"
                   name="code"
                   value="${old.code}"
                   pattern="^[A-Z0-9]{3}-[A-Z0-9]{2}-[A-Z0-9]{2}$"
                   required
                   class="form-control ${not empty errors.code ? 'is-invalid' : ''}">
            <div class="invalid-feedback">
                <c:out value="${errors.code}"/>
            </div>
            <div class="form-text">Chỉ chữ in hoa A–Z và số 0–9.</div>
        </div>

        <!-- Trạng thái -->
        <div class="mb-3">
            <label class="form-label">Trạng thái</label>
            <select name="status"
                    class="form-select ${not empty errors.status ? 'is-invalid' : ''}" required>
                <option value="">-- Chọn trạng thái --</option>
                <option value="Trống"   ${old.status=='Trống'   ? 'selected' : ''}>Trống</option>
                <option value="Hạ tầng" ${old.status=='Hạ tầng' ? 'selected' : ''}>Hạ tầng</option>
                <option value="Đầy đủ"  ${old.status=='Đầy đủ'  ? 'selected' : ''}>Đầy đủ</option>
            </select>
            <div class="invalid-feedback">
                <c:out value="${errors.status}"/>
            </div>
        </div>

        <div class="row">
            <!-- Diện tích -->
            <div class="col-md-6 mb-3">
                <label class="form-label">Diện tích (m²) &gt; 20</label>
                <input type="number"
                       name="area"
                       value="${old.area}"
                       step="0.01"
                       min="20.01"
                       required
                       class="form-control ${not empty errors.area ? 'is-invalid' : ''}">
                <div class="invalid-feedback">
                    <c:out value="${errors.area}"/>
                </div>
            </div>

            <!-- Tầng -->
            <div class="col-md-6 mb-3">
                <label class="form-label">Tầng (1–15)</label>
                <input type="number"
                       name="floor"
                       value="${old.floor}"
                       min="1" max="15"
                       required
                       class="form-control ${not empty errors.floor ? 'is-invalid' : ''}">
                <div class="invalid-feedback">
                    <c:out value="${errors.floor}"/>
                </div>
            </div>
        </div>

        <!-- Loại văn phòng -->
        <div class="mb-3">
            <label class="form-label">Loại văn phòng</label>
            <select name="type"
                    class="form-select ${not empty errors.type ? 'is-invalid' : ''}" required>
                <option value="">-- Chọn loại --</option>
                <option value="Văn phòng chia sẻ"  ${old.type=='Văn phòng chia sẻ' ? 'selected' : ''}>Văn phòng chia sẻ</option>
                <option value="Văn phòng trọn gói" ${old.type=='Văn phòng trọn gói' ? 'selected' : ''}>Văn phòng trọn gói</option>
            </select>
            <div class="invalid-feedback">
                <c:out value="${errors.type}"/>
            </div>
        </div>

        <!-- Giá -->
        <div class="mb-3">
            <label class="form-label">Giá (VND) &gt; 1.000.000</label>
            <input type="number"
                   name="price"
                   value="${old.price}"
                   min="1000001"
                   required
                   class="form-control ${not empty errors.price ? 'is-invalid' : ''}">
            <div class="invalid-feedback">
                <c:out value="${errors.price}"/>
            </div>
        </div>

        <div class="row">
            <!-- Ngày bắt đầu -->
            <div class="col-md-6 mb-3">
                <label class="form-label">Ngày bắt đầu (dd/MM/yyyy)</label>
                <input type="text"
                       name="startDate"
                       value="${old.startDate}"
                       placeholder="dd/MM/yyyy"
                       required
                       class="form-control ${not empty errors.startDate ? 'is-invalid' : ''}">
                <div class="invalid-feedback">
                    <c:out value="${errors.startDate}"/>
                </div>
            </div>

            <!-- Ngày kết thúc -->
            <div class="col-md-6 mb-3">
                <label class="form-label">Ngày kết thúc (dd/MM/yyyy)</label>
                <input type="text"
                       name="endDate"
                       value="${old.endDate}"
                       placeholder="dd/MM/yyyy"
                       required
                       class="form-control ${not empty errors.endDate ? 'is-invalid' : ''}">
                <div class="invalid-feedback">
                    <c:out value="${errors.endDate}"/>
                </div>
                <div class="form-text">Yêu cầu ≥ 6 tháng sau ngày bắt đầu.</div>
            </div>
        </div>

        <div class="mt-3">
            <button type="submit" class="btn btn-primary">Lưu</button>
            <a href="${pageContext.request.contextPath}/spaces" class="btn btn-secondary">Hủy</a>
        </div>
    </form>
</div>

<!-- Bootstrap JS (modal/validation, v.v.) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
