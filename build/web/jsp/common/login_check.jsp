<c:if test="${myAccountId == null || myAccountId == ''}">
  <c:redirect url="/login.do"></c:redirect>
</c:if>
