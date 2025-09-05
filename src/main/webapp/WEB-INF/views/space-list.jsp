<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
  <title>Danh sách mặt bằng</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</head>

<body>
<div class="container mt-4">
  <div class="d-flex justify-content-between align-items-center mb-3">
    <h1>Danh sách mặt bằng</h1>
    <div>
      <!-- Nút mở modal tìm kiếm nâng cao -->
      <button type="button" class="btn btn-outline-primary me-2"
              data-bs-toggle="modal" data-bs-target="#advancedSearchModal">
        🔍 Tìm kiếm
      </button>
      <a href="${pageContext.request.contextPath}/spaces/create" class="btn btn-primary">+ Thêm mặt bằng</a>
    </div>
  </div>

  <!-- (Tuỳ chọn) Hiển thị bộ lọc hiện hành -->
  <c:if test="${not empty param.type or not empty param.floor or not empty param.minPrice or not empty param.maxPrice}">
    <div class="mb-3">
      <c:if test="${not empty param.type}">
        <span class="badge bg-secondary me-1">Loại: ${param.type}</span>
      </c:if>
      <c:if test="${not empty param.floor}">
        <span class="badge bg-secondary me-1">Tầng: ${param.floor}</span>
      </c:if>
      <c:if test="${not empty param.minPrice}">
        <span class="badge bg-secondary me-1">Giá từ: ${param.minPrice}</span>
      </c:if>
      <c:if test="${not empty param.maxPrice}">
        <span class="badge bg-secondary me-1">Đến: ${param.maxPrice}</span>
      </c:if>
      <a class="btn btn-sm btn-outline-danger ms-2"
         href="${pageContext.request.contextPath}/spaces">Xóa bộ lọc</a>
    </div>
  </c:if>

  <!-- Bảng danh sách -->
  <table class="table table-hover table-bordered align-middle">
    <thead class="table-dark">
    <tr>
      <th>Mã</th>
      <th>Trạng thái</th>
      <th>Diện tích</th>
      <th>Tầng</th>
      <th>Loại</th>
      <th>Giá</th>
      <th>Bắt đầu</th>
      <th>Kết thúc</th>
      <th>Hành động</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="s" items="${spaces}">
      <tr>
        <td>${s.code}</td>
        <td><span class="badge bg-info">${s.status}</span></td>
        <td>${s.area} m²</td>
        <td>${s.floor}</td>
        <td>${s.type}</td>
        <td><fmt:formatNumber value="${s.price}" type="currency" currencySymbol="₫"/></td>
        <td>${s.startDate}</td>
        <td>${s.endDate}</td>
        <td>
          <a href="${pageContext.request.contextPath}/spaces/delete?id=${s.code}"
             class="btn btn-sm btn-danger"
             onclick="return confirm('Xóa mặt bằng này?')">Xóa</a>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>

<!-- Modal Tìm kiếm nâng cao (chỉ 1 modal, ID khác để tránh trùng) -->
<div class="modal fade" id="advancedSearchModal" tabindex="-1"
     aria-labelledby="advancedSearchModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <form method="get" action="${pageContext.request.contextPath}/spaces">
        <div class="modal-header">
          <h5 class="modal-title" id="advancedSearchModalLabel">Tìm kiếm mặt bằng</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
        </div>

        <div class="modal-body">
          <!-- Loại mặt bằng -->
          <div class="mb-3">
            <label class="form-label">Loại mặt bằng</label>
            <select name="type" class="form-select">
              <option value="">-- Tất cả --</option>
              <option value="Văn phòng chia sẻ"  ${param.type=='Văn phòng chia sẻ' ? 'selected':''}>Văn phòng chia sẻ</option>
              <option value="Văn phòng trọn gói" ${param.type=='Văn phòng trọn gói' ? 'selected':''}>Văn phòng trọn gói</option>
            </select>
          </div>

          <!-- Tầng -->
          <div class="mb-3">
            <label class="form-label">Tầng</label>
            <input type="number" name="floor" min="1" max="15"
                   value="${param.floor}" class="form-control" placeholder="VD: 5">
          </div>

          <!-- Giá thuê -->
          <div class="row">
            <div class="col-md-6 mb-3">
              <label class="form-label">Giá từ (VND)</label>
              <input type="number" name="minPrice" min="0"
                     value="${param.minPrice}" class="form-control" placeholder="VD: 1000000">
            </div>
            <div class="col-md-6 mb-3">
              <label class="form-label">Đến (VND)</label>
              <input type="number" name="maxPrice" min="0"
                     value="${param.maxPrice}" class="form-control" placeholder="VD: 3000000">
            </div>
          </div>
          <div class="form-text">Có thể để trống 1–2 trường; điền cả 3 là tìm kết hợp.</div>
        </div>

        <div class="modal-footer">
          <a class="btn btn-outline-secondary"
             href="${pageContext.request.contextPath}/spaces">Xóa bộ lọc</a>
          <button type="submit" class="btn btn-primary">Tìm</button>
        </div>
      </form>
    </div>
  </div>
</div>

</body>
</html>
