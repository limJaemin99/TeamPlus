// 비동기 방식으로 프로젝트 클릭 시 해당 프로젝트의 대략적인 내용을 출력
document.querySelector("#detaildiv").addEventListener('click',e=> {
    console.log("click");
    //#pagination 안에 있는 a 태그에 클릭 이벤트를 처리하도록 합니다.
    e.preventDefault()         //a 태그 등 pagination 안에 있는 기본 클릭 동작을 중지
    e.stopPropagation()         //클릭 이벤트는 자식 또는 부모요소에 전달되는데 그것을 중지
    const target = e.target
    if (target.tagName !== 'A') {
        return
    }         //클릭한 요소가 a 태그가 아니면 종료

    const projectNo = target.getAttribute("data-num")

    const showDetail = function (){
        const xhr = new XMLHttpRequest()            //비동기 통신 객체 생성
        xhr.open('GET','detail/'+projectNo)
        xhr.send()
        xhr.onload=function onload (){
            if(xhr.status===200||xhr.response===201){
                const res=JSON.parse(xhr.response)
                console.log(res);
                document.querySelector('#prj_title').innerHTML=res.prj.title;
                document.querySelector('#prj_description').innerHTML=res.prj.description;
                document.querySelector('#prj_startDate').innerHTML=res.prj.startDate;
                document.querySelector('#prj_dueDate').innerHTML=res.prj.dueDate;
                const status = res.prj.status
                let statusValue;
                if(status == 0){
                    document.querySelector('#prj_status').className = 'badge rounded-pill bg-info fs-12'
                    statusValue='진행전';
                }else if(status == 1){
                    document.querySelector('#prj_status').className = 'badge rounded-pill bg-success fs-12'
                    statusValue='완료';
                }else{
                    document.querySelector('#prj_status').className = 'badge rounded-pill bg-warning fs-12'
                    statusValue='진행중';
                }
                document.querySelector('#prj_status').innerHTML=statusValue;

                // 해당 프로젝트에 참여하는 사용자들(팀원)을 출력
                document.querySelector('#members').innerHTML='';
                console.log(res.users);
                res.users.forEach(user => {
                    const $tr = document.createElement("tr");
                    const $tmp = `<td class="fw-medium">
                                        <img class="rounded-circle header-profile-user" src="${user.profileURL}" alt="Header Avatar" style="width: 50px;height: 50px;">
                                        <span>${user.nickName}</span>
                                     </td>`;
                    $tr.innerHTML=$tmp
                    document.querySelector('#members').appendChild($tr);
                });

                // 예정 종료일이 오늘 이후인 할일들을 출력
                document.querySelector('#todolist').innerHTML='';
                res.todos.forEach(todo => {
                    const $tr = document.createElement("tr");
                    const $tmp = `<td class="fw-medium">${todo.title}</td>
                                     <td class="fw-medium">${todo.dueDate}</td>
                                     <td class="fw-medium">
                                        <div id="todo_status">
                                        ${todo.status==0?'진행전':todo.status==1?'완료':'진행중'}
                                        </div>
                                      </td>`;
                    $tr.innerHTML=$tmp
                    document.querySelector('#todolist').appendChild($tr);



                });
                const $tr2 = document.createElement("tr");
                $tr2.innerHTML = `<td class="text-center" colspan="3">.<br>.<br>.</td>`;
                document.querySelector('#todolist').appendChild($tr2);

                document.querySelector('#homebtn').href="home?projectNo="+projectNo;
                document.querySelector('#overviewbtn').href="overview?projectNo="+projectNo;

            }else{
                console.log(xhr.status)
                console.log(xhr.response)
            }
        }// end onload
    }// end showDetail

    showDetail();
});


// 리스트에서 프로젝트 선택 시 동작_프로젝트

