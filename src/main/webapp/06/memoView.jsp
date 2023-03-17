<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/custom.js"></script>
</head>
<body>
<h4>Restful 기반의 메모 관리</h4>
<form name="memoForm" action="${pageContext.request.contextPath}/memo" method="post">
   <input type="text" name="writer" placeholder="작성자">
   <input type="date" name="date" placeholder="작성일">
   <textarea name="content"></textarea>
   <input id="formButton" type="submit" value="등록">
</form>
<table class="table-bordered">
   <thead>
      <tr>
         <th>작성자</th>
         <th>작성일</th>
      </tr>
   </thead>
   <tbody id="listBody">
      
   </tbody>
</table>

<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="exampleModalLabel">Modal title</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
      
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript">
	let memoForm = $(document.memoForm).on("submit",function(){
		event.preventDefault();
	  /*this
		event.target
		$(this) */
		let url = this.action;
	  	let method = this.method;	  	
// 		let data = $(this).serialize(); // writer=작성자&date=작성일&content=내용 (QuertString)
		let data = $(this).serializeObject();// writer=작성자&date=작성일&content=내용 (QuertString)
		console.log(data);
		//  	let memoForm = this;
		$.ajax({
			url : url,
			method : method,
			contentType:"application/json;charset=UTF-8", // request content-type
			data : JSON.stringify(data),
			dataType : "json", // request Accept , response content-type
			success : function(resp) {
				makeListBody(resp.memoList);
				memoForm[0].reset();
			},
			error : function(jqXHR, status, error) {
				console.log(jqXHR);
				console.log(status);
				console.log(error);
			}
		});
		return false;
	});
//    EDD(Event Driven Development)
   $("#exampleModal").on("show.bs.modal",function(event){
//       this==event.target(모달그자체/이벤트그자체)
      let memo = $(event.relatedTarget).data("memo"); // modal을 오픈할 때 사용한 클릭 대상, tr
      console.log("memo : ",memo);
      $(this).find(".modal-body").html(memo.content);
   }).on("hidden.bs.modal", function(event){
      $(event.target).find("modal-body").empty();
   });
   
   let listBody = $("#listBody");
   let makeListBody = function(memoList){
      let trTags = [];
      if(makeListBody.length>0){
         //data-bs-toggle="modal" data-bs-target="#exampleModal"
         $.each(memoList, function(index, memo){ //인덱스와 한건의 메모를 값으로 받아온다
            let tr = $("<tr>").append(
               $("<td>").html(memo.writer), //메모라는 원형 데이터(매개변수에있는거)      
               $("<td>").html(this.date)      
            ).attr({
               "data-bs-toggle":"modal",
               "data-bs-target":"#exampleModal"
            }).data("memo",memo);
            trTags.push(tr);
         });
      } else {
         let tr = $("<tr>").html(
         $("<td>")
            .attr("colspan","2")
            .html("작성된 메모가 없습니다")
         );
      trTags.push(tr);
      }
      listBody.append(trTags);
   }
   
   $.ajax({
      url : "${pageContext.request.contextPath}/memo",
      method : "get", //post -새로운 메모를 작성하겠다, put->메모를 수정하겠다, 
      dataType : "json",
      success : function(resp) {
         //json데이터로 제대로 넘어올 시에 보여줘야하는 것
         console.log(resp.memoList);
         makeListBody(resp.memoList);
      },
      error : function(jqXHR, status, error) {
         console.log(jqXHR);
         console.log(status);
         console.log(error);
      }
   });
   

//    동기 통신인 form태그의 input[type='submit']을 가로챈다
   /* $("form").submit(function(){ // formButton의 타입을 button으로 바꾸고 클릭했을 때 가져오기
//    가로챌 데이터
      let writer = $("input[name='writer']"); // 뭔가 form에 자동적으로 있는데 이거 가져오기 좀 그럼...
      let date = $("input[name='date']"); // 가로챈다는 게 무슨 의미인지..
      let content = $("textArea[name='content']");
      //var form = $(this).serialize(); // 직렬화
      console.log(this);
      
      
//    가로챈 버튼에다 ajax를 넣고 데이터를 보낸다.
      $.ajax({
         url : "${pageContext.request.contextPath}/memo",
         method : "post",
         data : form,
         dataType : "json",
         success : function(resp) {
               makeListBody(resp.memoList);
               writer.val("");
               date.val("");
               content.val(""); 
         },
         error : function(jqXHR, status, error) {
            console.log(jqXHR);
            console.log(status);
            console.log(error);
         }
      });      
      return false;   
   }); */
   
</script>
<jsp:include page="/includee/postScript.jsp"/>
</body>
</html>