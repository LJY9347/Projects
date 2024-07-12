<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
    rel="stylesheet"
    integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
    crossorigin="anonymous">

<link rel="stylesheet" href="../css/mainPost_create.css" />

<title>글 작성｜GOLFRO</title>

</head>

<body>
    <header>
        <c:set var="pageTitle" value="새글 작성하기" />
        <%@ include file="../fragments/header.jspf"%>
    </header>

    <main>
        <div class="container" id="create">
            <div class="card border-0">
                <div class="card-header border-0 form-control">
                    <h3>새 글 작성하기</h3>
                </div>

                <div class="card-body form-control border-0">
                    <c:url var="mainPostCreate" value="/mainPost/create" />
                    <form method="post" action="${mainPostCreate}"
                        enctype="multipart/form-data">
                        <!-- 카테고리 선택 + 제목 영역 -->
                        <div class="mt-2 row">
                            <span>클럽 종류</span>
                            <div class="col-12 mt-2 mb-4">
                                <select id="clubType"
                                    class="form-control" name="clubType">
                                    <c:forEach items="${clubs}" var="c">
                                        <option value="${c.id}">${c.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <span>제목</span>
                            <div class="col-12 mt-2 mb-4">
                                <input id="title" class="form-control"
                                    type="text" name="title"
                                    placeholder="제목을 입력하세요." />
                            </div>
                            <div class="col-2">
                                <input id="author"
                                    class="form-control d-none"
                                    type="text" name="author"
                                    placeholder="작성자 코드값" required
                                    value="${signedInUser}" />
                            </div>
                        </div>
                        <!-- 내용 본문 영역 -->
                        <div class="container-fluid px-0">
                            <span>선택사항</span>
                            <div class="mt-2 mb-4 row g-1">
                                <div class="col">
                                    <input id="height"
                                        class="form-control"
                                        name="height" type="number"
                                        placeholder="키 입력" />
                                </div>
                                <div class="col">
                                    <input id="career"
                                        class="form-control"
                                        name="career" type="number"
                                        placeholder="구력 입력" />
                                </div>
                                <div class="col">
                                    <input id="handy"
                                        class="form-control"
                                        name="handy" type="number"
                                        placeholder="평균 핸디 입력" />
                                </div>
                                <div class="col">
                                    <input id="ironDistance"
                                        class="form-control"
                                        name="ironDistance"
                                        type="number"
                                        placeholder="7번 아이언 평균 비거리"
                                        maxlength="3" />
                                </div>
                                <div class="col">
                                    <input id="driverDistance"
                                        class="form-control"
                                        name="driverDistance"
                                        type="number"
                                        placeholder="드라이버 평균 비거리"
                                        maxlength="3" />
                                </div>
                            </div>
                        </div>
                        <div>
                            <textarea class="mt-2 mb-4 form-control"
                                name=content rows="15"
                                placeholder="동영상에 대한 설명과 코칭 받고 싶은 부분을 상세히 적어주세요."
                                required></textarea>
                        </div>

                        <!-- 영상 업로드 영역 -->
                        <div class="form-group mt-2">
                            <input id="media" type="file"
                                accept="video/*"
                                class="form-control-file" name="media"
                                required />
                        </div>
                        <!-- 하단 버튼 영역 -->
                        <div class="mt-2 d-flex justify-content-end">
                            <div>
                                <a href="/gaebokchi/mainPost/list" class="m-2 btn">취소하기</a>
                            </div>
                            <div>
                                <input class="m-2 btn" type="submit"
                                    value="작성완료" />
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </main>





    <script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>

    <script>
					document.addEventListener('DOMContentLoaded', function() {
						var heightInput = document.getElementById('height');

						heightInput.addEventListener('input', function() {
							if (this.value.length > 3) {
								this.value = this.value.slice(0, 3); // 최대 3자리까지만 허용
							}
						});
					});
				</script>
    <script>
					document.addEventListener('DOMContentLoaded', function() {
						var careerInput = document.getElementById('career');

						careerInput.addEventListener('input', function() {
							if (this.value.length > 3) {
								this.value = this.value.slice(0, 3); // 최대 3자리까지만 허용
							}
						});
					});
				</script>
    <script>
					document.addEventListener('DOMContentLoaded', function() {
						var handyInput = document.getElementById('handy');

						handyInput.addEventListener('input', function() {
							if (this.value.length > 4) {
								this.value = this.value.slice(0, 4); // 최대 4자리까지만 허용
							}
						});
					});
				</script>
    <script>
					document.addEventListener('DOMContentLoaded', function() {
						var ironDistanceInput = document
								.getElementById('ironDistance');

						ironDistanceInput.addEventListener('input', function() {
							if (this.value.length > 3) {
								this.value = this.value.slice(0, 3); // 최대 4자리까지만 허용
							}
						});
					});
				</script>
    <script>
					document.addEventListener('DOMContentLoaded', function() {
						var driverDistanceInput = document
								.getElementById('driverDistance');

						driverDistanceInput.addEventListener('input',
								function() {
									if (this.value.length > 3) {
										this.value = this.value.slice(0, 3); // 최대 4자리까지만 허용
									}
								});
					});
				</script>

</body>
</html>